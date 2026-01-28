package com.makecsv.autoInfoMapper.domain;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class SearchConditionDtos {
    private String primaryCategory;
    private String category;
    private String countryName;
    private String brandName;
    private int offset;

    public int provideOffset() {
        return this.offset==0? 0:this.offset-1;
    }

}
