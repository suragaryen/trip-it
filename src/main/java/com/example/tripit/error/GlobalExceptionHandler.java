package com.example.tripit.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

import static com.example.tripit.error.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleInvalidInputException(InvalidInputException ex) {
        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
                ex.getField(), ex.getValue(), ex.getReason()
        );
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "C002", "invalid input type", Collections.singletonList(errorDetail)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



//
//    @ExceptionHandler(InvalidInputException.class)
//    @ResponseBody
//    public ResponseEntity<CustomErrorResponse> EmailDuplicateException(InvalidInputException ex) {
//        CustomErrorResponse.ErrorDetail errorDetail = new CustomErrorResponse.ErrorDetail(
//                ex.getField(), ex.getValue(), ex.getReason()
//        );
//        CustomErrorResponse errorResponse = new CustomErrorResponse(
//                HttpStatus.BAD_REQUEST.value(), "C002", "invalid input type", Collections.singletonList(errorDetail)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

}
