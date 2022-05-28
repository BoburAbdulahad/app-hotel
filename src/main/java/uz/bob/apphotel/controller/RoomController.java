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
//    @GetMapping("/hotel/{hotelId}") // TODO: 5/27/2022 return rooms pageable by hotelId
//    public Page<Room> roomsPage(@PathVariable Integer hotelId, @RequestParam int page){
//        Pageable pageable= PageRequest.of(page,5);
//        Page<Room> roomPage = roomRepository.findAllByHotel_Id(hotelId, pageable);
//        return roomPage;
//    }
    @PostMapping
    public String add(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not founded";
        boolean existsByNumberAndFloor = roomRepository.existsByNumberAndFloorAndHotelId(// TODO: 5/27/2022 check jpa query
                roomDto.getNumber(), roomDto.getFloor(), roomDto.getHotelId());
        if (existsByNumberAndFloor)
            return "This number room already exists at the floor in hotel";
        Hotel hotel = optionalHotel.get();
        Room room=new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotel);
        roomRepository.save(room);
        return "Room added";
    }
    @DeleteMapping("/{roomId}")
    public String delete(@PathVariable Integer roomId){
        try {
            roomRepository.deleteById(roomId);
            return "Room deleted";
        }catch (Exception e){
            return "Error in deleting";
        }
    }
    @PutMapping("/{roomId}")
    public String edit(@PathVariable Integer roomId,@RequestBody RoomDto roomDto){
        Integer foundedRoomId;
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not founded";
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (!optionalRoom.isPresent()) {
            return "Room not founded";
        }
        Room room = optionalRoom.get();
        boolean b = roomRepository.existsByNumberAndFloorAndHotelId(roomDto.getNumber(), roomDto.getFloor(), roomDto.getHotelId());
        if (b ) {
            Room foundedRoom = roomRepository.findRoomByNumberAndFloorAndHotelId(roomDto.getNumber(), roomDto.getFloor(), roomDto.getHotelId());
            if (roomId!=foundedRoom.getId())
            return "This number room already exists at the floor in hotel";
        }
        Hotel hotel = optionalHotel.get();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotel);
        roomRepository.save(room);
        return "Room successfully edited";


    }

}
