package microserviesplatform.microservicio_cursos.repositories;

import microserviesplatform.microservicio_cursos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "cursos")
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
