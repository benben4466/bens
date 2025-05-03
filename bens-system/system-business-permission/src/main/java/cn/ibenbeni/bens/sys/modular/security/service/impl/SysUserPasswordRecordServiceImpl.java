package cn.ibenbeni.bens.sys.modular.security.service.impl;

import cn.ibenbeni.bens.sys.modular.security.entity.SysUserPasswordRecord;
import cn.ibenbeni.bens.sys.modular.security.mapper.SysUserPasswordRecordMapper;
import cn.ibenbeni.bens.sys.modular.security.service.SysUserPasswordRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户历史密码记录业务实现层
 *
 * @author benben
 * @date 2025/5/3  下午4:22
 */
@Service
public class SysUserPasswordRecordServiceImpl extends ServiceImpl<SysUserPasswordRecordMapper, SysUserPasswordRecord> implements SysUserPasswordRecordService {

    @Override
    public void add(SysUserPasswordRecord sysUserPasswordRecord) {
        this.save(sysUserPasswordRecord);
    }

    @Override
    public List<SysUserPasswordRecord> getRecentRecords(Long userId, Integer count) {
        if (count == null || count.equals(0)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysUserPasswordRecord> queryWrapper = Wrappers.lambdaQuery(SysUserPasswordRecord.class)
                .eq(SysUserPasswordRecord::getUserId, userId);
        Page<SysUserPasswordRecord> recordPage = new Page<>(1, count);
        Page<SysUserPasswordRecord> page = this.page(recordPage, queryWrapper);

        List<SysUserPasswordRecord> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            return records;
        }
        return new ArrayList<>();
    }

}
