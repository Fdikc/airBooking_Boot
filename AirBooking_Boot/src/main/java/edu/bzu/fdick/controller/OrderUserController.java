package edu.bzu.fdick.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.bzu.fdick.config.AlipayConfig;
import edu.bzu.fdick.domain.Flight;
import edu.bzu.fdick.domain.Order;
import edu.bzu.fdick.domain.User;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.FlightService;
import edu.bzu.fdick.service.OrderService;
import edu.bzu.fdick.service.PayService;
import edu.bzu.fdick.utils.JWTUtil;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * (OrderUser)表控制层
 *
 * @author fdick
 * @since 2023-03-11 12:44:56
 */
@RestController
@RequestMapping("/order")
public class OrderUserController  {
    /**
     * 服务对象
     */
    @Resource
    private PayService payService;



    @Autowired
    private OrderService orderUserService;


    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private FlightService flightService;



    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */

//    @GetMapping
//    public Result selectAll(Page<Order> page, Order orderUser) {
//        return Result.success(this.orderUserService.page(page, new QueryWrapper<>(orderUser)));
//    }
    @GetMapping("/{page}/{size}")
    public Result selectAll(@PathVariable("page")Integer page, @PathVariable("size")Integer size, Order order) {
        System.out.println(order+"=================================================");
        Page<Order> orderPage = orderUserService.getflightNumberPage(page, size, order);
        return Result.success(orderPage);
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.orderUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param orderUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Order orderUser) {
        orderUser.setId(UUID.randomUUID().toString());
        System.out.println(orderUser);
        return Result.success(this.orderUserService.save(orderUser));
    }

    /**
     * 修改数据
     *
     * @param orderUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Order orderUser) {
        return Result.success(this.orderUserService.updateById(orderUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
//    @DeleteMapping
//    public Result delete(@RequestParam("idList") List<Long> idList) {
//        return Result.success(this.orderUserService.removeByIds(idList));
//    }


    @DeleteMapping
    public Result delete(@RequestBody String idList) {
        //批量删除
        List<String> list = JSON.parseArray(idList, String.class);
        orderUserService.removeByIds(list);
        return Result.success();
    }




    @PostMapping("/PreparePay")
    public Result PreparePay(@RequestBody String PreparePay) {
//        System.out.println(PreparePay);
        return orderUserService.purchasingTickets(PreparePay);
//        return Result.success(orderUserService.PreparePay(order));
    }
    @PostMapping(value = "/pay")
    public Result pay(@RequestBody AlipayDTO dto, HttpServletResponse response) throws AlipayApiException, IOException {
        System.out.println(dto);
        //支付 整合实际业务创建或者更新订单
        return Result.success(this.payService.pay(dto, response));
    }
///mtohgy7190@sandbox.com
    @PostMapping("/notify")
    public String afterPay(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("支付完成");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //获取trade_status
        String status = params.get("trade_status");
        //获取订单号
        String outTradeNo = params.get("out_trade_no");
        System.out.println("订单号"+outTradeNo+"支付状态"+status);
        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getPublicKey(),
                alipayConfig.getCharset(),
                alipayConfig.getSignType()); //调用SDK验证签名

        System.out.println("签名验证"+signVerified);
        if (status.equals("TRADE_SUCCESS")){
            LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Order::getId, outTradeNo);
                Order orders = orderUserService.getOne(lambdaQueryWrapper);
                if (orders != null) {
                    orders.setStatu(1);
                    Integer grade = orders.getGrade();
                    orderUserService.updateById(orders);
                    //座位数减一
                    Flight flight = flightService.getById(orders.getFlightId());
                    if (grade == 0) {
                        flight.setFlightHighNumber(flight.getFlightHighNumber() - 1);
                    } else if (grade == 1) {
                        flight.setFlightBaseNumber(flight.getFlightBaseNumber() - 1);
                    }
                    flightService.updateById(flight);
                }
        }

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	{
	gmt_create=2023-03-15 11:13:12, //交易创建时间
	charset=utf-8, //编码格式
	gmt_payment=2023-03-15 11:13:17,//交易付款时间
	notify_time=2023-03-15 11:13:19, //
	subject=FDC1??1???, //订单标题
	sign=iEfgzZ3ztBHfiMCvH8EjAnmQABl/kal8KcPno+G0qAKcYgb/+w5yVX1Y3wCyafSiiP4O0hRtHIX7DXQslJExjdh+tuzWWPes8I8dWsrBxb4AuXhSX2KT+KtbIhB2z3ziSlRZmBqMaYdBuIiezPZVcWsJ+bJf7TmB1UeZ3FsAkOy0mwlDgETNsaFTGcAvAk7gvsVfU58Zpx1rEPaMJRUU7w2XYJdBcOHbeK6+xGJaEksglx2fNpwWST5JzqAJC4VOOJHHgG5dKDagn3P2MRduE4MFkL/qQWbaqeuEFlZFmFZ/gnks5hMOdT61cwsmm3/zY5QZVRdNHjoT6FUUlChfWQ==,
	buyer_id=2088102179956884, //买家支付宝用户号
	invoice_amount=555.00, //开票金额
	version=1.0, //接口版本
	notify_id=2023031500222111319056880528751592,//通知校验ID
	fund_bill_list=[{"amount":"555.00","fundChannel":"ALIPAYACCOUNT"}],//支付金额信息
	notify_type=trade_status_sync, //通知类型
	out_trade_no=27ab0c75-23a5-4c8d-8f0d-7c770d814429,//商户订单号
	total_amount=555.00, //订单金额
	trade_status=TRADE_SUCCESS, //交易状态
	trade_no=2023031522001456880504060058, //支付宝交易号
	auth_app_id=2016101500694447, //授权商户的appid
	receipt_amount=555.00, //实收金额
	point_amount=0.00, //集分宝金额
	app_id=2016101500694447, //支付宝分配给开发者的应用Id
	buyer_pay_amount=555.00, //付款金额
	sign_type=RSA2, //签名算法
	seller_id=2088102179728164//卖家支付宝用户号
	}
signVerified = false

	实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
//        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
//            System.out.println("=========支付宝异步回调========");
//
//            Map<String, String> params = new HashMap<>();
//            Map<String, String[]> requestParams = request.getParameterMap();
//            for (String name : requestParams.keySet()) {
//                params.put(name, request.getParameter(name));
//                // System.out.println(name + " = " + request.getParameter(name));
//            }
//
//            String outTradeNo = params.get("out_trade_no");
//            String gmtPayment = params.get("gmt_payment");
//            String alipayTradeNo = params.get("trade_no");
//            System.out.println("outTradeNo = " + outTradeNo);
//
//            String sign = params.get("sign");
//
//            // 验证签名
//            String content = AlipaySignature.getSignCheckContentV1(params);
//            boolean checkSignature = AlipaySignature.rsa256CheckContent(
//                    content, sign, alipayConfig.getPublicKey(), "UTF-8"); // 验证签名
//            // 支付宝验签
//            if (checkSignature) {
//                // 验签通过
//                System.out.println("交易名称: " + params.get("subject"));
//                System.out.println("交易状态: " + params.get("trade_status"));
//                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
//                System.out.println("商户订单号: " + params.get("out_trade_no"));
//                System.out.println("交易金额: " + params.get("total_amount"));
//                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
//                System.out.println("买家付款时间: " + params.get("gmt_payment"));
//                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
//
//                // 查询订单
//                LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//                lambdaQueryWrapper.eq(Order::getId, outTradeNo);
//                Order orders = orderUserService.getOne(lambdaQueryWrapper);
//
//                if (orders != null) {
//                    orders.setStatu(1);
//                    Integer grade = orders.getGrade();
////                    orders.setAlipayNo(alipayTradeNo);
////                    orders.setPayTime(new Date());
////                    orders.setState("已支付");
//                    orderUserService.updateById(orders);
//                    //座位数减一
//                    Flight flight = flightService.getById(orders.getFlightId());
//                    if (grade == 0) {
//                        flight.setFlightHighNumber(flight.getFlightHighNumber() - 1);
//                    } else if (grade == 1) {
//                        flight.setFlightBaseNumber(flight.getFlightBaseNumber() - 1);
//                    }
//                    flightService.updateById(flight);
//                }
//
            return "success";
        }






    @GetMapping("/orderList")
    public Result orderList() {
        String token = RequestToken.getCurrentToken();
        User userInfo = JWTUtil.getUserInfo(token, User.class);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userInfo.getId());
        List<Order> list = orderUserService.list(queryWrapper);
        for (Order order : list) {
            order.setFlight(flightService.getById(order.getFlightId()));
        }
        System.out.println(list);
        return Result.success(list);
    }



}

