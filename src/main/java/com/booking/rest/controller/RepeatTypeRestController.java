package com.booking.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.rest.entity.RepeatType;
import com.booking.rest.service.RepeatTypeService;

@RestController
public class RepeatTypeRestController {
    private final Logger logger = LoggerFactory.getLogger(RepeatTypeRestController.class);
    
	@Autowired
	private RepeatTypeService repeatTypeService;

	public void setRepeatTypeService(RepeatTypeService repeatTypeService) {
		this.repeatTypeService = repeatTypeService;
	}

	@GetMapping("/api/repeatTypes")
	public List<RepeatType> getRepeatTypes() {
		List<RepeatType> repeatTypes = repeatTypeService.retrieveRepeatTypes();
		return repeatTypes;
	}

	@GetMapping("/api/repeatTypes/{repeatTypeId}")
	public RepeatType getRepeatType(@PathVariable(name = "repeatTypeId") Integer repeatTypeId) {
		return repeatTypeService.getRepeatType(repeatTypeId);
	}

	@PostMapping("/api/repeatTypes")
	public void saveRepeatType(RepeatType repeatType) {
		repeatTypeService.saveRepeatType(repeatType);
		logger.debug("RepeatType Saved Successfully");
	}

	@DeleteMapping("/api/repeatTypes/{repeatTypeId}")
	public void deleteRepeatType(@PathVariable(name = "repeatTypeId") Integer repeatTypeId) {
		repeatTypeService.deleteRepeatType(repeatTypeId);
		logger.debug("RepeatType Deleted Successfully");
	}

	@PutMapping("/api/repeatTypes/{repeatTypeId}")
	public void updateRepeatType(@RequestBody RepeatType repeatType, @PathVariable(name = "repeatTypeId") Integer repeatTypeId) {
		RepeatType beforeRepeaType = repeatTypeService.getRepeatType(repeatTypeId);
		if (beforeRepeaType != null) {
			repeatTypeService.updateRepeatType(repeatType);
		}
	}
}
