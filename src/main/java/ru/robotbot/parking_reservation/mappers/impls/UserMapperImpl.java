package ru.robotbot.parking_reservation.mappers.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.dto.UserInfoResponse;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.mappers.MapperWithResponse;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements MapperWithResponse<UserDto, UserEntity, UserInfoResponse> {

    private final ModelMapper modelMapper;

    @Override
    public UserEntity mapDtoToEntity(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setRole("ROLE_USER");
        return userEntity;
    }

    @Override
    public UserDto mapEntityToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserInfoResponse mapEntityToResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserInfoResponse.class);
    }
}
