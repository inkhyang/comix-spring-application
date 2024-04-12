package com.inkhyang.comixapp.application.impl;

import com.inkhyang.comixapp.application.DocumentClient;
import com.inkhyang.comixapp.application.TitleService;
import com.inkhyang.comixapp.entity.Title;
import com.inkhyang.comixapp.entityRepository.TitleRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TitleServiceImpl implements TitleService {
    private final TitleRepository titleRepository;
    private final DocumentClient client;

    public TitleServiceImpl(TitleRepository titleRepository, DocumentClient client) {
        this.titleRepository = titleRepository;
        this.client = client;
    }
    public Optional<Title> getByName(String name) {
        return titleRepository.findByName(name);
    }

    public List<Title> getAll() {
        return titleRepository.findAll();
    }

    @Transactional
    public Title create(String name, String genres,
                        String description, MultipartFile image) {
        Title title = new Title();
        title.setName(name);
        title.setGenres(genres);
        title.setDescription(description);
        title.setImage(client.upload(image));
        return titleRepository.save(title);
    }

    @SneakyThrows
    @Transactional
    public void remove(String name) {
        Title title = titleRepository.findByName(name).orElseThrow();
        titleRepository.delete(title);
        client.delete(name);
    }

    @SneakyThrows
    @Transactional
    public void update(String name, String newName, String genres,
                       String description, MultipartFile image) {
        Title title = titleRepository.findByName(name).orElseThrow();
        title.setName(newName);
        title.setGenres(genres);
        title.setDescription(description);
        client.delete(title.getImage());
        title.setImage(client.upload(image));
        titleRepository.save(title);
    }
}
