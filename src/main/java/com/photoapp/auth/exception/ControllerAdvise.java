package com.photoapp.auth.exception;

import com.photoapp.auth.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestControllerAdvice
@Slf4j
public class ControllerAdvise extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Unknown Exception {} caught in {} path, Full error stack : ", ex.getMessage(), request.getContextPath(), ex);

        BindingResult bindingResult = ex.getBindingResult();
        Set<String> errors = bindingResult.getFieldErrors().stream().map(r -> format("%s:%s", r.getField(), r.getDefaultMessage())).collect(Collectors.toSet());
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(errors);
        errorResponseDTO.setResponseCode("400");
        errorResponseDTO.setResponseMessage("Bad Request.");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(InvalidCredentialsException.class)
    protected ResponseEntity<Object> handleCustomException(HttpServletResponse res, InvalidCredentialsException e) throws IOException {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setResponseCode("401");
        errorResponseDTO.setResponseMessage(e.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }
}
