package edu.bzu.fdick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.bzu.fdick.domain.SysUser;
import edu.bzu.fdick.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LoginService  extends IService<SysUser> {


    Result login(String name, String password, String yzm, String key,HttpServletResponse response);

    void getCode(HttpServletResponse response) throws IOException;
}
