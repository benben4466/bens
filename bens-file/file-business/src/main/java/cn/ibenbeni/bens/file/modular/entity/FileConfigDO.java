package cn.ibenbeni.bens.file.modular.entity;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.file.api.client.FileClientConfig;
import cn.ibenbeni.bens.file.api.client.local.LocalFileClientConfig;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.*;

import java.lang.reflect.Field;

/**
 * 文件配置表实体
 */
@TableName(value = "sys_file_config", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileConfigDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "file_config_id", type = IdType.ASSIGN_ID)
    private Long fileConfigId;

    /**
     * 文件配置名称
     */
    @TableField(value = "file_config_name")
    private String fileConfigName;

    /**
     * 文件配置编码
     */
    @TableField(value = "file_config_code")
    private String fileConfigCode;

    /**
     * 文件存储器
     * <p>枚举值: {@link cn.ibenbeni.bens.file.api.enums.FileStorageEnum}</p>
     */
    @TableField(value = "file_storage")
    private Integer fileStorage;

    /**
     * 是否主配置
     */
    @TableField(value = "master_flag")
    private Boolean masterFlag;

    /**
     * 存储配置
     */
    @TableField(value = "storage_config", typeHandler = FileClientConfigTypeHandler.class)
    private FileClientConfig storageConfig;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * FileClientConfig序列化器
     */
    public static class FileClientConfigTypeHandler extends AbstractJsonTypeHandler<Object> {

        public FileClientConfigTypeHandler(Class<?> type) {
            super(type);
        }

        public FileClientConfigTypeHandler(Class<?> type, Field field) {
            super(type, field);
        }

        @Override
        public Object parse(String json) {
            // 将JSON字符串反序列化为Java对象
            // 获取@type属性值, 再进行解析是为了防止类移动导致解析失败

            // 带有@type属性的JSON字符串, 在反序列化时可以自动识别出类，但是此处为了防止类移动导致解析失败，这里手动获取@type属性值
            // FileClientConfig fileClientConfig = JSON.parseObject(json, FileClientConfig.class);

            JSONObject jsonObj = JSON.parseObject(json);
            String className = jsonObj.getString("@type");
            className = StrUtil.subAfter(className, ".", true);
            if (LocalFileClientConfig.class.getSimpleName().equals(className)) {
                return JSON.parseObject(json, LocalFileClientConfig.class);
            }

            throw new IllegalArgumentException("未知的FileClientConfig类型: " + json);
        }

        @Override
        public String toJson(Object obj) {
            // 将Java对象序列化为JSON字符串
            // 将@type属性保持到JSON字符串中，方便反序列化时识别类
            return JSON.toJSONString(obj, JSONWriter.Feature.WriteClassName);
        }

    }

}
