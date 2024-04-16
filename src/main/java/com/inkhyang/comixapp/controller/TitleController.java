package com.inkhyang.comixapp.controller;

import com.inkhyang.comixapp.application.impl.TitleServiceImpl;
import com.inkhyang.comixapp.dto.ChapterDto;
import com.inkhyang.comixapp.dto.TitleDto;
import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entity.Title;
import com.inkhyang.comixapp.mapper.ChapterDtoMapper;
import com.inkhyang.comixapp.mapper.TitleDtoMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private final TitleServiceImpl service;
    private final TitleDtoMapper titleMapper;
    private final ChapterDtoMapper chapterMapper;

    public TitleController(TitleServiceImpl service, TitleDtoMapper titleMapper, ChapterDtoMapper chapterMapper) {
        this.service = service;
        this.titleMapper = titleMapper;
        this.chapterMapper = chapterMapper;
    }

    @GetMapping("/{name}")
    public TitleDto one(@PathVariable String name){
        return service.getTitleByName(name)
                .map(titleMapper::toDto)
                .orElseThrow();
    }
    @GetMapping
    public List<TitleDto> all(){
        return service.getAllTitles().stream()
                .map(titleMapper::toDto)
                .toList();
    }
    @PostMapping
    public TitleDto create(@RequestBody TitleDto titleDto){

        Title title = service.createTitle(
                titleDto.name(), titleDto.genres(),
                titleDto.description(), titleDto.image()
        );
        return titleMapper.toDto(title);
    }
    @PutMapping("/{name}")
    public void update(@PathVariable String name, @RequestBody TitleDto titleDto){
        service.updateTitle(
                name,
                titleDto.name(), titleDto.genres(),
                titleDto.description(), titleDto.image()
        );
    }
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name){
        service.removeTitle(name);
    }

    @GetMapping("/{name}/chapters")
    public List<ChapterDto> allChapters(@PathVariable String name){
        return service.getAllChaptersByTitleName(name).stream()
                .map(chapterMapper::toDto)
                .toList();
    }
    @GetMapping("/{name}/chapters/{number}")
    public ChapterDto oneChapter(@PathVariable String name, @PathVariable Integer number){
        return service.getChapterByNumberAndTitleName(name, number)
                .map(chapterMapper::toDto)
                .orElseThrow();
    }
    @PostMapping("/{name}")
    public ChapterDto createChapter(@PathVariable String name, @RequestBody ChapterDto chapterDto){
        Chapter chapter = service.createChapter(name, chapterDto.files());
        return chapterMapper.toDto(chapter);
    }

    @DeleteMapping("/{name}/chapter/{number}")
    public void deleteChapter(@PathVariable String name, @PathVariable Integer number){
        service.removeChapter(name, number);
    }
}
