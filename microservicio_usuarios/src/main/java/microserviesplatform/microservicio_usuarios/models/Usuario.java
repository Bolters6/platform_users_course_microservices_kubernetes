package microserviesplatform.microservicio_usuarios.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(name = "email", columnNames = "email")})
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Inserisca un nombre")
    private String nombre;
    @Email(message = "Formato email incorrecto")
    private String email;
    @NotBlank(message = "password debe existir")
    @Size(message = "password de 4 caracteres minimo", min = 4)
    private String password;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate nacimiento;
    @Formula("year(now()) - year(nacimiento)")
    private Integer edad;

}
