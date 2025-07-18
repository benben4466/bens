package cn.ibenbeni.bens.sys.modular.menu.controller;

import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
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

}
