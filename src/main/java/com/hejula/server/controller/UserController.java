package com.hejula.server.controller;

import com.hejula.server.dto.CommonResponse;
import com.hejula.server.dto.UserDto;
import com.hejula.server.entities.Customer;
import com.hejula.server.service.JwtService;
import com.hejula.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags={"고객 페이지 Api"})
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/signup")
    @ApiOperation(value="회원가입", notes="회원가입 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 ID", required = true, dataType = "String", defaultValue = "jooypark"),
            @ApiImplicitParam(name = "password", value = "비밀번호", required = true, dataType = "String", defaultValue = "jooypark!a"),
            @ApiImplicitParam(name = "birth", value = "출생연도", required = true, dataType = "String", defaultValue = "1995"),
            @ApiImplicitParam(name = "nickname", value = "닉네임", required = true, dataType = "String", defaultValue = "영국너구리"),
            @ApiImplicitParam(name = "sex", value = "성별(w:여자, m:남자)", required = true, dataType = "String", defaultValue = "w"),
            @ApiImplicitParam(name = "img", value = "file multipart", required = false, dataType = "multipartFile", defaultValue = ""),
            @ApiImplicitParam(name = "tagList", value = "tag List", required = false, dataType = "List<String>", defaultValue = "")
    })
    public CommonResponse<Boolean> signup(Customer customer, List<String> tagList,
                                          @RequestParam(value = "img", required = false) MultipartFile img) throws Exception {
        log.info("into signup()");
        log.info("customer : {} ", customer.toString());
        if(img != null) {
            log.info("img 존재 : {}", img);
        }

        CommonResponse<Boolean> res = new CommonResponse<>();
        customer = userService.setCustomer(customer, img, tagList);
        if(customer != null){
            res.setCompleted(true);
        }else{
            res.setCompleted(false);
        }
        res.setMessage("회원가입이 완료 되었습니다.");

        return res;
    }

    @GetMapping("/existId")
    @ApiOperation(value="중복체크", notes="ID 중복체크 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 ID", required = true, dataType = "String", defaultValue = "jooypark")
    })
    public CommonResponse<Boolean> isExistCustomerId(String id) {
        log.info("into isExistCustomerId()");

        CommonResponse<Boolean> res = new CommonResponse<>();
        res.setCompleted(true);

        boolean flag = userService.isExistCustomerId(id);
        String msg =  flag ? "이미 존재하는 ID 입니다." : "사용 가능한 ID 입니다.";
        res.setMessage(msg);
        res.setResultValue(flag);

        return res;
    }

    @PostMapping("/signIn")
    @ApiOperation(value="로그인", notes="로그인 API")
    @ApiImplicitParams({
    })
    public CommonResponse<UserDto> signIn(@RequestBody Customer customer, HttpServletResponse response, HttpServletRequest request) {
        CommonResponse<UserDto> res = new CommonResponse<>();
        UserDto user = new UserDto();

        Customer resultCustomer = userService.getCustomer(customer) ;
        boolean flag = resultCustomer == null ? false : true;
        res.setCompleted(flag);

        if(flag) {
            user.setUserId(resultCustomer.getId());
            user.setUserSeq(resultCustomer.getCustomerSeq());
            user.setNickname(resultCustomer.getNickname());

            String token = jwtService.createToken(customer.getId());
            user.setToken(token);

            res.setResultValue(user);
        }

        return res;
    }


    @GetMapping("/profile")
    @ApiOperation(value="프로필", notes="프로필 수정을 위한 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "비밀번호", required = true, dataType = "String", defaultValue = "jooypark!a"),
            @ApiImplicitParam(name = "nickname", value = "닉네임", required = true, dataType = "String", defaultValue = "영국너구리"),
            @ApiImplicitParam(name = "tagList", value = "tag List", required = true, dataType = "List<String>", defaultValue = "")
    })
    public CommonResponse<Boolean> signup(Customer customer, List<String> tagList) throws Exception {
        log.info("into profile()");
        log.info("customer : {} ", customer.toString());

        CommonResponse<Boolean> res = new CommonResponse<>();
        customer = userService.updateCustomer(customer, tagList);
        if(customer != null){
            res.setCompleted(true);
        }else{
            res.setCompleted(false);
        }
        res.setMessage("회원가입이 완료 되었습니다.");

        return res;
    }


}
