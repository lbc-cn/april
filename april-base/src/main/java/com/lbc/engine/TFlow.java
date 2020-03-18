//package com.lbc.engine;
//
//import com.youzan.test.core.flowengine.domains.TOrderStatus;
//import com.youzan.test.core.tips.IOrderRunner;
//import lombok.Getter;
//
//import java.util.List;
//
///**
// * @author lbc
// * @classname TFlow 流程配置
// * 两个属性 :
// * 1。订单状态，用于和传入的期望订单状态进行比对，直到经过runner执行后的状态=传入状态；
// * 2。订单执行器，当满足上述订单状态的条件后，顺序执行runner
// * @date 10:12 AM
// */
//@Getter
//public class TFlow {
//
//    /**
//     * 当前状态
//     */
//    private TOrderStatus status;
//
//    /**
//     * 执行器们
//     */
//    private List<IOrderRunner> runners;
//
//
//    public TFlow(TOrderStatus status , List<IOrderRunner> runners){
//        this.runners = runners;
//        this.status = status;
//    }
//}
