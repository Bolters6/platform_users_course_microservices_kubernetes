package microserviesplatform.microservicio_cursos.models;

import lombok.*;
import lombok.experimental.Accessors;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
//@EntityListeners(ListenEventCursoUsuario.class)
@Table(name = "curso_usuarios", uniqueConstraints = @UniqueConstraint(name = "usuario_id", columnNames = "usuarioId"))
public class CursoUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long usuarioId;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "cursoUsuarios", targetEntity = Curso.class)
    @JsonIgnore
    private Set<Curso> cursos;

}
