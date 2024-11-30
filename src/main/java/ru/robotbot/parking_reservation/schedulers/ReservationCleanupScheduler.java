package ru.robotbot.parking_reservation.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.services.ReservationService;

@Component
@RequiredArgsConstructor
public class ReservationCleanupScheduler {

    private final ReservationService reservationService;

    // Запускается каждый час
    @Scheduled(fixedRate = 60000)
    public void scheduleReservationCleanup() {
        reservationService.setExpiredReservations();
        System.out.println("Service");
    }

}
