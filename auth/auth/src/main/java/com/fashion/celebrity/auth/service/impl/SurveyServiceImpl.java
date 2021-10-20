package com.fashion.celebrity.auth.service.impl;

import com.fashion.celebrity.auth.dto.request.ReqSurveyDto;
import com.fashion.celebrity.auth.dto.response.ResBadDto;
import com.fashion.celebrity.auth.dto.response.ResColorDto;
import com.fashion.celebrity.auth.dto.response.ResGoodDto;
import com.fashion.celebrity.auth.dto.response.ResStyleDto;
import com.fashion.celebrity.auth.mapper.SurveyMapper;
import com.fashion.celebrity.auth.mapper.converter.SurveyConverter;
import com.fashion.celebrity.auth.model.CodeInfo;
import com.fashion.celebrity.auth.model.ColorCodeInfo;
import com.fashion.celebrity.auth.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SurveyService")
public class SurveyServiceImpl implements SurveyService {
    @Autowired
    SurveyMapper surveyMapper;

    @Autowired
    SurveyConverter converter;

    @Override
    public List<ResGoodDto> selectGoodList() {
        List<CodeInfo> list = this.surveyMapper.selectGoodList();

        List<ResGoodDto> result = new ArrayList<>();
        for (CodeInfo codeInfo : list) {
            ResGoodDto elem = this.converter.INSTANCE.convertGoodInfo(codeInfo);
            result.add(elem);
        }

        return result;
    }

    @Override
    public List<ResBadDto> selectBadList() {
        List<CodeInfo> list = this.surveyMapper.selectBadList();

        List<ResBadDto> result = new ArrayList<>();
        for (CodeInfo codeInfo : list) {
            ResBadDto elem = this.converter.INSTANCE.convertBadInfo(codeInfo);
            result.add(elem);
        }

        return result;
    }

    @Override
    public List<ResStyleDto> selectStyleList() {
        List<CodeInfo> list = this.surveyMapper.selectStyleList();

        List<ResStyleDto> result = new ArrayList<>();
        for (CodeInfo codeInfo : list) {
            ResStyleDto elem = this.converter.INSTANCE.convertStyleInfo(codeInfo);
            result.add(elem);
        }

        return result;
    }

    @Override
    public List<ResColorDto> selectColorList() {
        List<ColorCodeInfo> list = this.surveyMapper.selectColorList();

        List<ResColorDto> result = new ArrayList<>();
        for (ColorCodeInfo colorCodeInfo : list) {
            ResColorDto elem = this.converter.INSTANCE.convertColorInfo(colorCodeInfo);
            result.add(elem);
        }

        return result;
    }

    @Override
    public int createSurveyAnswer(ReqSurveyDto dto) {
        return this.surveyMapper.createSurveyAnswer(dto);
    }
}
