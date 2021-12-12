package com.fashion.celebrity.auth.mapper.converter;

import com.fashion.celebrity.auth.dto.ColorDtos;
import com.fashion.celebrity.auth.model.ColorCodeInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SurveyConverter {
    SurveyConverter INSTANCE = Mappers.getMapper(SurveyConverter.class);

    @Mapping(source="code", target="colorCode")
    @Mapping(source="codeDt", target="colorHex")
    @Mapping(source="codeNm", target="colorDesc")
    ColorDtos.Response convertColorInfo(ColorCodeInfo codeInfo);
}
