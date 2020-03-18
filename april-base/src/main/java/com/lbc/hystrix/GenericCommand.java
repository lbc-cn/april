//package com.lbc.hystrix;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * 功能描述：
// * HystrixCommand封装类
// */
//public class GenericCommand extends AbstractHystrixCommand<Object> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GenericCommand.class);
//
//    public GenericCommand(HystrixCommandBuilder builder) {
//        super(builder);
//    }
//
//    @Override
//    protected Object run() throws Exception {
//        LOGGER.debug("execute command: {}", getCommandKey().name());
//        return process(new Action() {
//            @Override
//            Object execute() {
//                return getCommandAction().execute(getExecutionType());
//            }
//        });
//    }
//
//    @Override
//    protected Object getFallback() {
//        final CommandAction commandAction = getFallbackMethodAction();
//        if (commandAction != null) {
//            try {
//                return process(new Action() {
//                    @Override
//                    Object execute() {
//                        return commandAction.execute(getExecutionType());
//                    }
//                });
//            } catch (Throwable e) {
//                LOGGER.error(FallbackErrorMessageBuilder.create()
//                        .append(commandAction, e).build());
//                throw new FallbackInvocationException(unwrapCause(e));
//            }
//        } else {
//            return super.getFallback();
//        }
//    }
//}
