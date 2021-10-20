package com.fashion.celebrity.auth.service;

import com.fashion.celebrity.auth.dto.UserDto;
import com.fashion.celebrity.auth.dto.request.*;
import com.fashion.celebrity.auth.dto.response.ResFindIdDto;
import com.fashion.celebrity.auth.dto.response.ResFindPwDto;
import com.fashion.celebrity.auth.dto.response.ResLoginDto;
import com.fashion.celebrity.auth.model.DupUserInfo;

public interface AuthService {
    /* 유저정보 조회(UserDetails) */
    UserDto selectAuthUser(String username);

    /* 이메일 중복 체크 */
    DupUserInfo selectDupMail(ReqDupMailDto dto);

    /* 이메일 정보 db 저장 */
    int createMail(String username);

    /* 이메일 전송 */
    boolean sendEmail(ReqDupMailDto dto);

    /* 이메일 인증 코드 조회 */
    String selectCertCode(String username);

    /* 닉네임 중복 체크 */
    DupUserInfo selectNickname(String nickname);

    /* 회원가입 */
    int createUser(ReqJoinDto dto);

    /* 유저정보 조회 */
    ResLoginDto selectUser(String username);

    /* 로그인한 유저 정보 업데이트 */
    int updateUserLogin(String username);

    /* 로그인 시도 업데이트 */
    int updateTrial(ReqLoginDto dto);

    /* 유저 상태 변경 */
    int updateUserStatus(ReqLoginDto dto);

    /* 로그아웃한 유저 정보 업데이트 */
    int updateUserLogout(String username);

    /* 아이디찾기 */
    ResFindIdDto findId(String phone);

    /* 비밀번호 찾기 */
    ResFindPwDto findPw(ReqFindPwDto dto);

    /* 비밀번호 변경 */
    int modPw(ReqModPwDto dto);
}
