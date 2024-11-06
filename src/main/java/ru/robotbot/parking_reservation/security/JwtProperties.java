package ru.robotbot.parking_reservation.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class JwtProperties {

    private String secretKey;
}
