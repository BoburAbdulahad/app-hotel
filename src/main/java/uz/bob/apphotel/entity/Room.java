package uz.bob.apphotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"number","floor"}))
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private Integer floor;
    private String size;
    @ManyToOne(optional = false)
    private Hotel hotel;

}
