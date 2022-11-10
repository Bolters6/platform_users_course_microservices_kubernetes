package microserviesplatform.microservicio_cursos.controllers;

import lombok.RequiredArgsConstructor;
import microserviesplatform.microservicio_cursos.exceptions.CursoException;
import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import microserviesplatform.microservicio_cursos.repositories.CursoRepository;
import microserviesplatform.microservicio_cursos.repositories.CursoUsuarioRepository;
import microserviesplatform.microservicio_cursos.utility.Error;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/cursousuario")
public class CursoUsuarioController {

    private final CursoRepository cursoRepository;
    private final CursoUsuarioRepository cursoUsuarioRepository;

    @DeleteMapping("/deletebyusuarioid/{id}")
    public void deleteCursoUsuarioByUsuarioId(@PathVariable Long id) throws CursoException {
        CursoUsuario cursoUsuario = cursoUsuarioRepository.findByUsuarioId(id).orElseThrow(() -> new CursoException(Error.CURSO_USUARIO_NOEXISTE));
        cursoUsuario.getCursos().forEach(curso -> cursoRepository.findById(curso.getId()).get().getCursoUsuarios().remove(cursoUsuario));
        cursoUsuarioRepository.deleteByUsuarioId(id);
    }
}
