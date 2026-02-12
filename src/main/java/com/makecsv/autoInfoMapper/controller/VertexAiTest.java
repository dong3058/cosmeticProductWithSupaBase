package com.makecsv.autoInfoMapper.controller;



import com.google.cloud.vertexai.api.*;
import com.google.genai.Client;
import com.google.genai.types.*;
import com.google.genai.types.Candidate;
import com.google.genai.types.Content;
import com.google.genai.types.FunctionDeclaration;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GroundingChunk;
import com.google.genai.types.Part;
import com.google.genai.types.SafetySetting;
import com.google.genai.types.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class VertexAiTest {

    private String projectId="project-26b2e958-ac97-4db3-b5e";
    private String location="asia-northeast3";//"global"-->global 아니면 엔드 포인트 지원이안됨.

    @Value("${gemini.api.key}")
    private String apiKey;


    @PostMapping("/vertexAi")
    public String vertextAi(@RequestBody ApiTest apiTest){
        Client client= Client.builder()
                .project(projectId)
                .location(location)
                .vertexAI(true)
                //.apiKey(apiKey)
                .build();

        FunctionDeclaration functionDeclaration=FunctionDeclaration.builder()
                .name("testFunction")
                .description("test gemini function calling")
                .build();

        com.google.genai.types.Tool tool= Tool.builder()
                .googleSearch(GoogleSearch.builder().build())
                .functionDeclarations(List.of(functionDeclaration))
                .build();
        /*
        *                                 "1. 브랜드 공식 홈페이지는 성분 정보가 이미지나 클릭해야 보이는 방식일 수 있으므로 피하세요.\n" +
                                "2. 대신 ** '올리브영' 같은 화장품 판매 사이트 만으로 한정 합니다.\n" +
                                "3. 검색 쿼리를 생성할 때 '[제품명] 전성분 텍스트', '[제품명] ingredients list text'와 같이 구체적으로 시도하세요.\n" +*/
        GenerateContentConfig contentConfig=GenerateContentConfig.builder()
                .systemInstruction(Content.fromParts(Part.fromText("당신은 화장품 성분 분석 전문가입니다. 사용자가 입력한 제품의 '전성분(Ingredients)' 정보를 찾아주세요.\n" +
                        "\n" +
                        "**중요 검색 전략:**\n" +
                        "1.'올리브영' 같은 화장품 판매 사이트 혹은 브랜드 사이트만으로 한정 합니다.\n" +
                        "2. '전성분' 혹은 '화장품법에 따라 기재해야 하는 모든 성분' 데이터를 가져와야 합니다.\n" +
                        "**출력 형식:**\n" +
                        "- 상품명: [찾은 상품명]\n" +
                        "- 성분 구성: [찾은 텍스트 그대로 기재]\n" +
                        "(찾을 수 없다면 '정보 없음' 출력)"+
                        "출력 형식은 반드시 준수 해주시기 바랍니다.")))
                .tools(List.of(tool))
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(0)
                        .build())
                .safetySettings(List.of(SafetySetting.builder()
                                .category(HarmCategory.HARM_CATEGORY_JAILBREAK.name())
                                .threshold(com.google.cloud.vertexai.api.SafetySetting.HarmBlockThreshold.BLOCK_LOW_AND_ABOVE.name())
                        .build(),SafetySetting.builder()
                                .category(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT.name())
                                .threshold(com.google.cloud.vertexai.api.SafetySetting.HarmBlockThreshold.BLOCK_LOW_AND_ABOVE.name())
                                .build()
                        ))
                .temperature(0f)
                .build();
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        apiTest.getText(),
                        contentConfig);
        response.candidates().ifPresent(candidates -> {
            if (!candidates.isEmpty()) {
                // 첫 번째 후보 가져오기
                var firstCandidate = candidates.getFirst();

                log.info("종료이유:{}",firstCandidate.finishReason());

                // 안전 등급이 존재하는지 확인 후 출력
                firstCandidate.safetyRatings().ifPresent(ratings -> {
                    ratings.forEach(x -> log.info("safety: {}", x));
                });

                // (참고) 만약 안전 등급이 없으면 로그를 찍어보세요
                if (firstCandidate.safetyRatings().isEmpty()) {
                    log.info("안전 등급 데이터가 없습니다.");
                }
            } else {
                log.warn("생성된 답변 후보(Candidate)가 없습니다.");
            }
        });
        if(response.candidates().isPresent()) {
            List<Candidate> candidates = response.candidates().get();

            candidates.stream().forEach(x -> {
                if (x.groundingMetadata().isPresent()&&x.groundingMetadata().get().groundingChunks().isPresent()) {
                    List<GroundingChunk> groundingChunks = x.groundingMetadata().get().groundingChunks().get();
                    groundingChunks.stream().forEach(y -> {
                        if (y.web().isPresent()) {
                            log.info("참고 uri:{}", y.web().get().uri());
                            log.info("제목:{}", y.web().get().title().get());
                        }
                    });
                }
            });
        }
        //System.out.println(response.text());
        return response.text();
    }

    public void testFunction(){
        System.out.println("testfunction calling");
    }

}
