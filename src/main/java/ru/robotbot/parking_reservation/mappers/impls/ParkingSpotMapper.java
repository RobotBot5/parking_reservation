package ru.robotbot.parking_reservation.mappers.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotDto;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.mappers.Mapper;

@Component
@RequiredArgsConstructor
public class ParkingSpotMapper implements Mapper<ParkingSpotEntity, ParkingSpotDto> {

    private final ModelMapper mapper;

    @Override
    public ParkingSpotDto mapTo(ParkingSpotEntity parkingSpotEntity) {
        return mapper.map(parkingSpotEntity, ParkingSpotDto.class);
    }

    @Override
    public ParkingSpotEntity mapFrom(ParkingSpotDto parkingSpotDto) {
        return mapper.map(parkingSpotDto, ParkingSpotEntity.class);
    }
}
