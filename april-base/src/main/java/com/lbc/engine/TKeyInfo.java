package com.lbc.engine;

import lombok.Data;

/**
 * @author lbc
 * @classname TKeyInfo
 * @description 包含订单类型和订单状态至少两种核心信息
 * 用于匹配Flow流程
 * @date 3:01 PM
 */
@Data
public class TKeyInfo {

    /**
     * 订单类型
     */
    private String bizType;

    /**
     * 订单类型
     */
    private String goodsType;

    /**
     * 订单类型
     */
    private String umpType;

    /**
     * 订单类型
     */
    private String deliveryType;

    /**
     * 订单类型
     */
    private String payType;

    /**
     * 其他标识
     */
    private String tags;


    @Override
    public String toString(){
        return "biz:"+bizType+
                "goods"+goodsType+
                "delivery:"+deliveryType+
                "ump:"+umpType+
                "pay:"+payType;
    }
}
