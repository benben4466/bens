package cn.ibenbeni.bens.message.center.modular.biz.message.mapper;

import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendTaskDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息发送任务 Mapper
 */
@Mapper
public interface MessageSendTaskMapper extends BaseMapper<MessageSendTaskDO> {
}
