package microserviesplatform.microservicio_cursos.controllers;

import lombok.RequiredArgsConstructor;
import microserviesplatform.microservicio_cursos.dto.UsuarioDTO;
import microserviesplatform.microservicio_cursos.exceptions.CursoException;
import microserviesplatform.microservicio_cursos.models.Curso;
import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import microserviesplatform.microservicio_cursos.repositories.CursoRepository;
import microserviesplatform.microservicio_cursos.repositories.CursoUsuarioRepository;
import microserviesplatform.microservicio_cursos.utility.Error;
import microserviesplatform.microservicio_cursos.utility.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curso")
@Transactional
public class CursoController {

    private final CursoRepository cursoRepository;
    private final CursoUsuarioRepository cursoUsuarioRepository;
    private final RestTemplate restTemplate;

    @Value("${rest.host}")
    private String host;
    private final Environment env;

    @PostMapping(path = "/create")
    public ResponseEntity<Curso> createCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult, @RequestHeader(value = "Authorization") String token) throws CursoException {

        cursoValidation(bindingResult);
        curso.setClase(PasswordGenerator.shuffleString(PasswordGenerator.generateRandomStringWithCodeAtEnd(curso.getNombre(), 4)));

        if(cursoRepository.existsByNombreAndClase(curso.getNombre(), curso.getClase())){
            throw new CursoException(Error.CURSO_EXISTENTE);
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(header);
        if(curso.getCursoUsuarios().size() > curso.getMaxUsuarios()){
            int cursosExtra = Math.round((float) (curso.getCursoUsuarios().size() - curso.getMaxUsuarios()) / curso.getMaxUsuarios());
            List<CursoUsuario> estudiantesExtra = curso.getCursoUsuarios().stream().skip(curso.getMaxUsuarios()).toList();
            for(int i = 0; i < cursosExtra; i++){
                List<CursoUsuario> estudiantesParaAñadir = new ArrayList<>();
                for(int j = i * 10; j < curso.getMaxUsuarios() && j < estudiantesExtra.size(); j++){
                    estudiantesParaAñadir.add(estudiantesExtra.get(j));
                }
                estudiantesParaAñadir.replaceAll(this::checkCursoUsuario);
                estudiantesParaAñadir.forEach(e -> {restTemplate.exchange("http://" + host + "/usuarios/" + e.getUsuarioId() ,HttpMethod.GET, entity, UsuarioDTO.class);});
                cursoRepository.save(new Curso().setMaxUsuarios(curso.getMaxUsuarios()).setCursoUsuarios(new HashSet<>(estudiantesParaAñadir)).setNombre(curso.getNombre()).setClase(PasswordGenerator.shuffleString(PasswordGenerator.generateRandomStringWithCodeAtEnd(curso.getNombre(), 4))));
            }
            estudiantesExtra.forEach(curso.getCursoUsuarios()::remove);
        }
        curso.getCursoUsuarios().stream().forEach(e -> {restTemplate.exchange("http://" + host + "/usuarios/" + e.getUsuarioId() ,HttpMethod.GET, entity, UsuarioDTO.class);});
        curso.setCursoUsuarios(curso.getCursoUsuarios().stream().map(this::checkCursoUsuario).collect(Collectors.toCollection(HashSet::new)));
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoRepository.save(curso));
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<Curso> saveCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult, @PathVariable Long id) throws CursoException {
        cursoValidation(bindingResult);
        Optional<Curso> cursoOptional = cursoRepository.findById(id);

        if(cursoOptional.isEmpty()){

            throw new CursoException(Error.CURSO_NO_EXISTENTE);
        }

        if(!cursoOptional.get().getNombre().equals(curso.getNombre())){
            curso.setClase(PasswordGenerator.shuffleString(PasswordGenerator.generateRandomStringWithCodeAtEnd(curso.getNombre(), 4)));
            if(cursoRepository.existsByNombreAndClase(curso.getNombre(), curso.getClase())){
                throw new CursoException(Error.CURSO_EXISTENTE);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoRepository.save(curso));
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) throws CursoException {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if(cursoOptional.isEmpty()) throw new CursoException(Error.CURSO_NO_EXISTENTE);
        List<CursoUsuario> usuariosDelCurso = new ArrayList<>(cursoOptional.get().getCursoUsuarios());
        cursoRepository.deleteById(id);
        usuariosDelCurso.stream().filter(u -> u.getCursos().size() == 1).map(CursoUsuario::getId).forEach(cursoUsuarioRepository::deleteById);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/crearusuario")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO, @RequestHeader(value = "Authorization") String token){
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", token);
        HttpEntity<UsuarioDTO> entity = new HttpEntity<>(usuarioDTO, header);
        System.out.println(env.getProperty("PROFILE") + ": " + env.getProperty("config.texto"));
        return restTemplate.exchange("http://" + host + "/usuarios/", HttpMethod.POST, entity, UsuarioDTO.class);
    }

    @PatchMapping(path = "/addusuarioacurso/{usuarioId}/{cursoId}")
    public ResponseEntity<?> addUsuarioACurso(@PathVariable Long usuarioId, @PathVariable Long cursoId, @RequestHeader(value = "Authorization") String token) throws CursoException {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if(cursoOptional.isEmpty()){
            throw new CursoException(Error.CURSO_NO_EXISTENTE);
        }
        if(cursoOptional.get().getCursoUsuarios().size() == cursoOptional.get().getMaxUsuarios()){
            throw new CursoException(Error.CURSO__LLENO);
        }
        if(cursoOptional.get().getCursoUsuarios().stream().anyMatch(cursoUsuario -> cursoUsuario.getUsuarioId() == usuarioId)){
            throw new CursoException(Error.USUARIO_EXISTENTE_EN_ESTE_CURSO);
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(header);
        restTemplate.exchange("http://" + host + "/usuarios/" +  usuarioId,HttpMethod.GET, entity, UsuarioDTO.class).getBody();
        cursoOptional.get().getCursoUsuarios().add(checkCursoUsuario(new CursoUsuario().setUsuarioId(usuarioId)));
        return ResponseEntity.ok().body("Usuario Añadido al Curso");
    }

    @DeleteMapping(path = "/deleteusuarioacurso/{usuarioId}/{cursoId}")
    public ResponseEntity<?> deleteUsuarioACurso(@PathVariable Long usuarioId, @PathVariable Long cursoId) throws CursoException {

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<CursoUsuario> cursoUsuarioOptional = cursoUsuarioRepository.findByUsuarioId(usuarioId);

        if(cursoOptional.isEmpty()){
            throw new CursoException(Error.CURSO_NO_EXISTENTE);
        }
        if(cursoUsuarioOptional.isEmpty()){
            throw new CursoException(Error.CURSO_USUARIO_NOEXISTE);
        }
        if(cursoOptional.get().getCursoUsuarios().stream().anyMatch(cursoUsuario -> cursoUsuario.getUsuarioId() == usuarioId)) {
            cursoOptional.get().getCursoUsuarios().remove(cursoUsuarioOptional.get());
            cursoUsuarioOptional.get().getCursos().remove(cursoOptional.get());
        }else {
            throw new CursoException(Error.USUARIO_NO_EXISTE);
        }
        if(cursoUsuarioOptional.get().getCursos().isEmpty()) cursoUsuarioRepository.deleteByUsuarioId(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/usuariosporcurso")
    public List<UsuarioDTO> usuariosPorCurso(@PathVariable  Long id, @RequestHeader(value = "Authorization") String token) throws CursoException {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(header);
        return cursoOptional.orElseThrow(() -> new CursoException(Error.CURSO_NO_EXISTENTE))
                .getCursoUsuarios()
                .stream()
                .map(cursoUsuario -> restTemplate.exchange("http://" + host + "/usuarios/" + cursoUsuario.getUsuarioId(), HttpMethod.GET, entity, UsuarioDTO.class).getBody())
                .collect(Collectors.toList());
    }
    private void cursoValidation(BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }
}
    private CursoUsuario checkCursoUsuario(CursoUsuario cursoUsuario){
        Optional<CursoUsuario> cursoUsuarioOptional = cursoUsuarioRepository.findByUsuarioId(cursoUsuario.getUsuarioId());
        return cursoUsuarioOptional.orElse(cursoUsuario);
    }
}
