package cn.ibenbeni.bens.file.api.pojo.response;

import lombok.Data;

import java.util.Date;

/**
 * 文件信息响应
 *
 * @author: benben
 * @time: 2025/6/20 下午5:04
 */
@Data
public class SysFileInfoResponse {

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 文件后缀
     * <p>如：txt</p>
     */
    private String fileSuffix;

    /**
     * 文件内容字节
     */
    private byte[] fileBytes;

    /**
     * 文件存储位置
     * <p>1=本地存储；2=MinIO；3=腾讯云COS；4=阿里云OSS；</p>
     */
    private Integer fileLocation;

    /**
     * 文件原始名称
     * <p>上传时候的文件名</p>
     */
    private String fileOriginName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件访问的路径
     */
    private String fileUrl;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 上传用户ID
     */
    private Long uploadUserId;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 文件MD5值
     */
    private String fileMd5;

}
