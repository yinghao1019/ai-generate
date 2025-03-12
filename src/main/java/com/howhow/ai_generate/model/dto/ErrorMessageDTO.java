package com.howhow.ai_generate.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageDTO {
    private String errorCode;
    private String traceId;
    private Long timestamp;
    private String message;
}
