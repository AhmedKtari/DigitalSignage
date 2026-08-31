package io.github.ahmedktarii.digitalsingage.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String userCode;
    @Column
    private String password;
    //  Admin / client
    @Enumerated(EnumType.STRING)
    private Roles role;
    @CreationTimestamp
    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt;



}
