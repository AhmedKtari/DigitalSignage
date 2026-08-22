package io.github.ahmedktarii.degitalesingage.Services;

import io.github.ahmedktarii.degitalesingage.Entities.Media;
import io.github.ahmedktarii.degitalesingage.Repositories.MediaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public void save(Media media) {
        mediaRepository.save(media);
    }

    public List<Media> filterBySize(long size) {
        return mediaRepository.findMediaBySize(size);
    }
    public Media findByHash(String hash) {
        return mediaRepository.findByFileHash(hash);
    }
    public List<Media> findAllByUploadedByOrderByCreatedAtDesc(long UploadedBy) {
        return mediaRepository.findAllByUploadedByOrderByCreatedAtDesc(UploadedBy);
    }

}

