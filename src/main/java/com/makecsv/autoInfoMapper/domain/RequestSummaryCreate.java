package com.makecsv.autoInfoMapper.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class RequestSummaryCreate {

    private Long cosmeticId;
    private List<ComseticSummaryCreate> requestSummaryCreateList;
}
