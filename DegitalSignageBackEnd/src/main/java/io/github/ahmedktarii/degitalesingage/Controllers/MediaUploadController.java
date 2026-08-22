package io.github.ahmedktarii.degitalesingage.Controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.ahmedktarii.degitalesingage.DTOS.MediaUploadRequest;
import io.github.ahmedktarii.degitalesingage.Entities.Media;
import io.github.ahmedktarii.degitalesingage.Services.MediaService;
import io.github.ahmedktarii.degitalesingage.Services.UserService;
import io.github.ahmedktarii.degitalesingage.Utils.FileHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
@CrossOrigin
@RequiredArgsConstructor
public class MediaUploadController {

    // calling cloudinary
    private final Cloudinary cloudinary;
    // calling userService
    private final UserService userService;
    // calling mediaService
    private final MediaService mediaService;




    @PostMapping("/MediaUpload")
    public ResponseEntity<?> mediaUpload(@ModelAttribute MediaUploadRequest request) throws Exception {
        // checking if the file empty
        if (request.getFile().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "file is empty"));
        }

        byte[] fileBytes = request.getFile().getBytes();
        String fileHash = FileHashUtil.computeFileHash(fileBytes);
        // a list of media that has the file size with the request one
        List<Media> filteredMedia = mediaService.filterBySize(request.getFile().getSize());
        boolean exists = false;
        //looping threw that list to see if there is a matching media with the request
        for (Media media : filteredMedia) {
            if (fileHash.equals(media.getFileHash())) {
                exists = true;
                break;
            }

        }

        try {
            String url;
            String publicId;
            String resourceType;
            String format;
            if (!exists) {
                // if there is no duplicate media in cloud upload a copy and return matadata
            Map uploadResult = cloudinary.uploader().upload(
                    request.getFile().getBytes(),
                    ObjectUtils.emptyMap()
                                );
                url = (String) uploadResult.get("secure_url");
                publicId = (String) uploadResult.get("public_id");
                resourceType = (String) uploadResult.get("resource_type");
                format = (String) uploadResult.get("format");// "image" or "video"
            }
            else{
                // if there is a duplicate avoid uploding to cloud and the metadata will be shared
                Media matchingMedia = mediaService.findByHash(fileHash);
                url =matchingMedia.getUrl();
                publicId =matchingMedia.getMediaPublicId();
                resourceType =matchingMedia.getType();
                format = matchingMedia.getFormat();// "image" or "video"

            }
            // the rest of the data size|filename|ownerId|ownerCode
            long size = request.getFile().getSize();
            String fileName = request.getFile().getOriginalFilename();
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            Long ownerId = userService.grapIdByEmail(request.getEmailRequest());
            if (ownerId == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "No user found for that email"));
            }
            String ownerCode = "U" + String.format("%02d", ownerId);
            // building the new media
            Media newMedia = Media.builder()
                    .url(url)
                    .mediaPublicId(publicId)
                    .type(resourceType)
                    .uploadedBy(ownerId)
                    .createdAt(LocalDateTime.now())
                    .size(size)
                    .ownerCode(ownerCode)
                    .format(format)
                    .fileName(fileName)
                    .fileHash(fileHash)
                    .build();

            mediaService.save(newMedia);
            // all good
            return ResponseEntity.ok(Map.of(
                    "message", "upload successful",
                    "url", url
            ));
        // return an error
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Upload failed: " + e.getMessage()));
        }



    }
}