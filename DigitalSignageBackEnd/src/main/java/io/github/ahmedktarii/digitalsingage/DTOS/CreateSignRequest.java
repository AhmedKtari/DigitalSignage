package io.github.ahmedktarii.digitalsingage.DTOS;


import io.github.ahmedktarii.digitalsingage.Entities.signStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
//Coming soon
public class CreateSignRequest {
    private String userEmailRequest;
    private Long mediaIdRequest;
    private Timestamp signaStartDateRequest;
    private Timestamp signaEndDateRequest;
    private String signTitleRequest;
    @Enumerated(EnumType.STRING)
    private signStatus signStatusRequest;

}
