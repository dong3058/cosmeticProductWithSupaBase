package com.makecsv.autoInfoMapper.domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Cosmetic {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String primaryCategory;
    private String category;
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;
    private String producer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;
    private String name;
    private String volume;
    private String spec;
    private String shelfLife;
    @Column(columnDefinition = "TEXT")
    private String ingredients;
    private String mfdsApprovalStatus;
    private String qualityAssuranceStandard;
    private String tel;
    private String description;


    @Builder
    public Cosmetic(String primaryCategory, String category, Brand brand,
                    String name,String volume, String spec,String shelfLife, String producer
            ,Country country,     String ingredients, String mfdsApprovalStatus,
                    String qualityAssuranceStandard, String tel, String description) {
        this.primaryCategory = primaryCategory;
        this.category = category;
        this.brand = brand;
        this.producer = producer;
        this.country = country;
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


    public void updateCategory(String category){
        this.category=category;
    }

    public void updatePrimaryCategory(String primaryCategory){
        this.primaryCategory=primaryCategory;
    }
    public void updateName(String name){
        this.name=name;
    }
    public void updateBrand(Brand brand){
        this.brand=brand;
    }
}
