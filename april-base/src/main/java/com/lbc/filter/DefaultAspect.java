//package com.lbc.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * @author lbc
// * @classname DefaultAspect
// */
//@Slf4j
//@Component
//@Aspect
//public class DefaultAspect {
//
//    @Pointcut("execution(public * com.xxx.test.tom.biz.impl..*.*(..))")
//    public void pointcut() {
//    }
//
//    @Around("pointcut()")
//    public Object function(ProceedingJoinPoint pjp) throws Throwable {
//        Object proceed = null;
//        try {
//            initMDC();
//            long startTime = System.currentTimeMillis();
//            proceed = pjp.proceed();
//            long endTime = System.currentTimeMillis();
//            log.info("[bingo][Method]:{}"+"[Request]:{}"+"[Result]:{}"+"[cost]:{}ms"
//                    ,HandlerHelper.getMethodString(pjp)
//                    ,HandlerHelper.getRequest(pjp)
//                    ,HandlerHelper.getResponse(proceed)
//                    ,endTime-startTime);
//        }catch (Exception e){
//            if (e instanceof TomException){
//                proceed = ResultUtil.getFailedResult(((TomException) e));
//                log.error("[Error#][Method]:{}"+"[Request]:{}"+"[Result]:{}"
//                        ,HandlerHelper.getMethodString(pjp)
//                        ,HandlerHelper.getRequest(pjp)
//                        ,HandlerHelper.getResponse(proceed));
//            }else {
//                log.error("[EEEEEError#]" + e);
//            }
//        }finally {
//            MDC.clear();
//        }
//
//        return proceed;
//    }
//
//    private void initMDC() {
//        MDC.put("INNER_TRACE_ID", generate());
//    }
//
//    public static String generate() {
//        long currentTime = System.currentTimeMillis();
//        long timeStamp = currentTime % 1000000L;
//        int randomNumber = ThreadLocalRandom.current().nextInt(10000);
//        long traceID = timeStamp * 10000 + randomNumber;
//        return Long.toHexString(traceID).toUpperCase();
//    }
//}
