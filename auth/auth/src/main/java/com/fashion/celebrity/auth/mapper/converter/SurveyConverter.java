package com.fashion.celebrity.auth.mapper.converter;

import com.fashion.celebrity.auth.dto.response.ResBadDto;
import com.fashion.celebrity.auth.dto.response.ResColorDto;
import com.fashion.celebrity.auth.dto.response.ResGoodDto;
import com.fashion.celebrity.auth.dto.response.ResStyleDto;
import com.fashion.celebrity.auth.model.CodeInfo;
import com.fashion.celebrity.auth.model.ColorCodeInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SurveyConverter {
    SurveyConverter INSTANCE = Mappers.getMapper(SurveyConverter.class);

    @Mapping(source="code", target="goodCode")
    @Mapping(source="codeNm", target="goodDesc")
    ResGoodDto convertGoodInfo(CodeInfo codeInfo);

    @Mapping(source="code", target="badCode")
    @Mapping(source="codeNm", target="badDesc")
    ResBadDto convertBadInfo(CodeInfo codeInfo);

    @Mapping(source="code", target="styleCode")
    @Mapping(source="codeNm", target="styleDesc")
    ResStyleDto convertStyleInfo(CodeInfo codeInfo);

    @Mapping(source="code", target="colorCode")
    @Mapping(source="codeDt", target="colorHex")
    @Mapping(source="codeNm", target="colorDesc")
    ResColorDto convertColorInfo(ColorCodeInfo codeInfo);
}
