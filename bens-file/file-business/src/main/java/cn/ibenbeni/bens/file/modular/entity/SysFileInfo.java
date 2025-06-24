package cn.ibenbeni.bens.file.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件信息实体
 *
 * @author: benben
 * @time: 2025/6/20 下午4:38
 */
@TableName(value = "sys_file_info", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFileInfo extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private Long fileId;

    /**
     * 文件存储位置
     */
    @TableField(value = "file_location")
    private Integer fileLocation;

    /**
     * 文件存储桶
     * <p>别名：文件夹</p>
     */
    @TableField("file_bucket")
    private String fileBucket;

    /**
     * 文件原始名称
     * <p>上传时候的文件名，如 file.txt</p>
     */
    @TableField(value = "file_origin_name")
    private String fileOriginName;

    /**
     * 文件后缀
     * <p>如：txt</p>
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * 存储桶中文件名称
     * <p>名称格式:主键ID+.后缀</p>
     */
    @TableField("file_object_name")
    private String fileObjectName;

    /**
     * 文件存储路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小(单位：KB)
     */
    @TableField("file_size_kb")
    private Long fileSizeKb;

    /**
     * 可读格式文件大小
     * <p>如88.9KB, 4.56MB等</p>
     */
    @TableField("readable_file_size")
    private String readableFileSize;

    // -----------------------------------------------------非实体字段-------------------------------------------------
    // region 非实体字段

    /**
     * 文件访问的URL
     */
    @TableField(exist = false)
    private String fileUrl;

    // endregion

}
