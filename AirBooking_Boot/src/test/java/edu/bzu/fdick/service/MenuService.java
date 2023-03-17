package edu.bzu.fdick.service;

import cn.hutool.crypto.digest.MD5;
import edu.bzu.fdick.domain.SysMenu;
import edu.bzu.fdick.domain.SysUser;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuService {


    @Autowired
    private SysMenuService menuService;
    @Test
    public void test1(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySW5mbyI6IntcImF2YXRhclwiOlwiaHR0cHM6Ly9pbWFnZS0xMzAwNTY2NTEzLmNvcy5hcC1ndWFuZ3pob3UubXlxY2xvdWQuY29tL3VwbG9hZC9pbWFnZXMvNWE5ZjQ4MTE4MTY2MzA4ZGFiYThiNmRhN2U0NjZhYWIuanBnXCIsXCJjaXR5XCI6XCLoj4_ms71cIixcImNyZWF0ZWRcIjoxNjczMjczNjMzMDAwLFwiZW1haWxcIjpcIjEyM0BxcS5jb21cIixcImlkXCI6MSxcInN0YXR1XCI6MSxcInVwZGF0ZWRcIjoxNjczNDI3NDUyMDAwLFwidXNlcm5hbWVcIjpcImFkbWluXCJ9IiwiZXhwIjoxNjc4MjgxNzQxfQ.0grUjFAzibeq4CGPwlDmxE5Ze7XTetY76gdIgxW7y_U";
        List<SysMenu> menu = menuService.getMenu(token);
        System.out.println(menu);

    }
    @Test
    public void test2(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySW5mbyI6IntcImF2YXRhclwiOlwiaHR0cHM6Ly9pbWFnZS0xMzAwNTY2NTEzLmNvcy5hcC1ndWFuZ3pob3UubXlxY2xvdWQuY29tL3VwbG9hZC9pbWFnZXMvNWE5ZjQ4MTE4MTY2MzA4ZGFiYThiNmRhN2U0NjZhYWIuanBnXCIsXCJjaXR5XCI6XCLoj4_ms71cIixcImNyZWF0ZWRcIjoxNjczMjczNjMzMDAwLFwiZW1haWxcIjpcIjEyM0BxcS5jb21cIixcImlkXCI6MSxcInN0YXR1XCI6MSxcInVwZGF0ZWRcIjoxNjczNDI3NDUyMDAwLFwidXNlcm5hbWVcIjpcImFkbWluXCJ9IiwiZXhwIjoxNjc4MjgxNzQxfQ.0grUjFAzibeq4CGPwlDmxE5Ze7XTetY76gdIgxW7y_U";

        boolean verify = JWTUtil.verify(token);
        System.out.println(verify);
        SysUser userInfo = JWTUtil.getUserInfo(token, SysUser.class);




        System.out.println(userInfo);


    }




}
