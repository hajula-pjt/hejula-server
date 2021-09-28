package com.hejula.server.controller;


import com.hejula.server.dto.*;
import com.hejula.server.entities.*;
import com.hejula.server.service.AdminService;
import com.hejula.server.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags={"관리자 페이지 Api"})
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final PagedResourcesAssembler<AccommodationReportDto> accommodationReportDtoPagedResourcesAssembler;
    private final JwtService jwtService;


    @PostMapping("/signIn")
    @ApiOperation(value="관리자 로그인", notes="관리자 로그인 API")
    @ApiImplicitParams({
    })
    public CommonResponse<UserDto> signIn(@RequestBody Admin admin, HttpServletResponse response, HttpServletRequest request) {
        CommonResponse<UserDto> res = new CommonResponse<>();
        UserDto user = new UserDto();

        Admin resultAdmin = adminService.getAdmin(admin) ;
        boolean flag = resultAdmin == null ? false : true;
        res.setCompleted(flag);

        if(flag) {
            user.setUserId(resultAdmin.getId());
            user.setUserSeq(resultAdmin.getAdminSeq());
            user.setNickname(resultAdmin.getNickname());

            String token = jwtService.createToken(admin.getId());
            user.setToken(token);

            res.setResultValue(user);
        }

        return res;
    }

    @GetMapping("/monthly/statistics/{adminId}")
    @ApiOperation(value="대시보드 상단 이번달 데이터 집합", notes="이번 달 방문자 수, 총 매출액, 가동률 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataType = "String", defaultValue = "1")
    })
    public CommonResponse<MonthlyStatisticsDto> getMonthlyStatistics(@PathVariable String adminId){

        CommonResponse<MonthlyStatisticsDto> res = new CommonResponse<>();
        res.setCompleted(true);

        Admin admin = adminService.getMonthlyStatistics(adminId);
        MonthlyStatisticsDto msDto = new MonthlyStatisticsDto();
        msDto.setVisitors(admin.getThisMonthVisitors());
        msDto.setRateOperation(admin.getThisMonthRateOperation());
        msDto.setSales(admin.getThisMonthSales());
        res.setResultValue(msDto);

        return res;

    }

    @GetMapping("/weekly/customer/statistics/{adminId}")
    @ApiOperation(value="대시보드 중간의 금주 투숙객 현황", notes="꺾은선 그래프를 그리기 위한 금주 투숙객 현황 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataType = "String", defaultValue = "1")
    })
    public CommonResponse<List<DailyVisitors>> getWeeklyCustomerStatus(@PathVariable String adminId){

        CommonResponse<List<DailyVisitors>> res = new CommonResponse<>();
        res.setCompleted(true);
        res.setResultValue(adminService.getWeeklyStatus(adminId));
        return res;
    }

    @GetMapping("/accommodation/report/{adminId}/{rows}/{pageNo}")
    @ApiOperation(value="대시보드 하단의 금주 숙박 현황", notes="숙박 현황 API, 기준일 + 30일까지 노출")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "rows", value = "노출하고자 하는 행 수, default : 10", required = true, dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNo", value = "조회하고자하는 페이지 번호", required = true, dataType = "int", defaultValue = "1")
    })
    public CommonResponse<PagedModel<EntityModel<AccommodationReportDto>>> getAccommodationReport(@PathVariable String adminId, @PathVariable int rows, @PathVariable int pageNo){
        if(rows == 0){ rows = 10; }
        CommonResponse<PagedModel<EntityModel<AccommodationReportDto>>> res = new CommonResponse<>();
        Page<AccommodationReportDto> list = adminService.getMonthlyAccommodationReport(adminId, rows, pageNo);
        res.setResultValue(accommodationReportDtoPagedResourcesAssembler.toModel(list));
        res.setCompleted(true);
        return res;
    }

    @GetMapping("/accommodation/{adminId}")
    @ApiOperation(value="숙소 리스트", notes="관리자 소유의 숙소 리스트 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataType = "String", defaultValue = "jooypark"),
    })
    public CommonResponse<List<Accommodation>> getAccommodationList(@PathVariable String adminId){
        CommonResponse<List<Accommodation>> res = new CommonResponse<>();
        res.setCompleted(true);
        res.setResultValue(adminService.getAccommodationList(adminId));
        return res;
    }

    @GetMapping("/accommodation/report/age/{accommodationSeq}")
    @ApiOperation(value="방문객 연령대", notes="숙소 분석의 방문객 연령대 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1")
    })
    public CommonResponse< List<AgeDto> > getVisitorsAge(@PathVariable Long accommodationSeq){
        CommonResponse< List<AgeDto> > res = new CommonResponse<>();
        res.setCompleted(true);
        res.setResultValue(adminService.getVisitorsAge(accommodationSeq));
        return res;
    }

    @GetMapping("/accommodation/report/tag/{accommodationSeq}")
    @ApiOperation(value="방문객 태그", notes="숙소 분석의 방문객 태그 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1")
    })
    public CommonResponse<List<AccommodationStatisticsTag>> getVisitorsTag(@PathVariable Long accommodationSeq){
        CommonResponse<List<AccommodationStatisticsTag>> res = new CommonResponse<>();
        res.setCompleted(true);
        res.setResultValue(adminService.getVisitorsTag(accommodationSeq));
        return res;
    }

    @GetMapping("/accommodation/report/stars/{accommodationSeq}")
    @ApiOperation(value="평점 조회", notes="숙소 분석의 평점 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1")
    })
    public CommonResponse<StarsDto> getStars(@PathVariable Long accommodationSeq){
        CommonResponse<StarsDto> res = new CommonResponse<>();
        res.setCompleted(true);
        res.setResultValue(adminService.getStars(accommodationSeq));
        return res;
    }

    @GetMapping("/accommodation/report/visitors/{accommodationSeq}")
    @ApiOperation(value="방문객 조회", notes="숙소 분석의 평점 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1")
    })
    public CommonResponse<List<VisitorsDto>> getVisitors(@PathVariable Long accommodationSeq){
        CommonResponse<List<VisitorsDto>> res = new CommonResponse<>();
        res.setCompleted(true);
        res.setResultValue(adminService.getVisitors(accommodationSeq));
        return res;
    }

}
