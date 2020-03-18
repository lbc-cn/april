//package com.lbc.hystrix;
//
//
//import com.google.common.collect.ImmutableMap;
//import com.netflix.hystrix.HystrixInvokable;
//import com.netflix.hystrix.exception.HystrixBadRequestException;
//import com.netflix.hystrix.exception.HystrixRuntimeException;
//import lombok.Setter;
//import org.apache.commons.lang3.Validate;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.lang.reflect.Method;
//import java.util.Map;
//
///**
// * AspectJ aspect to process methods which annotated with {@link HysCommand} annotation.
// */
//@Aspect
//@Configuration
//public class HystrixAspect {
//
//    /**
//     * 元数据构造工厂实例
//     */
//    private static final Map<HysPointcutType, HysMetaFactory> Hys_META_FACTORY_MAP;
//
//    static {
//        Hys_META_FACTORY_MAP = ImmutableMap.<HysPointcutType, HysMetaFactory>builder()
//                .put(HysPointcutType.COMMAND, new CommandHysMetaFactory())
//                .build();
//    }
//
//    @Pointcut("@annotation(com.xxx.Hys.core.annotation.HysCommand)")
//    public void HysCommandAnnotationPointcut() {
//    }
//
//    @Around("HysCommandAnnotationPointcut()")
//    public Object methodsAnnotatedWithHystrixCommand(final ProceedingJoinPoint joinPoint) throws Throwable {
//
//        HystrixInvokable invokable = null;
//        HysMeta HysMeta = null;
//        ExecutionType executionType = null;
//        try {
//            //注解只能作用在方法上
//            Method method = AopUtils.getMethodFromTarget(joinPoint);
//
//            //构造元数据
//            HysMetaFactory HysMetaFactory = Hys_META_FACTORY_MAP.get(HysPointcutType.of(method));
//            HysMeta = HysMetaFactory.create(joinPoint);  //获取元数据 自己根据情况定义
//            //构造command
//            invokable = HystrixCommandFactory.getInstance().create(HysMeta);
//        } catch (Throwable e) {
//            //构造command失败,所有异常全部忽略，执行业务方逻辑
//            logger.info("create HystrixCommand fail : " + e.getMessage());
//        }
//        //execute
//        Object result;
//            if (invokable != null && HysMeta != null) {
//                //构造HystrixCommand成功，则执行HystrixCommand
//                result = hystrix_command.execute
//            } else {
//                result = joinPoint.proceed();
//            }
//
//        return result;
//    }
//
//    public enum HysPointcutType {
//        COMMAND;
//
//        public static HysPointcutType of(Method method) {
//            if (method.isAnnotationPresent(HysCommand.class)) {
//                return COMMAND;
//            } else {
//                String methodInfo = AopUtils.getMethodInfo(method);
//                throw new IllegalStateException("no valid annotation found for: \n" + methodInfo);
//            }
//        }
//    }
//
//
//}
