package by.ezer.exceptions.handler;

import by.ezer.exceptions.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> handleException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> handleValidationException(ValidationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionDTO> handlesServiceException(HttpServletRequest request, ServiceException ex){
        return new ResponseEntity<>(new ExceptionDTO(ex.getMessage(), ex.getHttpStatus().value(),
                LocalDateTime.now(), request.getRequestURI()), ex.getHttpStatus());
    }
}
