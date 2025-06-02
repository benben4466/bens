package cn.ibenbeni.bens.sys.modular.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.rule.constants.SymbolConstant;
import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.tree.buildpids.PidStructureBuildUtil;
import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.menu.enums.exception.SysMenuExceptionEnum;
import cn.ibenbeni.bens.sys.modular.menu.factory.MenuTreeFactory;
import cn.ibenbeni.bens.sys.modular.menu.factory.MenuValidateFactory;
import cn.ibenbeni.bens.sys.modular.menu.mapper.SysMenuMapper;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuRequest;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import cn.ibenbeni.bens.sys.modular.menu.util.MenuOrderFixUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统菜单业务实现层
 *
 * @author: benben
 * @time: 2025/6/1 上午10:57
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource(name = "menuCodeCache")
    private CacheOperatorApi<String> menuCodeCache;

    @Override
    public void add(SysMenuRequest sysMenuRequest) {
        // 校验参数合法性
        MenuValidateFactory.validateAddMenuParam(sysMenuRequest);

        SysMenu sysMenu = BeanUtil.toBean(sysMenuRequest, SysMenu.class);
        sysMenu.setMenuPids(this.createPids(sysMenuRequest.getMenuParentId()));

        this.save(sysMenu);

        // TODO 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(SysMenuRequest sysMenuRequest) {
        SysMenu dbSysMenu = this.detail(sysMenuRequest);
        if (dbSysMenu == null) {
            throw new ServiceException(SysMenuExceptionEnum.SYS_MENU_NOT_EXISTED);
        }

        // 本菜单下所以子菜单
        Set<Long> totalMenuIdSet = sysMenuMapper.getChildIdsByMenuId(sysMenuRequest.getMenuId());
        totalMenuIdSet.add(sysMenuRequest.getMenuId());

        this.baseDelete(totalMenuIdSet);

        // TODO 记录日志
        // TODO 发布菜单删除事件
    }

    @Override
    public void edit(SysMenuRequest sysMenuRequest) {
        // 无法修改上下级结构（使用拖拽接口比修改接口更方便）
        sysMenuRequest.setMenuParentId(null);

        // 校验参数合法性
        MenuValidateFactory.validateAddMenuParam(sysMenuRequest);

        SysMenu dbSysMenu = this.querySysMenu(sysMenuRequest);
        BeanUtil.copyProperties(sysMenuRequest, dbSysMenu);

        this.updateById(dbSysMenu);

        // TODO 记录日志
        // TODO 发布菜单删除事件
    }

    @Override
    public SysMenu detail(SysMenuRequest sysMenuRequest) {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                .eq(SysMenu::getMenuId, sysMenuRequest.getMenuId())
                .select(SysMenu::getMenuName, SysMenu::getMenuCode, SysMenu::getMenuSort,
                        SysMenu::getMenuType, SysMenu::getAntdvComponent, SysMenu::getAntdvRouter, SysMenu::getAntdvVisible,
                        SysMenu::getAntdvActiveUrl, SysMenu::getAntdvLinkUrl, SysMenu::getAntdvIcon, SysMenu::getMenuParentId);
        SysMenu dbSysMenu = this.getOne(queryWrapper, false);
        if (ObjectUtil.isNotEmpty(dbSysMenu) && ObjectUtil.isNotEmpty(dbSysMenu.getMenuParentId())) {
            if (dbSysMenu.getMenuParentId().equals(TreeConstants.DEFAULT_PARENT_ID)) {
                dbSysMenu.setMenuParentName("根节点");
            } else {
                SysMenu parentMenu = this.getById(dbSysMenu.getMenuParentId());
                dbSysMenu.setMenuParentName(parentMenu.getMenuName());
            }
        }

        return dbSysMenu;
    }

    @Override
    public List<SysMenu> getTotalMenus() {
        return this.getTotalMenus(null);
    }

    @Override
    public List<SysMenu> getTotalMenus(Set<Long> limitMenuIds) {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                .select(SysMenu::getMenuId, SysMenu::getMenuName, SysMenu::getMenuParentId, SysMenu::getMenuSort)
                .orderByAsc(SysMenu::getMenuSort);
        if (ObjectUtil.isNotEmpty(limitMenuIds)) {
            queryWrapper.in(SysMenu::getMenuId, limitMenuIds);
        }

        List<SysMenu> list = this.list(queryWrapper);

        // 对菜单进行再次排序，因为有的菜单是101，有的菜单是10101，需要将位数小的补0，再次排序；101补为 10100
        // 如：组织架构是101，它的子菜单人员是 10101；权限控制是102， 它的子菜单角色是10201；如果不补0，排序后是101、102、10101、10201
        //    排序混乱，则给101等补充0，后排序：10100、10101、10200、10201，同一级别下在一起，而不是显示混乱
        MenuOrderFixUtil.fixOrder(list);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMenuTree(SysMenuRequest sysMenuRequest) {
        // 更新树节点的菜单顺序
        MenuTreeFactory.updateSort(sysMenuRequest.getUpdateMenuTree(), 1);

        // 填充树节点的parentId字段
        MenuTreeFactory.fillParentId(-1L, sysMenuRequest.getUpdateMenuTree());

        // 填充树节点的parentId字段
        MenuTreeFactory.fillParentId(-1L, sysMenuRequest.getUpdateMenuTree());

        // 平行展开树形结构，准备从新整理pids；如101、10101、102、10201展开
        ArrayList<SysMenu> totalMenuList = new ArrayList<>();
        MenuTreeFactory.collectTreeTasks(sysMenuRequest.getUpdateMenuTree(), totalMenuList);

        // 从新整理上下级结构，整理id和pid关系
        PidStructureBuildUtil.createPidStructure(totalMenuList);

        // 更新菜单的sort字段、pid字段和pids字段这3个字段
        this.updateBatchById(totalMenuList);
    }

    @Override
    public List<SysMenu> getTotalMenuList() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                .select(SysMenu::getMenuId)
                .orderByAsc(SysMenu::getMenuSort);
        return this.list(queryWrapper);
    }

    @Override
    public List<SysMenu> getIndexMenuInfoList(List<Long> menuIdList) {
        if (ObjectUtil.isEmpty(menuIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                .in(SysMenu::getMenuId, menuIdList)
                .select(SysMenu::getMenuId, SysMenu::getMenuParentId, SysMenu::getMenuPids, SysMenu::getMenuCode,
                        SysMenu::getMenuName, SysMenu::getMenuType, SysMenu::getAntdvIcon, SysMenu::getAntdvVisible,
                        SysMenu::getAntdvActiveUrl, SysMenu::getAntdvRouter, SysMenu::getAntdvComponent, SysMenu::getMenuSort)
                .orderByAsc(SysMenu::getMenuSort);
        return this.list(queryWrapper);
    }

    @Override
    public List<String> getMenuCodeList(List<Long> menuIdList) {
        List<String> result = new ArrayList<>();
        if (ObjectUtil.isEmpty(menuIdList)) {
            return result;
        }

        for (Long menuId : menuIdList) {
            String menuIdKey = menuId.toString();

            // 先从缓存查询，是否有菜单编码
            String menuCode = menuCodeCache.get(menuIdKey);
            if (StrUtil.isNotBlank(menuCode)) {
                result.add(menuCode);
                continue;
            }

            // 查询库中的菜单编码
            LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getMenuId, menuId)
                    .select(SysMenu::getMenuCode);
            SysMenu dbSysMenu = this.getOne(queryWrapper, false);
            if (dbSysMenu == null) {
                continue;
            }

            String menuCodeQueryResult = dbSysMenu.getMenuCode();
            if (ObjectUtil.isNotEmpty(menuCodeQueryResult)) {
                result.add(menuCodeQueryResult);
                // 添加到缓存一份
                menuCodeCache.put(menuIdKey, menuCodeQueryResult, SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
            }
        }

        return result;
    }

    @Override
    public Map<Long, Long> getMenuIdParentIdMap() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                .select(SysMenu::getMenuId, SysMenu::getMenuParentId);
        List<SysMenu> list = this.list(queryWrapper);
        if (ObjectUtil.isEmpty(list)) {
            return new HashMap<>();
        }

        return list.stream()
                .collect(Collectors.toMap(SysMenu::getMenuId, SysMenu::getMenuParentId));
    }

    /**
     * 创建Pids的值
     * <p>如果pid是顶级节点，pids = 【[-1],】</p>
     * <p>如果pid不是顶级节点，pids = 【父菜单的pids,[pid],】</p>
     *
     * @param menuParentId 父节点ID
     */
    private String createPids(Long menuParentId) {
        // 父节点是顶级节点
        if (TreeConstants.DEFAULT_PARENT_ID.equals(menuParentId)) {
            return SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
        } else {
            // 非顶级节点
            LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getMenuId, menuParentId)
                    .select(SysMenu::getMenuPids);
            SysMenu parentMenu = this.getOne(queryWrapper, false);
            if (parentMenu == null) {
                return SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
            } else {
                return parentMenu.getMenuPids() + SymbolConstant.LEFT_SQUARE_BRACKETS + menuParentId + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
            }
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

    private SysMenu querySysMenu(SysMenuRequest sysMenuRequest) {
        SysMenu sysMenu = this.getById(sysMenuRequest.getMenuId());
        if (ObjectUtil.isEmpty(sysMenu)) {
            throw new ServiceException(SysMenuExceptionEnum.SYS_MENU_NOT_EXISTED);
        }
        return sysMenu;
    }

}
