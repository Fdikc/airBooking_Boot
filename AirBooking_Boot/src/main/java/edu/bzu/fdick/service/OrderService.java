package edu.bzu.fdick.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.bzu.fdick.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.bzu.fdick.result.Result;

/**
* @author FU
* @description 针对表【order_user】的数据库操作Service
* @createDate 2023-03-11 12:42:20
*/
public interface OrderService extends IService<Order> {

    Page<Order> getflightNumberPage(Integer page, Integer size, Order order);

    Result purchasingTickets(String preparePay);
}
