package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.mappers.Mapper;
import ru.robotbot.parking_reservation.repositories.ParkingSpotRepository;
import ru.robotbot.parking_reservation.repositories.ReservationRepository;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.ReservationService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final Mapper<ReservationEntity, ReservationDto> mapper;

    @Override
    public boolean createReservation(ReservationDto reservationDto, UserPrincipal userPrincipal) {
        Optional<ParkingSpotEntity> parkingSpotEntityFromDb = parkingSpotRepository
                .findById(reservationDto.getParkingSpotId());
        ParkingSpotEntity parkingSpotEntity = parkingSpotEntityFromDb.orElse(null);
        if (parkingSpotEntity == null) {
            return false;
        }
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ReservationEntity reservationEntity = mapper.mapFrom(reservationDto);
        reservationEntity.setParkingSpotEntity(parkingSpotEntity);
        reservationEntity.setUserEntity(userEntity);
        reservationRepository.save(reservationEntity);
        return true;
    }
}
