package ru.robotbot.parking_reservation.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpotEntity parkingSpotEntity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isPaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReservationType reservationType;

}
