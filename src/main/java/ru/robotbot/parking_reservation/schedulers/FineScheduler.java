package ru.robotbot.parking_reservation.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.repositories.FineRepository;
import ru.robotbot.parking_reservation.repositories.ReservationRepository;
import ru.robotbot.parking_reservation.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FineScheduler {

    private final FineRepository fineRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private static final double FINE_INCREMENT_AMOUNT = 1000.0;
    private static final double FINE_AMOUNT = 1000.0;

    @Scheduled(fixedRate = 60000)
    public void updateOverdueFines() {
        log.info("updateOverdueFines");
        List<FineEntity> overdueFines = fineRepository.findByIsPaidFalseAndTimeToPayBefore(
                LocalDateTime.now()
        );

        for (FineEntity fine : overdueFines) {
            fine.setAmount(fine.getAmount() + FINE_INCREMENT_AMOUNT);
            fine.setTimeToPay(LocalDateTime.now().plusMinutes(2));

            fineRepository.save(fine);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void createFines() {
        log.info("createFines");
        List<ReservationEntity> reservations = reservationRepository
                .findAllByReservationTypeAndEndTimeBefore(
                        ReservationType.ACTIVE,
                        LocalDateTime.now().minusMinutes(15)
                );
        reservations.forEach(reservationEntity -> {
            long minutesFromEnd = reservationEntity.getEndTime().until(
                    LocalDateTime.now(),
                    ChronoUnit.MINUTES
            );
            double amountToPay = minutesFromEnd / 60.0 * reservationEntity.getParkingSpotEntity().getZone().getRate();
            FineEntity fineToSave = FineEntity.builder()
                    .amount(amountToPay + FINE_AMOUNT)
                    .timeToPay(LocalDateTime.now().plusMinutes(2))
                    .isPaid(false)
                    .build();
            fineRepository.save(fineToSave);
            UserEntity userEntity = reservationEntity.getUserEntity();
            userEntity.setFine(fineToSave);
            userRepository.save(userEntity);
        });
    }
}
