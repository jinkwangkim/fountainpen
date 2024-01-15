package com.kwang23.fountainpen.searchblog.adapter.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    @JsonProperty("is_end")
    private boolean isEnd;
    @JsonProperty("pageable_count")
    private int pageableCount;
    @JsonProperty("total_count")
    private int totalCount;
}
