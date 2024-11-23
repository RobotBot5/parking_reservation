package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotService {

    List<ParkingSpotEntity> getAllParkingSpots();

    void addParkingSpot(ParkingSpotEntity parkingSpotEntity);

    void deleteParkingSpot(ParkingSpotEntity parkingSpotEntity);

}
