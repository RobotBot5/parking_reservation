package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotFullReservationResponse;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotFullResponse;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.repositories.ParkingSpotRepository;
import ru.robotbot.parking_reservation.repositories.ReservationRepository;
import ru.robotbot.parking_reservation.services.ParkingSpotService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<ParkingSpotEntity> getAllParkingSpots() {
        return StreamSupport.stream(parkingSpotRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public void addParkingSpot(ParkingSpotEntity parkingSpotEntity) {
        parkingSpotRepository.save(parkingSpotEntity);
    }

    @Override
    public void deleteParkingSpot(ParkingSpotEntity parkingSpotEntity) {
        parkingSpotRepository.delete(parkingSpotEntity);
    }

    @Override
    public List<ParkingSpotEntity> getParkingSpotsByZone(ParkingSpotZone zone) {
        return parkingSpotRepository.findAllByZone(zone);
    }

    @Override
    public List<ParkingSpotEntity> getOccupiedParkingSpots(boolean occupied) {
        List<ParkingSpotEntity> occupiedParkingSpots = reservationRepository.findOccupiedParkingSpots();
        if (occupied) return occupiedParkingSpots;
        return parkingSpotRepository.findAvailableSpots(occupiedParkingSpots);
    }

    @Override
    public Optional<ParkingSpotFullResponse> findFullInfoById(Long id) {
        Optional<ParkingSpotEntity> parkingSpotEntityFromDb = parkingSpotRepository.findById(id);
        if (parkingSpotEntityFromDb.isEmpty()) {
            return Optional.empty();
        }
        ParkingSpotEntity parkingSpotEntity = parkingSpotEntityFromDb.get();
        List<ReservationEntity> reservations = reservationRepository.findAllByParkingSpotEntity(parkingSpotEntity);
        List<ParkingSpotFullReservationResponse> reservationList = reservations.stream().map((reservationEntity -> ParkingSpotFullReservationResponse.builder()
                .id(reservationEntity.getId())
                .startTime(reservationEntity.getStartTime())
                .endTime(reservationEntity.getEndTime())
                .userEntity(reservationEntity.getUserEntity())
                .isPaid(reservationEntity.getIsPaid())
                .reservationType(reservationEntity.getReservationType()).build())).toList();
        boolean isOccupied = reservationList.stream().anyMatch((reservation -> reservation.getReservationType() == ReservationType.ACTIVE));
        return Optional.of(ParkingSpotFullResponse.builder()
                .id(parkingSpotEntity.getId())
                .number(parkingSpotEntity.getNumber())
                .zone(parkingSpotEntity.getZone())
                .occupied(isOccupied)
                .reservations(reservationList).build());
    }
}
