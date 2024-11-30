package ru.robotbot.parking_reservation.services;

public interface CameraService {

    int canOpenBarrierAtEntry(String stateNumber);

    int canOpenBarrierAtExit(String stateNumber);

}
