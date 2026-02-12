package com.makecsv.autoInfoMapper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ComseticSummaryCreate {
    private String header;
    private String keyword;
    private String summary;

    public ComseticSummaryCreate(String header, String keyword, String summary) {

        this.header = header;
        this.keyword = keyword;
        this.summary = summary;
    }
}
