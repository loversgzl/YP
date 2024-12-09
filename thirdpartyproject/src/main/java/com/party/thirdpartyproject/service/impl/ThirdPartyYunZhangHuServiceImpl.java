package com.party.thirdpartyproject.service.impl;


import com.party.thirdpartyproject.constant.ThirdPartyParamNameConstant;
import com.party.thirdpartyproject.enums.*;
import com.party.thirdpartyproject.mapper.ThirdPartyInfoMapper;
import com.party.thirdpartyproject.mapper.ThirdPartyParamMapper;
import com.party.thirdpartyproject.model.ThirdPartyInfo;
import com.party.thirdpartyproject.model.ThirdPartyParam;
import com.party.thirdpartyproject.service.DistUserThirdPartyService;
import com.party.thirdpartyproject.vo.Result;
import com.yunzhanghu.sdk.base.YzhConfig;
import com.yunzhanghu.sdk.base.YzhRequest;
import com.yunzhanghu.sdk.base.YzhResponse;
import com.yunzhanghu.sdk.h5usersign.H5UserSignServiceClient;
import com.yunzhanghu.sdk.h5usersign.domain.*;
import com.yunzhanghu.sdk.payment.PaymentClient;
import com.yunzhanghu.sdk.payment.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：light
 * @date ：2024/11/22 15:58:50
 * @description : 第三方服务方为：云账户
 */
@Slf4j
@Service
public class ThirdPartyYunZhangHuServiceImpl implements DistUserThirdPartyService {
    @Resource
    private ThirdPartyInfoMapper thirdPartyInfoMapper;

    @Resource
    private ThirdPartyParamMapper thirdPartyParamMapper;

    /**
     * 初始化表结构
     */
    private void initTable() {
        // 检查是否存在前置表，没有则直接创建
        List<Map<String, String>> tableList = thirdPartyInfoMapper.isTableExist(ThirdPartyTableNameEnum.THIRD_PARTY_INFO.getTableName());
        if (tableList.size() == 0) {
            log.info("数据库没有前置-第三方平台信息表，需要新建");
            thirdPartyInfoMapper.cleanTable();
            thirdPartyInfoMapper.initTable();
            thirdPartyInfoMapper.initTableRecord();
            log.info("第三方平台信息表-新建完成");
        }

        tableList = thirdPartyParamMapper.isTableExist(ThirdPartyTableNameEnum.THIRD_PARTY_PARAM.getTableName());
        if (tableList.size() == 0) {
            log.info("数据库没有前置-第三方平台参数表，需要新建");
            thirdPartyParamMapper.cleanTable();
            thirdPartyParamMapper.initTable();
            thirdPartyParamMapper.initTableRecord();
            log.info("第三方平台参数表-新建完成");
        }

    }


    /**
     * 对接第三方 云账户平台 签约系统：
     * <p>
     * 云账户平台签约流程：
     * 1、预签约
     * 2、申请签约
     */
    @Override
    public Object userSignContract(Map<String, String> reqParams) {
        // 第三方平台编号保留：防止调用错第三方接口
        String thirdPartyNo = reqParams.get(ThirdPartyInfo.THIRD_PARTY_NO);
        if (thirdPartyNo.equals(ThirdPartyEnum.YUN_ZHANG_HU.getThirdPartyNo())) {
            //TODO 1、查询数据库，该用户是否已经签约，如果已经签约直接返回

            // 2、未签约，则先进行签约。云账户平台 1、预申请签约-检查二要素，返回一个token；2、申请签约-返回一个URL用来展示合约信息；
            return this.applySign(reqParams);
        }
        return Result.error("云账户平台编号错误，请输入：thirdPartyNo");
    }

    /**
     * 获取用户签约状态
     */
    @Override
    public Object getUserSignStatus(Map<String, String> reqParams) {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        H5UserSignServiceClient client = new H5UserSignServiceClient(config);

        //TODO 从参数中获取-测试数据身份证号为奇数即可
        String realName = "张三";
        String idCard = "110105194912310023";

        // 配置请求参数
        GetH5UserSignStatusRequest request = new GetH5UserSignStatusRequest();
        request.setDealerId(paramsMap.get("dealerId"));
        request.setBrokerId(paramsMap.get("brokerId"));
        request.setRealName(realName);
        request.setIdCard(idCard);
        YzhResponse<GetH5UserSignStatusResponse> response;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.getH5UserSignStatus(YzhRequest.build(timestamp, request));
            log.info("获取用户签约状态请求发送成功：request-id={}", timestamp);
            if (response.isSuccess()) {
                // 操作成功
                GetH5UserSignStatusResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 批次下单：平台企业可以通过批次下单接口，最多一次性发起 3,000 笔支付订单，每笔订单金额不可超过平台企业在对应支付路径设置的单笔限额。
     * 下单后，云账户会异步对每笔订单进行要素认证、签约校验和风控校验。校验失败或挂起的订单将通过异步回调的方式通知平台企业。
     */
    @Override
    public Object placeBatchOrder(Map<String, String> reqParams) {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        CreateBatchOrderRequest request = new CreateBatchOrderRequest();
        BatchOrderInfo[] infoArray = new BatchOrderInfo[2];
        BatchOrderInfo info1 = new BatchOrderInfo();
        BatchOrderInfo info2 = new BatchOrderInfo();
        info1.setOrderId("202009010016562010003");
        info1.setRealName("张三");
        info1.setIdCard("440524188001010014");
        info1.setCardNo("34252219983707827992");
        info1.setPay("1.00");
        info1.setPayRemark("备注1");
        info1.setNotifyUrl(paramsMap.get("withdrawCallBackUrl"));

        info2.setOrderId("202009010016562010004");
        info2.setRealName("李四");
        info2.setIdCard("11010519491231002X");
        info2.setCardNo("34252219983707827992");
        info2.setPay("2.30");
        info2.setPayRemark("备注2");
        info2.setNotifyUrl(paramsMap.get("withdrawCallBackUrl"));

        infoArray[0] = info1;
        infoArray[1] = info2;
        request.setBatchId("batch2032934858002");
        request.setDealerId(paramsMap.get("dealerId"));
        request.setBrokerId(paramsMap.get("brokerId"));
        request.setChannel(YZHWithdrawChannelEnum.BANK_PAY.getChannelZhName());
        request.setTotalPay("3.30");
        request.setTotalCount("2");
        request.setOrderList(infoArray);

        YzhResponse<CreateBatchOrderResponse> response;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.createBatchOrder(YzhRequest.build(timestamp, request));
            log.info("批次下单 请求发送成功：request-id={}", timestamp);
            if (response.isSuccess()) {
                // 操作成功
                CreateBatchOrderResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 批次确认：批次下单后，会异步进行要素认证和风控校验，在要素认证和风控检验未完成之前不允许请求此接口，
     * 如若请求则会返回“2042-该批次已支付或不符合支付要求”。建议批次下单后间隔 1 分钟后进行支付。
     */
    @Override
    public Object confirmBatchOrder() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        ConfirmBatchOrderRequest request = new ConfirmBatchOrderRequest();
        request.setBatchId("batch2032934858483");
        request.setDealerId(paramsMap.get("dealerId"));
        request.setBrokerId(paramsMap.get("brokerId"));
        request.setChannel(YZHWithdrawChannelEnum.BANK_PAY.getChannelZhName());
        YzhResponse<ConfirmBatchOrderResponse> response;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.confirmBatchOrder(YzhRequest.build(timestamp, request));
            log.info("批次确认 请求发送成功：request-id={}", timestamp);
            if (response.isSuccess()) {
                // 操作成功
                ConfirmBatchOrderResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 批次撤销：当批次下单完成后，在批次确认前，平台企业可通过此接口撤销该批次下所有订单。
     */
    @Override
    public Object cancelBatchOrder() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        CancelBatchOrderRequest request = new CancelBatchOrderRequest();
        request.setBatchId("batch2032934858003");
        request.setDealerId(paramsMap.get("dealerId"));
        request.setBrokerId(paramsMap.get("brokerId"));
        YzhResponse<CancelBatchOrderResponse> response;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.cancelBatchOrder(YzhRequest.build(timestamp, request));
            log.info("批次撤销 请求发送成功：request-id={}", timestamp);
            if (response.isSuccess()) {
                // 操作成功
                CancelBatchOrderResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 查询批次定单信息：平台企业批次下单后，使用此接口获取该批次订单状态及批次中所有订单信息。
     */
    @Override
    public Object queryBatchOrder() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        QueryBatchOrderRequest request = new QueryBatchOrderRequest();
        request.setBatchId("batch2032934858002");
        request.setDealerId(paramsMap.get("dealerId"));
        YzhResponse<QueryBatchOrderResponse> response;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            log.info("查询批次定单信息 请求发送成功：request-id={}", timestamp);
            response = client.queryBatchOrder(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                QueryBatchOrderResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 查询单笔订单信息：平台企业下单成功后，使用此接口获取订单支付状态。
     */
    @Override
    public Object queryOneOrder() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        GetOrderRequest request = new GetOrderRequest();
        request.setOrderId("202009010016562010003");
        request.setChannel(YZHWithdrawChannelEnum.BANK_PAY.getChannelZhName());
//        request.setDataType("encryption");
        YzhResponse<GetOrderResponse> response;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.getOrder(YzhRequest.build(timestamp, request));
            log.info("查询单笔订单信息 请求发送成功：request-id={}", timestamp);
            if (response.isSuccess()) {
                // 操作成功
                GetOrderResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 重试挂起状态的定单：平台企业可使用此接口重试支付由于用户未签约、账户余额不足等原因挂起的支付订单。
     */
    @Override
    public Object retrySuspendOrder() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        RetryOrderRequest request = new RetryOrderRequest();
        request.setDealerId(paramsMap.get("dealerId"));
        request.setOrderId("202009010016562010003");
        request.setChannel(YZHWithdrawChannelEnum.BANK_PAY.getChannelEnName());
        YzhResponse<RetryOrderResponse> response = null;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.retryOrder(YzhRequest.build(timestamp, request));
            log.info("查询单笔订单信息 请求发送成功：request-id={}", timestamp);
            if (response.isSuccess()) {
                // 操作成功
                RetryOrderResponse data = response.getData();
                log.info("操作成功，data={}", data);
            } else {
                // 失败返回
                log.info("失败返回 code={}, message={}, requestId={}", response.getCode(), response.getMessage(), response.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    @Override
    public Object sqlTest() {
        Map<String, String> params = this.getParams();
        System.out.println(params);
        return null;
    }


    private Object applySign(Map<String, String> reqParams) {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();
        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        H5UserSignServiceClient client = new H5UserSignServiceClient(config);

        //TODO 从参数中获取-测试数据身份证号为奇数即可
        String realName = "张三";
        String idCard = "110105194912310023";

        // 配置请求参数
        H5UserPresignRequest preRequest = new H5UserPresignRequest();
        preRequest.setDealerId(paramsMap.get("dealerId"));
        preRequest.setBrokerId(paramsMap.get("brokerId"));
        preRequest.setRealName(realName);
        preRequest.setIdCard(idCard);
        preRequest.setCertificateType(YZHCertificateTypeEnum.ID_CARD.getCertificateType()); // 证件类型：默认身份证
        YzhResponse<H5UserPresignResponse> preResponse;
        try {
            // timestamp：请求ID，请求的唯一标识，记录在日志中，便于问题发现及排查
            String timestamp = String.valueOf(System.currentTimeMillis());
            preResponse = client.h5UserPresign(YzhRequest.build(timestamp, preRequest));
            log.info("预申请签约请求发送成功：request-id={}", timestamp);
            // 1、预申请签约操作
            if (preResponse.isSuccess()) {
                H5UserPresignResponse preData = preResponse.getData();
                if (preData == null) {
                    log.info("预申请签约操作失败，返回数据为空");
                    return Result.error("预签约失败");
                }

                if (YZHSignStatusEnum.SIGNED.getSignStatus() != preData.getStatus()) {
                    log.info("预申请签约操作失败，已签约，无需再次签约, preData={}", preData);
                    return Result.error("已签约，无需再次签约");
                }
                log.info("预申请签约操作成功，response={} ", preData);

                // 2、第一步成功后，开始申请签约
                H5UserSignRequest request = new H5UserSignRequest();
                request.setToken(preData.getToken());
                request.setColor(paramsMap.get("color"));
                request.setUrl(paramsMap.get("signCallBackUrl"));
                request.setRedirectUrl(paramsMap.get("signRedirectUrl"));
                YzhResponse<H5UserSignResponse> response;
                response = client.h5UserSign(YzhRequest.build(request));
                if (response.isSuccess()) {
                    // 操作成功
                    H5UserSignResponse data = response.getData();
                    log.info("申请签约操作成功，response={} ", data);
                } else {
                    // 失败返回
                    log.info("申请签约失败返回 code：{}, message：{}, requestId：{}", response.getCode(), response.getMessage(), response.getRequestId());
                    return Result.error();
                }
            } else {
                // 失败返回
                log.info("预申请签约失败返回 code：{}, message：{}, requestId：{}", preResponse.getCode(), preResponse.getMessage(), preResponse.getRequestId());
                return Result.error();
            }
        } catch (Exception e) {
            // 发生异常
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    private Map<String, String> getParams() {
        // 检查是否存在前置表，没有则需要预处理创建
        System.out.println("前1111111111111111111111111111111111111111");
        this.initTable();
        System.out.println("后233333333333333333333333333333333333333");

        // 数据库查询第三方库的基本参数
        List<ThirdPartyParam> partyParamList = thirdPartyParamMapper.listParamByThirdNo(ThirdPartyEnum.YUN_ZHANG_HU.getThirdPartyNo());
        // 保存参数到map中
        Map<String, String> paramsMap = new HashMap<>();
        for (ThirdPartyParam param : partyParamList) {
            paramsMap.put(param.getParamName(), param.getParamValue());
        }
        return paramsMap;
    }

    private YzhConfig getYzhConfig(Map<String, String> paramsMap) {
        YzhConfig config = new YzhConfig();
        config.setDealerId(paramsMap.get(ThirdPartyParamNameConstant.DEALER_ID));
        config.setBrokerId(paramsMap.get(ThirdPartyParamNameConstant.BROKER_ID));
        config.setYzhAppKey(paramsMap.get(ThirdPartyParamNameConstant.APP_KEY));
        config.setYzh3DesKey(paramsMap.get(ThirdPartyParamNameConstant.THREE_DES_KEY));
        config.setYzhRsaPrivateKey(paramsMap.get(ThirdPartyParamNameConstant.RSA_PRIVATE_KEY));
        config.setYzhRsaPublicKey(paramsMap.get(ThirdPartyParamNameConstant.RSA_PUBLIC_KEY));
        config.setYzhUrl(paramsMap.get(ThirdPartyParamNameConstant.BASE_URL));
        config.setSignType(YzhConfig.SignType.RSA);
        return config;
    }

}

