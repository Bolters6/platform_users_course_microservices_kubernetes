package microserviesplatform.microservicio_cursos.exceptions;

import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
public class DataRestAdvice  {

    @ExceptionHandler(value = CursoException.class)
    public ResponseEntity<?> cursoExceptionHandler(CursoException cursoException){
        return ResponseEntity.status(cursoException.getStatus()).body(cursoException.getMessage());
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<?> clientErrorHandler(HttpClientErrorException exception){
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsString());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> handleValidationsException(ConstraintViolationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getConstraintViolations()
                .stream()
                .map(c -> "message: " + c.getMessage()).collect(Collectors.joining(", ")));
    }
}
