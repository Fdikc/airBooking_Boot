package edu.bzu.fdick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bzu.fdick.domain.SysMenu;
import edu.bzu.fdick.domain.SysUser;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.SysMenuService;
import edu.bzu.fdick.mapper.SysMenuMapper;
import edu.bzu.fdick.utils.JWTUtil;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author FU
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2023-03-07 20:03:11
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenu(String token) {
        SysUser user= JWTUtil.getUserInfo(token, SysUser.class);
        System.out.println(user);
        return sysMenuMapper.getByUserId(user.getId());
    }
}




