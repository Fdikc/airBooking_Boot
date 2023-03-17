package edu.bzu.fdick.service;


import edu.bzu.fdick.domain.SysUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserTest {


    @Autowired
    private SysUserService sysUserService;
    @Test
    public void test() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("11222hh11hh");
        sysUser.setCity("beiji1ng");
        boolean save = sysUserService.save(sysUser);
        System.out.println(save);

    }

}
