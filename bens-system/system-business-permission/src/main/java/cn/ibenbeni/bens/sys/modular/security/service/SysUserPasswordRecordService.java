package cn.ibenbeni.bens.sys.modular.security.service;

import cn.ibenbeni.bens.sys.modular.security.entity.SysUserPasswordRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户历史密码记录服务类
 *
 * @author benben
 * @date 2025/5/3  下午4:20
 */
public interface SysUserPasswordRecordService extends IService<SysUserPasswordRecord> {

    /**
     * 新增
     */
    void add(SysUserPasswordRecord sysUserPasswordRecord);

    /**
     * 获取最近几次的密码记录
     *
     * @param userId 用户ID
     * @param count  记录数量
     */
    List<SysUserPasswordRecord> getRecentRecords(Long userId, Integer count);

}
