package com.hejula.server.controller;

import com.hejula.server.dto.CommonResponse;
import com.hejula.server.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Api(tags={"토큰 Api"})
@RequiredArgsConstructor
@RestController
@Slf4j
public class CmmnController {

    private final JwtService jwtService;

    /**
     * 토큰을 발급받기 위한 API
     *
     * @param xUserId
     * @return
     */
    @GetMapping("/token")
    @ApiOperation(value="토큰 발급", notes="토큰을 발급받기 위한 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-USER-ID", value = "사용자 ID", required = true, dataType = "String", defaultValue = "v1")
    })
    public CommonResponse<String> getToken(HttpServletResponse response, @RequestHeader("X-USER-ID") String xUserId) {
        String token = jwtService.createToken(xUserId);

        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCompleted(true);
        commonResponse.setMessage("토큰이 성공적으로 발급되었습니다.");
        commonResponse.setResultValue(token); // response에만 담아서 주는 것이 옳으나, post man에서 테스트를 하기 위하여 token정보를 반환함
        response.setHeader("Set-Cookie","Authorization="+token+";path=/;HttpOnly");

        return commonResponse;
    }

    // feed image 반환하기
    @GetMapping(value = "image/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(value = "image 조회 ", notes = "Image를 반환합니다. 못 찾은 경우 기본 image를 반환")
    public ResponseEntity<byte[]> image(@PathVariable("imagename") String imagename) throws IOException {
        try (InputStream imageStream = new FileInputStream("C://images/feed/" + imagename)) {
            byte[] imageByteArray = IOUtils.toByteArray(imageStream);
            imageStream.close();
            return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
        } catch (Exception e) { //파일 없을 때 기본이미지 보여 주도록 처리

        }
        return null;
    }

}
