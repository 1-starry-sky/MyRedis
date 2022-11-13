package com.example.result;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.Serializable;
import java.util.Optional;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

@ApiModel(
        value = "响应结果",
        description = "统一响应结果封装类"
)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(
            value = "响应编码",
            required = true
    )
    private ResultCode resultCode;
    @ApiModelProperty(
            value = "返回消息",
            required = true
    )
    private String message;
    @ApiModelProperty("承载数据")
    private T data;

    private Result(T data) {
        this.resultCode = ResultCode.SUCCESS;
        this.message = "操作成功";
        this.data = data;
    }

    private Result(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    private Result(ResultCode resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result(data);
    }

    public static <T> Result<T> success(String message) {
        return new Result(ResultCode.SUCCESS, message);
    }

    public static <T> Result<T> fail(String message) {
        return new Result(ResultCode.FAILURE, message);
    }

    public static <T> Result<T> fail(ResultCode resultCode, String message) {
        return new Result(resultCode, message);
    }

    public static <T> Result<T> result(ResultCode resultCode, String message, T data) {
        return new Result(resultCode, message, data);
    }

    public static boolean isSuccess(@Nullable Result<?> result) {
        return (Boolean)Optional.ofNullable(result).map((r) -> {
            return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, r.resultCode.code);
        }).orElse(Boolean.FALSE);
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setResultCode(final ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Result)) {
            return false;
        } else {
            Result<?> other = (Result)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$resultCode = this.getResultCode();
                    Object other$resultCode = other.getResultCode();
                    if (this$resultCode == null) {
                        if (other$resultCode == null) {
                            break label47;
                        }
                    } else if (this$resultCode.equals(other$resultCode)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Result;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $resultCode = this.getResultCode();
        result = result * 59 + ($resultCode == null ? 43 : $resultCode.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "Result(resultCode=" + this.getResultCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
