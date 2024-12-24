package com.adm.test.extracomp;

import com.adm.test.dto.response.ApiResponse;
import com.adm.test.utility.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static com.adm.test.dto.response.ApiResponse.buildResponse;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({AuthorizationDeniedException.class, NotFoundException.class})
    public ResponseEntity<ApiResponse> handleUnAuthorizedAccess(Exception e) {
        log.error(e.getMessage(), e);
        return buildResponse(null, NOT_FOUND, e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(Exception e) {
        log.error(e.getMessage(), e);
        return buildResponse(null, BAD_REQUEST, e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class, DuplicateKeyException.class})
    public ResponseEntity<ApiResponse> handleValidationException(Exception e) {
        HttpStatus status = BAD_REQUEST;
        Object errorData = null;
        String errorMessage = "";
        if (e instanceof MethodArgumentNotValidException me) {
            Map<String, String> errors = new HashMap<>();
            me.getBindingResult()
                    .getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            errorData = errors;
            errorMessage = errors.values().toString();
        }
        if (e instanceof InvalidParameterException ie) {
            errorMessage = ie.getMessage();
        }
        return buildResponse(errorData, status, errorMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnIdentifiedException(Exception e) {
        log.error(e.getMessage(), e);
        return buildResponse(null, INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
