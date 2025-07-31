package cn.ibenbeni.bens.file.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.client.FileClient;
import cn.ibenbeni.bens.file.api.exception.FileException;
import cn.ibenbeni.bens.file.modular.entity.FileConfigDO;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigPageReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 文件配置-服务类
 */
public interface FileConfigService extends IService<FileConfigDO> {

    /**
     * 创建文件配置
     *
     * @return 文件配置ID
     */
    Long createFileConfig(FileConfigSaveReq req);

    /**
     * 删除文件配置
     *
     * @param fileConfigId 文件配置ID
     */
    void deleteFileConfig(Long fileConfigId);

    /**
     * 批量删除文件配置
     *
     * @param fileConfigIdSet 文件配置ID集合
     */
    void deleteFileConfig(Set<Long> fileConfigIdSet);

    /**
     * 修改文件配置
     */
    void updateFileConfig(FileConfigSaveReq req);

    /**
     * 修改文件配置为主配置
     *
     * @param fileConfigId 文件配置ID
     */
    void updateFileConfigMaster(Long fileConfigId);

    /**
     * 获取文件配置
     *
     * @param fileConfigId 文件配置ID
     */
    FileConfigDO getFileConfig(Long fileConfigId);

    /**
     * 获取文件配置
     *
     * @param fileConfigCode 文件配置编码
     */
    FileConfigDO getFileConfig(String fileConfigCode);

    /**
     * 获取文件配置分页列表
     */
    PageResult<FileConfigDO> getFileConfigPage(FileConfigPageReq req);

    /**
     * 测试文件配置是否正确
     * <p>通过上传默认文件进行测试</p>
     *
     * @param fileConfigId 文件配置ID
     * @return 文件URL
     */
    String testFileConfig(Long fileConfigId) throws Exception;

    /**
     * 校验文件配置是否存在
     *
     * @param fileConfigId 文件配置ID
     */
    FileConfigDO validateFileConfigExists(Long fileConfigId);

    /**
     * 校验文件配置是否存在
     *
     * @param fileConfigCode 文件配置编码
     */
    FileConfigDO validateFileConfigExists(String fileConfigCode) throws FileException;

    /**
     * 获取文件客户端
     *
     * @param fileConfigCode 文件配置编号
     * @return 文件客户端
     */
    FileClient getFileClient(String fileConfigCode);

    /**
     * 获取主文件客户端
     */
    FileClient getMasterFileClient();

}
