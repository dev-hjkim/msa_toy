package com.fashion.celebrity.auth.mapper;

import com.fashion.celebrity.auth.dto.SurveyDtos;
import com.fashion.celebrity.auth.model.ColorCodeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SurveyMapper {
    List<ColorCodeInfo> selectColorList();
    int createSurveyAnswer(SurveyDtos.Request dto);
}
