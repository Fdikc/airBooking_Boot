package edu.bzu.fdick.jwt;

import edu.bzu.fdick.domain.User;
import edu.bzu.fdick.utils.JWTUtil;
import org.junit.jupiter.api.Test;

public class JWTTest {

    @Test
    public void test1(){
        User user = new User();
        user.setId(1);
        user.setPassword("123456");
        user.setUsername("fdick");
        user.setEmail("1924258102@qq.com");
        String token = JWTUtil.getToken(user);
        System.out.println(token);

    }

    @Test
    public void test2(){

        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySW5mbyI6IntcImVtYWlsXCI6XCIxOTI0MjU4MTAyQHFxLmNvbVwiLFwiaWRcIjoxLFwicGFzc3dvcmRcIjpcIjEyMzQ1NlwiLFwicGhvbmVcIjpcIjEyMzQ1Njc4OTAxXCIsXCJ1c2VybmFtZVwiOlwiZmRpY2tcIn0iLCJleHAiOjE2NzgwMjIzNjR9.-BMEa6_XRIVZsnedEgsFtGvPIp5gVcHXUy4RBmEhiM0";
        boolean verify = JWTUtil.verify(token);
        System.out.println(verify);
        //转成user对象
        User user = JWTUtil.getUserInfo(token, User.class);
        System.out.println(user);




    }



}
