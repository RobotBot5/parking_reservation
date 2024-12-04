package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.dto.*;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.mappers.Mapper;
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
    private final Mapper<ParkingSpotEntity, ParkingSpotDto> mapper;

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
    public int deleteParkingSpotById(Long parkingSpotId) {
        if (!parkingSpotRepository.existsById(parkingSpotId)) {
            return 1;
        }
        ParkingSpotEntity parkingSpotEntity = parkingSpotRepository.findById(parkingSpotId).orElseThrow();
        if (reservationRepository.existsByParkingSpotEntityAndReservationType(
                parkingSpotEntity,
                ReservationType.ACTIVE
        )) {
            return 2; // exists reservations
        }
        reservationRepository.updateParkingSpotEntitiesBeforeDelete(parkingSpotEntity);
        parkingSpotRepository.deleteById(parkingSpotId);
        return 0;
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
        List<ParkingSpotFullReservationResponse> reservationList = reservations.stream()
                .map((reservationEntity -> ParkingSpotFullReservationResponse.builder()
                        .id(reservationEntity.getId())
                        .startTime(reservationEntity.getStartTime())
                        .endTime(reservationEntity.getEndTime())
                        .userEntity(reservationEntity.getUserEntity())
                        .isPaid(reservationEntity.getIsPaid())
                        .reservationType(reservationEntity.getReservationType()).build())).toList();
        boolean isOccupied = reservationList.stream().anyMatch((reservation ->
                reservation.getReservationType() == ReservationType.ACTIVE));
        return Optional.of(ParkingSpotFullResponse.builder()
                .id(parkingSpotEntity.getId())
                .number(parkingSpotEntity.getNumber())
                .zone(parkingSpotEntity.getZone())
                .occupied(isOccupied)
                .reservations(reservationList).build());
    }

    @Override
    public int createParkingSpot(ParkingSpotCreateRequest parkingSpotCreateRequest) {
        if (parkingSpotRepository.existsByZoneAndNumber(
                parkingSpotCreateRequest.getZone(),
                parkingSpotCreateRequest.getNumber()
        ))
            return 1; // Exists
        ParkingSpotEntity parkingSpotEntity = ParkingSpotEntity.builder()
                .number(parkingSpotCreateRequest.getNumber())
                .zone(parkingSpotCreateRequest.getZone())
                .build();
        parkingSpotRepository.save(parkingSpotEntity);
        return 0;
    }

    @Override
    public int updateParkingSpot(ParkingSpotDto parkingSpotDto) {
        if (!parkingSpotRepository.existsById(parkingSpotDto.getId()))
            return 1; // Not exists
        if (parkingSpotRepository.existsByZoneAndNumber(parkingSpotDto.getZone(), parkingSpotDto.getNumber()))
            return 2; // Exists
        ParkingSpotEntity parkingSpotEntity = mapper.mapEntityToDto(parkingSpotDto);
        parkingSpotRepository.save(parkingSpotEntity);
        return 0;
    }
}
