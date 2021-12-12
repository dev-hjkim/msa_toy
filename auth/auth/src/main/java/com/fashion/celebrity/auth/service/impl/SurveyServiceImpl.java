package com.fashion.celebrity.auth.service.impl;

import com.fashion.celebrity.auth.dto.ColorDtos;
import com.fashion.celebrity.auth.dto.SurveyDtos;
import com.fashion.celebrity.auth.mapper.SurveyMapper;
import com.fashion.celebrity.auth.mapper.converter.SurveyConverter;
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
    public List<ColorDtos.Response> selectColorList() {
        List<ColorCodeInfo> list = this.surveyMapper.selectColorList();

        List<ColorDtos.Response> result = new ArrayList<>();
        for (ColorCodeInfo colorCodeInfo : list) {
            ColorDtos.Response elem = this.converter.INSTANCE.convertColorInfo(colorCodeInfo);
            result.add(elem);
        }

        return result;
    }

    @Override
    public int createSurveyAnswer(SurveyDtos.Request dto) {
        return this.surveyMapper.createSurveyAnswer(dto);
    }
}
