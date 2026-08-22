package io.github.ahmedktarii.degitalesingage.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "media_public_id")
    private String mediaPublicId;

    @Column(name = "Format")
    private String format;

    // "image" or "video"
    @Column(nullable = false, length = 5)
    private String type;


    @JoinColumn(name = "uploaded_by", nullable = false)
    private long uploadedBy;

    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt;

    @Column()
    private long size;

    @Column(nullable = false)
    private String fileHash;

    @Column
    private String ownerCode;
}
