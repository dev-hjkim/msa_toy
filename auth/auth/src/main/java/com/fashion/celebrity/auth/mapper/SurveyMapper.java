package com.fashion.celebrity.auth.mapper;

import com.fashion.celebrity.auth.dto.request.ReqSurveyDto;
import com.fashion.celebrity.auth.model.CodeInfo;
import com.fashion.celebrity.auth.model.ColorCodeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SurveyMapper {

    List<CodeInfo> selectGoodList();
    List<CodeInfo> selectBadList();
    List<CodeInfo> selectStyleList();
    List<ColorCodeInfo> selectColorList();
    int createSurveyAnswer(ReqSurveyDto dto);
}
