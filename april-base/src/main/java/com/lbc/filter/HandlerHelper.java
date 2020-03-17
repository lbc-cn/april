//package com.lbc.filter;
//
//import com.alibaba.fastjson.JSON;
//import org.aspectj.lang.ProceedingJoinPoint;
//
//public class HandlerHelper {
//
//  public HandlerHelper() {
//  }
//
//  public static String getRequest(ProceedingJoinPoint pjp) {
//    return getParamsString(pjp);
//  }
//
//  public static String getMethodString(ProceedingJoinPoint pjp) {
//    return pjp.getTarget().getClass().getName() + "#" + pjp.getSignature().getName();
//  }
//
//  public static String getShortMethodString(ProceedingJoinPoint pjp) {
//    String className = pjp.getTarget().getClass().getName();
//    String methodName = pjp.getSignature().getName();
//    String[] nameArray = className.split("\\.");
//    return nameArray[nameArray.length - 1] + "." + methodName;
//  }
//
//
//  public static String getParamsString(ProceedingJoinPoint pjp) {
//    Object[] args = pjp.getArgs();
//    if (args != null && args.length != 0) {
//      StringBuilder sb = new StringBuilder(512);
//      int i = 0;
//      for (int len = args.length; i < len; ++i) {
//        sb.append(JSON.toJSONString(args[i])).append(",");
//      }
//      return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString();
//    } else {
//      return "";
//    }
//  }
//
//  public static String getResponse(Object result) {
//    return JSON.toJSONString(result);
//  }
//}
