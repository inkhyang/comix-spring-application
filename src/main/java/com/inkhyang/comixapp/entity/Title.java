package com.inkhyang.comixapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Title {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    private String genres;
    private String description;
    @Column(nullable = false)
    private String image;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;
}
