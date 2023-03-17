package edu.bzu.fdick.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.bzu.fdick.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author FU
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2023-03-07 20:03:11
* @Entity edu.bzu.fdick.domain.SysMenu
*/


public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getByUserId(@Param("id") Long id);

}




