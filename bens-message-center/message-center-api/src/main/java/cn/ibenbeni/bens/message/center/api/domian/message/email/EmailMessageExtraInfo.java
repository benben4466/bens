package cn.ibenbeni.bens.message.center.api.domian.message.email;

import cn.ibenbeni.bens.message.center.api.domian.message.SendMessageExtraInfo;
import lombok.*;

import java.util.List;

/**
 * 发送消息扩展信息-邮件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailMessageExtraInfo extends SendMessageExtraInfo {

    /**
     * 接收账号
     */
    private List<String> recipientAccounts;

    /**
     * 抄送者账号
     */
    private List<String> ccAccounts;

    /**
     * 密送账号
     */
    private List<String> bccAccounts;

    /**
     * 文件附件
     */
    List<FileAttachDTO> attachs;

    /**
     * 附件压缩包名称
     * <p>若填写此名称，则自动进行文件压缩。压缩文件为 attachs 集合的内容。</p>
     */
    private String fileZipName;

}
