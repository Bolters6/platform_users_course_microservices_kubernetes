package microserviesplatform.microservicio_cursos.validations;

import lombok.RequiredArgsConstructor;
import microserviesplatform.microservicio_cursos.exceptions.CursoException;
import microserviesplatform.microservicio_cursos.models.CursoUsuario;
import microserviesplatform.microservicio_cursos.repositories.CursoRepository;
import microserviesplatform.microservicio_cursos.repositories.CursoUsuarioRepository;
import microserviesplatform.microservicio_cursos.utility.Error;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RepositoryEventHandler
@Component
@Transactional
@RequiredArgsConstructor
public class CursoUsuarioValidation {

    private final CursoUsuarioRepository cursoUsuarioRepository;
    private final CursoRepository cursoRepository;

    @HandleBeforeCreate
    public void handleBeforeCreate(@Valid CursoUsuario cursoUsuario) throws CursoException {
        if(cursoUsuarioRepository.existsByUsuarioId(cursoUsuario.getUsuarioId())){
            throw new CursoException(Error.CURSO_USUARIO_EXISTENTE);
        }
    }

    @HandleBeforeSave
    public void handleBeforeSave(@Valid CursoUsuario cursoUsuario) {
    }

    @HandleBeforeDelete
    public void handleBeforeDelete(CursoUsuario cursoUsuario) {
        cursoUsuario.getCursos().forEach(curso -> cursoRepository.findById(curso.getId()).get().getCursoUsuarios().remove(cursoUsuario));
    }
}
