package com.hejula.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 응답코드를 규격화하여 API 통신에 사용
 *
 * @author jooyeon
 * @since 2021.07.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private boolean completed; //성공여부

    private String message; //사용자에게 노출할 메시지

    private String errorMessage; //개발자 확인용 에러 메시지

    private T resultValue; //결과값

}
