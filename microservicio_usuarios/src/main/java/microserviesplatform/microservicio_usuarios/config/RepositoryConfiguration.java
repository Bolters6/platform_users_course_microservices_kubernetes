/*package microserviesplatform.microservicio_usuarios.config;

import microserviesplatform.microservicio_usuarios.repositories.UsuarioRepository;
import microserviesplatform.microservicio_usuarios.validations.UsuarioValidations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public UsuarioValidations usuarioValidationsConfig(UsuarioRepository usuarioRepository, RestTemplate restTemplate){
        return new UsuarioValidations(usuarioRepository, restTemplate);
    }
}*/
