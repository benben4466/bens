package cn.ibenbeni.bens.sys.modular.menu.controller;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuRequest;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 菜单管理界面的接口
 *
 * @author: benben
 * @time: 2025/6/1 下午5:20
 */
@Slf4j
@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 添加菜单
     */
    @PostMapping("/sysMenu/add")
    public ResponseData<SysMenu> add(@RequestBody @Validated(BaseRequest.add.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.add(sysMenuRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除菜单
     */
    @PostMapping("/sysMenu/delete")
    public ResponseData<?> delete(@RequestBody @Validated(BaseRequest.delete.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.del(sysMenuRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑菜单
     */
    @PostMapping("/sysMenu/edit")
    public ResponseData<?> edit(@RequestBody @Validated(BaseRequest.edit.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.edit(sysMenuRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看菜单详情
     */
    @GetMapping("/sysMenu/detail")
    public ResponseData<SysMenu> detail(@Validated(BaseRequest.detail.class) SysMenuRequest sysMenuRequest) {
        return new SuccessResponseData<>(sysMenuService.detail(sysMenuRequest));
    }

    /**
     * 调整菜单上下级机构和菜单的顺序
     */
    @PostMapping("/sysMenu/updateMenuTree")
    public ResponseData<?> updateMenuTree(@RequestBody @Validated(SysMenuRequest.updateMenuTree.class) SysMenuRequest sysMenuRequest) {
        sysMenuService.updateMenuTree(sysMenuRequest);
        return new SuccessResponseData<>();
    }

}
