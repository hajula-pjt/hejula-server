package com.hejula.server.controller;

import com.hejula.server.dto.CommonResponse;
import com.hejula.server.service.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class CmmnController {

    private final JwtService jwtService;

    /**
     * 토큰을 발급받기 위한 API
     *
     * @param xUserId
     * @return
     */
    @GetMapping("/token")
    @ApiOperation(value="토큰 발급", notes="토큰을 발급받기 위한 API, ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "String", defaultValue = "v1")
    })
    public CommonResponse<String> getToken(HttpServletResponse response, @RequestHeader("userId") String xUserId) {
        String token = jwtService.createToken(xUserId);

        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCompleted(true);
        commonResponse.setMessage("토큰이 성공적으로 발급되었습니다.");
        commonResponse.setResultValue(token); // response에만 담아서 주는 것이 옳으나, post man에서 테스트를 하기 위하여 token정보를 반환함

        response.setHeader("Authorization", token);

        return commonResponse;
    }
}
