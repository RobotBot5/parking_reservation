package ru.robotbot.parking_reservation.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;

import java.util.Optional;

@Repository
public interface FineRepository extends CrudRepository<FineEntity, Long> {

    Optional<FineEntity> findByUser(UserEntity userEntity);

    @Modifying
    @Transactional
    @Query("UPDATE FineEntity f SET f.isPaid = true WHERE f.user = :userEntity")
    void updateIsPaid(UserEntity userEntity);

}
