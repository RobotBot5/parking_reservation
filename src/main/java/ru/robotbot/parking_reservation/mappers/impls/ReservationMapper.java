package ru.robotbot.parking_reservation.mappers.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.mappers.Mapper;
import ru.robotbot.parking_reservation.repositories.ParkingSpotRepository;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements Mapper<ReservationEntity, ReservationDto> {
    private final ModelMapper modelMapper;

    @Override
    public ReservationDto mapTo(ReservationEntity reservationEntity) {
        return modelMapper.map(reservationEntity, ReservationDto.class);
    }

    @Override
    public ReservationEntity mapFrom(ReservationDto reservationDto) {
        ReservationEntity reservationEntity = modelMapper.map(reservationDto, ReservationEntity.class);
        reservationEntity.setId(null);
        reservationEntity.setIsPaid(false);
        reservationEntity.setIsExtendedMustPay(false);
        reservationEntity.setAmountToExtend(0);
        reservationEntity.setReservationType(ReservationType.ACTIVE);
        return reservationEntity;
    }
}
