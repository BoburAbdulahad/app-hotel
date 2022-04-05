package uz.bob.apphotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.apphotel.entity.Room;
@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {
    Page<Room> findAllByHotel_Id(Integer hotel_id, Pageable pageable);
//    boolean existsByNumberAndFloorAndSizeAndHotel_Id(String number, Integer floor, String size, Integer hotel_id);
    boolean existsByNumberAndFloorAndHotelId(String number, Integer floor, Integer hotel_id);
}
