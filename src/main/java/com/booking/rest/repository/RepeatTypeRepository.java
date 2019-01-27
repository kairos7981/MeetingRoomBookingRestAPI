package com.booking.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.rest.entity.RepeatType;

@Repository
public interface RepeatTypeRepository extends JpaRepository<RepeatType, Integer> {
	List<RepeatType> findByRepeatTypeName(String repeatTypeName);
}