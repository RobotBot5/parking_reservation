package ru.robotbot.parking_reservation.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.repositories.FineRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FineUpdateScheduler {

    private final FineRepository fineRepository;

    private static final double FINE_INCREMENT_AMOUNT = 1000.0;

    @Scheduled(fixedRate = 60000)
    public void updateOverdueFines() {
        List<FineEntity> overdueFines = fineRepository.findByIsPaidFalseAndTimeToPayBefore(LocalDateTime.now());

        for (FineEntity fine : overdueFines) {
            fine.setAmount(fine.getAmount() + FINE_INCREMENT_AMOUNT);
            fine.setTimeToPay(LocalDateTime.now().plusMinutes(2));

            fineRepository.save(fine);
        }
    }

}
