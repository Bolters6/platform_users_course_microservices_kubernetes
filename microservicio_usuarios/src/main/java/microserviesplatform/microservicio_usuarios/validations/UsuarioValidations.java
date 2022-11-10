package microserviesplatform.microservicio_usuarios.validations;

import lombok.RequiredArgsConstructor;
import microserviesplatform.microservicio_usuarios.models.Usuario;
import microserviesplatform.microservicio_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    @Value("${rest.host}")
    private String host;



    @HandleBeforeCreate
    public void usuarioBeforeCreate(@Valid Usuario usuario){

        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ya existente");
        }
    }


    @HandleBeforeSave
    public void usuarioBeforeSave(@Valid Usuario usuario){

    }

    @HandleAfterDelete
    public void usuarioAfterDelete(Usuario usuario){
        restTemplate.delete("http://" + host + ":8081/cursousuario/deletebyusuarioid/" + usuario.getId());
    }

  /* private void usuarioValidation(BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining("\n")));
        }
    }*/

}
