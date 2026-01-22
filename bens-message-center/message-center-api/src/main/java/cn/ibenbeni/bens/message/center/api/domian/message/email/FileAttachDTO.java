package cn.ibenbeni.bens.message.center.api.domian.message.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件附件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachDTO {

    /**
     * 文件名称
     * <p>如：invoice.pdf</p>
     */
    private String fileName;

    /**
     * 文件 URL
     * <p>如：https://oss.aliyun.com/...</p>
     */
    private String fileUrl;

}
