package cn.ibenbeni.bens.file.api.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文件信息封装操作请求
 *
 * @author: benben
 * @time: 2025/6/20 下午5:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFileInfoRequest extends BaseRequest {

    @NotNull(message = "文件ID不能为空", groups = {detail.class, delete.class})
    private Long fileId;

    /**
     * 文件存储位置
     * <p>1=本地存储；2=MinIO；3=腾讯云COS；4=阿里云OSS；</p>
     */
    private Integer fileLocation;

    /**
     * 文件存储桶
     * <p>别名：文件夹</p>
     */
    private String fileBucket;

    /**
     * 文件原始名称
     * <p>上传时候的文件名，如 file.txt</p>
     */
    private String fileOriginName;

    /**
     * 文件后缀
     * <p>如：txt</p>
     */
    private String fileSuffix;

    /**
     * 存储桶中文件名称
     * <p>名称格式:主键ID+.后缀</p>
     */
    private String fileObjectName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件大小(单位：KB)
     */
    private Long fileSizeKb;

    /**
     * 文件ID集合
     * <p>集合操作</p>
     */
    private List<Long> fileIdList;

}
