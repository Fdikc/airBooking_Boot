package edu.bzu.fdick.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.SysUserService;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    //修改密码
    @PostMapping("/updatePassword")
    public Result updatePassword( @RequestBody String UserPass){

        JSONObject jsonObject = JSON.parseObject(UserPass);
        Integer id = jsonObject.getInteger("id");
        String oldPassword = jsonObject.getString("OriginalPass");
        String newPassword = jsonObject.getString("pass");
//        System.out.println(oldPassword+" "+newPassword);
//        System.out.println(id);
        boolean b = sysUserService.updatePassword(id, oldPassword, newPassword);
        if (b){
            return Result.success("操作成功");
        }
        return Result.error("操作失败");
    }



}
