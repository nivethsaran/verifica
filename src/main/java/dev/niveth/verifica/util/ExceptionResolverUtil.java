package dev.niveth.verifica.util;


import dev.niveth.verifica.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;

@ControllerAdvice
public class ExceptionResolverUtil {

    @ExceptionHandler({NoHandlerFoundException.class,IllegalArgumentException.class})
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response requestHandlingNoHandlerFound() {
        Response response = new Response();
        response.setError(Collections.singletonList("404 - Not Found"));
        return response;
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response servletRequestParameterFound() {
        Response response = new Response();
        response.setError(Collections.singletonList("Invalid or missing request parameters"));
        return response;
    }

}
