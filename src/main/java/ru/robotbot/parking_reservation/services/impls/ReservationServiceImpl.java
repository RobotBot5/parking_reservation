package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.mappers.Mapper;
import ru.robotbot.parking_reservation.repositories.ParkingSpotRepository;
import ru.robotbot.parking_reservation.repositories.ReservationRepository;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final Mapper<ReservationEntity, ReservationDto> mapper;

    @Override
    public int createReservation(ReservationDto reservationDto, UserPrincipal userPrincipal) {
        var startTime = reservationDto.getStartTime();
        var endTime = reservationDto.getEndTime();
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (startTime.isAfter(endTime)) {
            return 3; // Start time is after before time
        }
//        if (startTime.until(endTime, ChronoUnit.MINUTES) < 30) {
//            return 4; // Reservation's time must be at least 30 minutes
//        }
//        if (startTime.isBefore(LocalDateTime.now())) {
//            return 6; // Start time has passed
//        }
        if (reservationRepository.findByUserEntityAndReservationType(
                userEntity,
                ReservationType.ACTIVE
        ).isPresent()) {
            return 5; // One user can have only one active reservation
        }
        Optional<ParkingSpotEntity> parkingSpotEntityFromDb = parkingSpotRepository
                .findById(reservationDto.getParkingSpotId());
        ParkingSpotEntity parkingSpotEntity = parkingSpotEntityFromDb.orElse(null);
        if (parkingSpotEntity == null) {
            return 1; // parkingSpot with this id doesn't exist
        }
        ReservationEntity reservationEntity = mapper.mapFrom(reservationDto);
        reservationEntity.setParkingSpotEntity(parkingSpotEntity);
        reservationEntity.setUserEntity(userEntity);
        if (reservationRepository.existsByParkingSpotAndOnThisTime(
                reservationEntity.getParkingSpotEntity().getId(),
                reservationEntity.getStartTime(),
                reservationEntity.getEndTime()
        )) return 2; // exist reservation on this time
        reservationRepository.save(reservationEntity);
        return 0; // okay
    }

    @Override
    public Optional<ReservationEntity> getReservationByUser(UserPrincipal userPrincipal) {
        UserEntity user = userRepository.findById(
                        userPrincipal
                                .getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return reservationRepository.findFirstByUserEntityAndReservationType(user, ReservationType.ACTIVE);
    }

    @Override
    public List<ReservationEntity> getAllByStatus(ReservationType reservationType) {
        return reservationRepository.findAllByReservationType(reservationType);
    }

    @Override
    public List<ReservationEntity> getAllReservations() {
        return StreamSupport.stream(reservationRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationEntity> getAllByUserId(Long id) {
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findAllByUserEntity(userEntity);
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public int cancelReservation(UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<ReservationEntity> reservationFromBd =
                reservationRepository.findByUserEntityAndReservationType(userEntity, ReservationType.ACTIVE);
        if (reservationFromBd.isEmpty()) {
            return 1; // User doesn't have active reservation
        }
        ReservationEntity reservationEntity = reservationFromBd.get();
        if (LocalDateTime.now().until(reservationEntity.getStartTime(), ChronoUnit.MINUTES) <= 30) {
            return 2; // Reservation can be canceled only at 30 minutes
        }
        reservationRepository.delete(reservationEntity);
        return 0; // okay
    }

    @Override
    public void setExpiredReservations() {
        reservationRepository.updateExpiredReservations(LocalDateTime.now(), ReservationType.EXPIRED);
    }

    @Override
    public int payReservation(UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<ReservationEntity> reservationFromBd =
                reservationRepository.findByUserEntityAndReservationType(userEntity, ReservationType.ACTIVE);
        if (reservationFromBd.isEmpty()) {
            return 1; // User doesn't have active reservation
        }
        ReservationEntity reservationEntity = reservationFromBd.get();
        if (reservationEntity.getIsPaid()) {
            return 2; // Reservation is already payed
        }
        reservationRepository.updatePayReservations(userEntity);
        return 0; // okay
    }
}
