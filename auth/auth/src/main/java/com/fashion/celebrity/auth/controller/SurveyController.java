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
@RequestMapping("/v1/auth/survey")
public class SurveyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SurveyService surveyService;


    /**
     * request:
     * response: apiInfo(obj: colorCode, colorHex, colorDesc)
     * desc: 색깔 리스트
     */
    /**
     * @api {get} /v1/auth/survey/colors 01.GetColorList
     * @apiName GetColorList
     * @apiGroup Survey
     * @apiDescription 색깔 리스트 조회
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     * @apiSuccess {Object[]} obj 색깔 정보를 담은 리스트 객체
     * @apiSuccess {String} obj.colorCode 색깔 코드
     * @apiSuccess {String} obj.colorHex 16진수 색상코드
     * @apiSuccess {String} obj.colorDesc 색깔명
     *
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "SV002",
     *          "message": "성공적으로 조회되었습니다.",
     *          "obj": [
     *              {
     *                  "colorCode": "C01",
     *                  "colorHex": "#FFF2DF",
     *                  "colorDesc": "크림",
     *              },
     *              {
     *                  "colorCode": "C02",
     *                  "colorHex": "#FFC8C8",
     *                  "colorDesc": "핑크",
     *              },
     *              ...
     *          ]
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "SV005",
     *          "message": "조회 결과가 없습니다."
     *      }
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
     * @api {get} /v1/auth/survey/regist 02.Regist
     * @apiName Regist
     * @apiGroup Survey
     * @apiDescription 설문결과 저장
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} skinCode 피부톤 코드
     * @apiParam {int} height 키
     * @apiParam {int} weight 몸무게
     * @apiParam {String} colorCode1 선호 색깔 코드 1
     * @apiParam {String} colorCode2 선호 색깔 코드 2
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "asdf@naver.com",
     *          "skinCode": "02",
     *          "height": 155,
     *          "weight": 43,
     *          "colorCode1": "02",
     *          "colorCode2": "11"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "SV001",
     *          "message": "성공적으로 등록되었습니다."
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "",
     *          "message": ""
     *      }
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
