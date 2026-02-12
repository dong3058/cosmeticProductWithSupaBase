package com.makecsv.autoInfoMapper.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CosmeticSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cosmeticId;
    private String header;
    private String keyword;
    private String summary;

    @Builder
    public CosmeticSummary(Long id, Long cosmeticId, String header, String keyword, String summary) {
        this.id = id;
        this.cosmeticId = cosmeticId;
        this.header = header;
        this.keyword = keyword;
        this.summary = summary;
    }


    public void updateHeader(String header){
        this.header=header;
    }
    public void updateKeyword(String keyword){
        this.keyword=keyword;
    }
    public void updateSummary(String summary){
        this.summary=summary;
    }
}
