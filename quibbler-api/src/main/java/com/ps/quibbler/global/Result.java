package com.ps.quibbler.global;

import lombok.Data;

import static com.ps.quibbler.global.Constants.ERROR;
import static com.ps.quibbler.global.Constants.SUCCESS;

/**
 * @author ps
 */
@Data
public class Result {

    private Object code;
    private String message;
    private Object data;


    public static Result result(Object code,Object data) {
        return resultWithData(code, data);
    }

    private static Result resultWithData(Object code,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    private static Result resultWithMessage(Object code,String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    private static Result resultWithDataAndMessage(Object code,Object data,String message) {
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static Result successWithData(Object data) {
        return resultWithData(SUCCESS, data);
    }

    public static Result successWithMessage(String message) {
        return resultWithMessage(SUCCESS, message);
    }

    public static Result successWithDataAndMessage(Object data, String message) {
        return resultWithDataAndMessage(SUCCESS, data, message);
    }

    public static Result errorWithData(Object data) {
        return resultWithData(ERROR, data);
    }

    public static Result errorWithMessage(String message) {
        return resultWithMessage(ERROR, message);
    }

    public static Result errorWithDataAndMessage(Object data,String message) {
        return resultWithDataAndMessage(ERROR, data, message);
    }
}
