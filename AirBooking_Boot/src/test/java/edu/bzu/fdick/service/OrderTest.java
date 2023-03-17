package edu.bzu.fdick.service;


import edu.bzu.fdick.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderTest {


    @Autowired
    private OrderService orderService;


    @Test
    public void test(){
        List<Order> list = orderService.list();
        System.out.println(list);


    }
}
