package microserviesplatform.microservicio_cursos.exceptions;

import lombok.Getter;
import lombok.Setter;
import microserviesplatform.microservicio_cursos.utility.Error;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CursoException extends Exception{

    private Error error;
    private HttpStatus status;

    public CursoException(Error error) {
        super(error.getMessage());
        this.status = error.getStatus();
    }

}
