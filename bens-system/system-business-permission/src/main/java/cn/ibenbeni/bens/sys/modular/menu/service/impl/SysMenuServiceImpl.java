package cn.ibenbeni.bens.sys.modular.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.api.enums.menu.MenuTypeEnum;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.enums.exception.SysMenuExceptionEnum;
import cn.ibenbeni.bens.sys.modular.menu.factory.MenuValidateFactory;
import cn.ibenbeni.bens.sys.modular.menu.mapper.SysMenuMapper;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuListReq;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuSaveReq;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统菜单业务实现层
 *
 * @author: benben
 * @time: 2025/6/1 上午10:57
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuDO> implements SysMenuService {

    // region 属性

    @Resource
    private SysMenuMapper sysMenuMapper;

    // endregion

    // region 公共方法

    @Override
    public Long createMenu(SysMenuSaveReq req) {
        // 校验父菜单
        MenuValidateFactory.validateParentMenu(req.getMenuParentId(), null);
        // 校验菜单名称
        MenuValidateFactory.validateMenuName(req.getMenuParentId(), null, req.getMenuName());
        // 校验权限编码
        MenuValidateFactory.validateMenuPermissionCode(null, req.getPermissionCode());

        SysMenuDO menu = BeanUtil.toBean(req, SysMenuDO.class);
        // 初始化通用属性，防止误添加（针对按钮）
        this.initMenuCommonProperty(menu);
        this.save(menu);
        return menu.getMenuId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(Long menuId) {
        // 校验删除菜单是否存在
        if (sysMenuMapper.selectById(menuId) == null) {
            throw new SysException(SysMenuExceptionEnum.MENU_NOT_EXISTED);
        }

        // 校验是否存在子菜单
        if (sysMenuMapper.selectCountByParentId(menuId) > 0) {
            throw new SysException(SysMenuExceptionEnum.MENU_CHILDREN_EXISTED);
        }

        this.baseDelete(CollUtil.set(false, menuId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(Set<Long> idSet) {
        // 校验是否存在子菜单
        idSet.forEach(menuId -> {
            if (sysMenuMapper.selectCountByParentId(menuId) > 0) {
                throw new SysException(SysMenuExceptionEnum.MENU_CHILDREN_EXISTED);
            }
        });

        this.baseDelete(idSet);
    }

    @Override
    public void updateMenu(SysMenuSaveReq req) {
        // 校验删除菜单是否存在
        SysMenuDO menu = sysMenuMapper.selectById(req.getMenuId());
        if (menu == null) {
            throw new SysException(SysMenuExceptionEnum.MENU_NOT_EXISTED);
        }
        // 校验父菜单
        MenuValidateFactory.validateParentMenu(req.getMenuParentId(), req.getMenuId());
        // 校验菜单名称
        MenuValidateFactory.validateMenuName(req.getMenuParentId(), req.getMenuId(), req.getMenuName());
        // 校验权限编码
        MenuValidateFactory.validateMenuPermissionCode(req.getMenuId(), req.getPermissionCode());

        BeanUtil.copyProperties(req, menu);
        this.initMenuCommonProperty(menu);
        this.updateById(menu);
    }

    @Override
    public SysMenuDO getMenu(Long menuId) {
        return getById(menuId);
    }

    @Override
    public List<SysMenuDO> getMenuList() {
        return list();
    }

    @Override
    public List<SysMenuDO> getMenuList(Set<Long> menuIdSet) {
        if (CollUtil.isEmpty(menuIdSet)) {
            return new ArrayList<>();
        }
        return listByIds(menuIdSet);
    }

    @Override
    public List<SysMenuDO> getMenuList(SysMenuListReq req) {
        return sysMenuMapper.selectList(req);
    }

    @Override
    public List<SysMenuDO> filterDisableMenus(List<SysMenuDO> list) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        Map<Long, SysMenuDO> menuMap = CollectionUtils.convertMap(list, SysMenuDO::getMenuId);

        // 遍历菜单集合
        List<SysMenuDO> enabledMenus = new ArrayList<>();
        Set<Long> disabledMenuCache = new HashSet<>(); // 存下递归搜索过被禁用的菜单，防止重复的搜索

        for (SysMenuDO menu : list) {
            if (this.isMenuDisabled(menu, menuMap, disabledMenuCache)) {
                continue;
            }
            enabledMenus.add(menu);
        }
        return enabledMenus;
    }

    @Override
    public List<Long> listMenuIdByPermissionCode(String permissionCode) {
        List<SysMenuDO> menuList = sysMenuMapper.selectListByPermissionCode(permissionCode);
        return CollectionUtils.convertList(menuList, SysMenuDO::getMenuId);
    }

    // endregion

    // region 私有方法

    /**
     * 初始化菜单通用属性
     */
    private void initMenuCommonProperty(SysMenuDO menu) {
        // 若菜单是按钮，则清除component、icon等属性
        if (MenuTypeEnum.BUTTON.getCode().equals(menu.getMenuType())) {
            menu.setComponentIcon(null);
            menu.setComponentPath(null);
            menu.setComponentRouter(null);
            menu.setComponentVisible(null);
            menu.setKeepAlive(null);
            menu.setAlwaysShow(null);
        }
    }

    private void baseDelete(Set<Long> idSet) {
        Map<String, RemoveMenuCallbackApi> menuCallbackApiMap = SpringUtil.getBeansOfType(RemoveMenuCallbackApi.class);

        this.removeBatchByIds(idSet);

        // 联动删除所有和菜单相关其他业务数据
        for (RemoveMenuCallbackApi removeMenuCallbackApi : menuCallbackApiMap.values()) {
            removeMenuCallbackApi.removeMenuAction(idSet);
        }
    }

    /**
     * 判断菜单是否被禁用
     *
     * @param menu              菜单
     * @param menuMap           所有比对菜单集合
     * @param disabledMenuCache 被禁用的菜单ID缓存
     * @return 是否禁用
     */
    private boolean isMenuDisabled(SysMenuDO menu, Map<Long, SysMenuDO> menuMap, Set<Long> disabledMenuCache) {
        // 已存在被禁用缓存中，则直接返回
        if (disabledMenuCache.contains(menu.getMenuId())) {
            return true;
        }

        // 校验菜单被禁用
        if (StatusEnum.isDisable(menu.getStatusFlag())) {
            disabledMenuCache.add(menu.getMenuId());
            return true;
        }

        // 菜单是根级菜单，则直接返回
        if (TreeConstants.DEFAULT_PARENT_ID.equals(menu.getMenuParentId())) {
            return false;
        }

        // 递归判断父级菜单是否被禁用
        SysMenuDO parent = menuMap.get(menu.getMenuParentId());
        if (parent == null || this.isMenuDisabled(parent, menuMap, disabledMenuCache)) {
            disabledMenuCache.add(menu.getMenuId());
            return true;
        }

        return false;
    }

    // endregion

}
