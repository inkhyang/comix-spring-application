package com.inkhyang.comixapp.application;

import com.inkhyang.comixapp.entity.Title;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TitleService {
    Optional<Title> getByName(String name);
    List<Title> getAll();
    Title create(String name, String genres,
                 String description, MultipartFile image);
    void remove(String name);
    void update(String name, String newName, String genres, String description,
                MultipartFile image);
}
