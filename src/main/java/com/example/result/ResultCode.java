package com.example.result;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        value = "响应编码",
        description = "统一响应编码枚举类"
)
@JsonFormat(
        shape = Shape.OBJECT
)
public enum ResultCode implements IResultCode {
    SUCCESS(200, "请求成功"),
    FAILURE(400, "请求参数错误"),
    UN_AUTHORIZED(401, "请求未授权"),
    REQ_REJECT(403, "请求被拒绝"),
    NOT_FOUND(404, "请求未找到"),
    METHOD_NOT_SUPPORTED(405, "不支持当前请求方法"),
    MEDIA_TYPE_NOT_SUPPORTED(415, "不支持当前媒体类型"),
    INTERNAL_SERVER_ERROR(500, "服务器异常");

    @ApiModelProperty(
            value = "响应编码",
            required = true
    )
    final Integer code;
    @ApiModelProperty(
            value = "编码含义",
            required = true
    )
    final String codeMeaning;

    public static ResultCode resultCode(Integer code) {
        switch (code) {
            case 200:
                return SUCCESS;
            case 400:
                return FAILURE;
            case 401:
                return UN_AUTHORIZED;
            case 403:
                return REQ_REJECT;
            case 404:
                return NOT_FOUND;
            case 405:
                return METHOD_NOT_SUPPORTED;
            case 415:
                return MEDIA_TYPE_NOT_SUPPORTED;
            default:
                return INTERNAL_SERVER_ERROR;
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public String getCodeMeaning() {
        return this.codeMeaning;
    }

    private ResultCode(final Integer code, final String codeMeaning) {
        this.code = code;
        this.codeMeaning = codeMeaning;
    }
}
