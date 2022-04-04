package uz.bob.apphotel.payload;

import lombok.Data;

@Data
public class RoomDto {
    private String number;
    private Integer floor;
    private String size;
    private Integer hotelId;

}
