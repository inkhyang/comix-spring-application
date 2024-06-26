package com.inkhyang.comixapp.util;

import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entity.Title;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataUtils {

    public static Title getTitleWithoutChaptersTransient(){
        return Title.builder()
                .name("title without chapters")
                .genres("test")
                .description("test")
                .image("srr")
                .chapters(new ArrayList<>())
                .build();
    }
    public static Title getTitleWithoutChaptersPersisted(){
        return Title.builder()
                .id(UUID.randomUUID())
                .name("title without chapters")
                .genres("test")
                .description("test")
                .image("srr")
                .chapters(new ArrayList<>())
                .build();
    }
    public static Title getTitleWithChaptersTransient(){
        var title = Title.builder()
                .name("title with chapters")
                .genres("test")
                .description("test")
                .image("srr")
                .chapters(new ArrayList<>())
                .build();
        var chapter = Chapter.builder()
                .number(1)
                .images(List.of("srrf", "sfff"))
                .date(LocalDate.now())
                .title(title)
                .build();
        var chapter2 = Chapter.builder()
                .number(2)
                .images(List.of("srrff", "sdddff"))
                .date(LocalDate.now())
                .title(title)
                .build();
        title.getChapters().addAll(List.of(chapter, chapter2));
        return title;
    }
    public static Chapter getChapterTransient(){
        var title = Title.builder()
                .name("title with chapters")
                .genres("test")
                .description("test")
                .image("srr")
                .chapters(new ArrayList<>())
                .build();
        return Chapter.builder()
                .title(title)
                .number(1)
                .images(List.of("al", "co", "ho", "lic"))
                .date(LocalDate.now())
                .build();
    }
    public static Chapter getChapterPersisted(){
        var title = Title.builder()
                .name("title with chapters")
                .genres("test")
                .description("test")
                .image("srr")
                .chapters(new ArrayList<>())
                .build();
        return Chapter.builder()
                .id(UUID.randomUUID())
                .title(title)
                .number(1)
                .images(List.of("al", "co", "ho", "lic"))
                .date(LocalDate.now())
                .build();
    }
}
