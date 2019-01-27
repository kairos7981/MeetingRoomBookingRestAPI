package com.booking.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.booking.rest.entity.Room;
import com.booking.rest.service.RoomService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RoomRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RoomService roomService;

	@Test
	@DisplayName("test RoomRestController REST API ")
	void testRoomController() {

		// 입력 및 단건 조회
		Room room = new Room("회의실 Test");
		roomService.saveRoom(room);

		Room restRoom = this.restTemplate.getForObject("/api/rooms/" + room.getRoomId(), Room.class);

		Assertions.assertEquals(room.getRoomId(), restRoom.getRoomId());
		Assertions.assertEquals(room.getRoomName(), restRoom.getRoomName());

		// 수정
		Room updateRoom = new Room("회의실 Test 수정");
		updateRoom.setRoomId(room.getRoomId());

		HttpHeaders headers = restTemplate.headForHeaders("/api/rooms/" + room.getRoomId());
		Assertions.assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));

		HttpEntity<Room> requestUpdate = new HttpEntity<>(updateRoom, headers);

		this.restTemplate.exchange("/api/rooms/" + room.getRoomId(), HttpMethod.PUT, requestUpdate, Void.class);

		Room updateRoomAfter = this.restTemplate.getForObject("/api/rooms/" + room.getRoomId(), Room.class);

		Assertions.assertEquals(updateRoom.getRoomId(), updateRoomAfter.getRoomId());
		Assertions.assertEquals(updateRoom.getRoomName(), updateRoomAfter.getRoomName());

		// 삭제
		HttpEntity<Room> requestDelete = new HttpEntity<>(updateRoom, headers);

		this.restTemplate.exchange("/api/rooms/" + room.getRoomId(), HttpMethod.DELETE, requestDelete, Void.class);

		long deleteRoomAfter = roomService.countByRoomId(room.getRoomId());

		Assertions.assertEquals(0, deleteRoomAfter);
	}
}