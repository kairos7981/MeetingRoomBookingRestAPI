package com.booking.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.booking.rest.entity.RepeatType;
import com.booking.rest.service.RepeatTypeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RepeatTypeRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RepeatTypeService repeatTypeService;

	@Test
	@DisplayName("test RepeatTypeController REST API ")
	void testRepeatTypeController() {
		// 입력 및 단건 조회
		RepeatType repeatType = new RepeatType(99,"RepeatType테스트");
		repeatTypeService.saveRepeatType(repeatType);

		RepeatType restRepeatType = this.restTemplate.getForObject("/api/repeatTypes/" + repeatType.getRepeatTypeId(), RepeatType.class);

		Assertions.assertEquals(repeatType.getRepeatTypeId(), restRepeatType.getRepeatTypeId());
		Assertions.assertEquals(repeatType.getRepeatTypeName(), restRepeatType.getRepeatTypeName());
	}
}
