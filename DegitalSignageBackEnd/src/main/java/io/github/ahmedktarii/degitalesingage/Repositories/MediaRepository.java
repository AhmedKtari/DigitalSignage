package io.github.ahmedktarii.degitalesingage.Repositories;

import io.github.ahmedktarii.degitalesingage.Entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findMediaBySize(long size);
    //List<Media> findAllByEmailOrderByDateDesc(String email);
    List<Media>findAllByUploadedByOrderByCreatedAtDesc(long uploadedBy);
    Media findByFileHash(String fileHash);
}