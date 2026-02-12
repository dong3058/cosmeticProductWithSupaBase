package com.makecsv.autoInfoMapper.domain.entity;

public enum SecondaryCategory {

    // --- 스킨케어 (Skincare) ---
    SKIN_TONER("스킨/토너"),
    ESSENCE_SERUM_AMPOULE("에센스/세럼/앰플"),
    CREAM("크림"),
    LOTION("로션"),
    MIST_OIL("미스트/오일"),
    SKINCARE_SET("스킨케어세트"),
    SKINCARE_DEVICE("스킨케어 디바이스"),

    // --- 마스크팩 (Mask Pack) ---
    SHEET_MASK("시트팩"),
    PAD("패드"),
    FACIAL_PACK("페이셜팩"),
    NOSE_PACK("코팩"),
    PATCH("패치"),

    // --- 클렌징 (Cleansing) ---
    CLEANSING_FOAM_GEL("클렌징폼/젤"),
    CLEANSING_OIL_BALM("오일/밤"),
    CLEANSING_WATER_MILK("워터/밀크"),
    PEELING_SCRUB("필링&스크럽"),
    CLEANSING_TISSUE_PAD("티슈/패드"),
    LIP_EYE_REMOVER("립&아이리무버"),
    CLEANSING_DEVICE("클렌징 디바이스"),

    // --- 선케어 (Sun Care) ---
    SUNSCREEN("선크림"),
    SUN_STICK("선스틱"),
    SUN_CUSHION("선쿠션"),

    // --- 메이크업 (Makeup) ---
    LIP_MAKEUP("립메이크업"),
    BASE_MAKEUP("베이스메이크업"),
    EYE_MAKEUP("아이메이크업"),

    // --- 메이크업 툴 (Makeup Tools) ---
    MAKEUP_TOOLS("메이크업 툴"),
    EYELASH_TOOLS("아이래쉬 툴"),
    FACE_TOOLS("페이스 툴"),
    HAIR_BODY_TOOLS("헤어/바디 툴"),
    DAILY_TOOLS("데일리 툴"),

    MEN_SKINCARE("스킨케어"),
    MEN_MAKEUP("메이크업"),
    MEN_SHAVING_WAXING("쉐이빙/왁싱"),
    MEN_BODY_CARE("바디케어"),
    MEN_HAIR_CARE("헤어케어"),
    MEN_FRAGRANCE("프래그런스"),
    MEN_FASHION_HOBBIES("패션/취미"),
    MEN_FITNESS_FOOD("헬스용품/식품"),
    SHAMPOO_CONDITIONER("샴푸/린스"),
    HAIR_TREATMENT_PACK("트리트먼트/팩"),
    SCALP_AMPOULE_TONIC("두피앰플/토닉"),
    HAIR_ESSENCE("헤어에센스"),
    HAIR_DYE_PERM("염색약/펌"),
    HAIR_DEVICE_BRUSH("헤어기기/브러시"),
    HAIR_STYLING("스타일링"),
    BODY_LOTION_CREAM("바디로션/크림"),
    BODY_OIL_MIST("오일/미스트"),
    HAND_CARE("핸드케어"),
    FOOT_CARE("풋케어"),
    SHOWER_BATH("샤워/입욕"),
    HAIR_REMOVAL_WAXING("제모/왁싱"),
    DEODORANT("데오드란트"),
    BABY_CARE("베이비"),

    PERFUME("향수"),
    MINI_SOLID_PERFUME("미니/고체향수"),
    HOME_FRAGRANCE("홈프래그런스"),

    NAIL_POLISH("일반네일"),
    GEL_NAIL("젤네일"),
    NAIL_TIPS_STICKERS("네일팁/스티커"),
    NAIL_CARE_REMOVER("네일케어/리무버");

    private final String description;

    SecondaryCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
