package dev.yxy.app.global;

import lombok.Getter;

/**
 * 响应体
 *
 * @author yuanxy
 * @create 2022/7/4 13:35
 * @update 2022/7/4 13:35
 * @origin upload-download-demo
 */
@Getter
public class ResponseData {

    private Integer code;

    private String msg;

    private Object data;

    public ResponseData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // -------------------------------------------------- success --------------------------------------------------

    public static final int DEFAULT_SUCCESS_CODE = 200;
    public static final String DEFAULT_SUCCESS_MSG = "success";

    public static ResponseData success() {
        return new ResponseData(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG);
    }

    public static ResponseData successMsg(String msg) {
        return new ResponseData(DEFAULT_SUCCESS_CODE, msg);
    }

    public static ResponseData success(Integer code, String msg) {
        return new ResponseData(code, msg);
    }

    public static ResponseData success(Object data) {
        return new ResponseData(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, data);
    }

    public static ResponseData success(String msg, Object data) {
        return new ResponseData(DEFAULT_SUCCESS_CODE, msg, data);
    }

    public static ResponseData success(Integer code, String msg, Object data) {
        return new ResponseData(code, msg, data);
    }

    // -------------------------------------------------- fail --------------------------------------------------

    public static final int DEFAULT_FAIL_CODE = 500;
    public static final String DEFAULT_FAIL_MSG = "fail";

    public static ResponseData fail() {
        return new ResponseData(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG);
    }

    public static ResponseData failMsg(String msg) {
        return new ResponseData(DEFAULT_FAIL_CODE, msg);
    }

    public static ResponseData fail(Integer code, String msg) {
        return new ResponseData(code, msg);
    }

    public static ResponseData fail(Object data) {
        return new ResponseData(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG, data);
    }

    public static ResponseData fail(String msg, Object data) {
        return new ResponseData(DEFAULT_FAIL_CODE, msg, data);
    }

    public static ResponseData fail(Integer code, String msg, Object data) {
        return new ResponseData(code, msg, data);
    }
}
