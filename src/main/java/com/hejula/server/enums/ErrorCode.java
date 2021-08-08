package com.hejula.server.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //file
    INVALID_EXTENSION(400, "F01", "확장자가 올바르지 않습니다.");

    private int status;
    private String code;
    private String message;

    private static ErrorCode ofCode(String code){
        return Arrays.stream(ErrorCode.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
