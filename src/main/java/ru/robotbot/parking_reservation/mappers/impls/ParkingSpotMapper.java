package ru.robotbot.parking_reservation.mappers.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotDto;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotFullResponse;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotsResponse;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.mappers.MapperWithResponse;

@Component
@RequiredArgsConstructor
public class ParkingSpotMapper implements MapperWithResponse<ParkingSpotDto, ParkingSpotEntity, ParkingSpotsResponse> {

    private final ModelMapper mapper;

    @Override
    public ParkingSpotEntity mapDtoToEntity(ParkingSpotDto parkingSpotDto) {
        return mapper.map(parkingSpotDto, ParkingSpotEntity.class);
    }

    @Override
    public ParkingSpotDto mapEntityToDto(ParkingSpotEntity parkingSpotEntity) {
        return mapper.map(parkingSpotEntity, ParkingSpotDto.class);
    }

    @Override
    public ParkingSpotsResponse mapEntityToResponse(ParkingSpotEntity parkingSpotEntity) {
        return ParkingSpotsResponse.builder()
                .id(parkingSpotEntity.getId())
                .number(parkingSpotEntity.getNumber())
                .zone(parkingSpotEntity.getZone())
                .price(parkingSpotEntity.getZone().getRate())
                .build();
    }
}
