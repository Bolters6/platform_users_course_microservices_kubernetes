package microserviesplatform.microservicio_cursos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private LocalDate nacimiento;
    private Integer edad;
    private String codigoUsuario;
}
