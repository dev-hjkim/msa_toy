package com.fashion.celebrity.auth.service;

import com.fashion.celebrity.auth.dto.request.ReqSurveyDto;
import com.fashion.celebrity.auth.dto.response.ResBadDto;
import com.fashion.celebrity.auth.dto.response.ResColorDto;
import com.fashion.celebrity.auth.dto.response.ResGoodDto;
import com.fashion.celebrity.auth.dto.response.ResStyleDto;

import java.util.List;

public interface SurveyService {

    /* 자랑할점 리스트 조회 */
    List<ResGoodDto> selectGoodList();

    /* 보완할점 리스트 조회 */
    List<ResBadDto> selectBadList();

    /* 스타일 리스트 조회 */
    List<ResStyleDto> selectStyleList();

    /* 색깔 리스트 조회 */
    List<ResColorDto> selectColorList();

    /* 설문조사 결과 저장 */
    int createSurveyAnswer(ReqSurveyDto dto);

}
