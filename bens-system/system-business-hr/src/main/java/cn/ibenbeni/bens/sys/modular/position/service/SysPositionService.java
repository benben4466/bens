package cn.ibenbeni.bens.sys.modular.position.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.modular.position.entity.SysPositionDO;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionPageReq;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

/**
 * 职位信息-服务类
 *
 * @author: benben
 * @time: 2025/7/12 下午1:38
 */
public interface SysPositionService extends IService<SysPositionDO> {

    /**
     * 创建职位
     */
    Long createPosition(PositionSaveReq req);

    /**
     * 删除职位
     */
    void deletePosition(Long positionId);

    /**
     * 批量删除职位
     */
    void deletePositionList(Set<Long> positionIdSet);

    /**
     * 更新职位
     */
    void updatePosition(PositionSaveReq req);

    /**
     * 获取职位
     */
    SysPositionDO getPosition(Long positionId);

    /**
     * 获取职位列表
     *
     * @param positionIds 指定职位ID集合
     */
    List<SysPositionDO> getPositionList(@Nullable Set<Long> positionIds);

    /**
     * 获取职位列表
     *
     * @param positionIds 指定职位ID集合；根据限制职位id列表查询（若为空，则查询全部）
     * @param statusFlags 职位状态集合；查询状态集合
     */
    List<SysPositionDO> getPositionList(@Nullable Set<Long> positionIds, @Nullable Set<Integer> statusFlags);

    /**
     * 获取职位分页列表
     */
    PageResult<SysPositionDO> getPositionPage(PositionPageReq reqVO);

    /**
     * 校验职位是否有效
     * <p>
     * 职位无效情况
     * 1.职位编号不存在
     * 2.职位被禁用
     * </p>
     */
    void validatePositionList(Set<Long> positionIdSet);

}
