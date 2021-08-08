
package com.hejula.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejula.server.entities.Schedule;
import com.hejula.server.repository.AccommodationRepository;
import com.hejula.server.repository.GuRepository;
import com.hejula.server.repository.ScheduleRepository;
import com.hejula.server.service.AccommodationService;
import com.hejula.server.service.KafkaProducerService;
import com.hejula.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
@Slf4j
class HejulaServerApplicationTests {

	@Autowired
	GuRepository guRepository;

	@Autowired
	UserService userService;

	@Autowired
	AccommodationService accommodationService;

	@Autowired
	KafkaProducerService kafkaProducerService;

	@Autowired
	AccommodationRepository accommodationRepository;

	@Autowired
	ScheduleRepository scheduleRepository;


/*@Test
	void guTest() {
		List<Gu> list = guRepository.findByNameLike("english");
		log.debug("break;");
	}



	@Test
	public void test(){
		String pattern = "jpg|jpge|gif|png";
		log.info("jpeg : {}", Pattern.matches(pattern, "jpeg"));
		log.info("gif : {}", Pattern.matches(pattern, "gif"));
		log.info("png : {}", Pattern.matches(pattern, "png"));
		log.info("aa : {}", Pattern.matches(pattern, "aa"));
	}

	@Test
	public void setCustomerTest() throws Exception {
		Customer customer = new Customer().builder()
				.id("testId")
				.password("password")
				.birth("1998")
				.age(28)
				.nickname("영국오리")
				.sex("m")
				.build();
		customer = userService.setCustomer(customer, null);
		log.info("customer_seq : " + customer.getCustomerSeq());
	}



@Test
	public void getGu(){
		Calendar cal = Calendar.getInstance();
		cal.set(2021, 07, 24);
		Date startDate = cal.getTime();
		cal.set(2021, 07, 25);
		Date endDate = cal.getTime();

		AccommodationSearchDto asDto = new AccommodationSearchDto();
		asDto.setGuSeq(new Long(1));
		asDto.setCheckIn(startDate);
		asDto.setCheckOut(endDate);
		asDto.setPage(0);
		asDto.setRows(5);
		asDto.setPeople(1);

		Page<Accommodation> result = accommodationService.getAccommodationList(asDto);
		log.info("break");
	}


	@Test
	public void kafkaTest(){
		Accommodation accommodation = new Accommodation();
		accommodation.setAccommodationSeq(1);
		accommodation.setName("test");
		kafkaProducerService.test(accommodation);
	}

	@Test
	public void getAccommodation(){
		Accommodation a = accommodationRepository.getById(new Long(1));
		log.debug(a.toString());
	}*/

	@Test
	public void getSchedule() throws JsonProcessingException {
		Schedule s = scheduleRepository.getById(new Long(1));
		log.info("s:" + s.toString());
		ObjectMapper obejctMapper = new ObjectMapper();
		log.info(obejctMapper.writeValueAsString(s));
	}


}

