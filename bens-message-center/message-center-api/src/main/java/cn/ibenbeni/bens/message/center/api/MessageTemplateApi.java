package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateContentDTO;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateDTO;

import java.util.List;
import java.util.Set;

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
     * 根据模板编码和渠道类型查询内容配置
     *
     * @param templateCode 模板编码
     * @param channelType  渠道类型
     * @return 模板内容
     */
    MessageTemplateContentDTO getContentByTemplateCodeAndChannel(String templateCode, Integer channelType);

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
    MessageTemplateDTO checkTemplateAvailable(String templateCode);

    /**
     * 检查模板是否支持渠道
     *
     * @param channelTypes 渠道类型集合
     * @return true=支持；false=不支持；
     */
    boolean isSupportChannel(String templateCode, Set<Integer> channelTypes);

}
