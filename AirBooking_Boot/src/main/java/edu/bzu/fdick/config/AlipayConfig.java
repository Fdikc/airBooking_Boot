package edu.bzu.fdick.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    private String appId;
    private String privateKey;
    private String publicKey;
    private String returnUrl;
    private String notifyUrl;
    private String refundNotifyUrl;
    private String gatewayUrl;
    private String charset;
    private String signType;
//
//    @Value("${alipay.appId}")
//    private String appId;
//    @Value("${alipay.privateKey}")
//    private String privateKey;
//    @Value("${alipay.publicKey}")
//    private String publicKey;
//    @Value("${alipay.returnUrl}")
//    private String returnUrl;
//    @Value("${alipay.notifyUrl}")
//    private String notifyUrl;
//    @Value("${alipay.refundNotifyUrl}")
//    private String refundNotifyUrl;
//    @Value("${alipay.gatewayUrl}")
//    private String gatewayUrl;
//    @Value("${alipay.charset}")
//    private String charset;
//    @Value("${alipay.signType}")
//    private String signType;


}