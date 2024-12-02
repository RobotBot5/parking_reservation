package ru.robotbot.parking_reservation.mappers;

public interface UserMapper<D, E, R> {

    E mapDtoToEntity(D d);

    D mapEntityToDto(E e);

    R mapEntityToResponse(E e);

}
