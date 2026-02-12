package com.makecsv.autoInfoMapper.domain;


import com.makecsv.autoInfoMapper.domain.entity.PrimaryCategory;
import com.makecsv.autoInfoMapper.domain.entity.SecondaryCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CosmeticDtos {


    //@NotBlank(message = "대분류 필수")
    private PrimaryCategory primaryCategory;
    //@NotBlank(message = "카테고리 필수")
    private SecondaryCategory category;
    @NotBlank(message = "브랜드 이름 필수")
    private String brandName;
    @NotBlank(message = "화장품 이름 필수")
    private String name;
    private String description;
    @NotBlank(message = "상품정보 고지 필수")
    private String textToEdit;



    public void clearContent(){
        this.description=null;
        this.textToEdit=null;
        this.name=null;
    }
}
