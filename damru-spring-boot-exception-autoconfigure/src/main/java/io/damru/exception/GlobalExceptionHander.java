package io.damru.exception;

import io.damru.exception.model.ApiException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnWebApplication
@ControllerAdvice
public class GlobalExceptionHander {

    @ExceptionHandler(value = ApiException.class)
    protected ResponseEntity<Map> handleGenericException(
            ApiException apiException,
            WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", apiException.getReason());
        body.put("errors", apiException.getErrors());
        body.put("location", request.getDescription(false));
        return ResponseEntity.status(apiException.getStatus())
                             .body(body);
    }
}
