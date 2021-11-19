package com.ps.quibbler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author paksu.fang
 */

@AllArgsConstructor
@ToString
@Getter
public enum ErrorCodeEnum {

    RESOURCE_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "未找到资源"),
    REQUEST_VALIDATION_FAILED(1002,HttpStatus.BAD_REQUEST,"请求数据格式验证失败"),
    TOKEN_VALIDATION_FAILED(1003,HttpStatus.UNAUTHORIZED,"请求数据格式验证失败"),
    REQUEST_PARAM_VALIDATION_FAILED(1004,HttpStatus.BAD_REQUEST,"请求参数不能为空"),
    USER_EXIST(1005,HttpStatus.NOT_ACCEPTABLE,"用户已存在");

    private final int code;

    private final HttpStatus status;

    private final String message;
}
