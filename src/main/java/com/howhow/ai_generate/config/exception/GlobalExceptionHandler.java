package com.howhow.ai_generate.config.exception;

import com.howhow.ai_generate.constant.ErrorCode;
import com.howhow.ai_generate.constant.LogParam;
import com.howhow.ai_generate.exception.BadRequestException;
import com.howhow.ai_generate.exception.InternalServerErrorException;
import com.howhow.ai_generate.exception.ResourceNotFoundException;
import com.howhow.ai_generate.model.dto.ErrorMessageDTO;
import com.howhow.ai_generate.utils.LocaleUtils;
import com.howhow.ai_generate.utils.TimeUtils;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice(basePackages = "com.howhow.ai_generate.controller")
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 處理用戶端相關錯誤，回傳status code 400 MaxUploadSizeExceededException: 處理超過檔案上傳上限的例外， 回傳status code 400
     * IllegalArgumentException: 處理參數錯誤(如格式錯誤、沒有輸入等)的例外
     */
    @ExceptionHandler({
        MaxUploadSizeExceededException.class,
        IllegalArgumentException.class,
        BadRequestException.class
    })
    public ResponseEntity<ErrorMessageDTO> handleBadRequestException(
            HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {

        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(newErrorMessageDTO(ErrorCode.BAD_REQUEST, e.getMessage()));
    }

    /**
     * 處理用戶端相關錯誤，回傳status code 400 MaxUploadSizeExceededException: 處理超過檔案上傳上限的例外， 回傳status code 400
     * IllegalArgumentException: 處理參數錯誤(如格式錯誤、沒有輸入等)的例外
     */
    @ExceptionHandler({
        ResourceNotFoundException.class,
    })
    public ResponseEntity<ErrorMessageDTO> handleNotFoundException(
            HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {

        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(newErrorMessageDTO(ErrorCode.NOT_FOUND, e.getMessage()));
    }

    /** 401 Unauthorized (en-US) 需要授權以回應請求。它有點像 403，但這裡的授權，是有可能辦到的。 處理權限相關錯誤，回傳status code 401 */
    //  @ExceptionHandler({
    //          AuthenticationException.class,
    //          BadCredentialsException.class,
    //          AuthenticationCredentialsNotFoundException.class,
    //          ExpiredJwtException.class
    //  })
    //  public ResponseEntity<ErrorMessageDTO> handleAuthenticationException(
    //          HttpServletRequest request,
    //          Exception e,
    //          HandlerMethod handlerMethod) {
    //    String errorCode;
    //    String message;
    //    if (e instanceof AuthenticationCredentialsNotFoundException) {
    //      errorCode = ErrorCode.TOKEN_NOT_FOUND;
    //      message = LocaleUtils.get("AuthenticationCredentialsNotFoundException.message");
    //    } else if (e instanceof ExpiredJwtException) {
    //      errorCode = ErrorCode.TOKEN_EXPIRED;
    //      message = LocaleUtils.get("ExpiredJwtException.message");
    //    } else {
    //      errorCode = ErrorCode.TOKEN_INVALID;
    //      message = LocaleUtils.get("AuthenticationException.message");
    //    }
    //    log.error(message, e);
    //
    //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    //            .body(newErrorMessageDTO(errorCode, message));
    //  }

    /** 403 FORBIDDEN 已認證的使用者未具有存取該資源的權限,回傳status code 403 */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorMessageDTO> handleAuthorizationException(
            HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {
        String errorCode = ErrorCode.ACCESS_DENIED;
        String message = "Unauthorized access to resources";
        log.error(message, e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(newErrorMessageDTO(errorCode, message));
    }

    /**
     * 處理未能識別的例外，一律視為系統錯誤並回傳HTTP status code 500 NoMatchingSysParamSettingException: 沒有對應的系統參數例外
     * OfficialDocJsonMappingException: 公文系統傳入的JSON解析例外
     */
    @ExceptionHandler(
            value = {
                HttpMessageNotReadableException.class,
                Exception.class,
            })
    public ResponseEntity<ErrorMessageDTO> defaultInternalServerErrorHandler(
            HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {
        String errMsg;
        if (e instanceof HttpMessageNotReadableException) {
            errMsg = LocaleUtils.get("InvalidJsonFormat.message");
        } else {
            errMsg = LocaleUtils.get("UnknownException.message");
        }
        log.error(errMsg, e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(newErrorMessageDTO(ErrorCode.UNKNOWN_ERROR, errMsg));
    }

    /** GET/POST請求方法錯誤的攔截器 因為開發時可能比較常見, 而且發生在進入 controller 之前, 上面的攔截器攔截不到這個錯誤 所以定義了這個攔截器 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDTO> httpRequestMethodHandler() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        newErrorMessageDTO(
                                ErrorCode.UNKNOWN_ERROR,
                                LocaleUtils.get("InternalServerErrorException.message")));
    }

    @ExceptionHandler({InternalServerErrorException.class, UnsupportedEncodingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDTO> internalServerErrorExceptionHandler(
            InternalServerErrorException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        newErrorMessageDTO(
                                ErrorCode.UNKNOWN_ERROR,
                                LocaleUtils.get("InternalServerErrorException.message")));
    }

    private ErrorMessageDTO newErrorMessageDTO(String errorCode, String errorMessage) {
        return ErrorMessageDTO.builder()
                .errorCode(errorCode)
                .message(errorMessage)
                .timestamp(TimeUtils.getCurrentUTCMilliseconds())
                .traceId(MDC.get(LogParam.REQUEST_OID))
                .build();
    }
}
