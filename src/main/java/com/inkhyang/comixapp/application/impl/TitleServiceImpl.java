package com.inkhyang.comixapp.application.impl;

import com.inkhyang.comixapp.application.DocumentClient;
import com.inkhyang.comixapp.application.TitleService;
import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entity.Title;
import com.inkhyang.comixapp.entityRepository.ChapterRepository;
import com.inkhyang.comixapp.entityRepository.TitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TitleServiceImpl implements TitleService {
    private final TitleRepository titleRepository;
    private final ChapterRepository chapterRepository;
    private final DocumentClient client;

    public TitleServiceImpl(TitleRepository titleRepository, ChapterRepository chapterRepository, DocumentClient client) {
        this.titleRepository = titleRepository;
        this.chapterRepository = chapterRepository;
        this.client = client;
    }
    public Optional<Title> getTitleByName(String name) {
        return titleRepository.findByName(name);
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Title createTitle(String name, String genres, String description, MultipartFile image) {
        Title title = new Title();
        title.setName(name);
        title.setGenres(genres);
        title.setDescription(description);
        title.setImage(client.upload(image));
        return titleRepository.save(title);
    }

    public void removeTitle(String name) {
        Title title = titleRepository.findByName(name).orElseThrow();
        titleRepository.deleteByName(name);
        client.delete(title.getImage());
    }

    public Title updateTitle(String name, String newName, String genres, String description, MultipartFile image) {
        Title title = titleRepository.findByName(name).orElse(null);
        title.setName(newName);
        title.setGenres(genres);
        title.setDescription(description);
        client.delete(title.getImage());
        title.setImage(client.upload(image));
        return titleRepository.save(title);
    }

    public Optional<Chapter> getChapterByNumberAndTitleName(String titleName, Integer number) {
        return chapterRepository.findChapterByTitleNameAndNumber(titleName, number);
    }

    public List<Chapter> getAllChaptersByTitleName(String titleName) {
        return chapterRepository.findAllChaptersByTitleName(titleName);
    }

    public Chapter createChapter(String titleName, MultipartFile[] files) {
        Title title = titleRepository.findByName(titleName).orElseThrow();
        Chapter chapter = new Chapter();
        List<String> images = Arrays.stream(files)
                .map(client::upload).toList();
        chapter.setImages(images);
        chapter.setDate(LocalDate.now());
        chapter.setTitle(title);
        title.getChapters().add(chapter);
        return chapterRepository.save(chapter);
    }

    public void removeChapter(String titleName, Integer number) {
        Chapter chapter = chapterRepository.findChapterByTitleNameAndNumber(titleName, number).orElseThrow();
        chapterRepository.delete(chapter);
    }

    public void removeAllChapters(String titleName) {
        Title title = titleRepository.findByName(titleName).orElseThrow();
        chapterRepository.deleteAll(title.getChapters());
    }

}
