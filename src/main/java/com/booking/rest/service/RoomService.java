package com.booking.rest.service;

import java.util.List;

import com.booking.rest.entity.Room;

public interface RoomService {
 public List<Room> retrieveRooms();
 
 public Room getRoom(Long roomId);
 
 public void saveRoom(Room room);
 
 public void deleteRoom(Long roomId);
 
 public void updateRoom(Room room);
 
 public long countByRoomId(Long roomId);
 
}
