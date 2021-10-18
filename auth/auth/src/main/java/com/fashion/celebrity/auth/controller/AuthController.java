package com.fashion.celebrity.auth.controller;

import com.fashion.celebrity.auth.common.model.ApiInfo;
import com.fashion.celebrity.auth.common.security.JwtTokenProvider;
import com.fashion.celebrity.auth.dto.request.*;
import com.fashion.celebrity.auth.dto.response.ResFindIdDto;
import com.fashion.celebrity.auth.dto.response.ResFindPwDto;
import com.fashion.celebrity.auth.dto.response.ResLoginDto;
import com.fashion.celebrity.auth.model.DupUserInfo;
import com.fashion.celebrity.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;


    /**
     * request: username
     * response: apiInfo
     * desc: 이메일 유효성 체크 및 인증번호 전송
     *
     {
     "username": "guswlsapdlf@naver.com"
     }
     */
    @CrossOrigin
    @PostMapping(value="/validate/mail")
    public ResponseEntity<?> ValidateMail(@RequestBody ReqDupMailDto dto) {
        logger.info("ValidateMail {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        DupUserInfo user = this.authService.selectDupMail(dto);

        if (user != null) {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SE03");
            apiInfo.setMessage("이미 존재하는 아이디입니다.");
        } else {
            try {
                this.authService.createMail(dto.getUsername());

                boolean sended;
                sended = this.authService.sendEmail(dto);

                if (sended) {
                    apiInfo.setSuccess(true);
                    apiInfo.setCode("SS03");
                    apiInfo.setMessage("인증번호를 발송하였습니다.");
                } else {
                    apiInfo.setSuccess(false);
                    apiInfo.setCode("SE04");
                    apiInfo.setMessage("인증번호 발송에 실패하였습니다. 담당자에게 문의해주세요.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                apiInfo.setSuccess(false);
                apiInfo.setCode("SE04");
                apiInfo.setMessage("인증번호 발송에 실패하였습니다. 담당자에게 문의해주세요.");
            }
        }

        logger.info("ValidateMail res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }

    /**
     * request: username, certCode
     * response: apiInfo
     * desc: 이메일 인증하기
     *
     {
     "username": "guswlsapdlf@naver.com",
     "certCode": "PRc2Cq8I04"
     }
     */
    @CrossOrigin
    @PostMapping(value="/validate/mail/cert")
    public ResponseEntity<?> ValidateMailCheck(@RequestBody ReqCertMailDto dto) {
        logger.info("ValidateMailCheck {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        String certNum = this.authService.selectCertCode(dto.getUsername());

        if (certNum.equals(dto.getCertCode())) {
            apiInfo.setSuccess(true);
            apiInfo.setCode("SS04");
            apiInfo.setMessage("이메일 인증에 성공하였습니다.");
        } else {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SE05");
            apiInfo.setMessage("이메일 인증에 실패하였습니다.");
        }

        logger.info("ValidateMailCheck res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }

    /**
     * request: nickname
     * response: apiInfo
     * desc: 닉네임 중복체크
     *
     {
     "nickname": "딸기"
     }
     */
    @CrossOrigin
    @PostMapping(value="/validate/nickname")
    public ResponseEntity<?> ValidateNickname(@RequestBody ReqDupNickDto dto) {
        logger.info("ValidateNickname {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        DupUserInfo user = this.authService.selectNickname(dto.getNickname());

        if (user != null) {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SE06");
            apiInfo.setMessage("이미 존재하는 닉네임입니다.");
        } else {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SS06");
            apiInfo.setMessage("사용 가능한 닉네임입니다.");
        }

        logger.info("ValidateNickname res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }


    /**
     * request: username, password, nickname, phone, gender, birthDt, mktYn, pathCode
     * response: apiInfo(obj: userId, channelCd
     * desc: 회원가입
     *
     *
     {
     "username": "guswlsapdlf@naver.com",
     "password": "asdf",
     "nickname": "딸기",
     "phone": "01066331834",
     "gender": "02",
     "birthday": "20000101",
     "mktYn": "N",
     "pathCode": "01"
     }
     */
    @CrossOrigin
    @PostMapping(value="/regist")
    public ResponseEntity<?> Regist(@RequestBody ReqJoinDto dto) {
        logger.info("Regist {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        try {
            this.authService.createUser(dto);

            apiInfo.setSuccess(true);
            apiInfo.setCode("CS01");
            apiInfo.setMessage("성공적으로 등록되었습니다.");
        } catch (Exception ex) {
            ex.printStackTrace();
            apiInfo.setSuccess(false);
            apiInfo.setCode("CE99");
            apiInfo.setMessage("등록 중 오류가 발생했습니다. 담당자에게 문의해주세요.");
        }

        logger.info("Regist res ::: {}", apiInfo);


        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }


    /**
     * request: username, password
     * response: apiInfo
     *      (obj: username, status, accessToken, refreshToken, count)
     * desc: 로그인
     *
     {
     "username": "guswlsapdlf@naver.com",
     "password": "asdf"
     }
     */
    @CrossOrigin
    @PostMapping(value="/login")
    public ResponseEntity<?> Login(@RequestBody ReqLoginDto dto) {
        logger.info("Login {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        // 현재 날짜
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // 접속자의 ip 주소
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                ResLoginDto user = this.authService.selectUser(dto.getUsername());

                if (!user.getStatus().equals("0")) {
                    // 삭제 계정인지 체크
                    if (user.getStatus().equals("4")) {
                        logger.info("login ::: failed, deleted user, username {}, ip {}", user.getUsername(), ip);

                        apiInfo.setSuccess(false);
                        apiInfo.setCode("LE01");
                        apiInfo.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
                    } else if(user.getStatus().equals("3")) {
                        logger.info("login ::: failed, temp user, username {}, ip {}", user.getUsername(), ip);

                        apiInfo.setSuccess(false);
                        apiInfo.setCode("LE02");
                        apiInfo.setMessage("가입을 완료해주세요.");

                    } else {
                        logger.info("login ::: failed, locked user, username {}, ip {}", user.getUsername(), ip);

                        apiInfo.setSuccess(false);
                        apiInfo.setCode("LE03");
                        apiInfo.setMessage("잠금되었거나 중지된 계정입니다. 담당자에게 문의해주세요.");
                    }
                } else {
                    // 정상 로그인 성공
                    logger.info("login ::: success, username {}, ip {}", dto.getUsername(), ip);

                    this.authService.updateUserLogin(dto.getUsername());

                    String access = tokenProvider.generateToken(authentication, "ACCESS");
                    String refresh = tokenProvider.generateToken(authentication, "REFRESH");

                    user.setAccessToken(access);
                    user.setRefreshToken(refresh);
                    apiInfo.setObj(user);

                    if (dto.getUsername().equals(dto.getPassword())) {
                        apiInfo.setSuccess(true);
                        apiInfo.setMessage("LS02");
                        apiInfo.setMessage("비밀번호 변경이 필요합니다.");
                    } else {
                        apiInfo.setSuccess(true);
                        apiInfo.setCode("LS01");
                        apiInfo.setMessage("로그인에 성공하였습니다.");
                    }
                }
            } catch (Exception ex) {
                logger.error("ex {}", ex.getMessage());
                try {
                    this.authService.updateTrial(dto);

                    logger.info("login ::: failed, username {}, ip {}, (trial {})", dto.getUsername(), ip, dto.getCount());

                    if (dto.getCount() >= 5) {
                        dto.setStatus("1");
                        this.authService.updateUserStatus(dto);

                        apiInfo.setSuccess(false);
                        apiInfo.setCode("LE04");
                        apiInfo.setMessage("로그인 5회 이상 오류로 계정이 잠금되었습니다.");
                    } else {
                        apiInfo.setSuccess(false);
                        apiInfo.setCode("LE01");
                        apiInfo.setMessage("아이디 또는 비밀번호가 일치하지 않습니다. (" + dto.getCount() + "회 실패)");
                    }
                } catch (Exception ex2) {
                    logger.error("ex2 {}", ex2.getMessage());
                    logger.info("login ::: failed, typed username is not in db, ip {}", ip);

                    apiInfo.setSuccess(false);
                    apiInfo.setCode("LE01");
                    apiInfo.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
                }
            }
        } else {
            // 세션
            ResLoginDto user = this.authService.selectUser(dto.getUsername());
            apiInfo.setObj(user);
        }

        logger.info("Login res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }

    /**
     * request: username
     * response: null
     * desc: 로그아웃
     *
     {
     "username": "guswlsapdlf@naver.com"
     }
     */

    @CrossOrigin
    @PostMapping(value="/logout")
    public ResponseEntity<?> Logout(@RequestBody String username) {
        logger.info("Logout {}", username);

        // 접속자의 ip 주소
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();

        logger.info("logout ::: username {}, ip {}", username, ip);

        this.authService.updateUserLogout(username);

        return ResponseEntity.ok(null);
    }

    /**
     * request: phone
     * response: apiInfo(obj: username, pathCode)
     * desc: 아이디 찾기
     *
     {
     "phone": "01066331834"
     }
     */
    @CrossOrigin
    @PostMapping(value="/find/id")
    public ResponseEntity<?> FindId(@RequestBody ReqFindIdDto dto) {
        logger.info("FindId {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        ResFindIdDto user = this.authService.findId(dto.getPhone());

        if (user != null) {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SS01");
            apiInfo.setMessage("조회에 성공하였습니다.");
            apiInfo.setObj(user);
        } else {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SE02");
            apiInfo.setMessage("가입 정보가 존재하지 않습니다.");
        }

        logger.info("FindId res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }


    /**
     * request: username, phone
     * response: apiInfo
     * desc: 비밀번호 찾기
     *
     {
     "username": "guswlsapdlf@naver.com",
     "phone": "01066331834"
     }
     */
    @CrossOrigin
    @PostMapping(value="/find/pw")
    public ResponseEntity<?> FindPw(@RequestBody ReqFindPwDto dto) {
        logger.info("FindPw {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        ResFindPwDto user = this.authService.findPw(dto);

        if (user != null) {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SS01");
            apiInfo.setMessage("조회에 성공하였습니다.");
        } else {
            apiInfo.setSuccess(false);
            apiInfo.setCode("SE02");
            apiInfo.setMessage("가입 정보가 존재하지 않습니다.");
        }

        logger.info("FindPw res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }

    /**
     * request: username, password
     * response: apiInfo
     * desc: 비밀번호 변경
     *
     {
     "username": "guswlsapdlf@naver.com",
     "password": "asdf"
     }
     */
    @CrossOrigin
    @PostMapping(value="/mod/pw")
    public ResponseEntity<?> ModPw(@RequestBody ReqModPwDto dto) {
        logger.info("ModPw {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        int row = this.authService.modPw(dto);

        if (row == 1) {
            apiInfo.setSuccess(true);
            apiInfo.setCode("US01");
            apiInfo.setMessage("수정에 성공하였습니다.");
        } else {
            apiInfo.setSuccess(false);
            apiInfo.setCode("UE99");
            apiInfo.setMessage("수정 중 오류가 발생하였습니다. 담당자에게 문의해주세요.");
        }

        logger.info("ModPw res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }


    /**
     * request: username, accessToken, refreshToken
     * response: apiInfo
     * desc: 토큰 갱신
     *
     {
     "username": "guswlsapdlf@naver.com",
     "accessToken": "",
     "refreshToken": ""
     }
     */
    @CrossOrigin
    @PostMapping(value="/token")
    public ResponseEntity<?> GetToken(@RequestBody ReqTokenDto dto) {
        logger.info("GetToken {}", dto);

        ApiInfo apiInfo = new ApiInfo();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (tokenProvider.validateToken(dto.getRefreshToken())) {

            String access = tokenProvider.generateToken(auth, "ACCESS");
            apiInfo.setObj(access);

            apiInfo.setSuccess(true);
            apiInfo.setCode("");
            apiInfo.setMessage("access token이 정상적으로 발급되었습니다.");

        } else {
            apiInfo.setSuccess(false);
            apiInfo.setCode("");
            apiInfo.setMessage("재로그인이 필요합니다.");
        }

        logger.info("GetToken res ::: {}", apiInfo);

        return new ResponseEntity<Object>(apiInfo, HttpStatus.OK);
    }

}

