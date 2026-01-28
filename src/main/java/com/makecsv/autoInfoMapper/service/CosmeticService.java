package com.makecsv.autoInfoMapper.service;


import com.makecsv.autoInfoMapper.controller.cosmeticController;
import com.makecsv.autoInfoMapper.domain.*;
import com.makecsv.autoInfoMapper.domain.entity.*;
import com.makecsv.autoInfoMapper.repository.BrandRepository;
import com.makecsv.autoInfoMapper.repository.CosmeticRepository;
import com.makecsv.autoInfoMapper.repository.CountryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.makecsv.autoInfoMapper.domain.entity.QBrand.*;
import static com.makecsv.autoInfoMapper.domain.entity.QCosmetic.*;
import static com.makecsv.autoInfoMapper.domain.entity.QCountry.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CosmeticService {
    private final CosmeticRepository cosmeticRepository;
    private final BrandRepository brandRepository;
    private final CountryRepository countryRepository;
    private final JPAQueryFactory queryFactory;

    private static final List<String> HEADER_LIST = Arrays.asList(
            /*"대분류",
            "카테고리",
            "브랜드명",
            "화장품이름",*/
            "내용물의 용량 또는 중량",
            "제품 주요 사양",
            "사용기한(또는 개봉 후 사용기간)",
            "화장품제조업자,화장품책임판매업자 및 맞춤형화장품판매업자",
            "제조국",
            "화장품법에 따라 기재해야 하는 모든 성분",
            "기능성 화장품 식품의약품안전처 심사필 여부",
            "품질보증기준",
            "소비자상담 전화번호"
    );


    public UpdateCosmeticDto findDataForUpdate(Long id){
        Optional<Cosmetic> cosmetic1=cosmeticRepository.findById(id);
        return UpdateCosmeticDto.builder()
                .id(id)
                .brand(cosmetic1.get().getBrand().getBrandName())
                .category(cosmetic1.get().getCategory())
                .primaryCategory(cosmetic1.get().getPrimaryCategory())
                .name(cosmetic1.get().getName())
                .build();
    }


    public void saveCosmetic(CosmeticDtos cosmeticDtos) {
        Optional<Brand> b = brandRepository.findByBrandName(cosmeticDtos.getBrandName());
        if (b.isEmpty()) {
            b = Optional.of(new Brand(cosmeticDtos.getBrandName()));
            brandRepository.save(b.get());
        }

        Pattern pattern = Pattern.compile("\t([^\n]+)");
        Matcher matcher = pattern.matcher(cosmeticDtos.getTextToEdit());

        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            results.add(matcher.group(1));
        }
        Optional<Country> c=countryRepository.findByCountryName(results.get(5));
        if(c.isEmpty()) {
            c = Optional.of(countryRepository.save(new Country(results.get(5))));
        }

        Cosmetic cosmetic=mappingToCosmetic(b.get(),c.get(),cosmeticDtos,results);
        cosmeticRepository.save(cosmetic);
    }


    public Cosmetic findCosmeticByName(String name){
        Optional<Cosmetic> cosmetic=cosmeticRepository.findByName(name);
        if(cosmetic.isEmpty()){
            return null;
        }
        return cosmetic.get();
    }

    public void updateCosmeticData(UpdateCosmeticDto updateCosmeticDto,Long id){
        Optional<Cosmetic> cosmetic1=cosmeticRepository.findById(id);
        if(updateCosmeticDto.getCategory()!=null&&!updateCosmeticDto.getCategory().isBlank()){
            cosmetic1.get().updateCategory(updateCosmeticDto.getCategory());
        }
        if(updateCosmeticDto.getPrimaryCategory()!=null&&!updateCosmeticDto.getPrimaryCategory().isBlank()){
            cosmetic1.get().updatePrimaryCategory(updateCosmeticDto.getPrimaryCategory());
        }
        if(updateCosmeticDto.getBrand()!=null&&!updateCosmeticDto.getBrand().isBlank()){
            Optional<Brand> brand1=brandRepository.findByBrandName(updateCosmeticDto.getBrand());
            if(brand1.isEmpty()){
                brand1=Optional.of(brandRepository.save(new Brand(updateCosmeticDto.getBrand())));
            }
            cosmetic1.get().updateBrand(brand1.get());
        }
        if(updateCosmeticDto.getName()!=null&&!updateCosmeticDto.getName().isBlank()){
            cosmetic1.get().updateName(updateCosmeticDto.getName());
        }
    }


    public Page<CosmeticResponseDto> findByCondition(SearchConditionDtos searchConditionDtos){

            PageRequest pageRequest=PageRequest.of(searchConditionDtos.provideOffset(),5);


            List<CosmeticResponseDto> cosmeticResponseDtos=queryFactory.select(Projections.constructor(CosmeticResponseDto.class,
                    cosmetic.id,
                    cosmetic.primaryCategory,
                    cosmetic.category,
                    brand.brandName,
                    cosmetic.producer,
                    country.countryName,
                    cosmetic.name,
                    cosmetic.volume,
                    cosmetic.spec,
                    cosmetic.shelfLife,
                    cosmetic.ingredients,
                    cosmetic.mfdsApprovalStatus,
                    cosmetic.qualityAssuranceStandard,
                    cosmetic.tel
                    ,cosmetic.description
                    ))
                    .from(cosmetic)
                    .join(brand)
                    .on(brand.eq(cosmetic.brand))
                    .join(country)
                    .on(country.eq(cosmetic.country))
                    .where(primaryCondition(searchConditionDtos))
                    .offset(pageRequest.getOffset())
                    .limit(pageRequest.getPageSize())
                    .fetch();

            Long count=queryFactory.select(cosmetic.count())
                    .from(cosmetic)
                    .join(brand)
                    .on(brand.eq(cosmetic.brand))
                    .join(country)
                    .on(country.eq(cosmetic.country))
                    .where(primaryCondition(searchConditionDtos))
                    .offset(pageRequest.getOffset())
                    .limit(pageRequest.getPageSize())
                    .fetch().get(0);
        return new PageImpl<>(cosmeticResponseDtos, pageRequest, count);
    }

    private BooleanBuilder primaryCondition(SearchConditionDtos searchConditionDtos){

        BooleanBuilder predicate = new BooleanBuilder();

        if(searchConditionDtos.getPrimaryCategory()!=null&&!searchConditionDtos.getPrimaryCategory().isBlank()){
            predicate.and(cosmetic.primaryCategory.eq(searchConditionDtos.getPrimaryCategory()));
        }
        if(searchConditionDtos.getCategory()!=null&&!searchConditionDtos.getCategory().isBlank()){
            predicate.and(cosmetic.category.eq(searchConditionDtos.getCategory()));
        }
        if(searchConditionDtos.getCountryName()!=null&&!searchConditionDtos.getCountryName().isBlank()){
            predicate.and(country.countryName.eq(searchConditionDtos.getCountryName()));
        }
        if(searchConditionDtos.getBrandName()!=null&&!searchConditionDtos.getBrandName().isBlank()){
            predicate.and(brand.brandName.eq(searchConditionDtos.getBrandName()));
        }

        return predicate;
    }




    private Cosmetic mappingToCosmetic(Brand b,Country c,CosmeticDtos cosmeticDtos,List<String> results){
        return Cosmetic.builder()
                .primaryCategory(cosmeticDtos.getPrimaryCategory())
                .category(cosmeticDtos.getCategory())
                .name(cosmeticDtos.getName())
                .country(c)
                .brand(b)
                .description(cosmeticDtos.getDescription())
                .volume(results.get(0))
                .spec(results.get(1))
                .shelfLife(results.get(2))
                .producer(results.get(4))
                .ingredients(results.get(6))
                .mfdsApprovalStatus(results.get(7))
                .qualityAssuranceStandard(results.get(9))
                .tel(results.get(10))
                .build();
    }

}
