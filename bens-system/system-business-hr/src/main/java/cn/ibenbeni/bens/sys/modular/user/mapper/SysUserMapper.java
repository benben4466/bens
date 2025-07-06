package cn.ibenbeni.bens.sys.modular.user.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserPageReqVO;

import java.util.List;

/**
 * 系统用户 Mapper 接口
 *
 * @author benben
 * @date 2025/4/19  下午1:24
 */
public interface SysUserMapper extends BaseMapperX<SysUserDO> {

    default SysUserDO selectByAccount(String account) {
        return selectOne(SysUserDO::getAccount, account);
    }

    default List<SysUserDO> listByAccount(String account) {
        return selectList(SysUserDO::getAccount, account);
    }

    default PageResult<SysUserDO> selectPage(UserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysUserDO>()
                .likeIfPresent(SysUserDO::getAccount, reqVO.getAccount())
                .likeIfPresent(SysUserDO::getPhone, reqVO.getPhone())
                .eqIfPresent(SysUserDO::getStatusFlag, reqVO.getStatusFlag())
                .betweenIfPresent(SysUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SysUserDO::getUserSort)
                .orderByDesc(SysUserDO::getUserId)
        );
    }

}
