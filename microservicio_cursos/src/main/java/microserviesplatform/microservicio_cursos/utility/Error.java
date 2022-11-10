package microserviesplatform.microservicio_cursos.utility;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum Error {

    CURSO_EXISTENTE("Este curso ya existe", HttpStatus.BAD_REQUEST),
    CURSO_NO_EXISTENTE("Este curso no existe", HttpStatus.NOT_FOUND),
    CURSO__LLENO("Este curso esta lleno", HttpStatus.BAD_REQUEST),
    USUARIO_NO_EXISTE("Usuario no existe en este curso", HttpStatus.NOT_FOUND),
    USUARIO_EXISTENTE_EN_ESTE_CURSO("Este Usuario ya esta inscrito en este Curso", HttpStatus.BAD_REQUEST),
    CURSO_USUARIO_EXISTENTE("Este usuario ya existe en el curso usuario", HttpStatus.BAD_REQUEST),
    CURSO_USUARIO_NOEXISTE("Este usuario no existe en el Curso Usuario", HttpStatus.NOT_FOUND);

    private String message;
    private HttpStatus status;

    private Error(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }
}
