package com.party.thirdpartyproject.demos.web;

import com.party.thirdpartyproject.enums.YZHWithdrawChannelEnum;
import com.yunzhanghu.sdk.base.YzhConfig;
import com.yunzhanghu.sdk.base.YzhRequest;
import com.yunzhanghu.sdk.base.YzhResponse;
import com.yunzhanghu.sdk.h5usersign.H5UserSignServiceClient;
import com.yunzhanghu.sdk.h5usersign.domain.*;
import com.yunzhanghu.sdk.notify.NotifyClient;
import com.yunzhanghu.sdk.notify.domain.NotifyRequest;
import com.yunzhanghu.sdk.notify.domain.NotifyResponse;
import com.yunzhanghu.sdk.payment.PaymentClient;
import com.yunzhanghu.sdk.payment.domain.*;

import java.util.*;

/**
 * @author ：light
 * @date ：2024/11/26 15:51:33
 * @description :
 */
public class YZHdemo {

    private Map<String, String> getParams() {
        //TODO 从数据库获取必要的信息
        String dealerId = "27631898";
        String brokerId = "27532644";
        String appKey = "N491w1DvASmLG4UcELjRG68NcjWVVQ23";
        String threeDesKey = "v6DsaJxsKeOgQ89Ym04W156y";
        String rsaPrivateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDvve7MSadw/RcXGsgm2fiHcBjorSNZVqpP1DTaMY3Gcx+c/8Xtt8dOb6Y8iyBqbEgzDZkouTmE+rGuqoIl6Wpic2xxqAB2RCjVXIY8Q1XjS7QSn5vNlBGfCoa2y3y4y8Np5nziu5iIpSb2RAsUdn5SjnwwivBYuRolHtuESV7grIoQimSzZ4g7OUe5eI5whem6GM2DPwwzIY4Bt7lqLficmDsE7/v3TuxuQBTmHmDmjR5dKIxp30YGbAbcNdnu5fMNQ3uwy7hOJzpXE2tBe6t3+L35ocAdgFTpW12LgYo4n3H4RKQHFwUpjIEn+x8BndsCYHzY6IemB+8lauSzaP97AgMBAAECggEBAJF512Xva8OMUG6cuceZjM3in7rRFRopFL/MLSYZ28YIJa7HJfvSAQb/iQhEBC+P9V/FaKLlgpnG5frvTHpk4UFMeQmewGkDgyO1OV9oHL4IOgTzBUACEY9Ie+aAI/ae7r1cN3v+CdkhHjthnreUD6NZAtMR/DZaXBsaVmo3e3MpEvd01ktwyw27VRo3Cr0by2tzxYvjaEDPdhhA3axTTbP1A+tS5WXJjDpXG81BE82HI26ihDC4k3VwE3Mf7AETmr1/arDAhBS4YtEB67S1k9pitwFDP8h02s/Osgasd3aQL3kVBAlc6Y0C1x4g3+b/YI4OPz+FJHO3xJt+4DnEeMECgYEA/r82iVRc8T0eu1eHWmDbsJcYnYqdlrXJrr9NE+prJ9GNKJdwtqDOZmB5e5m5Uw4yY6EMjSV5q90loMk7srT0b6M73YFL7MIA3oXFJGBoF+sFi9vMrs4xH1HAOk+lWzNStwMon/nZ6RkRgmQbOdiUGJhmcD/hvX7h2srpS+xkp80CgYEA8OvTLRkPfewW7Nr9K7ESP0UZGzgsh76YC+kf4jvuUaXKzWrTrC0nx0cShRU1TthwP+y8wgOvEF0PV0X9m/eqx/OsACKNaUEWZaUW4qvr6zOb5YMqzmOfHUE7pbJrdwxBGq90TZovPNtON1sgVSu4f1PkOyq9U7NND9omKaObbGcCgYEAkq+2DoO2dO6VyJhRBxiox9lpQiX0MjkBJg9fqa0dALOoTYxOScr0rny01xggX5PYLggpOwkuM2lG5nuZVYDfE31x1k1CU9UuPPPYHecpaURaUDtDdBCktn0TbEA6XfaClYFLHjr3YEKgE23jQ37TYAo5Sj9uk/gAw0R968r19q0CgYAIHenfexiqhWnAUSB2eGDFAO73m8zkReeZfyEz3hEJbyyF/zNieaL9c8eL9Er3fRNLFA7H+pfLVVdO+Wyx9IYsdmk1F/TrtuWqRwWeXWx1YCfAc8rdaT3THn+D9Z7pQ+BtpbulYax1wG3CL6/HLtHoeEsYMzhtuXy/K/pdLMXvwQKBgQCWbFbVEVstQqS3sf4KiZ47OlzODGrkQT4dogSsy3SekDLyEZ8NxgKa/Omr6yjpfl6cshd5vI7/3CARvRg9FnQnaWEeXNXb/Yg4oVJ8BUG2RYoYPSxL+ZVdxWP4HGRWjpBNNQLl9HBNm2h4tzLlOEHVxxDk0YE84NcifxgE1Tvp3g==";
        String rsaPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA773uzEmncP0XFxrIJtn4h3AY6K0jWVaqT9Q02jGNxnMfnP/F7bfHTm+mPIsgamxIMw2ZKLk5hPqxrqqCJelqYnNscagAdkQo1VyGPENV40u0Ep+bzZQRnwqGtst8uMvDaeZ84ruYiKUm9kQLFHZ+Uo58MIrwWLkaJR7bhEle4KyKEIpks2eIOzlHuXiOcIXpuhjNgz8MMyGOAbe5ai34nJg7BO/7907sbkAU5h5g5o0eXSiMad9GBmwG3DXZ7uXzDUN7sMu4Tic6VxNrQXurd/i9+aHAHYBU6Vtdi4GKOJ9x+ESkBxcFKYyBJ/sfAZ3bAmB82OiHpgfvJWrks2j/ewIDAQAB";
//        String baseUrl = "https://api-service.yunzhanghu.com"; // 正式环境
        String baseUrl = "https://api-service.yunzhanghu.com/sandbox"; // 沙箱环境
        String color = "#8171ff";
        String callBackUrl = "https://callback-saas.ypszkj.com/api/yunzhanghu/signContactCallBack"; //回调地址
        String redirectUrl = "https://www.baidu.com/"; //跳转地址

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("dealerId", "27631898");
        paramsMap.put("brokerId", "27532644");
        paramsMap.put("appKey", "N491w1DvASmLG4UcELjRG68NcjWVVQ23");
        paramsMap.put("threeDesKey", "v6DsaJxsKeOgQ89Ym04W156y");
        paramsMap.put("rsaPrivateKey", "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDvve7MSadw/RcXGsgm2fiHcBjorSNZVqpP1DTaMY3Gcx+c/8Xtt8dOb6Y8iyBqbEgzDZkouTmE+rGuqoIl6Wpic2xxqAB2RCjVXIY8Q1XjS7QSn5vNlBGfCoa2y3y4y8Np5nziu5iIpSb2RAsUdn5SjnwwivBYuRolHtuESV7grIoQimSzZ4g7OUe5eI5whem6GM2DPwwzIY4Bt7lqLficmDsE7/v3TuxuQBTmHmDmjR5dKIxp30YGbAbcNdnu5fMNQ3uwy7hOJzpXE2tBe6t3+L35ocAdgFTpW12LgYo4n3H4RKQHFwUpjIEn+x8BndsCYHzY6IemB+8lauSzaP97AgMBAAECggEBAJF512Xva8OMUG6cuceZjM3in7rRFRopFL/MLSYZ28YIJa7HJfvSAQb/iQhEBC+P9V/FaKLlgpnG5frvTHpk4UFMeQmewGkDgyO1OV9oHL4IOgTzBUACEY9Ie+aAI/ae7r1cN3v+CdkhHjthnreUD6NZAtMR/DZaXBsaVmo3e3MpEvd01ktwyw27VRo3Cr0by2tzxYvjaEDPdhhA3axTTbP1A+tS5WXJjDpXG81BE82HI26ihDC4k3VwE3Mf7AETmr1/arDAhBS4YtEB67S1k9pitwFDP8h02s/Osgasd3aQL3kVBAlc6Y0C1x4g3+b/YI4OPz+FJHO3xJt+4DnEeMECgYEA/r82iVRc8T0eu1eHWmDbsJcYnYqdlrXJrr9NE+prJ9GNKJdwtqDOZmB5e5m5Uw4yY6EMjSV5q90loMk7srT0b6M73YFL7MIA3oXFJGBoF+sFi9vMrs4xH1HAOk+lWzNStwMon/nZ6RkRgmQbOdiUGJhmcD/hvX7h2srpS+xkp80CgYEA8OvTLRkPfewW7Nr9K7ESP0UZGzgsh76YC+kf4jvuUaXKzWrTrC0nx0cShRU1TthwP+y8wgOvEF0PV0X9m/eqx/OsACKNaUEWZaUW4qvr6zOb5YMqzmOfHUE7pbJrdwxBGq90TZovPNtON1sgVSu4f1PkOyq9U7NND9omKaObbGcCgYEAkq+2DoO2dO6VyJhRBxiox9lpQiX0MjkBJg9fqa0dALOoTYxOScr0rny01xggX5PYLggpOwkuM2lG5nuZVYDfE31x1k1CU9UuPPPYHecpaURaUDtDdBCktn0TbEA6XfaClYFLHjr3YEKgE23jQ37TYAo5Sj9uk/gAw0R968r19q0CgYAIHenfexiqhWnAUSB2eGDFAO73m8zkReeZfyEz3hEJbyyF/zNieaL9c8eL9Er3fRNLFA7H+pfLVVdO+Wyx9IYsdmk1F/TrtuWqRwWeXWx1YCfAc8rdaT3THn+D9Z7pQ+BtpbulYax1wG3CL6/HLtHoeEsYMzhtuXy/K/pdLMXvwQKBgQCWbFbVEVstQqS3sf4KiZ47OlzODGrkQT4dogSsy3SekDLyEZ8NxgKa/Omr6yjpfl6cshd5vI7/3CARvRg9FnQnaWEeXNXb/Yg4oVJ8BUG2RYoYPSxL+ZVdxWP4HGRWjpBNNQLl9HBNm2h4tzLlOEHVxxDk0YE84NcifxgE1Tvp3g==");
        paramsMap.put("rsaPublicKey", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA773uzEmncP0XFxrIJtn4h3AY6K0jWVaqT9Q02jGNxnMfnP/F7bfHTm+mPIsgamxIMw2ZKLk5hPqxrqqCJelqYnNscagAdkQo1VyGPENV40u0Ep+bzZQRnwqGtst8uMvDaeZ84ruYiKUm9kQLFHZ+Uo58MIrwWLkaJR7bhEle4KyKEIpks2eIOzlHuXiOcIXpuhjNgz8MMyGOAbe5ai34nJg7BO/7907sbkAU5h5g5o0eXSiMad9GBmwG3DXZ7uXzDUN7sMu4Tic6VxNrQXurd/i9+aHAHYBU6Vtdi4GKOJ9x+ESkBxcFKYyBJ/sfAZ3bAmB82OiHpgfvJWrks2j/ewIDAQAB");
        paramsMap.put("callbackPublicKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOO28UyZ6vZYwQm3IvswdvtDJ+DQc7ntWtUJC9BD1InqRS9YXW+PC6MFa1TFXnlr1fbDz+WfjtK80c8yVIKKYYZoUtdruEnXkTqaK4sbku0OrEV6i21RokdfRbvUKB4dWUcQznB22zlbRc2NXvqAEg2zXU3PQjTZcv9GlDxNY7pQIDAQAB");
//        paramsMap.put("baseUrl", "https://api-service.yunzhanghu.com");
        paramsMap.put("baseUrl", "https://api-service.yunzhanghu.com/sandbox");
        paramsMap.put("color", "#8171ff");
        paramsMap.put("signCallBackUrl", "https://callback-saas.ypszkj.com/api/yunzhanghu/signContactCallBack");
        paramsMap.put("signRedirectUrl", "https://www.baidu.com/");
        paramsMap.put("withdrawCallBackUrl", "https://callback-saas.ypszkj.com/api/yunzhanghu/placeBatchOrderCallBack");
        return paramsMap;
    }

    private YzhConfig getYzhConfig(Map<String, String> paramsMap) {
        YzhConfig config = new YzhConfig();
        config.setDealerId(paramsMap.get("dealerId"));
        config.setBrokerId(paramsMap.get("brokerId"));
        config.setYzhAppKey(paramsMap.get("appKey"));
        config.setYzh3DesKey(paramsMap.get("threeDesKey"));
        config.setYzhRsaPrivateKey(paramsMap.get("rsaPrivateKey"));
        config.setYzhRsaPublicKey(paramsMap.get("rsaPublicKey"));
        config.setYzhUrl(paramsMap.get("baseUrl"));
        config.setSignType(YzhConfig.SignType.RSA);
        return config;
    }

    private YzhConfig getCallbackConfig(Map<String, String> paramsMap) {
        YzhConfig config = new YzhConfig();
        config.setDealerId(paramsMap.get("dealerId"));
        config.setBrokerId(paramsMap.get("brokerId"));
        config.setYzhAppKey(paramsMap.get("appKey"));
        config.setYzh3DesKey(paramsMap.get("threeDesKey"));
        config.setYzhRsaPrivateKey(paramsMap.get("rsaPrivateKey"));
        config.setYzhRsaPublicKey(paramsMap.get("callbackPublicKey"));
        config.setYzhUrl(paramsMap.get("baseUrl"));
        config.setSignType(YzhConfig.SignType.RSA);
        return config;
    }

    public void applySign() {
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
        preRequest.setCertificateType(0); // 证件类型：默认身份证
        YzhResponse<H5UserPresignResponse> preResponse;
        try {
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            String timestamp = String.valueOf(System.currentTimeMillis());
            preResponse = client.h5UserPresign(YzhRequest.build(timestamp, preRequest));
            // 1、预申请签约操作
            if (preResponse.isSuccess()) {
                H5UserPresignResponse preData = preResponse.getData();
                System.out.println(preData);

                // 2、第一步成功后，开始申请签约
                H5UserSignRequest request = new H5UserSignRequest();
                request.setToken(preData.getToken());
                request.setColor(paramsMap.get("color"));
                request.setUrl(paramsMap.get("signCallBackUrl"));
                request.setRedirectUrl(paramsMap.get("signRedirectUrl"));
                YzhResponse<H5UserSignResponse> response;
                response = client.h5UserSign(YzhRequest.build(request));
                System.out.println(response);
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    public void getSignStatus() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        H5UserSignServiceClient client = new H5UserSignServiceClient(config);

        //TODO 从参数中获取-测试数据身份证号为奇数即可
        String realName = "张十";
        String idCard = "110105194912310051";

        // 配置请求参数
        GetH5UserSignStatusRequest request = new GetH5UserSignStatusRequest();
        request.setDealerId(paramsMap.get("dealerId"));
        request.setBrokerId(paramsMap.get("brokerId"));
        request.setRealName(realName);
        request.setIdCard(idCard);
        YzhResponse<GetH5UserSignStatusResponse> response;
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.getH5UserSignStatus(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                GetH5UserSignStatusResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    /**
     * 签约回调信息是加密的，需要解密
     */
    public void decryptSignCallbackInfo() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getCallbackConfig(paramsMap);
        NotifyClient client = new NotifyClient(config);

        // 异步回调参数
        NotifyRequest request = new NotifyRequest();
        String data = "/5vBCQ0GHlqutXPGg0QlPIVd0eYt/U1JvhvR+Zm8BzlTVTw6TuqmyolcQhh3WnZErkEUtlEiBwaVYi+YLiSK5KHOoEIBNLIJzUYj9epNiQvja31m3y4N620o3LjqqpZGHRHzhOO+szd1PjM6iMVLDw==";
        String mess = "494811889";
        String sign = "KvjcwWgEJueO4sUYDz5/cTCOZsc/WTb16MyzxOdTryDIafZktjaL6xrwOnjtTe7DdDJxAueuLJdmFaP8mmL5vveJJSHh4XcqQ7hUHp6y7V9W7+mcaE9Uf6GQr46NecMa09roNqV1k0Zd9wqgQDEUmS/Rq7Fj+GQ+LeD3cPh+VBc=";
        String timestamp = "1733041488";
        request.setData(data);
        request.setMess(mess);
        request.setSign(sign);
        request.setTimestamp(timestamp);

        // 发起验签解密
        try {
            NotifyResponse<NotifyH5UserSignRequest> response = client.notifyDecoder(request, NotifyH5UserSignRequest.class);
            if (response.getSignRes() && response.getDescryptRes()) {
                // 验签通过、解密成功
                NotifyH5UserSignRequest notifyRequest = response.getData();
                System.out.println("明文数据：" + notifyRequest);
            } else {
                // 验签未通过或解密失败
                System.out.println("验签结果：" + response.getSignRes() + " 解密结果：" + response.getDescryptRes());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    /**
     * 批次下单：平台企业可以通过批次下单接口，最多一次性发起 3,000 笔支付订单，每笔订单金额不可超过平台企业在对应支付路径设置的单笔限额。
     * 下单后，云账户会异步对每笔订单进行要素认证、签约校验和风控校验。校验失败或挂起的订单将通过异步回调的方式通知平台企业。
     */
    public void placeBatchOrder() {
        // 从数据库获取基础配置
        Map<String, String> paramsMap = this.getParams();

        // 配置基础信息
        YzhConfig config = this.getYzhConfig(paramsMap);
        PaymentClient client = new PaymentClient(config);

        // 配置请求参数
        CreateBatchOrderRequest request = new CreateBatchOrderRequest();
        BatchOrderInfo info1 = new BatchOrderInfo();
        BatchOrderInfo info2 = new BatchOrderInfo();
        info1.setOrderId("202009010016562010011");
        info1.setRealName("张三");
        info1.setIdCard("440524188001010014");
        info1.setCardNo("34252219983707827992");
        info1.setPay("100.00");
        info1.setPayRemark("备注1");
        info1.setNotifyUrl(paramsMap.get("withdrawCallBackUrl"));

        info2.setOrderId("202009010016562010012");
        info2.setRealName("李四");
        info2.setIdCard("11010519491231002X");
        info2.setCardNo("34252219983707827992");
        info2.setPay("10.00");
        info2.setPayRemark("备注2");
        info2.setNotifyUrl(paramsMap.get("withdrawCallBackUrl"));

        // 转换为 Integer 数组
        BatchOrderInfo[] infoArry = new BatchOrderInfo[2];
        infoArry[0] = info1;
        infoArry[1] = info2;

        request.setBatchId("batch2032934858005");
        request.setDealerId(paramsMap.get("dealerId"));
        request.setBrokerId(paramsMap.get("brokerId"));
        request.setChannel(YZHWithdrawChannelEnum.BANK_PAY.getChannelZhName());
        request.setTotalPay("110.00");
        request.setTotalCount("2");
        request.setOrderList(infoArry);

        YzhResponse<CreateBatchOrderResponse> response;
        try {
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.createBatchOrder(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                CreateBatchOrderResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    /**
     * 批次确认：批次下单后，会异步进行要素认证和风控校验，在要素认证和风控检验未完成之前不允许请求此接口，
     * 如若请求则会返回“2042-3该批次已支付或不符合支付要求1”。建议批次下单后间隔 1 分钟后进行支付1。
     */
    public void confirmBatchOrder() {
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
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.confirmBatchOrder(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                ConfirmBatchOrderResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    /**
     * 批次撤销：当批次下单完成后，在批次确认前，平台企业可通过此接口撤销该批次下所有订单。
     */
    public void cancelBatchOrder() {
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
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            //TODO 请求ID，打到日志里面，方便排查问题
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.cancelBatchOrder(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                CancelBatchOrderResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }


    /**
     * 查询批次定单信息：平台企业批次下单后，使用此接口获取该批次订单状态及批次中所有订单信息。
     */
    public void queryBatchOrder() {
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
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.queryBatchOrder(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                QueryBatchOrderResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    /**
     * 查询单笔订单信息：平台企业下单成功后，使用此接口获取订单支付状态。
     */
    public void queryOneOrder() {
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
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            String timestamp = String.valueOf(System.currentTimeMillis());
            response = client.getOrder(YzhRequest.build(timestamp, request));
            if (response.isSuccess()) {
                // 操作成功
                GetOrderResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    /**
     * 重试挂起状态的定单：平台企业可使用此接口重试支付由于用户未签约、账户余额不足等原因挂起的支付订单。
     */
    public void retrySuspendOrder() {
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
            // request-id：请求ID，请求的唯一标识
            // 建议平台企业自定义 request-id，并记录在日志中，便于问题发现及排查
            // 如未自定义 request-id，将使用 SDK 中的 UUID 方法自动生成。注意：UUID 方法生成的 request-id 不能保证全局唯一，推荐自定义 request-id
            response = client.retryOrder(YzhRequest.build("requestId", request));
            if (response.isSuccess()) {
                // 操作成功
                RetryOrderResponse data = response.getData();
                System.out.println("操作成功 " + data);
            } else {
                // 失败返回
                System.out.println("失败返回 code：" + response.getCode() + " message：" + response.getMessage() + " requestId：" + response.getRequestId());
            }
        } catch (Exception e) {
            // 发生异常
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        YZHdemo demo = new YZHdemo();
        demo.placeBatchOrder();
        // 注释1
    }
}
