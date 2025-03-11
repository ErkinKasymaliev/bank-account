package kg.kasymaliev.bankaccount.controller.handler;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import kg.kasymaliev.bankaccount.exception.BadRequestException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(NOT_FOUND)
  @ResponseBody
  CustomErrorResponse onNotFound(EntityNotFoundException e) {
    return new CustomErrorResponse(e.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  CustomErrorResponse onBadRequest(BadRequestException e) {
    return new CustomErrorResponse(e.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  CustomErrorResponse onMethodArgumentNotValid(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    return new CustomErrorResponse(
        fieldErrors.stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "))
    );
  }

  @ExceptionHandler
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  CustomErrorResponse onConstraintViolationException(ConstraintViolationException e) {
    return new CustomErrorResponse(e.getMessage());
  }

}
