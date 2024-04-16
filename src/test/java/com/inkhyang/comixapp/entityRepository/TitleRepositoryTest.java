package com.inkhyang.comixapp.entityRepository;

import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entity.Title;
import com.inkhyang.comixapp.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TitleRepositoryTest {
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    @BeforeEach
    void setUp(){
        titleRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save title without chapters functionality")
    void should_save_title_without_chapters(){
        //given
        var givenTitle = DataUtils.getTitleWithoutChaptersTransient();
        //when
        var savedTitle = titleRepository.save(givenTitle);
        //then
        assertThat(savedTitle).isNotNull();
        assertThat(savedTitle.getId()).isNotNull();
    }
    @Test
    @DisplayName("Test save title with chapters functionality")
    void should_save_title_with_chapters(){
        //given
        var givenTitle = DataUtils.getTitleWithChaptersTransient();
        //when
        var savedTitle = titleRepository.save(givenTitle);
        chapterRepository.saveAll(givenTitle.getChapters());
        //then
        assertThat(savedTitle).isNotNull();
        assertThat(savedTitle.getId()).isNotNull();
    }
    @Test
    @DisplayName("Test find title functionality")
    void should_find_title(){
        //given
        var givenTitle = DataUtils.getTitleWithChaptersTransient();
        titleRepository.save(givenTitle);
        chapterRepository.saveAll(givenTitle.getChapters());
        //when
        var obtainedTitle = titleRepository.findByName(givenTitle.getName()).orElse(null);
        //then
        assertThat(obtainedTitle).isNotNull();
        assertThat(obtainedTitle.getName()).isEqualTo(givenTitle.getName());
    }
    @Test
    @DisplayName("Test find title negative functionality")
    void should_not_find_title(){
        //given
        //when
        var obtainedTitle = titleRepository.findByName("testcase123").orElse(null);
        //then
        assertThat(obtainedTitle).isNull();
    }

    @Test
    void should_find_all_chapters_by_title_name(){
        //given
        var givenTitle = DataUtils.getTitleWithChaptersTransient();
        titleRepository.save(givenTitle);
        //when
        List<Chapter> obtainedChapters = chapterRepository.findAllChaptersByTitleName(givenTitle.getName());
        //then
        assertThat(obtainedChapters).isNotNull();
        assertEquals(obtainedChapters.get(0).getImages(), givenTitle.getChapters().get(0).getImages());

    }
    @Test
    void should_find_one_chapter(){
        //given
        var givenTitle = DataUtils.getTitleWithChaptersTransient();
        titleRepository.save(givenTitle);
        //when
        Chapter obtainedChapter = chapterRepository.findChapterByTitleNameAndNumber(givenTitle.getName(), 1).orElse(null);
        //then
        assertThat(obtainedChapter).isNotNull();
    }

    @Test
    void should_save_chapter(){
        //given
        var givenTitle = DataUtils.getTitleWithoutChaptersTransient();
        var givenChapter = DataUtils.getChapterTransient();
        //when
        givenChapter.setTitle(givenTitle);
        givenTitle.getChapters().add(givenChapter);
        titleRepository.save(givenTitle);
        //then
        List<Chapter> savedChapter = chapterRepository.findAll();
        assertThat(savedChapter).isNotNull().hasSize(1);
    }
    @Test
    void should_delete_chapter(){
        //given
        var givenTitle = DataUtils.getTitleWithChaptersTransient();
        titleRepository.save(givenTitle);
        assertThat(givenTitle.getChapters()).hasSize(givenTitle.getChapters().size());
        //when
        chapterRepository.delete(givenTitle.getChapters().get(0));
        //then
        assertThat(chapterRepository.findAll()).hasSize(givenTitle.getChapters().size() - 1);
    }

    @Test
    void should_update_title(){
        //given
        String updatedDescription = " test for update functionality";
        var givenTitle = DataUtils.getTitleWithChaptersTransient();
        titleRepository.save(givenTitle);
        //when
        Title titleToUpdate = titleRepository.findByName(givenTitle.getName()).orElse(null);
        titleToUpdate.setDescription(updatedDescription);
        Title updatedTitle = titleRepository.save(titleToUpdate);
        //then
        assertThat(updatedTitle.getDescription()).isNotNull().isEqualTo(updatedDescription);
    }
}