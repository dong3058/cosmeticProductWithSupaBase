package com.makecsv.autoInfoMapper.domain;


import com.makecsv.autoInfoMapper.domain.entity.Brand;
import com.makecsv.autoInfoMapper.domain.entity.Country;
import com.makecsv.autoInfoMapper.domain.entity.PrimaryCategory;
import com.makecsv.autoInfoMapper.domain.entity.SecondaryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UpdateCosmeticDto {

    @NotNull
    private Long id;
    private PrimaryCategory primaryCategory;
    private SecondaryCategory category;
    private String brand;
    private String name;


    @Builder
    public UpdateCosmeticDto(Long id, PrimaryCategory primaryCategory,
                             SecondaryCategory category, String brand, String name) {
        this.id = id;
        this.primaryCategory = primaryCategory;
        this.category = category;
        this.brand = brand;
        this.name = name;
    }
}
