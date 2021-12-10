package com.fashion.celebrity.auth.service;

import com.fashion.celebrity.auth.dto.*;
import com.fashion.celebrity.auth.model.DupUserInfo;

public interface AuthService {
    /* 유저정보 조회(UserDetails) */
    UserDto selectAuthUser(String username);

    /* 이메일 중복 체크 */
    DupUserInfo selectDupMail(ValidateDtos.RequestEmail dto);

    /* 이메일 정보 db 저장 */
    int createEmail(String email);

    /* 이메일 전송 */
    boolean sendEmail(ValidateDtos.RequestEmail dto);

    /* 이메일 인증 코드 조회 */
    String selectCertCode(String email);

    /* 닉네임 중복 체크 */
    DupUserInfo selectNickname(String nickname);

    /* 회원가입 */
    int createUser(SignupDtos.Request dto);

    /* 유저정보 조회 */
    LoginDtos.Response selectUser(String username);

    /* 로그인한 유저 정보 업데이트 */
    int updateUserLogin(String email);

    /* 로그인 시도 업데이트 */
    int updateTrial(LoginDtos.Request dto);

    /* 유저 상태 변경 */
    int updateUserStatus(LoginDtos.Request dto);

    /* 로그아웃한 유저 정보 업데이트 */
    int updateUserLogout(String email);

    /* 아이디찾기 */
    FindDtos.ResponseIdPw findId(String phone);

    /* 비밀번호 찾기 */
    FindDtos.ResponseIdPw findPw(FindDtos.RequestPw dto);

    /* 비밀번호 변경 */
    int modPw(ModifyDtos.RequestPw dto);
}
