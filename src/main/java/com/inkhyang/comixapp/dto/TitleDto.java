package com.inkhyang.comixapp.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record TitleDto(
        String name,
        String genres,
        String description,
        MultipartFile image,
        List<ChapterDto> chapters
) {
}
