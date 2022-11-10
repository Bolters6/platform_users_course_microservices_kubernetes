package microserviesplatform.microservicio_cursos;

import com.fasterxml.jackson.databind.ObjectMapper;
import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CursoUsuarioDataRestTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(0)
    public void cursoUsuarioSaveOk() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                        post("/cursoUsuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new CursoUsuario().setUsuarioId(1l))))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(1)
    @Disabled
    public void cursoUsuarioDeleteByUsuarioIdTestOk() throws Exception {
        mockMvc.perform(
                    get("/cursoUsuarios/search/delete")
                        .queryParam("idUsuario", "1"))
                .andExpect(status().is2xxSuccessful());
    }
}
