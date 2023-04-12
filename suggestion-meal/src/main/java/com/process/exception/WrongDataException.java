package com.process.exception;

import com.process.model.Enum.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongDataException extends RuntimeException{
    private final String code = ResponseStatus.WRONG_DATA.code;
    private final String message;

    public WrongDataException(String message) {
        this.message = message;
    }

}
