package uz.bob.apphotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.bob.apphotel.entity.Hotel;
import uz.bob.apphotel.entity.Room;
import uz.bob.apphotel.payload.RoomDto;
import uz.bob.apphotel.repository.HotelRepository;
import uz.bob.apphotel.repository.RoomRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Room> rooms(){
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }
    @GetMapping("/hotel/{hotelId}")
    public Page<Room> roomsPage(@PathVariable Integer hotelId, @RequestParam int page){
        Pageable pageable= PageRequest.of(page,10);
        Page<Room> roomPage = roomRepository.findAllByHotel_Id(hotelId, pageable);
        return roomPage;
    }
    @PostMapping
    public String add(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not founded";
        Hotel hotel = optionalHotel.get();
        Room room=new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotel);
        boolean exists = roomRepository.existsByNumberAndFloorAndSizeAndHotel_Id(room.getNumber(), room.getFloor(),
                room.getSize(), room.getHotel().getId());
        if (exists)
            return "This room already exist at the hotel";
        boolean existsByNumberAndFloor = roomRepository.existsByNumberAndFloor(room.getNumber(), room.getFloor());
        if (existsByNumberAndFloor)
            return "This number room already exists at the floor";
        roomRepository.save(room);
        return "Room added";
    }
//    @DeleteMapping("/{roomId}")
//    public String delete(@PathVariable Integer roomId){
//
//    }
}
