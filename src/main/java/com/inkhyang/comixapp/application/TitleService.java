package com.inkhyang.comixapp.application;

import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entity.Title;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TitleService {
    Optional<Title> getTitleByName(String name);
    List<Title> getAllTitles();
    Title createTitle(String name, String genres,
                 String description, MultipartFile image);
    void removeTitle(String name);
    void updateTitle(String name, String newName, String genres, String description,
                MultipartFile image);

    Optional<Chapter> getChapterByNumberAndTitleName(String name, Integer number);
    List<Chapter> getAllChaptersByTitleName(String name);
    Chapter createChapter(String name, MultipartFile[] files);
    void removeChapter(String name, Integer number);
    void removeAllChapters(String name);
}
