package com.inkhyang.comixapp.mapper;

import com.inkhyang.comixapp.dto.TitleDto;
import com.inkhyang.comixapp.entity.Title;
import org.mapstruct.Mapper;

@Mapper(uses = {ChapterDtoMapper.class})
public interface TitleDtoMapper {
    Title toEntity(TitleDto titleDto);
    TitleDto toDto(Title title);
}
