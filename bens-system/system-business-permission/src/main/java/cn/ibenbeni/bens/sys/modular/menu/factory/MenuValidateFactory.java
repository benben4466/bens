package cn.ibenbeni.bens.sys.modular.menu.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.enums.menu.MenuTypeEnum;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.menu.enums.exception.SysMenuExceptionEnum;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuRequest;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 菜单参数校验
 *
 * @author: benben
 * @time: 2025/6/1 下午3:09
 */
public class MenuValidateFactory {

    /**
     * 校验新增菜单时候的参数合法性
     */
    public static void validateAddMenuParam(SysMenuRequest sysMenuRequest) {
        SysMenuService sysMenuService = SpringUtil.getBean(SysMenuService.class);

        // 1.校验菜单编码不能重复，全局唯一，因为菜单编码涉及到权限分配，如果不唯一则会权限分配错乱
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                .eq(SysMenu::getMenuCode, sysMenuRequest.getMenuCode());

        // 如果是编辑菜单，则排除当前这个菜单的查询
        if (sysMenuRequest.getMenuId() != null) {
            queryWrapper.ne(SysMenu::getMenuId, sysMenuRequest.getMenuId());
        }

        long alreadyCount = sysMenuService.count(queryWrapper);
        if (alreadyCount > 0) {
            throw new ServiceException(SysMenuExceptionEnum.MENU_CODE_REPEAT);
        }

        // 2.校验vue组件相关的配置是否必填
        // 2.1 如果是后台菜单，校验路由地址、是否隐藏参数
        if (MenuTypeEnum.BACKEND_MENU.getKey().equals(sysMenuRequest.getMenuType())) {
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvRouter())) {
                throw new ServiceException(SysMenuExceptionEnum.URL_CANT_EMPTY);
            }
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvVisible())) {
                throw new ServiceException(SysMenuExceptionEnum.HIDDEN_FLAG_CANT_EMPTY);
            }
        }

        // 2.2 如果是纯前端路由，则判断路由地址和组件代码路径
        else if (MenuTypeEnum.FRONT_VUE.getKey().equals(sysMenuRequest.getMenuType())) {
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvRouter())) {
                throw new ServiceException(SysMenuExceptionEnum.URL_CANT_EMPTY);
            }
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvComponent())) {
                throw new ServiceException(SysMenuExceptionEnum.COMPONENT_PATH_CANT_EMPTY);
            }
        }

        // 2.3 如果是内部链接，判断路由地址和连接地址
        else if (MenuTypeEnum.INNER_URL.getKey().equals(sysMenuRequest.getMenuType())) {
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvRouter())) {
                throw new ServiceException(SysMenuExceptionEnum.URL_CANT_EMPTY);
            }
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvComponent())) {
                throw new ServiceException(SysMenuExceptionEnum.COMPONENT_PATH_CANT_EMPTY);
            }
        }

        // 2.4 如果是外部链接，则判断外部链接地址
        else if (MenuTypeEnum.OUT_URL.getKey().equals(sysMenuRequest.getMenuType())) {
            if (ObjectUtil.isEmpty(sysMenuRequest.getAntdvRouter())) {
                throw new ServiceException(SysMenuExceptionEnum.URL_CANT_EMPTY);
            }
        }
    }

}
