package com.boluo.base.exception;

import java.io.Serializable;

/**
 * @author Kirito
 * @version 1.0
 * @description 和前端约定返回的异常信息模型
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}