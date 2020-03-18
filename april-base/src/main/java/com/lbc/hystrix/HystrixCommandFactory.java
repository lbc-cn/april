//package com.lbc.hystrix;
//
//import com.netflix.hystrix.HystrixInvokable;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 功能描述：
// * 用于生成HystrixCommand
// */
//@Slf4j
//public class HystrixCommandFactory {
//    private static final HystrixCommandFactory INSTANCE = new HystrixCommandFactory();
//
//    private HystrixCommandFactory() {
//    }
//
//    public static HystrixCommandFactory getInstance() {
//        return INSTANCE;
//    }
//
//    /**
//     * 创建HystrixInvokable
//     * @param hystrixMeta
//     * @return
//     */
//    public HystrixInvokable create(HystrixMeta hystrixMeta){
//        CommandConfig config = HysConfigurationContext.getCommandConfig(hystrixMeta.getGroupKey(),
//                                                                          hystrixMeta.getCommandKey());
//
//      //获取不到配置或者command配置为禁用状态，则直接返回null，不再执行
//      if (config == null || !config.getEnabled()) {
//        return null;
//      }
//
//      HystrixMetaInterpreter interpreter = InterpreterFactory.getInterpreter(hystrixMeta);
//      if (interpreter == null) {
//        log.warn("Could not get Interpreter of hystrixMeta={}", hystrixMeta);
//        return null;
//      }
//      //CommandAction
//      CommandAction commandAction = interpreter.createCommandAct(hystrixMeta);
//      //FallbackMethod
//      CommandAction fallbackMethodAction = interpreter.createFallbackCommandAct(hystrixMeta);
//      //executionType
//      ExecutionType executionType = interpreter.getExecutionType(hystrixMeta);
//      HystrixCommandBuilder hystrixCommandBuilder = HystrixCommandBuilder.builder().
//          hystrixMeta(hystrixMeta).commandAction(commandAction).fallbackMethodAction(fallbackMethodAction).
//          executionType(executionType).commandConfig(config).build();
//      HystrixInvokable executable = new GenericCommand(hystrixCommandBuilder);
//      return executable;
//    }
//
//}
