package com.inkhyang.comixapp.controller;

import com.inkhyang.comixapp.application.impl.ChapterServiceImpl;
import com.inkhyang.comixapp.dto.ChapterDto;
import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.mapper.ChapterDtoMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
/*@RequestMapping("/titles/{title}")*/
@RequestMapping("/chapters")
public class ChapterController {
    private final ChapterServiceImpl service;
    private final ChapterDtoMapper mapper;

    public ChapterController(ChapterServiceImpl service, ChapterDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ChapterDto one(@PathVariable UUID id){
        return service.getById(id)
                .map(mapper::toDto)
                .orElseThrow();
    }
    @GetMapping
    public List<ChapterDto> all(){
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
    @PostMapping
    public ChapterDto create(@RequestBody ChapterDto chapterDto){
        Chapter chapter = service.create(chapterDto.files());
        return mapper.toDto(chapter);
    }

    @PutMapping
    public void update(@PathVariable UUID id, @RequestBody ChapterDto chapterDto){
        service.update(id, chapterDto.files());
    }
    @DeleteMapping
    public void delete(@PathVariable UUID id){
        service.remove(id);
    }
}
