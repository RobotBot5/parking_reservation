package ru.robotbot.parking_reservation.mappers;

public interface Mapper<A, B> {

    B mapDtoToEntity(A a);

    A mapEntityToDto(B b);

}
