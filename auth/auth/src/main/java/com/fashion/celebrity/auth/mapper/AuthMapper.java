package com.fashion.celebrity.auth.mapper;

import com.fashion.celebrity.auth.dto.*;
import com.fashion.celebrity.auth.model.DupUserInfo;
import com.fashion.celebrity.auth.model.FindIdInfo;
import com.fashion.celebrity.auth.model.FindPwInfo;
import com.fashion.celebrity.auth.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    UserInfo selectAuthUser(String username);
    DupUserInfo selectDupMail(ValidateDtos.RequestEmail dto);
    int createEmail(String email);
    int updateCertCode(ValidateDtos.RequestEmail dto);
    String selectCertCode(String email);
    DupUserInfo selectNickname(String nickname);
    int createUser(SignupDtos.Request dto);
    UserInfo selectUser(String email);
    int updateUserLogin(String email);
    int updateTrial(LoginDtos.Request dto);
    int updateUserStatus(LoginDtos.Request dto);
    int updateUserLogout(String email);
    FindIdInfo findId(String phone);
    FindPwInfo findPw(FindDtos.RequestPw dto);
    int modPw(ModifyDtos.RequestPw dto);
}
