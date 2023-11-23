package com.kwang23.fountainpen.domain.searchblog;

import com.kwang23.fountainpen.domain.event.KeyWordSearchEvent;
import com.kwang23.fountainpen.external.api.blog.BlogSearchExternalApiAdapter;
import com.kwang23.fountainpen.external.api.blog.Document;
import com.kwang23.fountainpen.external.api.blog.ExternalSearchResult;
import com.kwang23.fountainpen.external.api.blog.Meta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceTest {
    @InjectMocks
    BlogSearchService blogSearchService;
    @Mock
    BlogSearchExternalApiAdapter blogSearchExternalApiAdapter;
    @Mock
    ApplicationEventPublisher publisher;

    @BeforeEach
    void setUp() {
    }

    @Test
    void searchBlog() {
        BlogSearchDto blogSearchDto = BlogSearchDto.builder().keyWord("keyWord").targetBlog("/test").size(1).sort(SortEnum.ACCURACY).build();
        Document document = new Document("blogname", "contents", LocalDateTime.now(), "thumbnail", "title", "url");
        Meta meta = new Meta(false, 10, 100);
        when(blogSearchExternalApiAdapter.search(any())).thenReturn(new ExternalSearchResult(List.of(document), meta));

        PageSearchResult result = blogSearchService.searchBlog(blogSearchDto);

        verify(publisher, times(1)).publishEvent(any(KeyWordSearchEvent.class));
        assertThat(result.getBlogInfoList().size()).isEqualTo(1);
        BlogInfo blogInfo = result.getBlogInfoList().get(0);
        assertThat(blogInfo.getBlogname()).isEqualTo(document.getBlogname());
        assertThat(blogInfo.getContents()).isEqualTo(document.getContents());
        assertThat(blogInfo.getDatetime()).isEqualTo(document.getDatetime());
        assertThat(blogInfo.getThumbnail()).isEqualTo(document.getThumbnail());
        assertThat(blogInfo.getTitle()).isEqualTo(document.getTitle());
        assertThat(blogInfo.getUrl()).isEqualTo(document.getUrl());
        assertThat(result.getSize()).isEqualTo(blogSearchDto.getSize());
        assertThat(result.getCurrentPage()).isEqualTo(blogSearchDto.getPage());
        assertThat(result.getTotalCount()).isEqualTo(result.getTotalCount());
    }
}