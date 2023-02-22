package microserviesplatform.microservicio_usuarios.repositories;

import microserviesplatform.microservicio_usuarios.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "usuarios", collectionResourceRel = "usuarios")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @RestResource(path = "existbyemail")
    boolean existsByEmail(@Param("email") String email);
    @RestResource(path = "getUserByEmail")
    Optional<Usuario> findByEmail(String email);
}
