package edu.bzu.fdick.service;

import edu.bzu.fdick.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.bzu.fdick.result.Result;

import java.util.List;

/**
* @author FU
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2023-03-07 20:03:11
*/
public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu> getMenu(String token);
}
