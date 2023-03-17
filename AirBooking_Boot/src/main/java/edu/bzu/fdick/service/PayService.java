package edu.bzu.fdick.service;


import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import edu.bzu.fdick.config.AlipayConfig;
import edu.bzu.fdick.controller.AlipayDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PayService{

    @Resource
    private AlipayConfig alipayConfig;

    public String pay(AlipayDTO dto,HttpServletResponse response ) throws AlipayApiException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getPublicKey(),
                alipayConfig.getSignType());
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //回调地址 结合业务更新我方数据库
//        System.out.println("支付宝回调地址:" + alipayConfig.getNotifyUrl());
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        request.setReturnUrl(alipayConfig.getReturnUrl());
        //支付完 支付界面跳转的界面
        JSONObject bizContent = new JSONObject();

        bizContent.set("out_trade_no", dto.getOut_trade_no());
        bizContent.set("total_amount", dto.getTotal_amount());
        bizContent.set("subject", dto.getSubject());
//        bizContent.set("body", dto.getBody());
        bizContent.set("product_code", dto.getProduct_code());
        request.setBizContent(bizContent.toString());

        String form="";
        try {
            form = alipayClient.pageExecute(request).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

//        System.out.println(form);
//        response.setContentType("text/html;charset=" + alipayConfig.getCharset());
//        response.getWriter().write(form);//直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();



        return form;
//        return alipayClient.pageExecute(request).getBody();
//        request.setReturnUrl(alipayConfig.getReturnUrl());
//        request.setBizContent(JSON.toJSONString(dto));
//        return alipayClient.pageExecute(request).getBody();
    }
}