package io.github.ahmedktarii.digitalsingage.DTOS;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MediaUploadRequest {
    private MultipartFile file;
    private String emailRequest;
}