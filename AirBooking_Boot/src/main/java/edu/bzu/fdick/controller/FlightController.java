package edu.bzu.fdick.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.bzu.fdick.domain.Flight;
import edu.bzu.fdick.domain.User;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.FlightService;
import edu.bzu.fdick.utils.JWTUtil;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (Flight)表控制层
 *
 * @author fdick
 * @since 2023-03-09 15:24:08
 */
@RestController
@RequestMapping("/flight")
public class FlightController  {
    /**
     * 服务对象
     */
    @Autowired
    private FlightService flightService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询所有数据
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll() {
        return Result.success(this.flightService.list());
    }


    @GetMapping("/{page}/{size}")
    public Result selectAll(@PathVariable("page")Integer page,@PathVariable("size")Integer size, Flight flight) {
        LambdaQueryWrapper<Flight> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (flight.getFlightNumber()!=null){
            lambdaQueryWrapper.like(Flight::getFlightNumber,flight.getFlightNumber());
        }
        if (flight.getFlightStartPlace()!=null&&flight.getFlightEndPlace()!=null){
            lambdaQueryWrapper.ge(Flight::getFlightStartTime,flight.getFlightStartPlace());
            lambdaQueryWrapper.le(Flight::getFlightEndTime,flight.getFlightEndPlace());
        }

        Page<Flight> flightPage = this.flightService.page(new Page<>(page, size), lambdaQueryWrapper);
        return Result.success(flightPage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.flightService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param flight 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Flight flight) {
        return Result.success(this.flightService.save(flight));
    }

    /**
     * 修改数据
     *
     * @param flight 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Flight flight) {
        return Result.success(this.flightService.updateById(flight));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestBody String idList) {

        //批量删除
        List<Integer> list = JSON.parseArray(idList, Integer.class);
        flightService.removeByIds(list);
        return Result.success();
    }

    @PostMapping("/ticket")
        public Result searchTicket(@RequestBody String flight){
        String startPlace=JSON.parseObject(flight).getString("from");
        String endPlace=JSON.parseObject(flight).getString("to");
        String startTime=JSON.parseObject(flight).getString("time");
        Integer num = JSON.parseObject(flight).getInteger("num");
//        System.out.println(startPlace+" "+endPlace+" "+startTime+" "+num);
        LambdaQueryWrapper<Flight> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Flight> wrapper = lambdaQueryWrapper
                .like(Flight::getFlightStartPlace, startPlace)
                .like(Flight::getFlightEndPlace, endPlace)
                .ge(Flight::getFlightStartTime, startTime)
                .ge(Flight::getFlightBaseNumber, num);
        List<Flight> flights = flightService.list(wrapper);
//        return Result.success(flights);
//        System.out.println(flights);
        return Result.success(flights);
    }



    @GetMapping("/user/{id}/{type}")
    public Result PreGetOne(@PathVariable Serializable id,@PathVariable Integer type) {
        Flight flight = flightService.getById(id);
        String token = RequestToken.getCurrentToken();
        User userInfo = JWTUtil.getUserInfo(token, User.class);
        String key = "flight:"+userInfo.getId();
        stringRedisTemplate.opsForValue().set(key,flight.getFlightId().toString());
        stringRedisTemplate.opsForValue().set(key+":type",type.toString());
        return Result.success();
    }
}

