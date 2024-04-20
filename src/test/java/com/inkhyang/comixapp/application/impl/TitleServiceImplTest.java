package com.inkhyang.comixapp.application.impl;

import com.inkhyang.comixapp.application.DocumentClient;
import com.inkhyang.comixapp.entity.Chapter;
import com.inkhyang.comixapp.entity.Title;
import com.inkhyang.comixapp.entityRepository.ChapterRepository;
import com.inkhyang.comixapp.entityRepository.TitleRepository;
import com.inkhyang.comixapp.util.DataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TitleServiceImplTest {
    @Mock
    private DocumentClient documentClient;
    @Mock
    private ChapterRepository chapterRepository;
    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private TitleServiceImpl titleService;

    @Test
    void should_call_save_title_repository(){
        //given
        Title title = DataUtils.getTitleWithoutChaptersTransient();
        BDDMockito.given(titleRepository.save(any(Title.class)))
                .willReturn(DataUtils.getTitleWithoutChaptersPersisted());
        BDDMockito.given(documentClient.upload(any(MultipartFile.class)))
                .willReturn("testImage");

        //when
        Title savedTitle = titleService.createTitle(
                title.getName(),
                title.getGenres(),
                title.getDescription(),
                new MockMultipartFile(title.getName(), new byte[]{Byte.parseByte("1")})
        );
        //then
        assertThat(savedTitle).isNotNull();
    }
    @Test
    void should_call_get_title_repository(){
        //given
        Title savedTitle = DataUtils.getTitleWithoutChaptersPersisted();
        BDDMockito.given(titleRepository.findByName(anyString()))
                .willReturn(Optional.of(savedTitle));

        //when
        Title obtainedTitle = titleService.getTitleByName(savedTitle.getName()).orElse(null);
        //then
        assertThat(obtainedTitle).isNotNull();
    }
    @Test
    void should_call_update_title_repository(){
        //given
        Title titleToUpdate = DataUtils.getTitleWithoutChaptersPersisted();
        BDDMockito.given(titleRepository.findByName(anyString()))
                .willReturn(Optional.ofNullable(titleToUpdate));
        BDDMockito.given(titleRepository.save(any(Title.class)))
                .willReturn(titleToUpdate);
        BDDMockito.given(documentClient.upload(any(MultipartFile.class)))
                .willReturn("testImage");

        //when
        Title updatedTitle = titleService.updateTitle(
                titleToUpdate.getName(),
                titleToUpdate.getName(),
                titleToUpdate.getGenres(),
                titleToUpdate.getDescription(),
                new MockMultipartFile(titleToUpdate.getName(), new byte[]{Byte.parseByte("1")})
        );
        //then
        assertThat(updatedTitle).isNotNull();
        verify(titleRepository, times(1)).save(any(Title.class));
    }
    @Test
    void should_call_delete_title_repository(){
        //given
        Title savedTitle = DataUtils.getTitleWithoutChaptersPersisted();
        BDDMockito.given(titleRepository.findByName(anyString()))
                .willReturn(Optional.of(savedTitle));

        //when
        titleService.removeTitle(savedTitle.getName());
        //then
        verify(titleRepository, times(1)).deleteByName(savedTitle.getName());
    }
    @Test
    void should_call_save_chapter_repository(){
        //given
        Chapter chapter = DataUtils.getChapterTransient();
        BDDMockito.given(titleRepository.findByName(anyString()))
                        .willReturn(Optional.of(DataUtils.getTitleWithoutChaptersPersisted()));
        BDDMockito.given(chapterRepository.save(any(Chapter.class)))
                .willReturn(DataUtils.getChapterPersisted());
        BDDMockito.given(documentClient.upload(any(MultipartFile.class)))
                .willReturn("testImage");

        //when
        Chapter savedChapter = titleService.createChapter(
                chapter.getTitle().getName(),
                new MockMultipartFile[]{new MockMultipartFile("test", new byte[]{Byte.parseByte("1")}),new MockMultipartFile("test1", new byte[]{Byte.parseByte("2")})}
        );
        //then
        assertThat(savedChapter).isNotNull();
    }
    @Test
    void should_call_get_chapter_repository(){
        //given
        Chapter savedChapter = DataUtils.getChapterPersisted();
        BDDMockito.given(chapterRepository.findChapterByTitleNameAndNumber(
                anyString(), anyInt()))
                .willReturn(Optional.ofNullable(savedChapter));

        //when
        Chapter obtainedChapter = titleService
                .getChapterByNumberAndTitleName(savedChapter.getTitle().getName(), savedChapter.getNumber())
                .orElse(null);
        //then
        assertThat(obtainedChapter).isNotNull();
    }
    @Test
    void should_call_delete_chapter_repository(){
        //given
        Chapter savedChapter = DataUtils.getChapterPersisted();
        BDDMockito.given(chapterRepository.findChapterByTitleNameAndNumber(
                anyString(), anyInt()))
                .willReturn(Optional.ofNullable(savedChapter));
        //when
        titleService.removeChapter(savedChapter.getTitle().getName(), savedChapter.getNumber());
        //then
        verify(chapterRepository, times(1)).delete(savedChapter);
    }
}