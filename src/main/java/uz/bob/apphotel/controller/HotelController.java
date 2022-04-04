package uz.bob.apphotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.apphotel.entity.Hotel;
import uz.bob.apphotel.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels(){
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }
    @PostMapping
    public String add(@RequestBody Hotel hotel){
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists)
            return "This is hotel already exist";
        Hotel savedHotel=new Hotel();
        savedHotel.setName(hotel.getName());
        hotelRepository.save(savedHotel);
        return "Hotel saved";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        try {
            hotelRepository.deleteById(id);
            return "Hotel deleted";
        }catch (Exception exception){
            return "Error in deleting";
        }
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent())
            return "Hotel not founded";
        Hotel editingHotel = optionalHotel.get();
        editingHotel.setName(hotel.getName());
        return "Hotel edited";
    }

}
