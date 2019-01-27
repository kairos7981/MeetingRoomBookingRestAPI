package com.booking.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.rest.entity.Booking;
import com.booking.rest.service.BookingService;
import com.booking.rest.util.DateUtil;

@RestController
public class BookingRestController {
    private final Logger logger = LoggerFactory.getLogger(BookingRestController.class);
    
	@Autowired
	private BookingService bookingService;

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/api/bookings")
	public List<Booking> getBookings() {
		List<Booking> bookings = bookingService.retrieveBookings();
		return bookings;
	}

	@GetMapping("/api/bookingDates/{bookingDate}")
	public List<Booking> getBookingDates(@PathVariable(name = "bookingDate") String bookingDate) {
		List<Booking> bookings = bookingService.bookingDates(bookingDate);
		return bookings;
	}	

	@GetMapping("/api/bookings/{bookingId}")
	public Booking getBooking(@PathVariable(name = "bookingId") Long bookingId) {
		return bookingService.getBooking(bookingId);
	}
	
	@PostMapping("/api/bookings")
	public @ResponseBody ResponseEntity<?> saveBooking(@Valid @RequestBody Booking booking) {

		Map<String,Object> response = new HashMap<String, Object>();
 
	    if (booking.getStartTime().contentEquals(booking.getEndTime())) {
	        response.put("error", "시작-종료 시간이 동일 합니다.");
	        return ResponseEntity.badRequest().body(response);
	    }
	    
	    if( !StringUtils.isEmpty(booking.getStartTime()) && !StringUtils.isEmpty(booking.getEndTime()) ) {
	    	if(booking.getStartTime().length()!=4 || booking.getEndTime().length()!=4 ) {
		    	response.put("error", "회의실 예약은 정시, 30분 기준으로 가능 합니다.");
		    	return ResponseEntity.badRequest().body(response);	    		
	    	}
	    }
	    
	    if (!"00".contentEquals(booking.getStartTime().substring(2,4)) && !"30".contentEquals(booking.getStartTime().substring(2,4))) {
	    	response.put("error", "회의실 예약은 정시, 30분 기준으로 가능 합니다.");
	    	return ResponseEntity.badRequest().body(response);
	    }
	    
	    if (!"00".contentEquals(booking.getEndTime().substring(2,4)) && !"30".contentEquals(booking.getEndTime().substring(2,4))) {
	    	response.put("error", "회의실 예약은 정시, 30분 기준으로 가능 합니다.");
	    	return ResponseEntity.badRequest().body(response);
	    }
	    
		boolean repeateChecked1 = true;
		boolean repeateChecked2 = true;
		Booking newBooking = null;
	    if(booking.getRepeatTypeId() != null && booking.getRepeatTypeId() > 0) {
	    	String bookingDateOrg = booking.getBookingDate(); //반복 최초 일자
	    	String bookingDate = booking.getBookingDate();
	    	//검증
	    	for(int i=0;i<booking.getRepeatCount()-1;i++) {
	    		if(i==0) {
	    			repeateChecked1 = bookingService.bookingChecked(booking);
	    		}
	    		
	    		bookingDate = returnRepeatDate(booking, bookingDate);
	    		booking.setBookingDate(bookingDate);

	    		repeateChecked2 = bookingService.bookingChecked(booking);
	    	}
	    	logger.info("***********************************************");
	    	//입력
	    	if(repeateChecked1 && repeateChecked2) {
	    		booking.setBookingDate( bookingDateOrg );
	    		bookingDate = bookingDateOrg;

		    	for(int i=0;i<booking.getRepeatCount()-1;i++) {
		    		if(i==0) {
		    			newBooking = bookingService.saveBooking(insertBooking(booking));
		    		}
		    		
		    		bookingDate = returnRepeatDate(booking, bookingDate);
		    		booking.setBookingDate(bookingDate);
		    		
		    		bookingService.saveBooking(insertBooking(booking));
		    	}
		    	
		    	response.put("success", newBooking);
		    	
	    	} else { //실폐
	    		response.put("error", "회의실 예약이 중복 되었습니다.");
	    		return ResponseEntity.badRequest().body(response);
	    	}
	    } else {
	    	if( bookingService.bookingChecked(booking)) {
	    		newBooking = bookingService.saveBooking(booking);
	    		response.put("success", newBooking);
	    	} else {
	    		response.put("error", "회의실 예약이 중복 되었습니다.");
	    		return ResponseEntity.badRequest().body(response);
	    	}
	    }
	    
	    return ResponseEntity.accepted().body(response);
	}
	
	private String returnRepeatDate(Booking booking, String bookingDate) {
		if(booking.getRepeatTypeId() == 1) { //매일
			bookingDate = DateUtil.getAddDate(bookingDate, 1);
		} else if(booking.getRepeatTypeId() == 2) { //매주
			bookingDate = DateUtil.getAddDate(bookingDate, 7);
		} else if(booking.getRepeatTypeId() == 3) { //매월
			bookingDate = DateUtil.getAddMonth(bookingDate, 1);
		} else if(booking.getRepeatTypeId() == 4) { //매년
			bookingDate = DateUtil.getAddYear(bookingDate, 1);
		}
		
		return bookingDate;
	}
	
	private Booking insertBooking(Booking booking) {
		return new Booking(booking.getRoomId(), booking.getBookingDate(), booking.getStartTime(), booking.getEndTime(), 
				booking.getUserName(), booking.getRepeatTypeId(), booking.getRepeatCount());
	}
	
	@DeleteMapping("/api/bookings/{bookingId}")
	public Object deleteBooking(@PathVariable(name = "bookingId") Long bookingId) {
		
		Map<String,Object> response = new HashMap<String, Object>();
		Booking beforeBooking = bookingService.getBooking(bookingId);
		
		if (beforeBooking != null) {
			bookingService.deleteBooking(bookingId);
			response.put("success", beforeBooking);
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("error", "회의실 예약 정보를 찾지 못했습니다.");
			return ResponseEntity.accepted().body(response);
		}		
	}

	@PutMapping("/api/bookings/{bookingId}")
	public @ResponseBody ResponseEntity<?> updateBooking(@Valid @RequestBody Booking booking, @PathVariable(name = "bookingId") Long bookingId) {
		
		Map<String,Object> response = new HashMap<String, Object>();
		Booking beforeBooking = bookingService.getBooking(bookingId);
		
		if (beforeBooking != null) {
			bookingService.deleteBooking(bookingId);
			bookingService.updateBooking(booking);

    		response.put("success", booking);
    		return ResponseEntity.accepted().body(response);
		} else {
			response.put("error", "회의실 예약 정보를 찾지 못했습니다.");
			return ResponseEntity.accepted().body(response);
		}
	}

}
