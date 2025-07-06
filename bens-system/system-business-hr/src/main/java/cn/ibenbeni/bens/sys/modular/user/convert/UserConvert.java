package cn.ibenbeni.bens.sys.modular.user.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserRespVO;

import java.util.List;

/**
 * 用户转换类
 *
 * @author: benben
 * @time: 2025/7/6 下午3:00
 */
public class UserConvert {

    /**
     * 集合转换
     * <p>将 {@link SysUser} 转换为 {@link UserRespVO}</p>
     *
     * @param list 待转换集合
     */
    public static List<UserRespVO> convertList(List<SysUserDO> list) {
        return CollectionUtils.convertList(list, UserConvert::convert);
    }

    /**
     * 将 {@link SysUserDO} 转换为 {@link UserRespVO}
     */
    public static UserRespVO convert(SysUserDO user) {
        return BeanUtil.toBean(user, UserRespVO.class);
    }

}
