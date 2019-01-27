package com.booking.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.rest.entity.Room;
import com.booking.rest.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public List<Room> retrieveRooms() {
		List<Room> rooms = roomRepository.findAll();
		return rooms;
	}

	public Room getRoom(Long roomId) {
		Optional<Room> optRoom = roomRepository.findById(roomId);
		return optRoom.get();
	}

	public void saveRoom(Room room) {
		roomRepository.save(room);
	}

	public void deleteRoom(Long roomId) {
		roomRepository.deleteById(roomId);
	}

	public void updateRoom(Room room) {
		roomRepository.save(room);
	}
	
	public long countByRoomId(Long roomId) {
		return roomRepository.countByRoomId(roomId);
	}
}