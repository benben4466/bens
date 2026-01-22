package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateContentDTO;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateDTO;

import java.util.List;

/**
 * 消息模板查询 API 接口
 */
public interface MessageTemplateApi {

    /**
     * 根据模板编码查询模板信息
     *
     * @param templateCode 模板编码
     * @return 模板信息
     */
    MessageTemplateDTO getByCode(String templateCode);

    /**
     * 根据模板ID查询模板信息
     *
     * @param templateId 模板ID
     * @return 模板信息
     */
    MessageTemplateDTO getById(Long templateId);

    /**
     * 查询指定模板和渠道的内容配置
     *
     * @param templateId  模板ID
     * @param channelType 渠道类型
     * @return 模板内容
     */
    MessageTemplateContentDTO getContentByTemplateAndChannel(Long templateId, Integer channelType);

    /**
     * 查询模板的所有渠道内容配置
     *
     * @param templateId 模板ID
     * @return 模板内容列表
     */
    List<MessageTemplateContentDTO> listContentsByTemplateId(Long templateId);

    /**
     * 检查模板是否可用（存在、启用、已审核）
     *
     * @param templateCode 模板编码
     * @return 是否可用
     */
    boolean checkTemplateAvailable(String templateCode);

}
