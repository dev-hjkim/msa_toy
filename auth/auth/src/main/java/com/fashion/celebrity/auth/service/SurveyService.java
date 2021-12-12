package com.fashion.celebrity.auth.service;

import com.fashion.celebrity.auth.dto.ColorDtos;
import com.fashion.celebrity.auth.dto.SurveyDtos;

import java.util.List;

public interface SurveyService {
    /* 색깔 리스트 조회 */
    List<ColorDtos.Response> selectColorList();

    /* 설문조사 결과 저장 */
    int createSurveyAnswer(SurveyDtos.Request dto);

}
