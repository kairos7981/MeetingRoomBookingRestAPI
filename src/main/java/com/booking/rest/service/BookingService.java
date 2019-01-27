package com.booking.rest.service;

import java.util.List;

import com.booking.rest.entity.Booking;


public interface BookingService {
 public List<Booking> retrieveBookings();
 
 public List<Booking> bookingDates(String bookingDate);

 public List<Booking> findByRoomIdAndBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(Long roomId, String bookingDate, String startTime1, String startTime2);
 
 public List<Booking> findByRoomIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThanEqual(Long roomId, String bookingDate, String endTime1, String endTime2);
 
 public Booking getBooking(Long bookingId);
 
 public Booking saveBooking(Booking booking);
 
 public void deleteBooking(Long bookingId);
 
 public void updateBooking(Booking booking);
 
 public boolean bookingChecked(Booking booking);
}
