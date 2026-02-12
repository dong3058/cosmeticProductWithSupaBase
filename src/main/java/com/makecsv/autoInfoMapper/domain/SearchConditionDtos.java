package com.makecsv.autoInfoMapper.domain;


import com.makecsv.autoInfoMapper.domain.entity.PrimaryCategory;
import com.makecsv.autoInfoMapper.domain.entity.SecondaryCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class SearchConditionDtos {
    private PrimaryCategory primaryCategory;
    private SecondaryCategory category;
    private String countryName;
    private String brandName;
    private int offset;

    public int provideOffset() {
        return this.offset==0? 0:this.offset-1;
    }

}
