package com.makecsv.autoInfoMapper.controller;


import com.makecsv.autoInfoMapper.domain.*;
import com.makecsv.autoInfoMapper.domain.entity.CosmeticSummary;
import com.makecsv.autoInfoMapper.domain.entity.PrimaryCategory;
import com.makecsv.autoInfoMapper.domain.entity.SecondaryCategory;
import com.makecsv.autoInfoMapper.service.CosmeticService;
import com.makecsv.autoInfoMapper.service.CosmeticSummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class cosmeticController {

    private final CosmeticService cosmeticService;
    private final CosmeticSummaryService cosmeticSummaryService;




    @GetMapping("/cosmetic/{id}/summaries")
    public String viewSummaries(@PathVariable("id") Long id, Model model) {

        List<CosmeticSummary> summaries = cosmeticSummaryService.getSummaries(id);

        model.addAttribute("summaries", summaries);
        model.addAttribute("cosmeticId",id);
        if(summaries.size()>0){
            String description=cosmeticService.getCosmeticDescription(id);
            model.addAttribute("description",description);
        }
        return "cosmetic/summaryList";
    }

    @PostMapping("/cosmetic/summaries/create-json-ajax")
    public String createSummariesAjax(@RequestBody RequestSummaryCreate request, Model model) {

        // 1. 서비스 로직: 리스트 저장
        cosmeticSummaryService.createCosmeticSummaries(request);

        // 2. 갱신된 전체 리스트 다시 조회
        List<CosmeticSummary> summaries = cosmeticSummaryService.getSummaries(request.getCosmeticId());

        // 3. 모델에 담기
        model.addAttribute("summaries", summaries);

        // ★ 핵심: 전체 페이지("cosmetic/summaryList")가 아니라,
        // 해당 페이지 내부의 특정 조각(":: summaryListArea")만 렌더링해서 반환
        return "cosmetic/summaryList :: summaryListArea";
    }
    @PostMapping("/cosmetic/{cosmeticId}/summaries/update-ajax")
    public String updateSummaryAjax(@PathVariable("cosmeticId") Long cosmeticId,
                                    @RequestBody RequestComseticSummaryUpdate request,
                                    Model model) {

        // 1. 서비스: 수정 수행
        cosmeticSummaryService.updateCosmeticSummary(request);

        // 2. 갱신된 리스트 다시 조회
        List<CosmeticSummary> summaries = cosmeticSummaryService.getSummaries(cosmeticId);
        model.addAttribute("summaries", summaries);

        // 3. HTML 조각 반환 (리스트 부분만)
        return "cosmetic/summaryList :: summaryListArea";
    }

    /**
     * [DELETE] AJAX 삭제 처리
     */
    @PostMapping("/cosmetic/{cosmeticId}/summaries/{summaryId}/delete-ajax")
    public String deleteSummaryAjax(@PathVariable("cosmeticId") Long cosmeticId,
                                    @PathVariable("summaryId") Long summaryId,
                                    Model model) {

        // 1. 서비스: 삭제 수행
        cosmeticSummaryService.deleteCosmeticSummary(summaryId);

        // 2. 갱신된 리스트 다시 조회
        List<CosmeticSummary> summaries = cosmeticSummaryService.getSummaries(cosmeticId);
        model.addAttribute("summaries", summaries);

        // 3. HTML 조각 반환
        return "cosmetic/summaryList :: summaryListArea";
    }



    @GetMapping("/cosmetic/form")
    public String showForm(Model model) {
        // 1. 빈 객체 전달 (th:object 바인딩용)
        model.addAttribute("cosmeticDtos", new CosmeticDtos());
        model.addAttribute("mainCategory", PrimaryCategory.values());
        model.addAttribute("subCategory", SecondaryCategory.values());

        return "cosmetic/save"; // 위의 HTML 파일 이름
    }



    @PostMapping("/cosmetic/save")
    public String saveCosmetic(
            @ModelAttribute("cosmeticDtos") @Valid CosmeticDtos cosmeticDtos,
            BindingResult bindingResult,
            Model model) {

        // [유효성 검사 실패 시] -> 다시 입력 폼으로 돌려보냄
        if (bindingResult.hasErrors()) {

            return "cosmetic/save";
        }
        cosmeticService.saveCosmetic(cosmeticDtos);
        cosmeticDtos.clearContent();
        return "cosmetic/save";
    }
    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable(name = "id") Long id, Model model) {

        UpdateCosmeticDto form = cosmeticService.findDataForUpdate(id);

        model.addAttribute("form", form);
        model.addAttribute("mainCategory", PrimaryCategory.values());
        model.addAttribute("subCategory", SecondaryCategory.values());

        return "cosmetic/update"; // update.html
    }


    @PostMapping("/cosmetic/{id}/edit")
    public String updateData(@ModelAttribute("form") @Valid UpdateCosmeticDto cosmeticDto,BindingResult bindingResult,@PathVariable(name = "id") Long id,Model model){

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().stream().forEach(x->{
                log.info("error:{}",x.getDefaultMessage());
            });

            return "cosmetic/update";
        }
        // 서비스 호출 (ID와 변경된 데이터 전달)
        cosmeticService.updateCosmeticData(cosmeticDto,id);

        // 수정 완료 후 상세 페이지나 목록으로 리다이렉트
        return "redirect:/";
    }

    @GetMapping({"/","/getDataList"})
    public String getDataList(@ModelAttribute @Valid SearchConditionDtos searchConditionDtos,Model model){
        Page<CosmeticResponseDto> cosmeticResponseDtoPage=cosmeticService.findByCondition(searchConditionDtos);

        model.addAttribute("page",cosmeticResponseDtoPage);
        model.addAttribute("mainCategory", PrimaryCategory.values());
        model.addAttribute("subCategory", SecondaryCategory.values());
        return "cosmetic/list";
    }
}
