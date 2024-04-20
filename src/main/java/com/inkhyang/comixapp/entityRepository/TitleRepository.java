package com.inkhyang.comixapp.entityRepository;

import com.inkhyang.comixapp.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TitleRepository extends JpaRepository<Title, UUID> {
    Optional<Title> findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);
}
