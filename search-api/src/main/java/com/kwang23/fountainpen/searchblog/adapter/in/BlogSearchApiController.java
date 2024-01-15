package com.kwang23.fountainpen.searchblog.adapter.in;

import com.kwang23.fountainpen.searchblog.application.port.in.PageSearchResult;
import com.kwang23.fountainpen.searchblog.application.port.in.BlogSearchDto;
import com.kwang23.fountainpen.searchblog.application.port.in.BlogSearchService;
import com.kwang23.fountainpen.searchblog.application.port.in.SortEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BlogSearchApiController {
    private final BlogSearchService blogSearchService;

    /**
     * kakao 오픈 API를 이용하여 blog의 내용을 검색하기 위한 API
     *
     * @param keyWord    검색할 단어
     * @param page       페이징 처리 시 조회할 페이지 ( 1 ~ 50, default : 1)
     * @param size       한 페이지에 보여주기 위한 검색 결과 수 ( 1 ~ 50, default : 10)
     * @param targetBlog 특정 블로그내에서 검색
     * @param sort       정렬 조건 (accuracy(정확도순) / recency(최신순)
     * @return 검색 결과
     */
    @GetMapping("/v1/search/blog")
    public PageSearchResult searchBlog(@RequestParam String keyWord,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) String targetBlog,
                                       @RequestParam(required = false) String sort) {

        if (page < 1 || page > 50) throw new IllegalArgumentException("page 는 1 이상 50 이하의 값입니다.");
        if (size < 1 || size > 50) throw new IllegalArgumentException("size 는 1 이상 50 이하의 값입니다.");
        Optional<SortEnum> anEnum = SortEnum.getEnum(sort);
        if (StringUtils.hasText(sort) && anEnum.isEmpty()) {
            throw new IllegalArgumentException("sort 값은 accuracy 또는 recency 중 하나여야 합니다.");
        }

        return blogSearchService.searchBlog(BlogSearchDto.builder()
                .keyWord(keyWord)
                .targetBlog(targetBlog)
                .sort(anEnum.orElse(null))
                .page(page)
                .size(size)
                .build());
    }
}
