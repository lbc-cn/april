//package com.lbc.hystrix;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.exception.HystrixBadRequestException;
//import com.netflix.hystrix.exception.HystrixRuntimeException;
//import lombok.Getter;
//
///**
// * 功能描述：
// * HystrixCommand虚拟类，封装了主要的初始化逻辑以及异常处理
// */
//public abstract class AbstractHystrixCommand<T> extends HystrixCommand<T> {
//    @Getter
//    private final CommandConfig commandConfig;
//    @Getter
//    private final CommandAction commandAction;
//    @Getter
//    private final CommandAction fallbackMethodAction;
//    @Getter
//    private final ExecutionType executionType;
//
//    protected AbstractHystrixCommand(HystrixCommandBuilder builder) {
//        super(SetterBuilderUtils.getFromCommandConfig(builder.getCommandConfig(),builder.getHystrixMeta()));
//        this.commandConfig = builder.getCommandConfig();
//        this.commandAction = builder.getCommandAction();
//        this.fallbackMethodAction = builder.getFallbackMethodAction();
//        this.executionType = builder.getExecutionType();
//    }
//
//    @Override
//    protected abstract T run() throws Exception;
//
//    /**
//     * 是否忽略该异常
//     * @param throwable
//     * @return
//     */
//    boolean isIgnorable(Throwable throwable) {
//        return false;
//    }
//    /**
//     * action执行,执行失败异常包装
//     *
//     * @param action
//     * @return
//     * @throws Exception
//     */
//    Object process(Action action) throws Exception {
//        Object result;
//        try {
//            result = action.execute();
//        } catch (HysCommandExecutionException throwable) {
//            Throwable cause = throwable.getCause();
//            if (isIgnorable(cause)) {
//                throw new HystrixBadRequestException(cause.getMessage(), cause);
//            }
//            if (cause instanceof RuntimeException) {
//                throw (RuntimeException) cause;
//            } else if (cause instanceof Exception) {
//                throw (Exception) cause;
//            } else {
//                // instance of Throwable
//                throw new HysCommandExecutionException(cause);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Common action.
//     */
//    abstract class Action {
//        /**
//         * Each implementation of this method should wrap any exceptions in HysCommandExecutionException.
//         *
//         * @return execution result
//         * @throws HysCommandExecutionException
//         */
//        abstract Object execute() throws HysCommandExecutionException;
//    }
//
//
//    /**
//     * Builder to create error message for failed fallback operation.
//     */
//    static class FallbackErrorMessageBuilder {
//        private StringBuilder builder = new StringBuilder("failed to process fallback");
//
//        static FallbackErrorMessageBuilder create() {
//            return new FallbackErrorMessageBuilder();
//        }
//
//        public FallbackErrorMessageBuilder append(CommandAction action, Throwable throwable) {
//            return commandAction(action).exception(throwable);
//        }
//
//        private FallbackErrorMessageBuilder commandAction(CommandAction action) {
//            builder.append("fallback : ").append(action.getName());
//            return this;
//        }
//        private FallbackErrorMessageBuilder exception(Throwable throwable) {
//            if (throwable instanceof HystrixBadRequestException) {
//                builder.append("exception: '").append(throwable.getCause().getClass())
//                        .append("' occurred in fallback was ignored and wrapped to HystrixBadRequestException.\n");
//            } else if (throwable instanceof HystrixRuntimeException) {
//                builder.append("exception: '").append(throwable.getCause().getClass())
//                        .append("' occurred in fallback wasn't ignored.\n");
//            }
//            return this;
//        }
//
//        public String build() {
//            return builder.toString();
//        }
//    }
//
//}
