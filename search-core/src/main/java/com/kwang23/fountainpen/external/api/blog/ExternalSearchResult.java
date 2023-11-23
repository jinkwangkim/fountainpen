package com.kwang23.fountainpen.external.api.blog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"documents"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalSearchResult {
    private List<Document> documents;
    private Meta meta;
}
