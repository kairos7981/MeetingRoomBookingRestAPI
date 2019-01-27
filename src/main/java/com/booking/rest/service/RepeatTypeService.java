package com.booking.rest.service;

import java.util.List;

import com.booking.rest.entity.RepeatType;

public interface RepeatTypeService {
 public List<RepeatType> retrieveRepeatTypes();
 
 public RepeatType getRepeatType(Integer RepeatTypeId);
 
 public void saveRepeatType(RepeatType repeatType);
 
 public void deleteRepeatType(Integer repeatTypeId);
 
 public void updateRepeatType(RepeatType repeatType);
}
