package com.makecsv.autoInfoMapper.controller;


import com.google.cloud.vertexai.api.Type;
import com.google.cloud.vertexai.api.UrlContext;
import com.google.genai.Client;
import com.google.genai.types.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TestController {


    private String projectId="project-26b2e958-ac97-4db3-b5e";
    private String location="hwangdonggeun70-org";

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.googleAiStudioKey}")
    private String aiStudioKey;



    @PostMapping("/googleApi")
    public String testApi(@RequestBody ApiTest apiTest){
        Client client = Client.builder()
                .apiKey(aiStudioKey)
                //.vertexAI(true)
                .build();


        Tool tool=Tool.builder()
                .googleSearch(GoogleSearch.builder().build())
                .build();
        /*Schema productSchema = Schema.builder()
                .type("STRING")
                .build();

        Schema companySchema = Schema.builder()
                .type("STRING")
                .build();


        Schema responseSchema = Schema.builder()
                .type("OBJECT")
                .properties(Map.of(
                        "product", productSchema,
                        "company", companySchema
                ))

                // 필수 값 지정 (List 사용)
                .required(List.of("product", "company"))
                .build();*/


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
                //.responseMimeType("application/json")
                //.responseSchema(responseSchema)
                .temperature(0f)
                .build();



        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash-lite",
                        apiTest.getText(),
                        contentConfig);
        if(response.candidates().isPresent()) {
            List<Candidate> candidates = response.candidates().get();

            candidates.stream().forEach(x -> {
                if (x.groundingMetadata().isPresent()&&x.groundingMetadata().get().groundingChunks().isPresent()) {
                    List<GroundingChunk> groundingChunks = x.groundingMetadata().get().groundingChunks().get();
                    x.groundingMetadata().get().groundingSupports().stream().forEach(y->{
                        log.info("ground support:{}",y);
                    });
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

    @PostMapping("/googleApi2")
    public String testApi2(@RequestBody ApiTest apiTest){
        Client client = Client.builder()
                .apiKey(aiStudioKey).build();
        Tool tool=Tool.builder()
                .googleSearch(GoogleSearch.builder().build())
                .build();

        GenerateContentConfig contentConfig=GenerateContentConfig.builder()
                .systemInstruction(Content.fromParts(Part.fromText("# Role\n" +
                        "너는 화장품 추천 앱의 '요청 분석가'이다. 사용자의 자연어 입력을 분석하여 데이터베이스 검색을 위한 정형 데이터(JSON)로 변환한다.\n" +
                        "\n" +
                        "# Task\n" +
                        "사용자의 입력에서 다음 세 가지 요소를 추출하여 반드시 **JSON 형식**으로만 출력해라.\n" +
                        "\n" +
                        "1. **hard_filters (필수 조건):** 사용자가 명시적으로 요구한 조건. (예: \"빼줘\", \"반드시\", \"무조건\", \"오타가 있어도 문맥상 확실한 성분/조건\")\n" +
                        "   - value: 해당 키워드 (표준화된 단어로 변경, 예: '알 ㅋ 올' -> '알코올')\n" +
                        "   - type: \"include\" (포함/긍정) 또는 \"exclude\" (제외/부정)\n" +
                        "2. **soft_preferences (선호/기타):** 필수 조건은 아니지만, 추천 시 참고해야 할 뉘앙스, 분위기, 추가 설명.\n" +
                        "3. **intent (의도):** 이 요청이 단순히 정보를 묻는 것인지, 특정 제품을 추천받고 싶은 것인지 파악.\n" +
                        "\n" +
                        "# Output Format (JSON Only)\n" +
                        "{\n" +
                        "  \"hard_filters\": [\n" +
                        "    {\"value\": \"string\", \"type\": \"include/exclude\", \"category\": \"ingredient/brand/price/etc\"} \n" +
                        "  ],\n" +
                        "  \"soft_preferences\": [\"string\", \"string\"],\n" +
                        "  \"intent\": \"recommendation/information/complaint\"\n" +
                        "}\n" +
                        "\n" +
                        "# Example\n" +
                        "User: \"나 오렌지향은 진짜 토나오니까 무조건 빼고, 좀 촉촉한거 없냐? 아 가격은 3만원 밑으로.\"\n" +
                        "AI:\n" +
                        "{\n" +
                        "  \"hard_filters\": [\n" +
                        "    {\"value\": \"오렌지향\", \"type\": \"exclude\", \"category\": \"scent\"},\n" +
                        "    {\"value\": \"30000\", \"type\": \"include_under\", \"category\": \"price\"}\n" +
                        "  ],\n" +
                        "  \"soft_preferences\": [\"촉촉함\", \"수분감\", \"보습\"],\n" +
                        "  \"intent\": \"recommendation\"\n" +
                        "}")))
                .tools(List.of(tool))
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(0)
                        .build())
                .temperature(0f)
                .build();


        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash-lite",
                        apiTest.getText(),
                        contentConfig);

        if(response.candidates().isPresent()) {
            List<Candidate> candidates = response.candidates().get();

            candidates.stream().forEach(x -> {
                if (x.groundingMetadata().isPresent()&&x.groundingMetadata().get().groundingChunks().isPresent()) {
                    List<GroundingChunk> groundingChunks = x.groundingMetadata().get().groundingChunks().get();
                    x.groundingMetadata().get().groundingSupports().stream().forEach(y->{
                        log.info("ground support:{}",y);
                    });
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
