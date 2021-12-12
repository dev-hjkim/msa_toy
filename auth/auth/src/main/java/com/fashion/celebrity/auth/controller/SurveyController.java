package com.fashion.celebrity.auth.controller;

import com.fashion.celebrity.auth.common.dto.ApiDto;
import com.fashion.celebrity.auth.common.dto.SurveyResCode;
import com.fashion.celebrity.auth.dto.ColorDtos;
import com.fashion.celebrity.auth.dto.SurveyDtos;
import com.fashion.celebrity.auth.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SurveyService surveyService;


    /**
     * request:
     * response: apiInfo(obj: colorCode, colorHex, colorDesc)
     * desc: 색깔 리스트
     */
    @GetMapping(value="/colors")
    public ResponseEntity<?> GetColorList() {
        logger.info("GetColorList");

        ApiDto apiDto = new ApiDto();

        List<ColorDtos.Response> colorList = this.surveyService.selectColorList();

        if (colorList.size() > 0) {
            apiDto.setSuccess(true);
            apiDto.setCode(SurveyResCode.SV002.name());
            apiDto.setMessage(SurveyResCode.SV002.getMessage());
            apiDto.setObj(colorList);
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode(SurveyResCode.SV005.name());
            apiDto.setMessage(SurveyResCode.SV005.getMessage());
        }

        logger.info("GetColorList res ::: {}", apiDto);


        return new ResponseEntity<Object>(apiDto, HttpStatus.OK);
    }


    /**
     * request: email, skinCode, height, weight,
     *          colorCode1, colorCode2
     * response: apiInfo
     * desc: 설문결과 저장
     *
     *
     {
     "email": "asdf@naver.com"
     "skinCode": "02",
     "height": 155,
     "weight": 43,
     "colorCode1": "02",
     "colorCode2": "11"
     }
     */
    @PostMapping(value="/regist")
    public ResponseEntity<?> Regist(@Valid @RequestBody SurveyDtos.Request dto) {
        logger.info("Regist {}", dto);

        ApiDto apiDto = new ApiDto();

        try {
            this.surveyService.createSurveyAnswer(dto);

            apiDto.setSuccess(true);
            apiDto.setCode(SurveyResCode.SV001.name());
            apiDto.setMessage(SurveyResCode.SV001.getMessage());
        } catch (Exception ex) {
            logger.info("{}", ex.getMessage());
            apiDto.setSuccess(false);
            apiDto.setCode("CE99");
            apiDto.setMessage("등록 중 오류가 발생했습니다. 담당자에게 문의해주세요.");
        }

        logger.info("Regist res ::: {}", apiDto);


        return new ResponseEntity<Object>(apiDto, HttpStatus.OK);
    }
}
