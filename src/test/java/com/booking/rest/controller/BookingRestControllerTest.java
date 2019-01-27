package com.booking.rest.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.booking.rest.entity.Booking;
import com.booking.rest.service.BookingService;
import com.booking.rest.util.DateUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookingRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private BookingService bookingService;

	@Test
	void testGetBookingDates() {
		@SuppressWarnings("unchecked")
		List<Object> restBookingDates = (List<Object>) this.restTemplate
				.getForObject("/api/bookingDates/" + DateUtil.getToday("yyyyMMdd"), List.class);
		Assertions.assertEquals(5, restBookingDates.size());
	}

	@Test
	void testGetBooking() {

		Booking booking = new Booking((long) 7, DateUtil.getToday("yyyyMMdd"), "0900", "1100", "jUnit테스트", null, 0);

		bookingService.saveBooking(booking);

		Booking restBooking = this.restTemplate.getForObject("/api/bookings/" + booking.getBookingId(), Booking.class);
		Assertions.assertNotNull(restBooking);

		Assertions.assertEquals(booking.getBookingId(), restBooking.getBookingId());
		Assertions.assertEquals(booking.getRoomId(), restBooking.getRoomId());
		Assertions.assertEquals(booking.getBookingDate(), restBooking.getBookingDate());
		Assertions.assertEquals(booking.getStartTime(), restBooking.getStartTime());
		Assertions.assertEquals(booking.getEndTime(), restBooking.getEndTime());
		Assertions.assertEquals(booking.getUserName(), restBooking.getUserName());
		Assertions.assertEquals(booking.getRepeatTypeId(), restBooking.getRepeatTypeId());
		Assertions.assertEquals(booking.getRepeatCount(), restBooking.getRepeatCount());
	}
}
