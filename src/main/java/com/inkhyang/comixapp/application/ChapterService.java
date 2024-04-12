package com.inkhyang.comixapp.application;

import com.inkhyang.comixapp.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChapterService {
    Optional<Chapter> getById(UUID id);
    List<Chapter> getAll();
    Chapter create(MultipartFile[] files);
    void remove(UUID id);
    void update(UUID id, MultipartFile[] files);
}
