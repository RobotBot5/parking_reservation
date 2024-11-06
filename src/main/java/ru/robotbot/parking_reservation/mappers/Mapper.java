package ru.robotbot.parking_reservation.mappers;

public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);

}
