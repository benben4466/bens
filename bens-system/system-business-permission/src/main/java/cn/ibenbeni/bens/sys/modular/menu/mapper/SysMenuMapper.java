package cn.ibenbeni.bens.sys.modular.menu.mapper;

import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 系统菜单Mapper接口
 *
 * @author: benben
 * @time: 2025/6/1 上午10:50
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据菜单ID获取子菜单ID集合，不含orgId
     * <p>自动忽略顶级父节点，即忽略-1</p>
     *
     * @param menuId 菜单ID
     * @return 父机构ID集合
     */
    Set<Long> getChildIdsByMenuId(@Param("menuId") Long menuId);

}
