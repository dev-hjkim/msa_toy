package com.fashion.celebrity.auth.mapper.converter;

import com.fashion.celebrity.auth.dto.response.ResFindIdDto;
import com.fashion.celebrity.auth.dto.response.ResFindPwDto;
import com.fashion.celebrity.auth.dto.response.ResLoginDto;
import com.fashion.celebrity.auth.model.FindIdInfo;
import com.fashion.celebrity.auth.model.FindPwInfo;
import com.fashion.celebrity.auth.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConverter {
    AuthConverter INSTANCE = Mappers.getMapper(AuthConverter.class);

    @Mapping(source = "userId", target="username")
    @Mapping(source = "passwd", target="password")
    ResLoginDto convertLogin(UserInfo userInfo);

    @Mapping(source = "userId", target="username")
    @Mapping(source = "cnlCd", target="pathCode")
    ResFindIdDto convertFindId(FindIdInfo findIdInfo);

    @Mapping(source = "userId", target="username")
    @Mapping(source = "cnlCd", target="pathCode")
    ResFindPwDto convertFindPw(FindPwInfo findPwInfo);
}
