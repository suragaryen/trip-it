package com.example.tripit.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputException extends RuntimeException {
    private String field;
    private String value;
    private String reason;

    public InvalidInputException(String field, String value, String reason) {
        super(reason);
        this.field = field;
        this.value = value;
        this.reason = reason;
    }
}
