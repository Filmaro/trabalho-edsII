package filmaro.com.host.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .userMessage("Erro ao validar os campos")
                .erros(errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()))
                .developerMessage(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
