package ru.robotbot.parking_reservation.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;

@Repository
public interface ParkingSpotRepository extends CrudRepository<ParkingSpotEntity, Long> {
}