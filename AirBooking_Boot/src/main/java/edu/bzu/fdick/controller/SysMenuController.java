package edu.bzu.fdick.controller;


import edu.bzu.fdick.domain.SysMenu;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.SysMenuService;
import edu.bzu.fdick.utils.RequestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (SysMenu)表控制层
 *
 * @author fdick
 * @since 2023-03-07 20:04:24
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Autowired
    private SysMenuService menuService;



    //根据token查询用户角色对应的菜单
    @GetMapping
    public Result findByToken() {

        String token = RequestToken.getCurrentToken();
        List<SysMenu> menus = menuService.getMenu(token);
        return Result.success(menus);
    }



    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysMenu 查询实体
     * @return 所有数据
     */
//    @GetMapping
//    public Result selectAll(Page<SysMenu> page, SysMenu sysMenu) {
//        return Result.success(this.sysMenuService.page(page, new QueryWrapper<>(sysMenu)));
//    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
//    @GetMapping("{id}")
//    public Result selectOne(@PathVariable Serializable id) {
//        return Result.success(this.sysMenuService.getById(id));
//    }

    /**
     * 新增数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
//    @PostMapping
//    public Result insert(@RequestBody SysMenu sysMenu) {
//        return Result.success(this.sysMenuService.save(sysMenu));
//    }

    /**
     * 修改数据
     *
     * @param sysMenu 实体对象
     * @return 修改结果
     */
//    @PutMapping
//    public Result update(@RequestBody SysMenu sysMenu) {
//        return Result.success(this.sysMenuService.updateById(sysMenu));
//    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
//    @DeleteMapping
//    public Result delete(@RequestParam("idList") List<Long> idList) {
//        return Result.success(this.sysMenuService.removeByIds(idList));
//    }
}

