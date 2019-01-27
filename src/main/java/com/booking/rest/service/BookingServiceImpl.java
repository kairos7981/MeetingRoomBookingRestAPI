package com.booking.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.rest.entity.Booking;
import com.booking.rest.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public List<Booking> retrieveBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		return bookings;
	}

	public List<Booking> bookingDates(String bookingDate) {
		List<Booking> bookings = bookingRepository.findByBookingDate(bookingDate);
		return bookings;
	}

	public List<Booking> findByRoomIdAndBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(Long roomId,
			String bookingDate, String startTime1, String startTime2) {
		List<Booking> bookings = bookingRepository
				.findByRoomIdAndBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(roomId, bookingDate,
						startTime1, startTime2);
		return bookings;
	}

	public List<Booking> findByRoomIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThanEqual(Long roomId,
			String bookingDate, String endTime1, String endTime2) {
		List<Booking> bookings = bookingRepository
				.findByRoomIdAndBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(roomId, bookingDate, endTime1,
						endTime2);
		return bookings;
	}

	public Booking getBooking(Long bookingId) {
		Optional<Booking> optEmp = bookingRepository.findById(bookingId);
		return optEmp.get();
	}

	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	public void deleteBooking(Long bookingId) {
		bookingRepository.deleteById(bookingId);
	}

	public void updateBooking(Booking booking) {
		bookingRepository.save(booking);
	}

	public boolean bookingChecked(Booking booking) {
		boolean checked = true;
		String[] mmPrefix = { "00", "30" };
		int sHH = Integer.parseInt(booking.getStartTime().substring(0, 2));
		int sMM = Integer.parseInt(booking.getStartTime().substring(2, 4));
		int eHH = Integer.parseInt(booking.getEndTime().substring(0, 2));
		int eMM = Integer.parseInt(booking.getEndTime().substring(2, 4));
		int startCount = sMM == 0 ? 0 : 1;
		int endCount = (((eHH - sHH) * 60) - sMM + eMM) / 30;
		endCount = endCount + startCount;
		for (int i = startCount; i < endCount; i++) {
			String shhMM = "";
			String ehhMM = "";
			if (i % 2 == 0) {
				shhMM = convNumF(sHH) + mmPrefix[0];
				ehhMM = convNumF(sHH) + mmPrefix[1];
			} else {
				shhMM = convNumF(sHH) + mmPrefix[1];
				ehhMM = convNumF(sHH + 1) + mmPrefix[0];
				sHH++;
			}

			List<Booking> bookingChecked1 = this.findByRoomIdAndBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThan(booking.getRoomId(), booking.getBookingDate(), shhMM, shhMM);
			List<Booking> bookingChecked2 = this.findByRoomIdAndBookingDateAndStartTimeLessThanAndEndTimeGreaterThanEqual(booking.getRoomId(), booking.getBookingDate(), ehhMM, ehhMM);
			if (bookingChecked1.size() > 0 || bookingChecked2.size() > 0) {
				checked = false;
				break;
			}
		}

		return checked;
	}
	
	
	private String convNumF(int n) {
		return ( n < 10 ? "0" : "" ) + n;
	}	
}