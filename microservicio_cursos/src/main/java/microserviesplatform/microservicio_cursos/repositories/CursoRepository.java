package microserviesplatform.microservicio_cursos.repositories;

import microserviesplatform.microservicio_cursos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "cursos", collectionResourceRel = "cursos")
public interface CursoRepository extends JpaRepository<Curso, Long> {
    @RestResource(exported = false)
    boolean existsByNombre(String nombre);
    @RestResource(exported = false)
    boolean existsByNombreAndClase(String nombre, String clase);

    @Override
    @RestResource(exported = false)
    <S extends Curso> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    <S extends Curso> S save(S entity);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Curso entity);
}
