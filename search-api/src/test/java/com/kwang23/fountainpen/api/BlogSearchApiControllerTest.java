package com.kwang23.fountainpen.api;

import com.kwang23.fountainpen.domain.searchblog.BlogInfo;
import com.kwang23.fountainpen.domain.searchblog.PageSearchResult;
import com.kwang23.fountainpen.domain.searchblog.BlogSearchDto;
import com.kwang23.fountainpen.domain.searchblog.BlogSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BlogSearchApiController.class)
class BlogSearchApiControllerTest {
    public static final String V_1_SEARCH_BLOG_URL = "/v1/search/blog";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BlogSearchService blogSearchService;
    PageSearchResult result;
    BlogInfo blogInfo;
    @BeforeEach
    void setUp() {
        blogInfo = BlogInfo.builder()
                .url("/test")
                .title("test title")
                .build();
        result = PageSearchResult.builder()
                .blogInfoList(List.of(blogInfo))
                .totalCount(10)
                .currentPage(2)
                .size(5)
                .build();
    }
    @Test
    void testSearchBlog() throws Exception {
        Mockito.when(blogSearchService.searchBlog(Mockito.any(BlogSearchDto.class))).thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get(V_1_SEARCH_BLOG_URL).queryParam("keyWord","test")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalCount", is(result.getTotalCount())))
                .andExpect(jsonPath("$.currentPage", is(result.getCurrentPage())))
                .andExpect(jsonPath("$.size", is(result.getSize())))
                .andExpect(jsonPath("$.blogInfoList[0].title", is(blogInfo.getTitle())))
                .andExpect(jsonPath("$.blogInfoList[0].url", is(blogInfo.getUrl())));
    }
    @Test
    void testSearchBlog_invalid_size() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(V_1_SEARCH_BLOG_URL)
                        .queryParam("keyWord", "test")
                        .queryParam("size", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSearchBlog_invalid_page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(V_1_SEARCH_BLOG_URL)
                        .queryParam("keyWord", "test")
                        .queryParam("page", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}