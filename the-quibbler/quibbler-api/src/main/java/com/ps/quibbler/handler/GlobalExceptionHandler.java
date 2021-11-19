package com.ps.quibbler.handler;

import com.ps.quibbler.base.BaseException;
import com.ps.quibbler.global.ErrorResponse;
import com.ps.quibbler.global.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Paksu
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleAppException(BaseException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(exception, request.getRequestURI());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), exception.getErrorCode().getStatus());
    }
}
