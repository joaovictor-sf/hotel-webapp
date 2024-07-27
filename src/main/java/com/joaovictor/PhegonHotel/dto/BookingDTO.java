package com.joaovictor.PhegonHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private Long id;
    private String checkInDate;
    private String checkOutDate;
    private Integer numOfAdults;
    private Integer numOfChildren;
    private Integer numOfGuests;
    private String bookingConfigurationCode;
    private UserDTO user;
    private RoomDTO room;
}
