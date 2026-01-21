package cn.ibenbeni.bens.message.center.modular.biz.notify.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.mapper.NotifyTemplateMapper;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyTemplateSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.notify.service.NotifyTemplateService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 站内信-服务实现
 */
@Slf4j
@Service
public class NotifyTemplateServiceImpl extends ServiceImpl<NotifyTemplateMapper, NotifyTemplateDO> implements NotifyTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private NotifyTemplateMapper notifyTemplateMapper;

    // region 公共方法
    // endregion

    @Override
    public Long createNotifyTemplate(NotifyTemplateSaveReq saveReq) {
        // 校验模版编码是否重复
        validateNotifyTemplateCodeDuplicate(null, saveReq.getCode());

        NotifyTemplateDO notifyTemplate = BeanUtil.toBean(saveReq, NotifyTemplateDO.class);
        notifyTemplate.setParams(parseTemplateContentParams(saveReq.getContent()));
        notifyTemplateMapper.insert(notifyTemplate);
        return 0L;
    }

    @Override
    public void deleteNotifyTemplate(Long templateId) {
        // 校验模板是否存在
        validateNotifyTemplateExists(templateId);
        notifyTemplateMapper.deleteById(templateId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteNotifyTemplate(Set<Long> templateIdSet) {
        notifyTemplateMapper.deleteByIds(templateIdSet);
    }

    @Override
    public void updateNotifyTemplate(NotifyTemplateSaveReq updateReq) {
        // 校验模板是否存在
        validateNotifyTemplateExists(updateReq.getId());
        // 校验模版编码是否重复
        validateNotifyTemplateCodeDuplicate(updateReq.getId(), updateReq.getCode());

        NotifyTemplateDO notifyTemplate = BeanUtil.toBean(updateReq, NotifyTemplateDO.class);
        notifyTemplate.setParams(parseTemplateContentParams(updateReq.getContent()));
        notifyTemplateMapper.updateById(notifyTemplate);
    }

    @Override
    public NotifyTemplateDO getNotifyTemplate(Long templateId) {
        return notifyTemplateMapper.selectById(templateId);
    }

    @Override
    public NotifyTemplateDO getNotifyTemplateByCode(String templateCode) {
        return notifyTemplateMapper.selectByCode(templateCode);
    }

    @Override
    public PageResult<NotifyTemplateDO> pageNotifyTemplate(NotifyTemplatePageReq pageReq) {
        return notifyTemplateMapper.pageNotifyTemplate(pageReq);
    }

    @Override
    public String formatNotifyTemplateContent(String templateContent, Map<String, Object> params) {
        return StrUtil.format(templateContent, params);
    }

    // region 私有方法

    private void validateNotifyTemplateCodeDuplicate(Long templateId, String templateCode) {
        NotifyTemplateDO notifyTemplate = notifyTemplateMapper.selectByCode(templateCode);
        if (notifyTemplate == null) {
            return;
        }

        if (templateId == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.NOTIFY_TEMPLATE_NOT_EXIST, templateCode);
        }
        if (!notifyTemplate.getId().equals(templateId)) {
            throw new MessageCenterException(MessageCenterExceptionEnum.NOTIFY_TEMPLATE_NOT_EXIST, templateCode);
        }
    }

    private void validateNotifyTemplateExists(Long templateId) {
        if (notifyTemplateMapper.selectById(templateId) == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.NOTIFY_TEMPLATE_NOT_EXIST);
        }
    }

    /**
     * 解析模版内容中的参数，并返回参数名称集合
     *
     * @param templateContent 模版内容
     * @return 返回参数名称集合
     */
    private List<String> parseTemplateContentParams(String templateContent) {
        // 使用正则表达式匹配 {} 中的变量, 并将其提取出来
        return ReUtil.findAllGroup1(PATTERN_PARAMS, templateContent);
    }

    // endregion

}
