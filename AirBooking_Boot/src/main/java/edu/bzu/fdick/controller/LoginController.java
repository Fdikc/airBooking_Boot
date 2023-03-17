package edu.bzu.fdick.controller;


import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {


    @Autowired
    private LoginService loginService;
    @GetMapping ("/login")
    //登录接受name 和password yzm key 四个参数
    public Result login(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("yzm") String yzm, @RequestParam("key") String key,HttpServletResponse response) {

//        System.out.println(name+" "+password+" "+yzm+" "+key);
        return loginService.login(name, password, yzm, key,response);
    }

    /**
     * 生成验证码
     * @return
     * @throws Exception
     */
    @GetMapping("/getCode")
    public void getCode(HttpServletResponse response) throws Exception {

        loginService.getCode(response);
    }



    @PostMapping("/logout")
    public Result logout(){

        //清除redis中的token



        return Result.success();
    }

}


