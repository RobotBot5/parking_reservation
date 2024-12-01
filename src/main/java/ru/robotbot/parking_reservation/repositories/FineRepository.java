package ru.robotbot.parking_reservation.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepository extends CrudRepository<FineEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE FineEntity f SET f.isPaid = true WHERE f.id = :id")
    void updateIsPaid(Long id);


    List<FineEntity> findByIsPaidFalseAndTimeToPayBefore(LocalDateTime currentTime);

}
