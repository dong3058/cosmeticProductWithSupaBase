package com.makecsv.autoInfoMapper.domain;


import com.makecsv.autoInfoMapper.domain.entity.Brand;
import com.makecsv.autoInfoMapper.domain.entity.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CosmeticResponseDto {
    private Long id;
    private String primaryCategory;
    private String category;
    private String brandName;
    private String producer;
    private String countryName;
    private String name;
    private String volume;
    private String spec;
    private String shelfLife;
    private String ingredients;
    private String mfdsApprovalStatus;
    private String qualityAssuranceStandard;
    private String tel;
    private String description;

    public CosmeticResponseDto(Long id, String primaryCategory, String category,
                               String brandName, String producer, String countryName, String name
            , String volume, String spec, String shelfLife, String ingredients
            , String mfdsApprovalStatus, String qualityAssuranceStandard, String tel, String description) {
        this.id = id;
        this.primaryCategory = primaryCategory;
        this.category = category;
        this.brandName = brandName;
        this.producer = producer;
        this.countryName = countryName;
        this.name = name;
        this.volume = volume;
        this.spec = spec;
        this.shelfLife = shelfLife;
        this.ingredients = ingredients;
        this.mfdsApprovalStatus = mfdsApprovalStatus;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.tel = tel;
        this.description = description;
    }
}
