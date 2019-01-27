package com.booking.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.rest.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByRoomId(Long roomId);

	List<Booking> findByBookingDate(String bookingDate);

	List<Booking> findByRoomIdAndBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(Long roomId, String bookingDate, String startTime1, String startTime2);
	
	List<Booking> findByRoomIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThanEqual(Long roomId, String bookingDate, String endTime1, String endTime2);
	
}