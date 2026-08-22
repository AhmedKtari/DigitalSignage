package io.github.ahmedktarii.degitalesingage.DTOS;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaShowResponse {
    private long id ;
    private String name;
    private String link;
    private String type;
    private long size;
}
