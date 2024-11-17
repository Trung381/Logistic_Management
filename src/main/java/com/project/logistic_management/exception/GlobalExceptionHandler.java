package com.project.logistic_management.exception;

import com.project.logistic_management.dto.response.BaseResponse;
import com.project.logistic_management.exception.def.ConflictException;
import com.project.logistic_management.exception.def.ForbiddenException;
import com.project.logistic_management.exception.def.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                BaseResponse.fail(Objects.requireNonNull(e.getFieldError()).getDefaultMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException (NotFoundException e) {
        return new ResponseEntity<>(
                BaseResponse.fail(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException e) {
        return new ResponseEntity<>(
                BaseResponse.fail(e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(BaseResponse.fail("Sai tên đăng nhập hoặc mật khẩu."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<BaseResponse<String>> handleIllegalArgumentException(ForbiddenException ex) {
        return new ResponseEntity<>(BaseResponse.fail(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(
                BaseResponse.fail(e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
