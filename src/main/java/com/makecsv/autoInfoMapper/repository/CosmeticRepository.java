package com.makecsv.autoInfoMapper.repository;

import com.makecsv.autoInfoMapper.domain.entity.Cosmetic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CosmeticRepository extends JpaRepository<Cosmetic,Long> {
    Optional<Cosmetic> findByName(String name);
}
