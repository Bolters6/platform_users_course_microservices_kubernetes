package microserviesplatform.microservicio_usuarios.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Min(message = "password de 4 caracteres minimo", value = 4)
    private String password;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate nacimiento;
    @Formula("year(now()) - year(nacimiento)")
    private Integer edad;

}
