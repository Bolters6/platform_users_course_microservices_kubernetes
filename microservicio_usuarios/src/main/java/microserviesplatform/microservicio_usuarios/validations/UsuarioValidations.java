package microserviesplatform.microservicio_usuarios.validations;

import lombok.RequiredArgsConstructor;
import microserviesplatform.microservicio_usuarios.models.Usuario;
import microserviesplatform.microservicio_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class UsuarioValidations {

    private final UsuarioRepository usuarioRepository;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${rest.host}")
    private String host;

    private final Environment env;

    @HandleBeforeCreate
    public void usuarioBeforeCreate(@Valid Usuario usuario){
        System.out.println(env.getProperty("PROFILE") + ": " + env.getProperty("config.texto"));
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ya existente");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    }


    @HandleBeforeSave
    public void usuarioBeforeSave(@Valid Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    }

    @HandleAfterDelete
    public void usuarioAfterDelete(Usuario usuario){
        restTemplate.delete("http://" + host + "/cursousuario/deletebyusuarioid/" + usuario.getId());
    }

  /* private void usuarioValidation(BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining("\n")));
        }
    }*/

}
