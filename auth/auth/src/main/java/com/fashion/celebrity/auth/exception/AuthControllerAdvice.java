package com.fashion.celebrity.auth.exception;

import com.fashion.celebrity.auth.common.dto.ApiDto;
import com.fashion.celebrity.auth.common.dto.CommonResCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    final Logger log = LoggerFactory.getLogger(AuthControllerAdvice.class);

    // method 잘못된 경우
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleWrongMethod(HttpRequestMethodNotSupportedException ex) {
        log.error("handleWrongMethod - HttpRequestMethodNotSupportedException caught error", ex);

        ApiDto apiDto = new ApiDto();

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM003.name());
        apiDto.setMessage(CommonResCode.CM003.getMessage());

        return new ResponseEntity<Object>(apiDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // HEADER: 누락된 경우
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<?> handleMissingHeader(MissingRequestHeaderException ex) {
        log.error("handleMissingHeader - MissingRequestHeaderException caught error", ex);

        ApiDto apiDto = new ApiDto();
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getHeaderName(), "비어있을 수 없습니다.");

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM004.name());
        apiDto.setMessage(CommonResCode.CM004.getMessage());
        apiDto.setObj(errors);

        return new ResponseEntity<Object>(apiDto, HttpStatus.UNAUTHORIZED);
    }

    // TOKEN: 잘못된 토큰을 입력한 경우
    @ExceptionHandler({MalformedJwtException.class})
    public ResponseEntity<?> handleMissingHeader(MalformedJwtException ex) {
        log.error("handleMissingHeader - MalformedJwtException caught error", ex);

        ApiDto apiDto = new ApiDto();

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM001.name());
        apiDto.setMessage(CommonResCode.CM001.getMessage());

        return new ResponseEntity<Object>(apiDto, HttpStatus.UNAUTHORIZED);
    }

    // TOKEN: 토큰 만료
    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<?> handleExpiredToken(ExpiredJwtException ex) {
        log.error("handleExpiredToken - ExpiredJwtException caught error", ex);

        ApiDto apiDto = new ApiDto();

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM002.name());
        apiDto.setMessage(CommonResCode.CM002.getMessage());

        return new ResponseEntity<Object>(apiDto, HttpStatus.UNAUTHORIZED);
    }

    // GET: 필수 파라미터 아예 없을 때(파라미터 중 아무것도 전달하지 않음)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleMissingGetReqParam(MissingServletRequestParameterException ex) {
        log.error("handleMissingGetReqParam - MissingServletRequestParameterException caught error", ex);

        ApiDto apiDto = new ApiDto();
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getParameterName(), "비어있을 수 없습니다.");

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM004.name());
        apiDto.setMessage(CommonResCode.CM004.getMessage());
        apiDto.setObj(errors);

        return new ResponseEntity<Object>(apiDto, HttpStatus.BAD_REQUEST);
    }

    // GET: 필수 파라미터 값이 비어있는 경우 (ex.userId:)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleBlankGetReqParam(ConstraintViolationException ex) {
        log.error("handleBlankGetReqParam - ConstraintViolationException caught error", ex);

        ApiDto apiDto = new ApiDto();
        Map<String, String> errors = new HashMap<>();

        ConstraintViolation<?> firstError = ex.getConstraintViolations().iterator().next();
        String queryParamPath = firstError.getPropertyPath().toString();
        String queryParam = queryParamPath.contains(".") ?
                queryParamPath.substring(queryParamPath.indexOf(".") + 1) :
                queryParamPath;

        errors.put(queryParam, firstError.getMessage());

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM004.name());
        apiDto.setMessage(CommonResCode.CM004.getMessage());
        apiDto.setObj(errors);

        return new ResponseEntity<Object>(apiDto, HttpStatus.BAD_REQUEST);
    }

    // POST: requestbody 필수 parameter 빼먹었을 때
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMissingPostReqParam(MethodArgumentNotValidException ex) {
        log.error("handleMissingPostReqParam - MethodArgumentNotValidException caught error", ex);

        ApiDto apiDto = new ApiDto();
        Map<String, String> errors = new HashMap<>();
        Object firstError = ex.getBindingResult().getAllErrors().get(0);
        errors.put(((FieldError) firstError).getField(), ((FieldError) firstError).getDefaultMessage());

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM005.name());
        apiDto.setMessage(CommonResCode.CM005.getMessage());
        apiDto.setObj(errors);

        return new ResponseEntity<Object>(apiDto, HttpStatus.BAD_REQUEST);
    }

    // request 데이터 타입 오류
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleValidationExceptions(HttpMessageNotReadableException ex) {
        log.error("handleValidationExceptions - HttpMessageNotReadableException caught error", ex);

        ApiDto apiDto = new ApiDto();

        apiDto.setSuccess(false);
        apiDto.setCode(CommonResCode.CM006.name());
        apiDto.setMessage(CommonResCode.CM006.getMessage());

        return new ResponseEntity<Object>(apiDto, HttpStatus.BAD_REQUEST);
    }

    // DB: 중복된 값 insert 하는 경우
    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<?> handleDBDupError(DuplicateKeyException ex) {
        log.error("handleDBDupError - DuplicateKeyException caught error", ex);

        ApiDto apiDto = new ApiDto();

        if (ex.getCause().getMessage().contains("Duplicate")) {
            apiDto.setSuccess(false);
            apiDto.setCode(CommonResCode.CM007.name());
            apiDto.setMessage(CommonResCode.CM007.getMessage());
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode(CommonResCode.CM008.name());
            apiDto.setMessage(CommonResCode.CM008.getMessage());
        }

        return new ResponseEntity<Object>(apiDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // DB: not-null 항목 안넣은 경우
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDBNotNullError(DataIntegrityViolationException ex) {
        log.error("handleDBNotNullError - DataIntegrityViolationException caught error", ex);

        ApiDto apiDto = new ApiDto();

        if (ex.getCause().getMessage().contains("null")) {
            apiDto.setSuccess(false);
            apiDto.setCode(CommonResCode.CM009.name());
            apiDto.setMessage(CommonResCode.CM009.getMessage());
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode(CommonResCode.CM008.name());
            apiDto.setMessage(CommonResCode.CM008.getMessage());
        }

        return new ResponseEntity<Object>(apiDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
