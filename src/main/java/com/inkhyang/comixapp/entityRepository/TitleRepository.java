package com.inkhyang.comixapp.entityRepository;

import com.inkhyang.comixapp.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TitleRepository extends JpaRepository<Title, UUID> {
    Optional<Title> findByName(String name);

    void deleteByName(String name);
}
