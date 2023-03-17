package edu.bzu.fdick.controller;



import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.bzu.fdick.domain.Flight;
import edu.bzu.fdick.domain.User;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.SendMailService;
import edu.bzu.fdick.service.UserService;
import edu.bzu.fdick.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户表(User)表控制层
 *
 * @author fdick
 * @since 2023-03-10 17:21:06
 */
@RestController
@RequestMapping("/user")
public class UserController  {
    /**
     * 服务对象
     */

    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<User> page, User user) {
        return Result.success(this.userService.page(page, new QueryWrapper<>(user)));
    }


    @GetMapping("/{page}/{size}")
    public Result selectAll(@PathVariable("page")Integer page,@PathVariable("size")Integer size,User user) {
        System.out.println(user);
        if (user.getUsername() == null) {
            Page<User> userPage = this.userService.page(new Page<>(page, size));
            return Result.success(userPage);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> wrapper = lambdaQueryWrapper.like(User::getUsername, user.getUsername())
                .or().like(User::getIdCard, user.getUsername())
                .or().like(User::getEmail, user.getUsername())
                .or().like(User::getPhone, user.getUsername());
        wrapper.orderByDesc(User::getId);
        Page<User> userPage = this.userService.page(new Page<>(page, size), lambdaQueryWrapper);
        return Result.success(userPage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody User user) {
        return Result.success(this.userService.save(user));
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody User user) {
        return Result.success(this.userService.updateById(user));
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
        userService.removeByIds(list);
        return Result.success();
    }





    @GetMapping("/email/{email}")
    public Result selectOne(@PathVariable String email) {
        System.out.println(email+"=====================================");
        int codeSix = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println(codeSix);
        //存入redis
        stringRedisTemplate.opsForValue().set(email, String.valueOf(codeSix));
        sendMailService.sendSimpleMail(email, String.valueOf(codeSix));
        return Result.success("发送成功");
    }


    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping("/register")
    public Result insertUser(@RequestBody User user) {
        System.out.println(user+"=====================================");
        //判断验证码是否正确
        String code = stringRedisTemplate.opsForValue().get(user.getEmail());
        if (code == null) {
            return Result.error("验证码已过期");
        }
        if (!code.equals(user.getYzm())) {
            return Result.error("验证码错误");
        }
        //判断用户名是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> wrapper = lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User one = this.userService.getOne(wrapper);
        if (one != null) {
            return Result.error("用户名已存在");
        }
        return Result.success( this.userService.save(user));
    }


    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse response){
        System.out.println(user+"---------------------------------------------------");
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> wrapper = lambdaQueryWrapper.eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword());
        User one = this.userService.getOne(wrapper);
        if (one == null) {
            return Result.error("用户名或密码错误");
        }
        one.setPassword(null);
        //生成token
        String token = JWTUtil.getToken(one);
        System.out.println(token+"---------------------------------------------------");
        one.setPassword(token);
        //存入redis
        stringRedisTemplate.opsForValue().set(one.getId().toString(),token,100, TimeUnit.MINUTES);
        response.setHeader("authorization",token);
        return Result.success(one);

    }




}

