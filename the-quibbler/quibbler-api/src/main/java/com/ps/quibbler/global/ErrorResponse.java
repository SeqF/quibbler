package com.ps.quibbler.global;

import com.ps.quibbler.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {

    private int errorCode;
    private int status;
    private String message;
    private String path;
    private Instant timestamp;
    private Map<String, Object> data = new HashMap<>();

    public ErrorResponse(BaseException exception, String path) {
        this(exception.getErrorCode().getCode(), exception.getErrorCode().getStatus().value(), exception.getErrorCode().getMessage(), path, exception.getData());
    }

    public ErrorResponse(int code, int status, String message, String path, HashMap<String, Object> data) {
        this.errorCode = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }
}
