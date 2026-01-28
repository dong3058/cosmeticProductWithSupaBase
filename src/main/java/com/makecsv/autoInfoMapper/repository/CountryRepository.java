package com.makecsv.autoInfoMapper.repository;

import com.makecsv.autoInfoMapper.domain.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {

    Optional<Country> findByCountryName(String brandName);
}
