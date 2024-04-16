package com.inkhyang.comixapp.entityRepository;

import com.inkhyang.comixapp.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
    @Query(value = """
            SELECT * FROM Chapter c
            WHERE c.title_id IN (SELECT t.id FROM Title t WHERE t.name = ?1)
            """, nativeQuery = true)
    List<Chapter> findAllChaptersByTitleName(String titleName);
    @Query(value = """
            SELECT * FROM Chapter c
            WHERE c.title_id IN (SELECT t.id FROM Title t WHERE t.name = ?1)
            AND c.number = ?2
            """, nativeQuery = true)
    Optional<Chapter> findChapterByTitleNameAndNumber(String titleName, Integer number);
}
