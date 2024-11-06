package ru.robotbot.parking_reservation.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Phone number is required")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "At least one car state is required")
    @Size(min = 1, message = "At least one car state is required")
    private List<String> carStates;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

}
