package microserviesplatform.microservicio_usuarios.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor
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

}
