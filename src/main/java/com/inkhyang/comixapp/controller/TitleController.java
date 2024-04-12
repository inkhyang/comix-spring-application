package com.inkhyang.comixapp.controller;

import com.inkhyang.comixapp.application.impl.TitleServiceImpl;
import com.inkhyang.comixapp.dto.TitleDto;
import com.inkhyang.comixapp.entity.Title;
import com.inkhyang.comixapp.mapper.TitleDtoMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleController {
    private final TitleServiceImpl service;
    private final TitleDtoMapper mapper;

    public TitleController(TitleServiceImpl service, TitleDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @GetMapping("/{name}")
    public TitleDto one(@PathVariable String name){
        return service.getByName(name)
                .map(mapper::toDto)
                .orElseThrow();
    }
    @GetMapping
    public List<TitleDto> all(){
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }
    @PostMapping
    public TitleDto create(@RequestBody TitleDto titleDto){

        Title title = service.create(
                titleDto.name(), titleDto.genres(),
                titleDto.description(), titleDto.image()
        );
        return mapper.toDto(title);
    }
    @PutMapping
    public void update(@PathVariable String name, @RequestBody TitleDto titleDto){
        service.update(
                name,
                titleDto.name(), titleDto.genres(),
                titleDto.description(), titleDto.image()
        );
    }
    @DeleteMapping
    public void delete(@PathVariable String name){
        service.remove(name);
    }
}
