package com.fashion.celebrity.auth.controller;

import com.fashion.celebrity.auth.common.dto.ApiDto;
import com.fashion.celebrity.auth.common.dto.AuthResCode;
import com.fashion.celebrity.auth.common.security.JwtTokenProvider;
import com.fashion.celebrity.auth.dto.*;
import com.fashion.celebrity.auth.model.DupUserInfo;
import com.fashion.celebrity.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/v1/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AuthService authService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtTokenProvider tokenProvider;


    /**
     * @api {get} /v1/auth/validate/email 01.ValidateEmail
     * @apiName ValidateEmail
     * @apiGroup Auth
     * @apiDescription 이메일 유효성 체크 및 인증번호 전송
     * 
     * @apiParam {String} email 이메일 아이디
     * 
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU007",
     *          "message": "인증번호를 발송하였습니다."
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU006",
     *          "message": "이미 존재하는 아이디입니다."
     *      }
     * @apiSuccessExample {json} False-Response2:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU008",
     *          "message": "인증번호 발송에 실패하였습니다."
     *      }
     */
    @GetMapping(value="/validate/email")
    public ResponseEntity<Object> ValidateEmail(@Valid ValidateDtos.RequestEmail dto) {
        logger.info("ValidateEmail {}", dto);
        ApiDto apiDto;

        DupUserInfo user = this.authService.selectDupMail(dto);

        // 중복된 이메일 존재하는 경우
        if (user != null) {
            apiDto = new ApiDto(false, AuthResCode.AU006);
        } else {
            try {
                this.authService.createEmail(dto.getEmail());

                boolean sent;
                sent = this.authService.sendEmail(dto);

                if (sent) {
                    apiDto = new ApiDto(true, AuthResCode.AU007);
                } else {
                    apiDto = new ApiDto(false, AuthResCode.AU008);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                apiDto = new ApiDto(false, AuthResCode.AU008);
            }
        }

        logger.info("ValidateEmail res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }

    /**
     * @api {post} /v1/auth/validate/check/email 02.ValidateCheckEmail
     * @apiName ValidateCheckEmail
     * @apiGroup Auth
     * @apiDescription 이메일 인증하기
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} certCode 인증번호 10자리
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com",
     *          "certCode": "PRc2Cq8I04"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU009",
     *          "message": "이메일 인증에 성공하였습니다."
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU010",
     *          "message": "이메일 인증에 실패하였습니다."
     *      }
     */
    @PostMapping(value="/validate/check/email")
    public ResponseEntity<Object> ValidateCheckEmail(@Valid @RequestBody ValidateCheckDtos.RequestEmail dto) {
        logger.info("ValidateCheckEmail {}", dto);
        ApiDto apiDto;

        String certNum = this.authService.selectCertCode(dto.getEmail());

        if (certNum.equals(dto.getCertCode())) {
            apiDto = new ApiDto(true, AuthResCode.AU009);
        } else {
            apiDto = new ApiDto(false, AuthResCode.AU010);
        }

        logger.info("ValidateCheckEmail res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }

    /**
     * @api {get} /v1/auth/validate/nickname 03.ValidateNickname
     * @apiName ValidateNickname
     * @apiGroup Auth
     * @apiDescription 닉네임 중복체크
     *
     * @apiParam {String} nickname 별명
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "nickname": "딸기"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU012",
     *          "message": "사용 가능한 닉네임입니다."
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU011",
     *          "message": "이미 존재하는 닉네임입니다."
     *      }
     *
     */
    @GetMapping(value="/validate/nickname")
    public ResponseEntity<Object> ValidateNickname(@Valid ValidateDtos.RequestNickname dto) {
        logger.info("ValidateNickname {}", dto);
        ApiDto apiDto;

        DupUserInfo user = this.authService.selectNickname(dto.getNickname());

        if (user != null) {
            apiDto = new ApiDto(false, AuthResCode.AU011);
        } else {
            apiDto = new ApiDto(true, AuthResCode.AU012);
        }

        logger.info("ValidateNickname res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }


    /**
     * @api {post} /v1/auth/signup 04.Signup
     * @apiName Signup
     * @apiGroup Auth
     * @apiDescription 회원가입
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} password 비밀번호
     * @apiParam {String} nickname 별명
     * @apiParam {String} phone 핸드폰 번호('-' 제외)
     * @apiParam {String} gender 성별(0: 남성 / 1: 여성)
     * @apiParam {String} birthDate 생년월일(YYYYMMDD)
     * @apiParam {String} mktYn 마케팅 수신 동의 여부
     * @apiParam {String} pathCode 가입경로 코드(01: 이메일 로그인, 현재는 01만 존재)
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com",
     *          "password": "asdf",
     *          "nickname": "딸기",
     *          "phone": "01012341234",
     *          "gender": "1",
     *          "birthday": "20220101",
     *          "marketingYn": "N",
     *          "pathCode": "01"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU001",
     *          "message": "성공적으로 등록되었습니다."
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU020",
     *          "message": "처리 중 오류가 발생하였습니다."
     *      }
     */
    @PostMapping(value="/signup")
    public ResponseEntity<Object> Signup(@Valid @RequestBody SignupDtos.Request dto) {
        logger.info("Signup {}", dto);
        ApiDto apiDto;

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        try {
            this.authService.createUser(dto);
            apiDto = new ApiDto(true, AuthResCode.AU001);
        } catch (Exception ex) {
            ex.printStackTrace();
            apiDto = new ApiDto(false, AuthResCode.AU020);
        }

        logger.info("Signup res ::: {}", apiDto);


        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }



    /**
     * @api {post} /v1/auth/login 05.Login
     * @apiName Login
     * @apiGroup Auth
     * @apiDescription 로그인
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} password 비밀번호
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     * @apiSuccess {Object} obj 유저 정보를 담은 객체
     * @apiSuccess {String} obj.email 이메일 아이디
     * @apiSuccess {String} obj.status 유저 상태코드
     * @apiSuccess {String} obj.accessToken access token
     * @apiSuccess {String} obj.refreshToken refresh token
     * @apiSuccess {String} obj.count 비밀번호 오류 횟수
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com",
     *          "password": "asdf"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU013",
     *          "message": "로그인에 성공하였습니다.",
     *          "obj": {
     *              "email": "guswlsapdlf@naver.com",
     *              "status": "0",
     *              "accessToken": (access token),
     *              "refreshToken": (refresh token)
     *          }
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU014",
     *          "message": "아이디 또는 비밀번호가 일치하지 않습니다."
     *      }
     * @apiSuccessExample {json} False-Response2:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU015",
     *          "message": "가입을 완료해주세요."
     *      }
     * @apiSuccessExample {json} False-Response3:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU016",
     *          "message": "잠금되었거나 중지된 계정입니다."
     *      }
     * @apiSuccessExample {json} False-Response4:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU017",
     *          "message": "로그인 5회 이상 오류로 계정이 잠금되었습니다."
     *      }
     */
    @PostMapping(value="/login")
    public ResponseEntity<Object> Login(@Valid @RequestBody LoginDtos.Request dto) {
        logger.info("Login {}", dto);
        ApiDto apiDto;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            LoginDtos.Response user = this.authService.selectUser(dto.getEmail());

            if (!user.getStatus().equals("0")) {
                // 삭제 계정인지 체크
                if (user.getStatus().equals("4")) {
                    logger.info("login ::: failed, deleted user, email {}", user.getEmail());
                    apiDto = new ApiDto(false, AuthResCode.AU014);
                } else if(user.getStatus().equals("3")) {
                    logger.info("login ::: failed, temp user, email {}", user.getEmail());
                    apiDto = new ApiDto(false, AuthResCode.AU015);
                } else {
                    logger.info("login ::: failed, locked user, email {}", user.getEmail());
                    apiDto = new ApiDto(false, AuthResCode.AU016);
                }
            } else {
                // 정상 로그인 성공
                logger.info("login ::: success, email {}", dto.getEmail());

                this.authService.updateUserLogin(dto.getEmail());

                String access = tokenProvider.generateToken(authentication, "ACCESS");
                String refresh = tokenProvider.generateToken(authentication, "REFRESH");

                user.setAccessToken(access);
                user.setRefreshToken(refresh);
                apiDto = new ApiDto(true, AuthResCode.AU013, user);
            }
        } catch (Exception ex) {
            logger.error("ex {}", ex.getMessage());
            try {
                this.authService.updateTrial(dto);

                logger.info("login ::: failed, email {} (trial {})", dto.getEmail(), dto.getCount());

                if (dto.getCount() >= 5) {
                    dto.setStatus("1");
                    this.authService.updateUserStatus(dto);
                    apiDto = new ApiDto(false, AuthResCode.AU017);
                } else {
                    apiDto = new ApiDto(false, AuthResCode.AU014);
                }
            } catch (Exception ex2) {
                logger.error("ex2 {}", ex2.getMessage());
                logger.info("login ::: failed, typed email is not in db");
                apiDto = new ApiDto(false, AuthResCode.AU014);
            }
        }

        logger.info("Login res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }


    /**
     * @api {get} /v1/auth/logout 06.Logout
     * @apiName Logout
     * @apiGroup Auth
     * @apiDescription 로그아웃
     *
     * @apiParam {String} email 이메일 아이디
     *
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com"
     *      }
     *
     */
    @GetMapping(value="/logout")
    public ResponseEntity<?> Logout(@Valid LogoutDtos.Request dto) {
        logger.info("Logout {}", dto);
        logger.info("logout ::: email {}", dto.getEmail());

        this.authService.updateUserLogout(dto.getEmail());

        return ResponseEntity.ok(null);
    }



    /**
     * @api {post} /v1/auth/find/id 07.FindId
     * @apiName FindId
     * @apiGroup Auth
     * @apiDescription 아이디 찾기
     *
     * @apiParam {String} phone 핸드폰 번호('-' 제외)
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     * @apiSuccess {String} obj 유저 정보를 담은 객체
     * @apiSuccess {String} obj.email 이메일 아이디
     * @apiSuccess {String} obj.pathCode 가입경로 코드(01: 이메일 로그인, 현재는 01만 존재)
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "phone": "01012341234"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU002",
     *          "message": "성공적으로 조회되었습니다.",
     *          "obj": {
     *              "email": "guswlsapdlf@naver.com",
     *              "pathCode": "01"
     *          }
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU005",
     *          "message": "조회 결과가 없습니다."
     *      }
     *
     */
    @PostMapping(value="/find/id")
    public ResponseEntity<Object> FindId(@Valid @RequestBody FindDtos.RequestId dto) {
        logger.info("FindId {}", dto);
        ApiDto apiDto;

        FindDtos.ResponseIdPw user = this.authService.findId(dto.getPhone());

        if (user != null) {
            apiDto = new ApiDto(true, AuthResCode.AU002, user);
        } else {
            apiDto = new ApiDto(false, AuthResCode.AU005);
        }

        logger.info("FindId res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }



    /**
     * @api {post} /v1/auth/find/password 08.FindPw
     * @apiName FindPw
     * @apiGroup Auth
     * @apiDescription 비밀번호 찾기
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} phone 핸드폰 번호('-' 제외)
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     * @apiSuccess {String} obj 유저 정보를 담은 객체
     * @apiSuccess {String} obj.email 이메일 아이디
     * @apiSuccess {String} obj.pathCode 가입경로 코드(01: 이메일 로그인, 현재는 01만 존재)
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com",
     *          "phone": "01012341234"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU002",
     *          "message": "성공적으로 조회되었습니다.",
     *          "obj": {
     *              "email": "guswlsapdlf@naver.com",
     *              "pathCode": "01"
     *          }
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU005",
     *          "message": "조회 결과가 없습니다."
     *      }
     *
     */
    @PostMapping(value="/find/password")
    public ResponseEntity<Object> FindPw(@Valid @RequestBody FindDtos.RequestPw dto) {
        logger.info("FindPw {}", dto);
        ApiDto apiDto;

        FindDtos.ResponseIdPw user = this.authService.findPw(dto);

        if (user != null) {
            apiDto = new ApiDto(true, AuthResCode.AU002, user);
        } else {
            apiDto = new ApiDto(false, AuthResCode.AU005);
        }

        logger.info("FindPw res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }


    /**
     * @api {post} /v1/auth/modify/password 09.ModifyPw
     * @apiName ModifyPw
     * @apiGroup Auth
     * @apiDescription 비밀번호 변경
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} password 새 비밀번호
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com",
     *          "password": "asdf"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU003",
     *          "message": "성공적으로 수정되었습니다."
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU020",
     *          "message": "처리 중 오류가 발생하였습니다."
     *      }
     *
     */
    @PostMapping(value="/modify/password")
    public ResponseEntity<Object> ModifyPw(@Valid @RequestBody ModifyDtos.RequestPw dto) {
        logger.info("ModifyPw {}", dto);
        ApiDto apiDto;

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        int row = this.authService.modPw(dto);

        if (row == 1) {
            apiDto = new ApiDto(true, AuthResCode.AU003);
        } else {
            apiDto = new ApiDto(false, AuthResCode.AU020);
        }

        logger.info("ModifyPw res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }

    
    /**
     * @api {post} /v1/auth/refresh/token 10.RefreshToken
     * @apiName RefreshToken
     * @apiGroup Auth
     * @apiDescription 토큰 갱신
     *
     * @apiParam {String} email 이메일 아이디
     * @apiParam {String} accessToken access token
     * @apiParam {String} refreshToken refresh token
     *
     * @apiSuccess {String} success API 성공 여부
     * @apiSuccess {String} code API 응답 코드
     * @apiSuccess {String} message API 응답 메시지
     * @apiSuccess {String} obj 새로 생성된 토큰 정보를 담은 객체
     * @apiSuccess {String} obj.accessToken 새로 발급된 access token
     *
     * @apiParamExample {json} Request-Example:
     *      {
     *          "email": "guswlsapdlf@naver.com",
     *          "accessToken": "(access token)",
     *          "refreshToken": "(refresh token)"
     *      }
     *
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": true,
     *          "code": "AU018",
     *          "message": "access token이 정상적으로 재발급되었습니다.",
     *          "obj": {
     *              "accessToken": "(access token)"
     *          }
     *      }
     * @apiSuccessExample {json} False-Response:
     *      HTTP/1.1 200 OK
     *      {
     *          "success": false,
     *          "code": "AU019",
     *          "message": "재로그인이 필요합니다."
     *      }
     *
     */
    @PostMapping(value="/refresh/token")
    public ResponseEntity<Object> RefreshToken(@Valid @RequestBody TokenDtos.RequestRenew dto) {
        logger.info("RefreshToken {}", dto);
        ApiDto apiDto;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (tokenProvider.validateToken(dto.getRefreshToken())) {
            String access = tokenProvider.generateToken(auth, "ACCESS");

            TokenDtos.ResponseRenew res = new TokenDtos.ResponseRenew();
            res.setAccessToken(access);

            apiDto = new ApiDto(true, AuthResCode.AU018, res);
        } else {
            apiDto = new ApiDto(false, AuthResCode.AU019);
        }

        logger.info("RefreshToken res ::: {}", apiDto);

        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }

}

