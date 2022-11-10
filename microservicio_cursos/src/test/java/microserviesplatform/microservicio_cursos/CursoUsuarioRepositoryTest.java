package microserviesplatform.microservicio_cursos;

import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import microserviesplatform.microservicio_cursos.repositories.CursoUsuarioRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CursoUsuarioRepositoryTest {

    @Autowired
    private CursoUsuarioRepository cursoUsuarioRepository;

    @Test
    @Order(0)
    public void cursoUsuarioSaveOk(){
        cursoUsuarioRepository.save(new CursoUsuario().setUsuarioId(1L));
        assertThat(cursoUsuarioRepository.findById(1L).get()).isEqualTo(new CursoUsuario().setId(1L).setUsuarioId(1L).setCursos(null));
    }

    @Test
    @Order(1)
    public void cursoUsuarioDelete204(){
        cursoUsuarioRepository.save(new CursoUsuario().setUsuarioId(1L));
        cursoUsuarioRepository.deleteByUsuarioId(1L);
        assertThat(cursoUsuarioRepository.findById(1L).isEmpty()).isEqualTo(true);
    }
}
