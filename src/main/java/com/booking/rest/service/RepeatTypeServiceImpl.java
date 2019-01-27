package com.booking.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.rest.entity.RepeatType;
import com.booking.rest.repository.RepeatTypeRepository;

@Service
public class RepeatTypeServiceImpl implements RepeatTypeService {

	@Autowired
	private RepeatTypeRepository repeatTypeRepository;

	public void setRepeatTypeRepository(RepeatTypeRepository repeatTypeRepository) {
		this.repeatTypeRepository = repeatTypeRepository;
	}

	public List<RepeatType> retrieveRepeatTypes() {
		List<RepeatType> repeatTypes = repeatTypeRepository.findAll();
		return repeatTypes;
	}

	public RepeatType getRepeatType(Integer repeatTypeId) {
		Optional<RepeatType> optrepeatType = repeatTypeRepository.findById(repeatTypeId);
		return optrepeatType.get();
	}

	public void saveRepeatType(RepeatType repeatType) {
		repeatTypeRepository.save(repeatType);
	}

	public void deleteRepeatType(Integer repeatTypeId) {
		repeatTypeRepository.deleteById(repeatTypeId);
	}

	public void updateRepeatType(RepeatType repeatType) {
		repeatTypeRepository.save(repeatType);
	}
}