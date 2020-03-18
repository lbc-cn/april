//package com.lbc.engine;
//

//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author lbc
// * @classname TOrderFlowRouter
// * @description 获取订单执行器  //todo 换接口
// * @date 4:48 PM
// */
//@Slf4j
//@Component
//@DependsOn(value = "applicationContextUtil")
//public class TOrderFlowRouter {
//
//    public Map<TKeyInfo, List<TFlow>> orderFlowMap = new HashMap<>();
//
//    @Autowired
//    private RuleDAO ruleDAO;
//
//    @Autowired
//    private DomainDAO domainDAO;
//
//    /**
//     * 根据订单状态 订单类型 获取订单流转执行器
//     * @return
//     */
//    @PostConstruct
//    private void init() {
//        List<RuleDO> rules= ruleDAO.queryAll();
//        for (RuleDO rule : rules){
//            orderFlowMap.put(formatKeyInfo(rule.getDomainId()), formatFlow(rule.getRunners()));
//        }
//    }
//
//
//    /**
//     * 获取domain 并转化为代码中的keyinfo
//     * @param domainId
//     * @return
//     */
//    private TKeyInfo formatKeyInfo(int domainId){
//        DomainDO domainDO = domainDAO.queryById(domainId);
//        String bizTypeName = domainDO.getBizType();
//        String goodsTypeName = domainDO.getGoodsType();
//        String payTypeName = domainDO.getPayType();
//        String deliveryTypeName = domainDO.getDeliveryType();
//        String umpTypeName = domainDO.getUmpType();
//
//        TKeyInfo key = new TKeyInfo();
//        key.setUmpType(umpTypeName);
//        key.setBizType(bizTypeName);
//        key.setPayType(payTypeName);
//        key.setGoodsType(goodsTypeName);
//        key.setDeliveryType(deliveryTypeName);
//        key.setTags(domainDO.getTags());
//        return key;
//    }
//
//    /**
//     * 字符串规则格式化:
//     * 状态 : 执行期A , 执行期B ;
//     * 状态 : 执行期A , 执行期B , 执行期C;
//     * @param ruleString
//     * @return
//     */
//    private List<TFlow> formatFlow(String ruleString){
//        List<TFlow> flow = new ArrayList();
//        try {
//            //以分号为各个规则的终止符
//            String[] statusRule = ruleString.split(";");
//            for (String oneRule: statusRule){
//                String[] runnerRule = oneRule.split(":");
//                //冒号前为状态
//                TOrderStatus status = TOrderStatus.getStatusByValue(Integer.valueOf(runnerRule[0]));
//                //冒号后为执行器列表
//                String[] runners = runnerRule[1].split(",");
//                List<IOrderRunner> runnerList = new ArrayList<>();
//                for (String runner : runners){
//                    runnerList.add((IOrderRunner) ApplicationContextUtil.getBean(runner));
//                }
//                TFlow flowNode = new TFlow(status,runnerList);
//                flow.add(flowNode);
//            }
//        }catch (Exception e){
//            log.error("ERROR FORMAT RULE :"+ruleString);
//        }
//        return flow;
//    }
//
//    /**
//     * 根据keyinfo获取map value值
//     *
//     * @param keyInfo
//     * @return
//     */
//    public List<TFlow> findRunners(TKeyInfo keyInfo) {
//        return orderFlowMap.get(keyInfo);
//    }
//
//}
