package cn.ibenbeni.bens.file.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.client.FileClient;
import cn.ibenbeni.bens.file.api.client.FileClientConfig;
import cn.ibenbeni.bens.file.api.enums.FileStorageEnum;
import cn.ibenbeni.bens.file.api.exception.FileException;
import cn.ibenbeni.bens.file.api.exception.enums.FileExceptionEnum;
import cn.ibenbeni.bens.file.modular.entity.FileConfigDO;
import cn.ibenbeni.bens.file.modular.helper.FileClientHelper;
import cn.ibenbeni.bens.file.modular.mapper.FileConfigMapper;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigPageReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigSaveReq;
import cn.ibenbeni.bens.file.modular.service.FileConfigService;
import cn.ibenbeni.bens.validator.api.util.ValidationUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class FileConfigServiceImpl extends ServiceImpl<FileConfigMapper, FileConfigDO> implements FileConfigService {

    // region 属性

    /**
     * 校验器
     */
    @Resource
    private Validator validator;

    @Resource
    private FileConfigMapper fileConfigMapper;

    // endregion

    // region 公共方法

    @Override
    public Long createFileConfig(FileConfigSaveReq req) {
        // 校验文件配置编码是否唯一
        validateFileConfigCodeUnique(null, req.getFileConfigCode());

        FileConfigDO fileConfig = BeanUtil.toBean(req, FileConfigDO.class);
        // 没有主文件配置，则设置为主文件配置，否则默认非主文件配置
        if (getMasterFileConfig() == null) {
            fileConfig.setMasterFlag(Boolean.TRUE);
        } else {
            fileConfig.setMasterFlag(Boolean.FALSE);
        }
        fileConfig.setStorageConfig(parseClientConfig(req.getFileStorage(), req.getConfig()));

        save(fileConfig);
        return fileConfig.getFileConfigId();
    }

    @Override
    public void deleteFileConfig(Long fileConfigId) {
        // 校验文件配置是否存在
        FileConfigDO fileConfig = validateFileConfigExists(fileConfigId);
        if (Boolean.TRUE.equals(fileConfig.getMasterFlag())) {
            throw new FileException(FileExceptionEnum.FILE_CONFIG_DELETE_FAIL_MASTER);
        }

        removeById(fileConfigId);
        clearCache(fileConfig.getFileConfigCode(), null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFileConfig(Set<Long> fileConfigIdSet) {
        List<FileConfigDO> fileConfigList = listByIds(fileConfigIdSet);
        // 检查是否存在删除主配置文件
        fileConfigList.forEach(fileConfig -> {
            if (Boolean.TRUE.equals(fileConfig.getMasterFlag())) {
                throw new FileException(FileExceptionEnum.FILE_CONFIG_DELETE_FAIL_MASTER);
            }
        });

        removeByIds(fileConfigIdSet);

        // 删除缓存
        fileConfigList.forEach(fileConfig -> clearCache(fileConfig.getFileConfigCode(), null));
    }

    @Override
    public void updateFileConfig(FileConfigSaveReq req) {
        // 校验是否存在
        FileConfigDO fileConfig = validateFileConfigExists(req.getFileConfigId());
        // 校验文件配置编码是否唯一
        validateFileConfigCodeUnique(req.getFileConfigId(), req.getFileConfigCode());

        BeanUtil.copyProperties(req, fileConfig);
        fileConfig.setStorageConfig(parseClientConfig(req.getFileStorage(), req.getConfig()));

        updateById(fileConfig);

        clearCache(fileConfig.getFileConfigCode(), null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFileConfigMaster(Long fileConfigId) {
        // 校验存在
        FileConfigDO fileConfig = validateFileConfigExists(fileConfigId);

        // 其余文件配置为非主配置
        fileConfigMapper.updateBatch(FileConfigDO.builder().masterFlag(false).build());

        // 设置当前文件配置为主文件配置
        updateById(FileConfigDO.builder().fileConfigId(fileConfigId).masterFlag(true).build());

        // 清理缓存
        clearCache(fileConfig.getFileConfigCode(), null);
    }

    @Override
    public FileConfigDO getFileConfig(Long fileConfigId) {
        return getById(fileConfigId);
    }

    @Override
    public FileConfigDO getFileConfig(String fileConfigCode) {
        return fileConfigMapper.selectByCode(fileConfigCode);
    }

    @Override
    public PageResult<FileConfigDO> getFileConfigPage(FileConfigPageReq req) {
        return fileConfigMapper.selectPage(req);
    }

    @Override
    public String testFileConfig(Long fileConfigId) throws Exception {
        // 校验文件配置是否存在
        FileConfigDO fileConfig = validateFileConfigExists(fileConfigId);
        // 读取测试文件内容
        byte[] fileContent = ResourceUtil.readBytes("file/testFileConfig.jpg");
        // 上传文件
        return getFileClient(fileConfig.getFileConfigCode()).upload(fileContent, IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
    }

    @Override
    public FileConfigDO validateFileConfigExists(String fileConfigCode) throws FileException {
        FileConfigDO fileConfig = getFileConfig(fileConfigCode);
        if (fileConfig == null) {
            throw new FileException(FileExceptionEnum.FILE_CONFIG_NOT_EXIST);
        }

        return fileConfig;
    }

    @Override
    public FileClient getFileClient(String fileConfigCode) {
        FileClient fileClient = FileClientHelper.getFileClient(fileConfigCode);
        // 若为空，从数据库重新加载
        if (fileClient == null) {
            FileConfigDO fileConfig = validateFileConfigExists(fileConfigCode);
            FileStorageEnum fileStorageEnum = FileStorageEnum.parseToEnum(fileConfig.getFileStorage());
            Assert.notNull(fileStorageEnum, String.format("文件配置存储器(%s)不符合要求", fileStorageEnum));
            FileClientHelper.createOrUpdateFileClient(fileConfig.getFileConfigId(), fileConfig.getFileConfigCode(), fileConfig.getFileStorage(), fileConfig.getStorageConfig());
            fileClient = FileClientHelper.getFileClient(fileConfigCode);
        }
        return fileClient;
    }

    @Override
    public FileClient getMasterFileClient() {
        FileConfigDO fileConfig = fileConfigMapper.selectByMasterFlag();
        Assert.notNull(fileConfig, String.format("文件配置存储器(%s)不符合要求", fileConfig.getFileConfigCode()));
        FileClientHelper.createOrUpdateFileClient(fileConfig.getFileConfigId(), fileConfig.getFileConfigCode(), fileConfig.getFileStorage(), fileConfig.getStorageConfig());
        return FileClientHelper.getFileClient(fileConfig.getFileConfigCode());
    }

    @Override
    public FileConfigDO validateFileConfigExists(Long fileConfigId) {
        FileConfigDO fileConfig = getById(fileConfigId);
        if (fileConfig == null) {
            throw new FileException(FileExceptionEnum.FILE_CONFIG_NOT_EXIST);
        }
        return fileConfig;
    }

    // endregion

    // region 私有方法

    /**
     * 校验文件配置编码是否唯一
     *
     * @param fileConfigId   文件配置ID
     * @param fileConfigCode 文件配置编码
     */
    private void validateFileConfigCodeUnique(Long fileConfigId, String fileConfigCode) {
        FileConfigDO fileConfig = fileConfigMapper.selectByCode(fileConfigCode);
        if (fileConfig == null) {
            return;
        }

        if (fileConfigId == null) {
            throw new FileException(FileExceptionEnum.FILE_CONFIG_CODE_DUPLICATE);
        }
        if (ObjectUtil.notEqual(fileConfigId, fileConfig.getFileConfigId())) {
            throw new FileException(FileExceptionEnum.FILE_CONFIG_CODE_DUPLICATE);
        }
    }

    /**
     * 将配置集合根据文件存储器类型，转为相应的实例
     *
     * @param fileStorage 文件存储器
     * @param configMap   文件配置集合
     * @return 文件配置实例
     */
    private FileClientConfig parseClientConfig(Integer fileStorage, Map<String, Object> configMap) {
        FileStorageEnum fileStorageEnum = FileStorageEnum.parseToEnum(fileStorage);
        Assert.notNull(fileStorageEnum, String.format("文件配置(%s)为空", fileStorageEnum));
        FileClientConfig fileClientConfig = JSON.parseObject(JSON.toJSONString(configMap), fileStorageEnum.getConfigClass());
        // 校验类上的验证注解
        ValidationUtils.validate(validator, fileClientConfig);
        return fileClientConfig;
    }

    /**
     * 清除缓存
     *
     * @param fileConfigCode 文件配置编码
     * @param isMaster       是否主文件配置
     */
    private void clearCache(String fileConfigCode, Boolean isMaster) {
        if (StrUtil.isNotBlank(fileConfigCode)) {
            FileClientHelper.removeFileClient(fileConfigCode);
        }

        // 若是主文件配置，则重新获取主文件配置
        if (Boolean.TRUE.equals(isMaster)) {
            getMasterFileClient();
        }
    }

    private FileConfigDO getMasterFileConfig() {
        return fileConfigMapper.selectByMasterFlag();
    }

    // endregion

}
