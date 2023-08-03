package com.boluo.base.exception;


import com.boluo.base.enumeration.ErrorEnum;

/**
 * 学成在线 统一异常处理
 *
 * @author spongzi
 * @date 2023/07/17
 */
public class XueChengPlusException extends RuntimeException {

   private String errMessage;

   public XueChengPlusException() {
      super();
   }

   public XueChengPlusException(String errMessage) {
      super(errMessage);
      this.errMessage = errMessage;
   }

   public String getErrMessage() {
      return errMessage;
   }

   public static void cast(CommonError commonError){
       throw new XueChengPlusException(commonError.getErrMessage());
   }

   public static void cast(String errMessage){
       throw new XueChengPlusException(errMessage);
   }

   public static void cast(ErrorEnum errorEnum){
      throw new XueChengPlusException(errorEnum.getErrMessage());
   }
}
