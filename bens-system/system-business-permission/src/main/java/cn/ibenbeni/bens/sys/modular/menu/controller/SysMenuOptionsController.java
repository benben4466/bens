package cn.ibenbeni.bens.sys.modular.menu.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuOptionsRequest;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单下的功能操作接口
 *
 * @author: benben
 * @time: 2025/6/2 上午9:54
 */
@Slf4j
@RestController
public class SysMenuOptionsController {

    @Resource
    private SysMenuOptionsService sysMenuOptionsService;

    /**
     * 添加菜单功能
     */
    @PostMapping("/sysMenuOptions/add")
    public ResponseData<SysMenuOptions> add(@RequestBody SysMenuOptionsRequest sysMenuOptionsRequest) {
        sysMenuOptionsService.add(sysMenuOptionsRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除菜单功能
     */
    @PostMapping("/sysMenuOptions/delete")
    public ResponseData<?> delete(@RequestBody SysMenuOptionsRequest sysMenuOptionsRequest) {
        sysMenuOptionsService.del(sysMenuOptionsRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑菜单功能
     */
    @PostMapping("/sysMenuOptions/edit")
    public ResponseData<?> edit(@RequestBody SysMenuOptionsRequest sysMenuOptionsRequest) {
        sysMenuOptionsService.edit(sysMenuOptionsRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 分页查询-菜单功能
     */
    @GetMapping("/sysMenuOptions/page")
    public ResponseData<PageResult<SysMenuOptions>> page(SysMenuOptionsRequest sysMenuOptionsRequest) {
        return new SuccessResponseData<>(sysMenuOptionsService.findPage(sysMenuOptionsRequest));
    }

    /**
     * 获取同一菜单ID的所有功能列表
     */
    @GetMapping("/sysMenuOptions/list")
    public ResponseData<List<SysMenuOptions>> getRoleList(SysMenuOptionsRequest sysMenuOptionsRequest) {
        return new SuccessResponseData<>(sysMenuOptionsService.findList(sysMenuOptionsRequest));
    }

}
