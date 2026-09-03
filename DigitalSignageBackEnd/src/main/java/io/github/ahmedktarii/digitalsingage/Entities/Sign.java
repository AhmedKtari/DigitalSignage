package io.github.ahmedktarii.digitalsingage.Entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "owner_id")
    private Long ownerId;

    @Column(nullable = false, length = 25 )
    private String title;


    // Random token used in the public URL ;
    @Column(nullable = false, unique = true, length = 255)
    private String slug;


    private String status;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}