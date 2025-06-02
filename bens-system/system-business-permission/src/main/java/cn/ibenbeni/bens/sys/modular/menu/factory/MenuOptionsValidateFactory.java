package cn.ibenbeni.bens.sys.modular.menu.factory;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.enums.exception.SysMenuOptionsExceptionEnum;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuOptionsRequest;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 菜单功能参数校验
 *
 * @author: benben
 * @time: 2025/6/2 上午10:22
 */
public class MenuOptionsValidateFactory {

    /**
     * 校验新增菜单功能时候的参数合法性
     */
    public static void validateMenuOptionsParam(SysMenuOptionsRequest sysMenuOptionsRequest) {
        SysMenuOptionsService sysMenuOptionsService = SpringUtil.getBean(SysMenuOptionsService.class);

        // 1.功能编码全局唯一
        LambdaQueryWrapper<SysMenuOptions> codeWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .eq(SysMenuOptions::getOptionCode, sysMenuOptionsRequest.getOptionCode());
        // 如果是编辑菜单，则排除当前这个菜单的查询
        if (sysMenuOptionsRequest.getMenuOptionId() != null) {
            codeWrapper.ne(SysMenuOptions::getMenuOptionId, sysMenuOptionsRequest.getMenuOptionId());
        }
        long alreadyCodeCount = sysMenuOptionsService.count(codeWrapper);
        if (alreadyCodeCount > 0) {
            throw new ServiceException(SysMenuOptionsExceptionEnum.OPTIONS_CODE_REPEAT);
        }

        // 2.同菜单下功能名称不重复
        LambdaQueryWrapper<SysMenuOptions> nameWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .eq(SysMenuOptions::getMenuId, sysMenuOptionsRequest.getMenuId())
                .eq(SysMenuOptions::getOptionName, sysMenuOptionsRequest.getOptionName());
        // 如果是编辑菜单，则排除当前这个菜单的查询
        if (sysMenuOptionsRequest.getMenuOptionId() != null) {
            nameWrapper.ne(SysMenuOptions::getMenuOptionId, sysMenuOptionsRequest.getMenuOptionId());
        }
        long alreadyNameCount = sysMenuOptionsService.count(nameWrapper);
        if (alreadyNameCount > 0) {
            throw new ServiceException(SysMenuOptionsExceptionEnum.OPTIONS_NAME_REPEAT);
        }
    }

}
