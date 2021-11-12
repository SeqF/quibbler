package com.ps.quibbler.base;

import com.ps.quibbler.enums.ErrorCodeEnum;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author paksu
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCodeEnum errorCode;
    private final HashMap<String, Object> data = new HashMap<>();

    public BaseException(ErrorCodeEnum errorCode, Map<String,Object> data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    protected BaseException(ErrorCodeEnum errorCode, Map<String, Object> data, Throwable cause) {
        super(errorCode.getMessage(),cause);
        this.errorCode = errorCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }
}
