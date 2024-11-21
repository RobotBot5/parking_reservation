package ru.robotbot.parking_reservation.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM ReservationEntity r " +
            "WHERE r.parkingSpotEntity.id = :parkingSpotId " +
            "AND r.reservationType = 'ACTIVE'" +
            "AND ((:startTime BETWEEN r.startTime AND r.endTime) " +
            "OR (:endTime BETWEEN r.startTime AND r.endTime))")
    boolean existsByParkingSpotAndOnThisTime(@Param("parkingSpotId") Long parkingSpotId,
                                              @Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);

    Optional<ReservationEntity> findFirstByUserEntityAndReservationType(UserEntity userEntity, ReservationType reservationType);
    List<ReservationEntity> findAllByReservationType(ReservationType reservationType);
    List<ReservationEntity> findAllByUserEntity(UserEntity userEntity);

    boolean existsByUserEntityAndReservationType(UserEntity userEntity, ReservationType reservationType);

}
