package com.fashion.celebrity.auth.controller;

import com.fashion.celebrity.auth.common.dto.ApiDto;
import com.fashion.celebrity.auth.dto.request.ReqSurveyDto;
import com.fashion.celebrity.auth.dto.response.ResBadDto;
import com.fashion.celebrity.auth.dto.response.ResColorDto;
import com.fashion.celebrity.auth.dto.response.ResGoodDto;
import com.fashion.celebrity.auth.dto.response.ResStyleDto;
import com.fashion.celebrity.auth.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SurveyService surveyService;


    /**
     * request:
     * response: apiInfo(obj: goodCode, goodDesc)
     * desc: 자랑스러운점 리스트
     */
    @CrossOrigin
    @GetMapping(value="/good-points")
    public ResponseEntity<?> GetGoodPointList() {
        logger.info("GetGoodPointList");

        ApiDto apiDto = new ApiDto();

        List<ResGoodDto> goodList = this.surveyService.selectGoodList();

        if (goodList.size() > 0) {
            apiDto.setSuccess(true);
            apiDto.setCode("SS01");
            apiDto.setMessage("성공적으로 조회하었습니다.");
            apiDto.setObj(goodList);
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode("");
            apiDto.setMessage("조회에 오류가 발생하였습니다. 담당자에게 문의해주세요.");
        }

        logger.info("GetGoodPointList res ::: {}", apiDto);

        return new ResponseEntity<Object>(apiDto, HttpStatus.OK);
    }

    /**
     * request:
     * response: apiInfo(obj: badCode, badDesc)
     * desc: 보완할점 리스트
     */
    @CrossOrigin
    @GetMapping(value="/bad-points")
    public ResponseEntity<?> GetBadPointList() {
        logger.info("GetBadPointList");

        ApiDto apiDto = new ApiDto();

        List<ResBadDto> badList = this.surveyService.selectBadList();

        if (badList.size() > 0) {
            apiDto.setSuccess(true);
            apiDto.setCode("SS01");
            apiDto.setMessage("성공적으로 조회하었습니다.");
            apiDto.setObj(badList);
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode("");
            apiDto.setMessage("조회에 오류가 발생하였습니다. 담당자에게 문의해주세요.");
        }

        logger.info("GetBadPointList res ::: {}", apiDto);


        return new ResponseEntity<Object>(apiDto, HttpStatus.OK);
    }

    /**
     * request:
     * response: apiInfo(obj: styleCode, styleDesc)
     * desc: 스타일 리스트
     */
    @CrossOrigin
    @GetMapping(value="/styles")
    public ResponseEntity<?> GetStyleList() {
        logger.info("GetStyleList");

        ApiDto apiDto = new ApiDto();

        List<ResStyleDto> styleList = this.surveyService.selectStyleList();

        if (styleList.size() > 0) {
            apiDto.setSuccess(true);
            apiDto.setCode("SS01");
            apiDto.setMessage("성공적으로 조회하었습니다.");
            apiDto.setObj(styleList);
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode("");
            apiDto.setMessage("조회에 오류가 발생하였습니다. 담당자에게 문의해주세요.");
        }

        logger.info("GetStyleList res ::: {}", apiDto);


        return new ResponseEntity<Object>(apiDto, HttpStatus.OK);
    }

    /**
     * request:
     * response: apiInfo(obj: colorCode, colorHex, colorDesc)
     * desc: 색깔 리스트
     */
    @CrossOrigin
    @GetMapping(value="/colors")
    public ResponseEntity<?> GetColorList() {
        logger.info("GetColorList");

        ApiDto apiDto = new ApiDto();

        List<ResColorDto> colorList = this.surveyService.selectColorList();

        if (colorList.size() > 0) {
            apiDto.setSuccess(true);
            apiDto.setCode("SS01");
            apiDto.setMessage("성공적으로 조회하었습니다.");
            apiDto.setObj(colorList);
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode("");
            apiDto.setMessage("조회에 오류가 발생하였습니다. 담당자에게 문의해주세요.");
        }

        logger.info("GetColorList res ::: {}", apiDto);


        return new ResponseEntity<Object>(apiDto, HttpStatus.OK);
    }


    /**
     * request: skin, height, weight,
     *          bodyGood1, bodyGood2, bodyBad1, bodyBad2, style
     *          color1, color2
     * response: apiInfo
     * desc: 설문결과 저장
     *
     *
     {
     "skin": "02",
     "height": "155",
     "weight": "43",
     "bodyGood1": "01",
     "bodyGood2": "03",
     "bodyBad1": "01",
     "bodyBad2": "02",
     "style": "01",
     "color1": "02",
     "color2": "11"
     }
     */
    @CrossOrigin
    @PostMapping(value="/regist")
    public ResponseEntity<?> Regist(@RequestBody ReqSurveyDto dto) {
        logger.info("Regist {}", dto);

        ApiDto apiDto = new ApiDto();

        try {
            this.surveyService.createSurveyAnswer(dto);

            apiDto.setSuccess(true);
            apiDto.setCode("CS01");
            apiDto.setMessage("성공적으로 등록되었습니다.");
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
