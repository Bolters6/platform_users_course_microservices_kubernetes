package microserviesplatform.microservicio_cursos.exceptions;

import microserviesplatform.microservicio_cursos.controllers.CursoController;
import microserviesplatform.microservicio_cursos.controllers.CursoUsuarioController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = {CursoController.class, CursoUsuarioController.class})
public class CursoAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CursoException.class)
    public ResponseEntity<?> cursoExceptionHandler(CursoException cursoException){
        return ResponseEntity.status(cursoException.getStatus()).body(cursoException.getMessage());
    }

/*    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> springDataRestRestTemplateExceptions(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidad No Existe");
    }*/

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
