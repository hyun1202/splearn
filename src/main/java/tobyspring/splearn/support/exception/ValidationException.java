package tobyspring.splearn.support.exception;

import lombok.Getter;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter private final List<String> errors;

    public ValidationException(List<String> errors) {
        this.errors = errors;
    }
}
