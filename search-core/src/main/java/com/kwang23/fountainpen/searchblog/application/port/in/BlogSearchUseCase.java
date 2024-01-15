package com.kwang23.fountainpen.searchblog.application.port.in;

public interface BlogSearchUseCase {
    PageSearchResult searchBlog(BlogSearchDto blogSearch);
}
