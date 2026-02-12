package com.makecsv.autoInfoMapper.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCosmetic is a Querydsl query type for Cosmetic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCosmetic extends EntityPathBase<Cosmetic> {

    private static final long serialVersionUID = -481290755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCosmetic cosmetic = new QCosmetic("cosmetic");

    public final QBrand brand;

    public final QCountry country;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ingredients = createString("ingredients");

    public final StringPath mfdsApprovalStatus = createString("mfdsApprovalStatus");

    public final StringPath name = createString("name");

    public final EnumPath<PrimaryCategory> primaryCategory = createEnum("primaryCategory", PrimaryCategory.class);

    public final StringPath producer = createString("producer");

    public final StringPath qualityAssuranceStandard = createString("qualityAssuranceStandard");

    public final EnumPath<SecondaryCategory> secondaryCategory = createEnum("secondaryCategory", SecondaryCategory.class);

    public final StringPath shelfLife = createString("shelfLife");

    public final StringPath spec = createString("spec");

    public final StringPath tel = createString("tel");

    public final StringPath volume = createString("volume");

    public QCosmetic(String variable) {
        this(Cosmetic.class, forVariable(variable), INITS);
    }

    public QCosmetic(Path<? extends Cosmetic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCosmetic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCosmetic(PathMetadata metadata, PathInits inits) {
        this(Cosmetic.class, metadata, inits);
    }

    public QCosmetic(Class<? extends Cosmetic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new QBrand(forProperty("brand")) : null;
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country")) : null;
    }

}

