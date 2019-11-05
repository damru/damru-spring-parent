package io.damru.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

public abstract class ApiException extends ResponseStatusException {
    private Map<String, Object> errors = new HashMap<>();

    public ApiException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ApiException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public Map<String, ?> getErrors() {
        return errors;
    }

    public void addError(String key, Object value) {
        this.errors.put(key, value);
    }

}
