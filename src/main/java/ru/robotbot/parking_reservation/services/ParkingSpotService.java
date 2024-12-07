package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.dto.ParkingSpotDto;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotFullResponse;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotCreateRequest;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotsResponse;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingSpotService {

    List<ParkingSpotEntity> getAllParkingSpots();

    void addParkingSpot(ParkingSpotEntity parkingSpotEntity);

    int deleteParkingSpotById(Long parkingSpotId);

    List<ParkingSpotEntity> getParkingSpotsByZone(ParkingSpotZone zone);

    List<ParkingSpotsResponse> getOccupiedParkingSpots(boolean occupied, LocalDateTime start, LocalDateTime end);

    Optional<ParkingSpotFullResponse> findFullInfoById(Long id);

    int createParkingSpot(ParkingSpotCreateRequest parkingSpotCreateRequest);

    int updateParkingSpot(ParkingSpotDto parkingSpotDto);

}
