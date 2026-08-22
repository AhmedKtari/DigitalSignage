package io.github.ahmedktarii.degitalesingage.Controllers;


import io.github.ahmedktarii.degitalesingage.DTOS.MediaShowRequest;
import io.github.ahmedktarii.degitalesingage.DTOS.MediaShowResponse;
import io.github.ahmedktarii.degitalesingage.Entities.Media;
import io.github.ahmedktarii.degitalesingage.Services.MediaService;
import io.github.ahmedktarii.degitalesingage.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/media")
@CrossOrigin
@RequiredArgsConstructor
public class ShowMediaController {
    // calling userService
    private final UserService userService;
    // calling mediaService
    private final MediaService mediaService;

    @GetMapping("/showMedia")
    public ResponseEntity<?> uploadMedia(@RequestParam String emailRequest ) {
        System.out.println(emailRequest.toString());
        try {
           long uploadedBy = userService.grapIdByEmail(emailRequest);
           List<Media> listMedia = mediaService.findAllByUploadedByOrderByCreatedAtDesc(uploadedBy);
           List<MediaShowResponse> responseList = new ArrayList<>();
           for (Media media : listMedia) {
               MediaShowResponse mediaShowResponse = MediaShowResponse.builder()
                       .id(media.getId())
                       .size(media.getSize())
                       .name(media.getFileName() + media.getFormat())
                       .type(media.getType())
                       .link(media.getUrl())
                       .build();
               responseList.add(mediaShowResponse);
           }
           System.out.println(responseList.toString());
           return ResponseEntity.ok(responseList);
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
           return ResponseEntity.status(500).body(Map.of(
                   "message", "Upload failed: " + e.getMessage()));
       }
    }

}
