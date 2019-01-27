package com.booking.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.rest.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long>{
	List<Room> findByRoomName(String roomName);
	
	long countByRoomId(Long roomId);
}