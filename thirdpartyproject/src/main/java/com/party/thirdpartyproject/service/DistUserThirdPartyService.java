package com.party.thirdpartyproject.service;

import java.util.Map;


/**
 * @author ：light
 * @date ：2024/11/25 09:03:36
 * @description : 用户调用第三方接口进行签约体现
 */
public interface DistUserThirdPartyService {

    /**
     * 用户签约
     */
    Object userSignContract(Map<String, String> params);

    /**
     * 获取用户签约状态
     */
    Object getUserSignStatus(Map<String, String> params);

    /**
     * 批次下单：平台企业可以通过批次下单接口，最多一次性发起 3,000 笔支付订单，每笔订单金额不可超过平台企业在对应支付路径设置的单笔限额。
     * 下单后，云账户会异步对每笔订单进行要素认证、签约校验和风控校验。校验失败或挂起的订单将通过异步回调的方式通知平台企业。
     */
    Object placeBatchOrder(Map<String, String> reqParams);

    /**
     * 批次确认：批次下单后，会异步进行要素认证和风控校验，在要素认证和风控检验未完成之前不允许请求此接口，
     * 如若请求则会返回“2042-该批次已支付或不符合支付要求”。建议批次下单后间隔 1 分钟后进行支付。
     */
    Object confirmBatchOrder();

    /**
     * 批次撤销：当批次下单完成后，在批次确认前，平台企业可通过此接口撤销该批次下所有订单。
     */
    Object cancelBatchOrder();

    /**
     * 查询批次定单信息：平台企业批次下单后，使用此接口获取该批次订单状态及批次中所有订单信息。
     */
    Object queryBatchOrder();

    /**
     * 查询单笔订单信息：平台企业下单成功后，使用此接口获取订单支付状态。
     */
    Object queryOneOrder();

    /**
     * 重试挂起状态的定单：平台企业可使用此接口重试支付由于用户未签约、账户余额不足等原因挂起的支付订单。
     */
    Object retrySuspendOrder();

    Object sqlTest();

}

