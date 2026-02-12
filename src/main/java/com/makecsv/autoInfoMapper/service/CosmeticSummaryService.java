package com.makecsv.autoInfoMapper.service;


import com.makecsv.autoInfoMapper.domain.ComseticSummaryCreate;
import com.makecsv.autoInfoMapper.domain.RequestComseticSummaryUpdate;
import com.makecsv.autoInfoMapper.domain.RequestSummaryCreate;
import com.makecsv.autoInfoMapper.domain.entity.CosmeticSummary;
import com.makecsv.autoInfoMapper.repository.CosmeticSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CosmeticSummaryService {

    private final CosmeticSummaryRepository cosmeticSummaryRepository;


    public List<CosmeticSummary> getSummaries(Long cosmeticId){
        return cosmeticSummaryRepository.findAllByCosmeticId(cosmeticId);
    }


    public void createCosmeticSummaries(RequestSummaryCreate requestSummaryCreate){
        List<CosmeticSummary> cosmeticSummaries=requestSummaryCreate.getRequestSummaryCreateList().stream()
                .map(x->{
                    return CosmeticSummary.builder()
                            .cosmeticId(requestSummaryCreate.getCosmeticId())
                            .summary(x.getSummary())
                            .header(x.getHeader())
                            .keyword(x.getKeyword())
                            .build();
                }).collect(Collectors.toList());
        cosmeticSummaryRepository.saveAll(cosmeticSummaries);
    }
    public void updateCosmeticSummary(RequestComseticSummaryUpdate requestComseticSummaryUpdate){
        Optional<CosmeticSummary> cosmeticSummaryOptional=
                cosmeticSummaryRepository.findById(requestComseticSummaryUpdate.getCosmeticSummaryId());
        log.info("updata:{}",requestComseticSummaryUpdate.getKeyword());

        if(requestComseticSummaryUpdate.getHeader()!=null){
            cosmeticSummaryOptional.get().updateHeader(requestComseticSummaryUpdate.getHeader());
        }

        if(requestComseticSummaryUpdate.getKeyword()!=null){
            cosmeticSummaryOptional.get().updateKeyword(requestComseticSummaryUpdate.getKeyword());
        }


        if(requestComseticSummaryUpdate.getSummary()!=null){
            cosmeticSummaryOptional.get().updateSummary(requestComseticSummaryUpdate.getSummary());
        }
    }
    public void deleteCosmeticSummary(Long id){
        cosmeticSummaryRepository.deleteById(id);
    }
}
