package com.makecsv.autoInfoMapper.domain.entity;

public enum PrimaryCategory {
    SKINCARE("스킨케어"),
    MASK_PACK("마스크팩"),
    CLEANSING("클렌징"),
    SUN_CARE("선케어"),
    MAKEUP("메이크업"),
    MAKEUP_TOOLS("메이크업 툴"),
    MENS_CARE("맨즈케어"),
    HAIR_CARE("헤어케어"),
    BODY_CARE("바디케어"),
    PERFUME_DIFFUSER("향수/디퓨저"),
    NAIL("네일");

    private final String description;

    PrimaryCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
