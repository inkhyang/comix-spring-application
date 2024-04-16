package com.inkhyang.comixapp.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Chapter {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private List<String> images;
    @ManyToOne(fetch = FetchType.LAZY)
    private Title title;
}
