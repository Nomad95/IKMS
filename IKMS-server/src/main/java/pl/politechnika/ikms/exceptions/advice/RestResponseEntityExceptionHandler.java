package pl.politechnika.ikms.exceptions.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleElementNotFound(RuntimeException ex, WebRequest request){
        //zbyt ogolny, dodajemy informacje przy tworzeniu exceptiona
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DisabledException.class)
    protected ResponseEntity<Object> handleUserNotEnabled(RuntimeException ex, WebRequest request){
        final String message = "Użytkownik jest zdezaktywowany";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.LOCKED, request);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(RuntimeException ex, WebRequest request){
        final String message = "Błędny login lub hasło";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
