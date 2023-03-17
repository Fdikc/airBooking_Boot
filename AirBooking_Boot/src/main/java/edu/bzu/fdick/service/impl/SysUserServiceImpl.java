package edu.bzu.fdick.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bzu.fdick.domain.SysUser;
import edu.bzu.fdick.service.SysUserService;
import edu.bzu.fdick.mapper.SysUserMapper;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.stereotype.Service;

/**
* @author FU
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-03-07 17:32:28
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Override
    public SysUser getRole(String token) {


        return null;
    }

    @Override
    public boolean updatePassword(Integer id,String oldPassword, String newPassword) {


        //根据id查询用户
        SysUser user = this.getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("新密码不能与旧密码相同");
        }
        String OldDigestHex = new MD5().digestHex(oldPassword);
        if (!OldDigestHex.equals(user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }
        user.setPassword(new MD5().digestHex(newPassword));
        //旧密码正确
        return this.updateById(user);
    }
}




