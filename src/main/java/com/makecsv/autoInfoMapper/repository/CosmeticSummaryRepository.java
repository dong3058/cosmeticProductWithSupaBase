package com.makecsv.autoInfoMapper.repository;

import com.makecsv.autoInfoMapper.domain.entity.CosmeticSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CosmeticSummaryRepository extends JpaRepository<CosmeticSummary,Long> {


    List<CosmeticSummary> findAllByCosmeticId(Long cosmeticId);
}
