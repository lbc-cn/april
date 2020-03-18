//package com.lbc.engine;
//
//import com.youzan.test.Abstract;
//import com.youzan.test.core.data.CaseName;
//import com.youzan.test.core.flowengine.domains.*;
//import com.youzan.test.core.tips.IOrderRunner;
//import com.youzan.test.dal.ufo.dao.DataDAO;
//import com.youzan.test.dal.ufo.dao.DomainDAO;
//import com.youzan.test.dal.ufo.dataobject.DataDO;
//import com.youzan.test.dal.ufo.dataobject.DomainDO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.testng.Assert;
//import org.testng.ITestContext;
//import org.testng.annotations.BeforeMethod;
//
//import java.lang.reflect.Method;
//import java.util.List;
//
///**
// * @author lbc
// * @classname RunningMan
// */
//@Slf4j
//public abstract class RunningMan extends Abstract {
//
//    @Autowired
//    private TOrderFlowRouter router;
//
//    @Autowired
//    private DataDAO dataDAO;
//
//    @Autowired
//    private DomainDAO domainDAO;
//
//    @BeforeMethod
//    public void beforeMethod(ITestContext context ,Method method){
//        //写入当前执行方法，并在方法执行前清空上次使用过的TOrderData
//        String caseName = "";
//        if (method.isAnnotationPresent(CaseName.class)){
//            if (method.getAnnotation(CaseName.class).datable()){
//                //这里给caseName赋值 注意上面的两个判断（datable默认为false 及不通过caseName获取数据）
//                caseName = method.getAnnotation(CaseName.class).value();
//            }
//        }
//        context.setAttribute("caseName",caseName);
//        context.setAttribute("TOrderData",null);
//    }
//
//    public void start(TOrderStatus targetStatus,ITestContext context) {
//        TKeyInfo keyInfo = getKeyInfo(context);
//        TOrderData currData = (TOrderData)context.getAttribute("TOrderData");
//        currData = initData(keyInfo,currData,context,targetStatus);
//        List<TFlow> ruleRunners = getRunners(keyInfo);
//
//        for (TFlow rule : ruleRunners){
//            try{
//                //单步执行情况下：过滤订单状态 忽略已经执行过的执行阶段 和 target状态之后的订单状态（因为已经没必要执行了）
//                int runnerStatus = rule.getStatus().getValue();
//                int currStatus = currData.getCurrOrderStatus().getValue();
//                if (currStatus < runnerStatus && runnerStatus <= targetStatus.getValue()){
//                    List<IOrderRunner> runners = rule.getRunners();
//                    runners.forEach(x -> {
//                        if (x.condition(context)) {
//                            x.run(context);
//                        }
//                    });
//                }
//            }catch (Exception e){
//                log.error("执行器异常 ---- ERROR:"+keyInfo.toString(),e);
//            }
//        }
//    }
//
//    /**
//     * 具体执行某个状态的执行器
//     * @param targetStatus
//     * @param context
//     */
//    public void startWho(TOrderStatus targetStatus,ITestContext context) {
//        TKeyInfo keyInfo = getKeyInfo(context);
//        TOrderData currData = (TOrderData)context.getAttribute("TOrderData");
//        initData(keyInfo,currData,context,targetStatus);
//        List<TFlow> ruleRunners = getRunners(keyInfo);
//
//        for (TFlow rule : ruleRunners){
//            try{
//                //如果指定的执行器状态 = 目标执行器状态的话 才会执行。比如指定7020，那么当rule的规则与目标匹配时，执行对应执行器
//                int runnerStatus = rule.getStatus().getValue();
//                if (targetStatus.getValue() == runnerStatus){
//                    List<IOrderRunner> runners = rule.getRunners();
//                    runners.forEach(x -> {
//                        if (x.condition(context)) {
//                            x.run(context);
//                        }
//                    });
//                }
//            }catch (Exception e){
//                log.error("执行器异常 ---- ERROR:"+keyInfo.toString(),e);
//            }
//        }
//    }
//
//    /**
//     * 从什么状态。 到 什么状态。
//     * @param targetStatus
//     * @param context
//     */
//    public void startFromTo(TOrderStatus startStatus,TOrderStatus targetStatus,ITestContext context) {
//        TKeyInfo keyInfo = getKeyInfo(context);
//        TOrderData currData = (TOrderData)context.getAttribute("TOrderData");
//        initData(keyInfo,currData,context,targetStatus);
//        List<TFlow> ruleRunners = getRunners(keyInfo);
//
//        for (TFlow rule : ruleRunners){
//            try{
//                //如果指定的执行器状态 = 目标执行器状态的话 才会执行。比如指定7020，那么当rule的规则与目标匹配时，执行对应执行器
//                int runnerStatus = rule.getStatus().getValue();
//                if (targetStatus.getValue() >= runnerStatus &&  runnerStatus >= startStatus.getValue()){
//                    List<IOrderRunner> runners = rule.getRunners();
//                    runners.forEach(x -> {
//                        if (x.condition(context)) {
//                            x.run(context);
//                        }
//                    });
//                }
//            }catch (Exception e){
//                log.error("执行器异常 ---- ERROR:"+keyInfo.toString(),e);
//            }
//        }
//    }
//
//    /**
//     * 根据 casename 获取keyinfo 这部分实际可以替换tom接口
//     */
//    private TKeyInfo getKeyInfo(ITestContext context){
//        String caseName = (String) context.getAttribute("caseName");
//        Assert.assertNotNull(caseName);
//        Assert.assertTrue(caseName != null && caseName != "");
//        DataDO data = dataDAO.queryByCaseName(caseName);
//        Assert.assertNotNull(data);
//        DomainDO domain = domainDAO.queryById(data.getDomainId());
//
//        TKeyInfo keyInfo = new TKeyInfo();
//        keyInfo.setTags(domain.getTags());
//        keyInfo.setDeliveryType(domain.getDeliveryType());
//        keyInfo.setGoodsType(domain.getGoodsType());
//        keyInfo.setPayType(domain.getPayType());
//        keyInfo.setBizType(domain.getBizType());
//        keyInfo.setUmpType(domain.getUmpType());
//
//        return keyInfo;
//    }
//
//    /**
//     * 有点憨批的方法 一般不会调
//     */
//    private TOrderData initData(TKeyInfo keyInfo,TOrderData currData,ITestContext context,TOrderStatus targetStatus){
//        if (currData == null) {
//            //初始化
//            TOrderData orderData = new TOrderData(keyInfo);
//            orderData.setTargetOrderStatus(targetStatus);
//            currData = orderData;
//            context.setAttribute("TOrderData", currData);
//        }
//        return currData;
//    }
//
//    public List<TFlow> getRunners(TKeyInfo keyInfo){
//        List<TFlow> ruleRunners = router.findRunners(keyInfo);
//        Assert.assertNotNull(ruleRunners,"执行规则获取异常，请检查配置。"+keyInfo);
//        return ruleRunners;
//    }
//
//    public TOrderData getContextData(ITestContext context){
//        return (TOrderData) context.getAttribute("TOrderData");
//    }
//}
