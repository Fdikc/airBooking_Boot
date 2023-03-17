package edu.bzu.fdick.service;

import edu.bzu.fdick.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author FU
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-03-07 17:32:28
*/
public interface SysUserService extends IService<SysUser> {



    //查询用户所属角色
    public SysUser getRole(String token);

    boolean updatePassword(Integer id,String oldPassword, String newPassword);
}
