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

import com.booking.rest.BookingRestApiApplication;
import com.booking.rest.entity.Room;
import com.booking.rest.service.RoomService;

@RestController
public class RoomRestController {
    private final Logger logger = LoggerFactory.getLogger(RoomRestController.class);
    
	@Autowired
	private RoomService roomService;

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	@GetMapping("/api/rooms")
	public List<Room> getRooms() {
		List<Room> rooms = roomService.retrieveRooms();
		return rooms;
	}

	@GetMapping("/api/rooms/{roomId}")
	public Room getRoom(@PathVariable(name = "roomId") Long roomId) {
		return roomService.getRoom(roomId);
	}

	@PostMapping("/api/rooms")
	public void saveRoom(Room room) {
		roomService.saveRoom(room);
		logger.debug("Room Saved Successfully");
	}

	@DeleteMapping("/api/rooms/{roomId}")
	public void deleteRoom(@PathVariable(name = "roomId") Long roomId) {
		roomService.deleteRoom(roomId);
		logger.debug("Room Deleted Successfully");
	}

	@PutMapping("/api/rooms/{roomId}")
	public void updateRoom(@RequestBody Room room, @PathVariable(name = "roomId") Long roomId) {
		Room beforeRoom = roomService.getRoom(roomId);
		if (beforeRoom != null) {
			roomService.updateRoom(room);
		}
	}
}
