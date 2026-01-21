package cn.ibenbeni.bens.message.center.modular.biz.notify.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyTemplateSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

/**
 * 站内信-服务
 */
public interface NotifyTemplateService extends IService<NotifyTemplateDO> {

    /**
     * 创建站内信模版
     *
     * @return 模版ID
     */
    Long createNotifyTemplate(@Valid NotifyTemplateSaveReq saveReq);

    /**
     * 删除站内信模版
     *
     * @param templateId 模版ID
     */
    void deleteNotifyTemplate(Long templateId);

    /**
     * 批量删除站内信模版
     *
     * @param templateIdSet 模版ID集合
     */
    void deleteNotifyTemplate(Set<Long> templateIdSet);

    /**
     * 修改站内信模版
     */
    void updateNotifyTemplate(@Valid NotifyTemplateSaveReq updateReq);

    /**
     * 获取站内信模版
     *
     * @param templateId 模版ID
     */
    NotifyTemplateDO getNotifyTemplate(Long templateId);

    /**
     * 根据模版编码获取站内信模版
     *
     * @param templateCode 模版编码
     */
    NotifyTemplateDO getNotifyTemplateByCode(String templateCode);

    /**
     * 获取站内信模版分页列表
     */
    PageResult<NotifyTemplateDO> pageNotifyTemplate(NotifyTemplatePageReq pageReq);

    /**
     * 格式化站内信模版内容
     *
     * @param templateContent 模版内容
     * @param params          参数
     * @return 格式化后的内容
     */
    String formatNotifyTemplateContent(String templateContent, Map<String, Object> params);

}
