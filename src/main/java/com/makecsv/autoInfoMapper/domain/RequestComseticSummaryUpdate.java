package com.makecsv.autoInfoMapper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestComseticSummaryUpdate {

    private Long cosmeticSummaryId;
    private String keyword;
    private String header;
    private String summary;

    public RequestComseticSummaryUpdate(Long cosmeticSummaryId, String keyword, String header, String summary) {
        this.cosmeticSummaryId = cosmeticSummaryId;
        this.keyword = keyword;
        this.header = header;
        this.summary = summary;
    }
}
