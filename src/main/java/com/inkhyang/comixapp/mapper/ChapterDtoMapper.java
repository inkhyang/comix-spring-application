package com.inkhyang.comixapp.mapper;

import com.inkhyang.comixapp.dto.ChapterDto;
import com.inkhyang.comixapp.entity.Chapter;
import org.mapstruct.Mapper;

@Mapper
public interface ChapterDtoMapper {
    Chapter toEntity(ChapterDto chapterDto);
    ChapterDto toDto(Chapter chapter);
}
