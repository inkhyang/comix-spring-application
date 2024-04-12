package com.inkhyang.comixapp.application.impl;

import com.inkhyang.comixapp.application.ChapterService;
import com.inkhyang.comixapp.application.DocumentClient;
import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entityRepository.ChapterRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final DocumentClient client;

    public ChapterServiceImpl(ChapterRepository chapterRepository, DocumentClient client) {
        this.chapterRepository = chapterRepository;
        this.client = client;
    }

    public Optional<Chapter> getById(UUID id) {
        return chapterRepository.findById(id);
    }

    public List<Chapter> getAll() {
        return chapterRepository.findAll();
    }

    @Transactional
    public Chapter create(MultipartFile[] files) {
        Chapter chapter = new Chapter();
        List<String> images = Arrays.stream(files)
                .map(client::upload).toList();
        chapter.setImages(images);
        chapter.setDate(LocalDate.now());
        return chapterRepository.save(chapter);
    }

    @SneakyThrows
    @Transactional
    public void update(UUID id, MultipartFile[] files) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow();
        List<String> images = Arrays.stream(files)
                .map(client::upload).toList();
        for (String image : chapter.getImages()) {
            client.delete(image);
        }
        chapter.setImages(images);
        chapter.setDate(LocalDate.now());
        chapterRepository.save(chapter);
    }

    @SneakyThrows
    @Transactional
    public void remove(UUID id) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow();
        chapterRepository.deleteById(id);
        for (String image : chapter.getImages()) {
            client.delete(image);
        }
    }

}
