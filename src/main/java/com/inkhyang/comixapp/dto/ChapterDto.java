package com.inkhyang.comixapp.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record ChapterDto(
        LocalDate date,
        MultipartFile[] files
) {
}
