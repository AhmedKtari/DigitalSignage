package io.github.ahmedktarii.digitalsingage.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String emailRequest;
    private String passwordRequest;
}
