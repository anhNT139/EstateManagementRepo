package com.kepler.controlleradvice;

import com.kepler.dto.error.ErrorDTO;
import com.kepler.exception.FileHandlingException;
import com.kepler.exception.UnProcessableEntityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorDTO errorDTO = new ErrorDTO(
                httpStatus,
                ex.getBindingResult().getFieldErrors().stream()
                                                      .map(FieldError::getDefaultMessage)
                                                      .distinct()
                                                      .collect(Collectors.toList()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorDTO errorDTO = new ErrorDTO(
                httpStatus,
                ex.getConstraintViolations().stream()
                                            .map(ConstraintViolation::getMessage)
                                            .distinct()
                                            .collect(Collectors.toList()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @ExceptionHandler(value = {UnProcessableEntityException.class})
    public ResponseEntity<ErrorDTO> handleUnProcessableEntityException(RuntimeException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDTO errorDTO = new ErrorDTO(httpStatus, Collections.singletonList(ex.getMessage()), LocalDateTime.now());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @ExceptionHandler(value = FileHandlingException.class)
    public ResponseEntity<ErrorDTO> handleFileException(FileHandlingException ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDTO errorDTO = new ErrorDTO(httpStatus, Collections.singletonList(ex.getMessage()), LocalDateTime.now());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> handleMaxUploadSizeExceededException() {
        HttpStatus httpStatus = HttpStatus.PAYLOAD_TOO_LARGE;
        ErrorDTO errorDTO = new ErrorDTO(httpStatus, Collections.singletonList("Kích thước file phải bé hơn " + maxFileSize), LocalDateTime.now());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }
}

