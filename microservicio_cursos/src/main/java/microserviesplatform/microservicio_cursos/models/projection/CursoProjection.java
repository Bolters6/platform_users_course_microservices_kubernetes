package microserviesplatform.microservicio_cursos.models.projection;

import microserviesplatform.microservicio_cursos.models.Curso;
import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "cursoview", types = {Curso.class})
public interface CursoProjection {
    public String getNombre();
    public String getClase();
    public Integer getMaxUsuarios();
    public Set<CursoUsuario> getCursoUsuarios();
    @Value("#{target.getCursoUsuarios().size()}")
    public int getCantidadUsuariosInscritos();
}
