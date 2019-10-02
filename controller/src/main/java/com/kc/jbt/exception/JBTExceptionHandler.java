package com.kc.jbt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class JBTExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleClientException(HttpServletRequest request, Exception ex) {
        return generateResponseForException(request, ex);
    }

    private Map<String, String> generateResponseForException(HttpServletRequest request, Exception ex) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("http_status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.put("message", ex.getMessage());
        return response;
    }
}
