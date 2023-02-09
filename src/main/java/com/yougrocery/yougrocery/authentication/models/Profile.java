package com.yougrocery.yougrocery.authentication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "display_name", length = 101)
    @Size(max = 101)
    private String displayName;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;


}