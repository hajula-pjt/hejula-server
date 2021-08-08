package com.hejula.server.controller;

import com.hejula.server.dto.AccommodationSearchDto;
import com.hejula.server.dto.CommonResponse;
import com.hejula.server.dto.ScheduleDto;
import com.hejula.server.entities.Accommodation;
import com.hejula.server.entities.Gu;
import com.hejula.server.service.AccommodationService;
import com.hejula.server.service.KafkaProducerService;
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
@RequestMapping("/accommodation")
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final PagedResourcesAssembler<Accommodation> accommodationPagedResourcesAssembler;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/gu")
    @ApiOperation(value="구 검색", notes="숙박 검색을 위해 숙박위치인 '구'를 검색할 때 사용하는 API, name을 보내지 않을 경우 전체목록 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "구 이름", required = true, dataType = "String", defaultValue = "v1")
    })
    public CommonResponse<List<Gu>> getGuList(String name){
        CommonResponse<List<Gu>> res = new CommonResponse();
        res.setCompleted(true);
        res.setResultValue(accommodationService.getGuList(name));
        res.setMessage("조회가 완료되었습니다.");

        return res;
    }


    @PostMapping("/search")
    @ApiOperation(value="숙박 장소 검색", notes="숙박 장소를 검색하기 위한 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "guSeq", value = "구 고유번호", required = true, dataType = "Long", defaultValue = "1"),
            @ApiImplicitParam(name = "checkIn", value = "체크인 날짜", required = true, dataType = "Date", defaultValue = "2021-01-03"),
            @ApiImplicitParam(name = "checkOut", value = "체크아웃 날짜", required = true, dataType = "Date", defaultValue = "2021-01-01"),
            @ApiImplicitParam(name = "people", value = "사람 수(어른+아이)", required = true, dataType = "Integer", defaultValue = "2"),
            @ApiImplicitParam(name = "page", value = "조회를 하려고 하는 페이지 번호(0번부터 발번 됨)", required = true, dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "rows", value = "한 페이지의 행 수", required = true, dataType = "Integer", defaultValue = "10"),
    })
    public CommonResponse<PagedModel<EntityModel<Accommodation>>> getAccommodationList (@RequestBody AccommodationSearchDto asDto){
        CommonResponse<PagedModel<EntityModel<Accommodation>>> res = new CommonResponse<>();
        res.setCompleted(true);
        Page<Accommodation> list = accommodationService.getAccommodationList(asDto);
        res.setResultValue(accommodationPagedResourcesAssembler.toModel(list));

        return res;
    }

    @GetMapping("/{accommodationSeq}")
    @ApiOperation(value="숙소 상세", notes="숙소 상세 정보를 조회하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accommodationSeq", value = "숙소 고유번호", required = true, dataType = "Long", defaultValue = "1")
    })
    public CommonResponse<Map<String, Object>> getAccommodationDetail(@PathVariable("accommodationSeq") long accommodationSeq){
        CommonResponse<Map<String, Object>> res = new CommonResponse<>();

        Map<String, Object> map = new HashMap<>();
        Accommodation accommodation = accommodationService.getAccommodationDetail(accommodationSeq);
        map.put("accommodation", accommodation);
        map.put("files", accommodation.getFiles());
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
        res.setResultValue(kafkaProducerService.reservation(scheduleDto));
        res.setCompleted(true);
        return res;
    }
}
