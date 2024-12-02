package ru.robotbot.parking_reservation.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    @JsonIgnore
    private String password;

    private String firstName;

    @ElementCollection
    @CollectionTable(name = "users_car_states", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "car_state")
    private List<String> carStates;

    private String role;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "fine_id", nullable = true)
    private FineEntity fine;

}
