package com.joaovictor.PhegonHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private String role;
    private String email;
    private String phoneNumber;
    private List<BookingDTO> bookings = new ArrayList<>();

}
