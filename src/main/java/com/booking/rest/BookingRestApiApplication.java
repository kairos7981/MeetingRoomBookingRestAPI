package com.booking.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.booking.rest.entity.Booking;
import com.booking.rest.entity.RepeatType;
import com.booking.rest.entity.Room;
import com.booking.rest.repository.BookingRepository;
import com.booking.rest.repository.RepeatTypeRepository;
import com.booking.rest.repository.RoomRepository;
import com.booking.rest.util.DateUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "com.booking.rest" })
@SpringBootApplication
@EnableSwagger2
@Controller
public class BookingRestApiApplication {
	private final Logger log = LoggerFactory.getLogger(BookingRestApiApplication.class);

	@GetMapping("/")
	public String index() {
		return "main";
	}

	public static void main(String[] args) {
		SpringApplication.run(BookingRestApiApplication.class, args);

	}

	@Bean
	public CommandLineRunner inputInitData(RoomRepository roomRepo, BookingRepository bookingRepo,
			RepeatTypeRepository repeatTypeRepo) {
		return (args) -> {
			roomRepo.save(new Room("회의실 A"));
			roomRepo.save(new Room("회의실 B"));
			roomRepo.save(new Room("회의실 C"));
			roomRepo.save(new Room("회의실 D"));
			roomRepo.save(new Room("회의실 E"));
			roomRepo.save(new Room("회의실 F"));
			roomRepo.save(new Room("회의실 G"));

			repeatTypeRepo.save(new RepeatType(1, "매일"));
			repeatTypeRepo.save(new RepeatType(2, "매주"));
			repeatTypeRepo.save(new RepeatType(3, "매월"));
			repeatTypeRepo.save(new RepeatType(4, "매년"));
			log.info("-------------------------------");
			log.info("Rooms found with findAll():");
			log.info("-------------------------------");
			for (Room room : roomRepo.findAll()) {
				log.info(room.toString());
			}
			log.info("-------------------------------");

			log.info("RepeatType found with findAll():");
			log.info("-------------------------------");
			for (RepeatType repeatType : repeatTypeRepo.findAll()) {
				log.info(repeatType.toString());
			}
			log.info("-------------------------------");

			// fetch an individual customer by ID
			roomRepo.findById(1L).ifPresent(room -> {
				bookingRepo.save(new Booking(room.getRoomId(), DateUtil.getToday("yyyyMMdd"), "0900", "1100", "사용자1", null , 0));
				bookingRepo.save(new Booking((long)2, DateUtil.getToday("yyyyMMdd"), "0930", "1130", "사용자1", null, 0));
				bookingRepo.save(new Booking((long)3, DateUtil.getToday("yyyyMMdd"), "1000", "1130", "사용자1", null, 0));
				bookingRepo.save(new Booking((long)4, DateUtil.getToday("yyyyMMdd"), "1030", "1100", "사용자1", null, 0));
				bookingRepo.save(new Booking((long)5, DateUtil.getToday("yyyyMMdd"), "0800", "0830", "사용자1", null, 0));

				log.info("Room found with findById(1L):");
				log.info("--------------------------------");
				log.info(room.toString());
				for (Booking booking : bookingRepo.findByRoomId(room.getRoomId())) {
					log.info(booking.toString());
				}
				log.info("");
			});

			// fetch customers by last name
			log.info("--------------------------------------------");
			log.info("Customer found with findByLastName('B'):");
			log.info("--------------------------------------------");
			roomRepo.findByRoomName("B").forEach(b -> {
				log.info(b.toString());
			});
			log.info("--------------------------------------------");
		};
	}

}
