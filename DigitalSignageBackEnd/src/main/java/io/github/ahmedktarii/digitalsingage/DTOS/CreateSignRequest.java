package io.github.ahmedktarii.digitalsingage.DTOS;


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
    private Timestamp signStartDateRequest;
    private Timestamp signEndDateRequest;
    private String signTitleRequest;

    private String signStatusRequest;

}
