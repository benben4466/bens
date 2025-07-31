package cn.ibenbeni.bens.file.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 文件信息实体
 *
 * @author: benben
 * @time: 2025/6/20 下午4:38
 */
@TableName(value = "sys_file", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private Long fileId;

    /**
     * 文件配置编码
     */
    @TableField(value = "file_config_code")
    private String fileConfigCode;

    /**
     * 文件原始名称
     * <p>上传时候的文件名，如 file.txt</p>
     */
    @TableField(value = "file_origin_name")
    private String fileOriginName;

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
     * 文件URL
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件大小(单位：KB)
     */
    @TableField("file_size_kb")
    private Long fileSizeKb;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件后缀
     * <p>如：txt</p>
     */
    @TableField("file_suffix")
    private String fileSuffix;

}
