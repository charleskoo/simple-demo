package com.example.demo.entity;

import lombok.Data;

@Data
public class ErrorResponse {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回内容
     */
    private String message;

    /**
     * 全参构造函数
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     */
    private ErrorResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回数据
     */
    private Object data;

    public static <T extends Exception> ErrorResponse ofException(T t) {
        return new ErrorResponse(Status.UNKNOWN_ERROR.getCode(), t.getMessage(), null);
    }
}
