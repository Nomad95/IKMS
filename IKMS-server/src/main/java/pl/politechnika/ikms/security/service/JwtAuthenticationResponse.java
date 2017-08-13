package pl.politechnika.ikms.security.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtAuthenticationResponse{
    private final String token;
}
