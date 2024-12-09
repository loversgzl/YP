package com.party.thirdpartyproject.vo;

import com.party.thirdpartyproject.enums.ResultEnum;

import java.io.Serializable;

/**
 * @author ：light
 * @date ：2024/11/26 08:59:44
 * @description : 返回信息处理
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success = true;
    private int code = ResultEnum.SUCCESS_CODE.getCode();
    private String message = "";
    private String errorMessage = "";
    private T data;

    public boolean isSuccess() {
        return this.success;
    }

    public static <T> Result<T> success() {
        return new Result();
    }

    public static <T> Result<T> success(T data) {
        return new Result(data);
    }

    public static <T> Result<T> error() {
        return new Result(false, ResultEnum.SERVER_ERROR_CODE);
    }

    public static <T> Result<T> error(String message) {
        return new Result(false, ResultEnum.SERVER_ERROR_CODE.getCode(), message);
    }

    public static <T> Result<T> error(String message, int code) {
        return new Result(false, code, message);
    }

    public static <T> Result<T> error(String message, int code, String errorMessage) {
        return new Result(false, code, message, errorMessage);
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(boolean success, ResultEnum resultEnum) {
        this.message = resultEnum.getMsg();
        this.code = resultEnum.getCode();
        this.success = success;
    }

    public Result(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(boolean success, int code, String message, String errorMessage) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.errorMessage = errorMessage;
    }

    /**
     * 自定义错误返回（全局异常处理使用）
     * [msg]
     *
     * @return com.center.api.result.Result<T>
     * @author cgh
     * @since 2023/10/19 0019 下午 14:10
     */
    public static <T> Result<T> bizError(String msg) {
        return new Result<T>(false, 400, msg);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public T getData() {
        return this.data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setData(T data) {
        this.data = data;
    }
}
