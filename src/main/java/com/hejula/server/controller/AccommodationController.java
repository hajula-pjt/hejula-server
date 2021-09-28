package com.hejula.server.controller;

import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.dto.CommonResponse;
import com.hejula.server.dto.ReviewSearchDto;
import com.hejula.server.dto.ScheduleDto;
import com.hejula.server.entities.*;
import com.hejula.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 숙소에 관한 정보를 관리하기 위한 api
 *
 * @author jooyeon
 * @since 2021.07.18
 */

@Api(tags={"숙소 Api"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final PagedResourcesAssembler<Accommodation> accommodationPagedResourcesAssembler;
    private final PagedResourcesAssembler<Review> reviewPagedResourcesAssembler;
    private final KafkaProducerService kafkaProducerService;
    private final ScheduleService scheduleService;
    private final PriceService priceService;
    private final ReviewService reviewService;


    @Value("${custom.file.path}")
    private String SAVE_PATH;



    @GetMapping("/accommodation/gu")
    @ApiOperation(value="구 검색", notes="숙박 검색을 위해 숙박위치인 '구'를 검색할 때 사용하는 API, name을 보내지 않을 경우 전체목록 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "구 이름", required = true, dataType = "String", defaultValue = "강")
    })
    public CommonResponse<List<Gu>> getGuList(String name){
        CommonResponse<List<Gu>> res = new CommonResponse();
        res.setCompleted(true);
        res.setResultValue(accommodationService.getGuList(name));
        res.setMessage("조회가 완료되었습니다.");

        return res;
    }


    @PostMapping("/accommodation/search")
    @ApiOperation(value="숙박 장소 검색", notes="숙박 장소를 검색하기 위한 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "guSeq", value = "구 고유번호", required = true, dataType = "Long", defaultValue = "1"),
            @ApiImplicitParam(name = "checkIn", value = "체크인 날짜", required = true, dataType = "Date", defaultValue = "2021-01-03"),
            @ApiImplicitParam(name = "checkOut", value = "체크아웃 날짜", required = true, dataType = "Date", defaultValue = "2021-01-01"),
            @ApiImplicitParam(name = "people", value = "사람 수(어른+아이)", required = true, dataType = "Integer", defaultValue = "2"),
            @ApiImplicitParam(name = "page", value = "조회를 하려고 하는 페이지 번호(0번부터 발번 됨)", required = true, dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "rows", value = "한 페이지의 행 수", required = true, dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "searchType", value = "검색 필터 조건(VIEW:조회수,VISITOR:방문객,LOWPRICE:낮은가격,HIGHPRICE:높은가격)", required = true, dataType = "String", defaultValue = "v")
    })
    public CommonResponse<PagedModel<EntityModel<Accommodation>>> getAccommodationList (@RequestBody AccommodationSearchDto asDto) throws IllegalAccessException {
        CommonResponse<PagedModel<EntityModel<Accommodation>>> res = new CommonResponse<>();
        res.setCompleted(true);
        Page<Accommodation> list = accommodationService.getAccommodationList(asDto);
        res.setResultValue(accommodationPagedResourcesAssembler.toModel(list));

        return res;
    }

    @GetMapping("/accommodation/{accommodationSeq}")
    @ApiOperation(value="숙소 상세", notes="숙소 상세 정보를 조회하는 API, 숙소 이미지 노출시 'http://49.247.213.4:8080/fileEntity.filePath + fileEntity.fileNm'를 사용해주면 됨. " +
            "(ex) http://49.247.213.4:8080/accommodation/image/1-1.jpg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1")
    })
    public CommonResponse<Map<String, Object>> getAccommodationDetail(@PathVariable("accommodationSeq") long accommodationSeq){
        CommonResponse<Map<String, Object>> res = new CommonResponse<>();

        Map<String, Object> map = new HashMap<>();
        Accommodation accommodation = accommodationService.getAccommodationDetail(accommodationSeq);
        map.put("accommodation", accommodation);
//        map.put("files", accommodation.getFiles());
        res.setResultValue(map);

        res.setCompleted(true);
        return res;
    }


    @PostMapping("/reservation")
    @ApiOperation(value="숙소 예약", notes="숙소 예약하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schedule", value = "스케줄", required = true, dataType = "ScheduleDto", defaultValue = "")
    })
    public CommonResponse<Boolean> setSchedule(@RequestBody ScheduleDto scheduleDto){
        CommonResponse<Boolean> res = new CommonResponse<>();
        kafkaProducerService.reservation(scheduleDto);
        res.setCompleted(true);
        return res;
    }


    @GetMapping(value = "/accommodation/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(value = "image 조회 ", notes = "Image를 반환합니다. 못 찾은 경우 기본 image를 반환")
    public ResponseEntity<byte[]> image(@PathVariable String imageName) throws IOException {
        String path = SAVE_PATH + File.separator + imageName;
        try (InputStream imageStream = new FileInputStream(path)) {
            byte[] imageByteArray = IOUtils.toByteArray(imageStream);
            imageStream.close();
            return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
        } catch (IOException e) { //파일 없을 때 기본이미지 보여 주도록 처리
            throw new IOException(e);
        }
    }

    @GetMapping("/accommodation/schedule/{accommodationSeq}/{fromDate}/{toDate}")
    @ApiOperation(value="스케줄", notes="숙소의 연,월에 대한 스케줄 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "fromDate", value = "시작 연월", required = true, dataType = "String", defaultValue = "2021-08"),
            @ApiImplicitParam(name = "toDate", value = "종료 연월", required = true, dataType = "String", defaultValue = "2021-09")
    })
    public CommonResponse<List<Schedule>> getSchedule(@PathVariable String accommodationSeq, @PathVariable String fromDate, @PathVariable String toDate) throws ParseException {
        CommonResponse<List<Schedule>> res = new CommonResponse<>();
        res.setResultValue(scheduleService.getScheduleByFromToDate(accommodationSeq, fromDate, toDate));
        res.setCompleted(true);
        return res;
    }

    @GetMapping("/accommodation/price/{accommodationSeq}/{fromDate}/{toDate}")
    @ApiOperation(value="가격", notes="숙소 해당기간에 대한 가격 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "fromDate", value = "시작 날짜", required = true, dataType = "String", defaultValue = "2021-08-01"),
            @ApiImplicitParam(name = "toDate", value = "종료 날짜", required = true, dataType = "String", defaultValue = "2021-08-05")
    })
    public CommonResponse<List<Price>> getPrice(@PathVariable String accommodationSeq, @PathVariable String fromDate, @PathVariable String toDate) throws ParseException {
        CommonResponse<List<Price>> res = new CommonResponse<>();
        res.setResultValue(priceService.getPrice(accommodationSeq, fromDate, toDate));
        res.setCompleted(true);
        return res;
    }

    @GetMapping("/accommodation/accommodationviewplus/{accommodationSeq}")
    @ApiOperation(value="조회수 증가", notes="조회수 증가 api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "String", defaultValue = "1")
    })
    public CommonResponse<Boolean> viewPlus(@PathVariable String accommodationSeq){
        CommonResponse<Boolean> res = new CommonResponse<>();
        kafkaProducerService.setViewPlus(accommodationSeq);
        res.setCompleted(true);
        return res;
    }

    @PostMapping("/accommodation/review")
    @ApiOperation(value="리뷰 조회", notes="리뷰 조회 api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1"),
            @ApiImplicitParam(name = "keyword", value = "검색 키워드", required = true, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "rows", value = "노출하고자 하는 행 수, default : 10", required = true, dataType = "int", defaultValue = "10"),
            @ApiImplicitParam(name = "pageNo", value = "조회하고자하는 페이지 번호", required = true, dataType = "int", defaultValue = "0")
    })
    public CommonResponse<PagedModel<EntityModel<Review>>> getReview(@RequestBody ReviewSearchDto reviewSearchDto){
        CommonResponse<PagedModel<EntityModel<Review>>> res = new CommonResponse<>();
        Page<Review> list = reviewService.getReviewList(reviewSearchDto);
        res.setResultValue(reviewPagedResourcesAssembler.toModel(list));
        res.setCompleted(true);
        return res;
    }

}
