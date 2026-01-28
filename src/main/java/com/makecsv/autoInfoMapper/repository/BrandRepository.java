package com.makecsv.autoInfoMapper.repository;

import com.makecsv.autoInfoMapper.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    Optional<Brand> findByBrandName(String brandName);
}
