package cn.ibenbeni.bens.db.mp.dboperator;

import cn.hutool.core.convert.Convert;
import cn.ibenbeni.bens.db.api.DbOperatorApi;
import cn.ibenbeni.bens.db.api.pojo.db.DbProp;
import cn.ibenbeni.bens.db.api.util.DatabaseTypeUtil;
import cn.ibenbeni.bens.rule.enums.DbTypeEnum;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 数据库操作的实现
 *
 * @author: benben
 * @time: 2025/6/16 下午10:29
 */
@Service
public class DbOperatorImpl implements DbOperatorApi {

    @Resource
    private DbProp dbProp;

    @Override
    public DbTypeEnum getCurrentDbType() {
        return DatabaseTypeUtil.getDbType(dbProp.getUrl());
    }

    @Override
    public int selectCount(String sql, Object... args) {
        long selectCount = SqlRunner.db().selectCount(sql, args);
        return Convert.toInt(selectCount);
    }

}
