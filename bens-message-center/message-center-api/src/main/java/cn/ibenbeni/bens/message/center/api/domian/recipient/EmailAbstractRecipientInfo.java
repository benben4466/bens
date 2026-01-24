package cn.ibenbeni.bens.message.center.api.domian.recipient;

import cn.ibenbeni.bens.message.center.api.domian.message.email.FileAttachDTO;
import lombok.*;

import java.util.List;

/**
 * 收件人信息-邮件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailAbstractRecipientInfo extends AbstractRecipientInfo {

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
