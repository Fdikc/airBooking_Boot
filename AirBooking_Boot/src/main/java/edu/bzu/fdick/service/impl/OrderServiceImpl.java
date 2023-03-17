package edu.bzu.fdick.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bzu.fdick.domain.Flight;
import edu.bzu.fdick.domain.Order;
import edu.bzu.fdick.domain.User;
import edu.bzu.fdick.mapper.FlightMapper;
import edu.bzu.fdick.mapper.UserMapper;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.OrderService;
import edu.bzu.fdick.mapper.OrderMapper;
import edu.bzu.fdick.utils.JWTUtil;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* @author FU
* @description 针对表【order_user】的数据库操作Service实现
* @createDate 2023-03-11 12:42:20
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {

//redis

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FlightMapper flightMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Page<Order> getflightNumberPage(Integer page, Integer size, Order order) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (order.getIdCard() != null && order.getIdCard() != "") {
            lambdaQueryWrapper.eq(Order::getIdCard, order.getIdCard());
        }
        if (order.getFlightId() != null) {
            lambdaQueryWrapper.eq(Order::getFlightId, order.getFlightId());
        }
        Page<Order> orderPage = this.page(new Page<>(page, size), lambdaQueryWrapper);
        //根据flightId 查询航班信息
        for (Order o1 : orderPage.getRecords()) {
            Flight flight = flightMapper.selectById(o1.getFlightId());
            User user = userMapper.selectById(o1.getUserId());
            o1.setUser(user);
            o1.setFlight(flight);
        }
        return orderPage;
    }

    @Override
    public Result purchasingTickets(String preparePay) {

        //{
        // "idCard":[{"key":1,"value":"111"},{"key":1678775039895,"value":"2222"}],
        // "name":[{"key":1,"value":"111"},{"key":1678775039895,"value":"222"}]
        // }
        //获取请求头的token
        String token = RequestToken.getCurrentToken();
        //根据token获取用户信息
        User userInfo = JWTUtil.getUserInfo(token, User.class);
        //根据用户信息获取用户id
        Integer userId = userInfo.getId();
//        System.out.println(userInfo + "===============================");

        String key = "flight:"+userInfo.getId();
        //获取redis中的航班信息
        String s = stringRedisTemplate.opsForValue().get(key);
        int flightId = Integer.parseInt(s);//航班id
        //
        String s1 = stringRedisTemplate.opsForValue().get(key + ":type");
        int type = Integer.parseInt(s1);//航班类型

        Flight flight = flightMapper.selectById(flightId);
        String orderName = flight.getFlightNumber() + "航班" + type + "号舱位";

        //        {
//        "idCard":[
//              {"key":1,"value":"111"},
//              {"key":1678774325203,"value":"222"}
//          ],
//        "name":[
//              {"key":1,"value":"111"},
//              {"key":1678774325203,"value":"222"}
//          ]
//        }
        //获取前端传来的数据
        JSONObject jsonObject = JSON.parseObject(preparePay);
        //获取身份证号
        List<JSONObject> idCard = jsonObject.getJSONArray("idCard").toJavaList(JSONObject.class);
//        System.out.println(idCard+"=============================");
        //[{"value":"372924","key":1}, {"value":"09866","key":1678784662943}]
        //获取姓名
        List<JSONObject> name = jsonObject.getJSONArray("name").toJavaList(JSONObject.class);
//        System.out.println(name+"================-------------=============");
        //[{"value":"辅导从","key":1}, {"value":"付uuu","key":1678784662943}]
        //获取每一个身份证号和姓名

//        for (JSONObject jsonIdCard : idCard){
//            Object value = jsonIdCard.get("value");
//            System.out.println(value+"=============================");
//        }
//        for (JSONObject jsonName : name){
//            Object value = jsonName.get("value");
//            System.out.println(value+"=============================");
//        }
//        System.out.println(idCard.size()+"=============人数================");

       Double price = 0.0;
        if (type==0){
            price = flight.getFlightHighPrice()*idCard.size();
        }else {
            price = flight.getFlightBasePrice()*idCard.size();
        }

        ArrayList<Order> orders = new ArrayList<>();
        for (int i=0; i<idCard.size(); i++){
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            Object idCardOne = (idCard.get(i).get("value"));
            String nameOne = (String)(name.get(i).get("value"));
//            System.out.println(id+"============================="+nameOne);
//            System.out.println(idCardOne+"============================="+nameOne);
            order.setIdCard(idCardOne.toString());
            order.setUserName(nameOne);
            order.setFlightId(flightId);
            order.setOrderName(orderName);
            order.setUserId(userId);
            order.setGrade(type);
            order.setPrice(price);
            orders.add(order);
        }

//        System.out.println(orders+"=============================");
        this.saveBatch(orders);
        return Result.success(orders);

    }

}




