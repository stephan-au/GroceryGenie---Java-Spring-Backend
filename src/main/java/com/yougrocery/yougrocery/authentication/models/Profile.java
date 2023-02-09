package com.yougrocery.yougrocery.authentication.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;


}