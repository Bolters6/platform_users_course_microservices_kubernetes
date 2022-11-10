package microserviesplatform.microservicio_cursos.repositories;

import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.Optional;

@RepositoryRestResource(path = "cursoUsuarios", collectionResourceRel = "cursoUsuarios")
@Transactional
public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario, Long> {

    @RestResource(exported = false)
    Optional<CursoUsuario> findByUsuarioId(@Param("usuarioId") Long usuarioId);
/*    @Modifying //SE USA PARA LAS QUERY CUSTOM QUE MODIFIQUEN LA TABLA
    @Query(value = "delete from curso_usuarios c where c.usuario_id = :idUsuario", nativeQuery = true)*/
    @RestResource(path = "delete", exported = false)
    void deleteByUsuarioId(@Param(value = "idUsuario") Long id);
    @RestResource(exported = false)
    boolean existsByUsuarioId(Long usuarioId);
}
