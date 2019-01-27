package com.booking.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bookingId;
	
	@NotNull(message="회의실을 선택해 주세요.")
	private Long roomId;
	
	@Column(length = 8)
	@NotBlank(message="회의실을 예약 일자를 입력해 주세요.")
	private String bookingDate;

	@Column(length = 4)
	@NotBlank(message="회의실 예약 시작 시간을 입력해 주세요.")
	private String startTime;
	
	@Column(length = 4)
	@NotBlank(message="회의실 예약 종료 시간을 입력해 주세요.")
	private String endTime;
	
	@NotBlank(message="회의실 예약자명을 입력해 주세요.")
	private String userName;
	
	private Integer repeatTypeId;
	
	private Integer repeatCount;

	
	protected Booking() {
	}
	
	public Booking(Long roomId, String bookingDate, String startTime, String endTime, String userName,
			Integer repeatTypeId, Integer repeatCount) {
		super();
		this.roomId = roomId;
		this.bookingDate = bookingDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userName = userName;
		this.repeatTypeId = repeatTypeId;
		this.repeatCount = repeatCount;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", roomId=" + roomId + ", bookingDate=" + bookingDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", userName=" + userName + ", repeatTypeId="
				+ repeatTypeId + ", repeatCount=" + repeatCount + "]";
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRepeatTypeId() {
		return repeatTypeId;
	}

	public void setRepeatTypeId(Integer repeatTypeId) {
		this.repeatTypeId = repeatTypeId;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	
}
 
