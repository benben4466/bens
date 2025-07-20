package cn.ibenbeni.bens.sys.modular.menu.controller;

import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuListReq;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuSaveReq;
import cn.ibenbeni.bens.sys.modular.menu.pojo.response.MenuResp;
import cn.ibenbeni.bens.sys.modular.menu.pojo.response.MenuSimpleResp;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理界面的接口
 *
 * @author: benben
 * @time: 2025/6/1 下午5:20
 */
@Slf4j
@RestController
@Tag(name = "管理后台 - 菜单")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Operation(summary = "创建菜单")
    @PostMapping("/system/menu/create")
    public ResponseData<Long> createMenu(@Valid @RequestBody SysMenuSaveReq req) {
        return new SuccessResponseData<>(sysMenuService.createMenu(req));
    }

    @Operation(summary = "修改菜单")
    @PutMapping("/system/menu/update")
    public ResponseData<Boolean> updateMenu(@Valid @RequestBody SysMenuSaveReq req) {
        sysMenuService.updateMenu(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "菜单ID", required = true, example = "10")
    @DeleteMapping("/system/menu/delete")
    public ResponseData<Boolean> deleteMenu(@RequestParam("id") Long id) {
        sysMenuService.deleteMenu(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除菜单")
    @Parameter(name = "ids", description = "菜单ID集合", required = true, example = "[1,10]")
    @DeleteMapping("/system/menu/delete-list")
    public ResponseData<Boolean> deleteMenu(@RequestParam("ids") Set<Long> ids) {
        sysMenuService.deleteMenu(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取菜单信息")
    @Parameter(name = "id", description = "菜单ID", required = true, example = "10")
    @GetMapping("/system/menu/get")
    public ResponseData<MenuResp> getMenu(@RequestParam("id") Long id) {
        return new SuccessResponseData<>(BeanUtils.toBean(sysMenuService.getMenu(id), MenuResp.class));
    }

    @Operation(summary = "获取菜单列表", description = "用于【菜单管理】界面")
    @GetMapping("/system/menu/list")
    public ResponseData<List<MenuResp>> getMenuList(SysMenuListReq req) {
        List<SysMenuDO> list = sysMenuService.getMenuList(req);
        list.sort(Comparator.comparing(SysMenuDO::getMenuSort));
        return new SuccessResponseData<>(BeanUtils.toBean(list, MenuResp.class));
    }

    @Operation(summary = "获取菜单精简信息列表", description = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。")
    @GetMapping("/system/menu/simple-list")
    public ResponseData<List<MenuSimpleResp>> getSimpleMenuList() {
        List<SysMenuDO> menuList = sysMenuService.getMenuList();
        menuList = sysMenuService.filterDisableMenus(menuList);
        menuList.sort(Comparator.comparing(SysMenuDO::getMenuSort));
        return new SuccessResponseData<>(BeanUtils.toBean(menuList, MenuSimpleResp.class));
    }

}
