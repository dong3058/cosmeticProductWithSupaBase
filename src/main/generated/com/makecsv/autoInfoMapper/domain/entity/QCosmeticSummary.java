package com.makecsv.autoInfoMapper.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCosmeticSummary is a Querydsl query type for CosmeticSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCosmeticSummary extends EntityPathBase<CosmeticSummary> {

    private static final long serialVersionUID = -647568663L;

    public static final QCosmeticSummary cosmeticSummary = new QCosmeticSummary("cosmeticSummary");

    public final NumberPath<Long> cosmeticId = createNumber("cosmeticId", Long.class);

    public final StringPath header = createString("header");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public final StringPath summary = createString("summary");

    public QCosmeticSummary(String variable) {
        super(CosmeticSummary.class, forVariable(variable));
    }

    public QCosmeticSummary(Path<? extends CosmeticSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCosmeticSummary(PathMetadata metadata) {
        super(CosmeticSummary.class, metadata);
    }

}

