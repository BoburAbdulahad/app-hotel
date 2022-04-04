package uz.bob.apphotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.apphotel.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {

//    boolean findByName(String name);
    boolean existsByName(String name);
}
