/*
package com.hejula.server.controller;

import com.hejula.server.entities.Accommodation;
import com.hejula.server.service.AccommodationService;
import com.hejula.server.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.hamcrest.core.Is.is;

@WebMvcTest(AccommodationController.class) //단위테스트
//@SpringBootTest
//@AutoConfigureMockMvc
@Slf4j
public class AccommodationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private AccommodationService aService;

    @MockBean
    private JwtService jwtService;

    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public LinkDiscoverers discovers() {

            List<LinkDiscoverer> plugins = new ArrayList<>();
            plugins.add(new CollectionJsonLinkDiscoverer());
            return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

        }
    }


    @Test
    void accommodationDetailTest() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(1970,1,1,10,00); //시간만 쓸 경우 기본 셋팅값
        Date checkInTime = cal.getTime();
        cal.set(1970,1,1,12,00); //시간만 쓸 경우 기본 셋팅값
        Date checkOutTime = cal.getTime();
        cal.set(2017,7,24); //시간만 쓸 경우 기본 셋팅값
        Date regDate = cal.getTime();


        //given
        Accommodation accommodation = new Accommodation().builder()
                .accommodationSeq(1)
                .name("[Open sale] 연남 시네마 #홍대입구역 4분#연트럴파크 1분")
                .information("안녕하세요 연남 시네마 입니다~")
                .address("서울특별시 영등포구 경인로 846")
                .detailAddress("2층")
                .postalCode("0")
                .checkinTime(checkInTime)
                .checkoutTime(checkOutTime)
                .selfCheckinWay("key")
                .rating(4.20)
                .views(100)
                .visitors(211)
                .max(2)
                .bedroom(1)
                .bathroom(1)
                .xCoordinate(0)
                .yCoordinate(0)
                .registDate(regDate)
                .build();

        //given
        given(aService.getAccommodationDetail(1))
                    .willReturn(accommodation);

        //when
        final ResultActions actions = mvc.perform(get("/accommodation/1"))
                .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultValue.accommodationSeq", is(1)))
                .andDo(print());
    }
*/
/*
    @Test
    void accommodationDetailTest2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/accommodation/1")
                .characterEncoding("UTF-8");

       MvcResult result = mvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("content : {}", content);
                //.andExpect(MockMvcResultMatchers.jsonPath("$.resultValue.accommodationSeq", is(1)));
    }*//*


}
*/
