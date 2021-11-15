package com.ps.quibbler.exception;

import com.ps.quibbler.base.BaseException;
import com.ps.quibbler.enums.ErrorCodeEnum;

import java.util.Map;

public class QuibblerException extends BaseException {

    public QuibblerException(ErrorCodeEnum errorCode,Map<String, Object> data) {
        super(errorCode, data);
    }
}
