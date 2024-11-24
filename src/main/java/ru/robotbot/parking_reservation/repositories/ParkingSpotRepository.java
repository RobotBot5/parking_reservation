package ru.robotbot.parking_reservation.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends CrudRepository<ParkingSpotEntity, Long> {

    List<ParkingSpotEntity> findAllByZone(ParkingSpotZone zone);

    @Query("SELECT p FROM ParkingSpotEntity p WHERE p NOT IN :occupiedSpots")
    List<ParkingSpotEntity> findAvailableSpots(@Param("occupiedSpots") List<ParkingSpotEntity> occupiedSpots);

}
