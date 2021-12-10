package com.fashion.celebrity.auth.mapper.converter;

import com.fashion.celebrity.auth.dto.FindDtos;
import com.fashion.celebrity.auth.dto.LoginDtos;
import com.fashion.celebrity.auth.dto.UserDto;
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
    UserDto convertAuthLogin(UserInfo userInfo);

    @Mapping(source = "userId", target="email")
    LoginDtos.Response convertLogin(UserInfo userInfo);

    @Mapping(source = "userId", target="email")
    @Mapping(source = "cnlCd", target="pathCode")
    FindDtos.ResponseIdPw convertFindId(FindIdInfo findIdInfo);

    @Mapping(source = "userId", target="email")
    @Mapping(source = "cnlCd", target="pathCode")
    FindDtos.ResponseIdPw convertFindPw(FindPwInfo findPwInfo);
}
