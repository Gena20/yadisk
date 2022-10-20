package org.yadisk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.yadisk.dto.ErrorResponse;

@ControllerAdvice
public class SystemItemExceptionHandler {
    private static final String DEFAULT_NOT_FOUND_MSG = "Item not found";
    private static final String DEFAULT_VALIDATION_MSG = "Validation Failed";

//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleBadRequestException() {
//        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), DEFAULT_VALIDATION_MSG);
//    }

    @ResponseBody
    @ExceptionHandler(SystemItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSystemItemNotFoundException() {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), DEFAULT_NOT_FOUND_MSG);
    }
}
