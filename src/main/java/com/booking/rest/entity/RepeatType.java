package com.booking.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RepeatType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer repeatTypeId;

	@Column
	private String repeatTypeName;
	
	protected RepeatType() {
	}

	public RepeatType(Integer repeatTypeId, String repeatTypeName) {
		super();
		this.repeatTypeId = repeatTypeId;
		this.repeatTypeName = repeatTypeName;
	}

	public Integer getRepeatTypeId() {
		return repeatTypeId;
	}

	public void setRepeatTypeId(Integer repeatTypeId) {
		this.repeatTypeId = repeatTypeId;
	}

	public String getRepeatTypeName() {
		return repeatTypeName;
	}

	public void setRepeatTypeName(String repeatTypeName) {
		this.repeatTypeName = repeatTypeName;
	}

	@Override
	public String toString() {
		return "RepeatType [repeatTypeId=" + repeatTypeId + ", repeatTypeName=" + repeatTypeName + "]";
	}
	
	
}
