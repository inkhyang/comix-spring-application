package com.inkhyang.comixapp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Chapter {
    @Id
    private UUID id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private List<String> images;
}
