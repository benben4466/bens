package cn.ibenbeni.bens.sys.modular.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.enums.exception.SysMenuOptionsExceptionEnum;
import cn.ibenbeni.bens.sys.modular.menu.factory.MenuOptionsValidateFactory;
import cn.ibenbeni.bens.sys.modular.menu.mapper.SysMenuOptionsMapper;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuOptionsRequest;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单下的功能操作服务实现类
 *
 * @author: benben
 * @time: 2025/6/2 上午9:53
 */
@Service
public class SysMenuOptionsServiceImpl extends ServiceImpl<SysMenuOptionsMapper, SysMenuOptions> implements SysMenuOptionsService {

    @Resource(name = "menuCodeCache")
    private CacheOperatorApi<String> menuCodeCache;

    @Override
    public void add(SysMenuOptionsRequest sysMenuOptionsRequest) {
        // 校验参数合法性
        MenuOptionsValidateFactory.validateMenuOptionsParam(sysMenuOptionsRequest);

        SysMenuOptions sysMenuOptions = BeanUtil.toBean(sysMenuOptionsRequest, SysMenuOptions.class);
        this.save(sysMenuOptions);

        // TODO 记录日志
    }

    @Override
    public void del(SysMenuOptionsRequest sysMenuOptionsRequest) {
        this.removeById(sysMenuOptionsRequest.getMenuOptionId());

        // TODO 删除角色绑定的功能关联
        // TODO 发布菜单功能的更新事件
    }

    @Override
    public void edit(SysMenuOptionsRequest sysMenuOptionsRequest) {
        // 校验参数合法性
        MenuOptionsValidateFactory.validateMenuOptionsParam(sysMenuOptionsRequest);

        SysMenuOptions dbMenuOptions = this.queryMenuOptions(sysMenuOptionsRequest);
        BeanUtil.copyProperties(sysMenuOptionsRequest, dbMenuOptions);
        this.updateById(dbMenuOptions);

        // TODO 发布菜单功能的更新事件
    }

    @Override
    public SysMenuOptions detail(SysMenuOptionsRequest sysMenuOptionsRequest) {
        return this.queryMenuOptions(sysMenuOptionsRequest);
    }

    @Override
    public List<SysMenuOptions> findList(SysMenuOptionsRequest sysMenuOptionsRequest) {
        LambdaQueryWrapper<SysMenuOptions> queryWrapper = this.createWrapper(sysMenuOptionsRequest);
        // 只查询有用字段
        queryWrapper.select(SysMenuOptions::getOptionName, SysMenuOptions::getOptionCode, SysMenuOptions::getMenuId,
                SysMenuOptions::getMenuOptionId);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<SysMenuOptions> findPage(SysMenuOptionsRequest sysMenuOptionsRequest) {
        LambdaQueryWrapper<SysMenuOptions> queryWrapper = this.createWrapper(sysMenuOptionsRequest);

        // 只查询有用字段
        queryWrapper.select(SysMenuOptions::getOptionName, SysMenuOptions::getOptionCode, SysMenuOptions::getMenuId,
                SysMenuOptions::getMenuOptionId);

        Page<SysMenuOptions> sysMenuOptionsPage = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(sysMenuOptionsPage);
    }

    @Override
    public List<SysMenuOptions> getTotalMenuOptionsList() {
        return this.getTotalMenuOptionsList(null);
    }

    @Override
    public List<SysMenuOptions> getTotalMenuOptionsList(Set<Long> roleLimitMenuIdsAndOptionIds) {
        LambdaQueryWrapper<SysMenuOptions> queryWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .select(SysMenuOptions::getMenuOptionId, SysMenuOptions::getMenuId, SysMenuOptions::getOptionName)
                .in(CollUtil.isEmpty(roleLimitMenuIdsAndOptionIds), SysMenuOptions::getMenuOptionId, roleLimitMenuIdsAndOptionIds);
        return this.list(queryWrapper);
    }

    @Override
    public List<String> getOptionsCodeList(List<Long> optionsIdList) {
        List<String> result = new ArrayList<>();
        if (ObjectUtil.isEmpty(optionsIdList)) {
            return result;
        }

        for (Long optionsId : optionsIdList) {

            // 先从缓存获取是否有对应的编码
            String cachedCode = menuCodeCache.get(optionsId.toString());
            if (StrUtil.isNotBlank(cachedCode)) {
                result.add(cachedCode);
                continue;
            }

            // 缓存没有，从数据库查询功能的编码
            LambdaQueryWrapper<SysMenuOptions> queryWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                    .eq(SysMenuOptions::getMenuOptionId, optionsId)
                    .select(SysMenuOptions::getOptionCode);
            SysMenuOptions dbMenuOptions = this.getOne(queryWrapper, false);
            if (dbMenuOptions == null) {
                continue;
            }
            result.add(dbMenuOptions.getOptionCode());
            // 添加到缓存中一份
            menuCodeCache.put(optionsId.toString(), dbMenuOptions.getOptionCode(), SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        }

        return result;
    }

    private SysMenuOptions queryMenuOptions(SysMenuOptionsRequest sysMenuOptionsRequest) {
        LambdaQueryWrapper<SysMenuOptions> queryWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .eq(SysMenuOptions::getMenuOptionId, sysMenuOptionsRequest.getMenuOptionId());
        SysMenuOptions sysMenuOptions = this.getOne(queryWrapper, false);
        if (sysMenuOptions == null) {
            throw new ServiceException(SysMenuOptionsExceptionEnum.SYS_MENU_OPTIONS_NOT_EXISTED);
        }
        return sysMenuOptions;
    }

    /**
     * 创建查询wrapper
     */
    private LambdaQueryWrapper<SysMenuOptions> createWrapper(SysMenuOptionsRequest sysMenuOptionsRequest) {
        return Wrappers.lambdaQuery(SysMenuOptions.class)
                .eq(ObjectUtil.isNotEmpty(sysMenuOptionsRequest.getMenuId()), SysMenuOptions::getMenuId, sysMenuOptionsRequest.getMenuId());
    }

    @Override
    public void removeMenuAction(Set<Long> beRemovedMenuIdList) {
        LambdaQueryWrapper<SysMenuOptions> queryWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .in(CollUtil.isNotEmpty(beRemovedMenuIdList), SysMenuOptions::getMenuId, beRemovedMenuIdList);
        this.remove(queryWrapper);
    }

}
