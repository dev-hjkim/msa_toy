package com.fashion.celebrity.auth.mapper;

import com.fashion.celebrity.auth.dto.request.*;
import com.fashion.celebrity.auth.model.DupUserInfo;
import com.fashion.celebrity.auth.model.FindIdInfo;
import com.fashion.celebrity.auth.model.FindPwInfo;
import com.fashion.celebrity.auth.model.UserInfo;

public interface AuthMapper {
    DupUserInfo selectDupMail(ReqDupMailDto dto);
    int createMail(String username);
    int updateCertCode(ReqDupMailDto dto);
    String selectCertCode(String username);
    DupUserInfo selectNickname(String nickname);
    int createUser(ReqJoinDto dto);
    UserInfo selectUser(String username);
    int updateUserLogin(String username);
    int updateTrial(ReqLoginDto dto);
    int updateUserStatus(ReqLoginDto dto);
    int updateUserLogout(String username);
    FindIdInfo findId(String phone);
    FindPwInfo findPw(ReqFindPwDto dto);
    int modPw(ReqModPwDto dto);
}
