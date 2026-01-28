package com.makecsv.autoInfoMapper.controller;


import com.makecsv.autoInfoMapper.domain.*;
import com.makecsv.autoInfoMapper.service.CosmeticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class cosmeticController {

    private final CosmeticService cosmeticService;



    @GetMapping("/cosmetic/form")
    public String showForm(Model model) {
        // 1. 빈 객체 전달 (th:object 바인딩용)
        model.addAttribute("cosmeticDtos", new CosmeticDtos());

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

        return "cosmetic/list";
    }
}
