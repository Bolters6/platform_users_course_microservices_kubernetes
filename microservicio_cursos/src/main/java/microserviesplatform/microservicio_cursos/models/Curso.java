package microserviesplatform.microservicio_cursos.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "nombre requerido")
    private String nombre;
    @Column(unique = true)
    private String clase;
    @NotNull
    @Min(value = 10, message = "aumente el numero maximo de usuarios para este curso")
    private Integer maxUsuarios;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(targetEntity = CursoUsuario.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<CursoUsuario> cursoUsuarios;

    @PreRemove
    public void removerUsuariosSinCurso(){
        cursoUsuarios.removeIf(cursoUsuario -> cursoUsuario.getCursos().size() == 1);
    }
}
