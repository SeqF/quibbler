package com.ps.quibbler.exception;

import com.ps.quibbler.base.BaseException;
import com.ps.quibbler.enums.ErrorCodeEnum;

import java.util.Map;

/**
 * @author Paksu
 */
public class QuibblerException extends BaseException {

    public QuibblerException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }
}
