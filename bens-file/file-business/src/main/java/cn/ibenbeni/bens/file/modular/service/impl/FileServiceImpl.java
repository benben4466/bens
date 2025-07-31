package cn.ibenbeni.bens.file.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.client.FileClient;
import cn.ibenbeni.bens.file.api.exception.FileException;
import cn.ibenbeni.bens.file.api.exception.enums.FileExceptionEnum;
import cn.ibenbeni.bens.file.api.pojo.s3.FilePreSignatureUrlRespDTO;
import cn.ibenbeni.bens.file.api.util.FileTypeUtils;
import cn.ibenbeni.bens.file.modular.entity.FileDO;
import cn.ibenbeni.bens.file.modular.mapper.FileMapper;
import cn.ibenbeni.bens.file.modular.pojo.file.GenerateUploadPathResult;
import cn.ibenbeni.bens.file.modular.pojo.request.FileCreateReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FilePageReq;
import cn.ibenbeni.bens.file.modular.pojo.response.FilePreSignatureUrlResp;
import cn.ibenbeni.bens.file.modular.service.FileConfigService;
import cn.ibenbeni.bens.file.modular.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static cn.hutool.core.date.DatePattern.PURE_DATE_PATTERN;

/**
 * 文件信息-服务实现类
 *
 * @author: benben
 * @time: 2025/6/20 下午5:02
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDO> implements FileService {

    // region 属性

    /**
     * 路径前缀-日期-是否启用
     *
     * <p>按照日期进行分目录, 目录格式: yyyyMMdd</p>
     */
    private static boolean PATH_PREFIX_DATE_ENABLE = true;

    /**
     * 上传文件的后缀，是否包含时间戳
     * <p>保证唯一性，避免覆盖</p>
     */
    private static boolean PATH_SUFFIX_TIMESTAMP_ENABLE = true;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private FileConfigService fileConfigService;

    // endregion

    // region 公共方法

    @Override
    public Long createFile(FileCreateReq req) {
        // 校验文件配置是否存在
        fileConfigService.validateFileConfigExists(req.getFileConfigCode());

        FileDO file = BeanUtil.toBean(req, FileDO.class);
        if (StrUtil.isNotBlank(req.getFileOriginName())) {
            String fileSuffix = FileUtil.extName(req.getFileOriginName());
            if (StrUtil.isBlank(fileSuffix)) {
                fileSuffix = FileTypeUtils.getFileSuffix(file.getFileType());
            }
            file.setFileSuffix(fileSuffix);
        }
        file.setFileObjectName(StrUtil.subAfter(req.getFileUrl(), StrPool.SLASH,true));

        save(file);
        return file.getFileId();
    }

    @SneakyThrows
    @Override
    public String createFile(@NotEmpty(message = "文件内容不能为空") byte[] fileContent, String fileName, String fileDirectory, String fileType) {
        String fileOriginName = fileName;
        // 若文件类型为空,则推断文件类型
        if (StrUtil.isBlank(fileType)) {
            fileType = FileTypeUtils.inferMineType(fileContent, fileName);
        }
        // 若文件名称为空,则使用文件内容生成的SHA-256哈希值字符串作为文件名称
        if (StrUtil.isBlank(fileName)) {
            fileName = DigestUtil.sha256Hex(fileContent);
        }
        // 若文件名称无后缀,则补充文件后缀
        String fileSuffix = null;
        if (StrUtil.isEmpty(FileUtil.extName(fileName))) {
            fileSuffix = FileTypeUtils.getFileSuffix(fileType);
            if (StrUtil.isNotBlank(fileSuffix)) {
                fileName += fileSuffix;
            }
        } else {
            fileSuffix = FileUtil.extName(fileName);
        }


        // 文件路径,保证唯一
        GenerateUploadPathResult uploadPathResult = generateUploadPath(fileName, fileDirectory);
        FileClient fileClient = fileConfigService.getMasterFileClient();
        Assert.notNull(fileClient, "客户端(master) 不能为空");
        // 上传文件
        String fileUrl = fileClient.upload(fileContent, uploadPathResult.getFileUploadPath(), fileType);
        // 计算文件大小kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(fileContent.length), BigDecimal.valueOf(1024)).setScale(0, BigDecimal.ROUND_HALF_UP));

        // 存储文件信息
        FileDO file = FileDO.builder()
                .fileConfigCode(fileClient.getClientCode())
                .fileOriginName(fileOriginName)
                .fileObjectName(uploadPathResult.getFileObjectName())
                .filePath(uploadPathResult.getFileUploadPath())
                .fileUrl(fileUrl)
                .fileSizeKb(fileSizeKb)
                .fileType(fileType)
                .fileSuffix(fileSuffix)
                .build();
        save(file);
        return fileUrl;
    }

    @Override
    public void deleteFile(Long fileId) throws Exception {
        // 校验文件是否存在
        FileDO file = validateFileExists(fileId);
        // 从文件存储器中删除
        FileClient fileClient = fileConfigService.getFileClient(file.getFileConfigCode());
        Assert.notNull(fileClient, "客户端({}) 不能为空", file.getFileConfigCode());
        fileClient.delete(file.getFilePath());

        removeById(fileId);
    }

    @Override
    public void deleteFile(Set<Long> fileIdSet) throws Exception {
        List<FileDO> fileList = listByIds(fileIdSet);
        for (FileDO file : fileList) {
            FileClient fileClient = fileConfigService.getFileClient(file.getFileConfigCode());
            Assert.notNull(fileClient, "客户端({})不能为空, 文件路径:{}", file.getFileConfigCode(), file.getFilePath());
            // 删除文件
            fileClient.delete(file.getFilePath());
        }
        // 删除键记录
        removeByIds(fileIdSet);
    }

    @Override
    public byte[] getFileContent(String fileConfigCode, String filePath) throws Exception {
        FileClient fileClient = fileConfigService.getFileClient(fileConfigCode);
        Assert.notNull(fileClient, "客户端({}) 不能为空", fileClient.getClientCode());
        return fileClient.getContent(filePath);
    }

    @Override
    public PageResult<FileDO> getFilePage(FilePageReq req) {
        return fileMapper.selectPage(req);
    }

    @SneakyThrows
    @Override
    public FilePreSignatureUrlResp getPreSignatureFileUrl(String fileName, String fileDirectory) {
        // 上传路径
        GenerateUploadPathResult uploadPathResult = generateUploadPath(fileName, fileDirectory);

        FileClient fileClient = fileConfigService.getMasterFileClient();
        FilePreSignatureUrlRespDTO preSignatureFileUrl = fileClient.getPreSignatureFileUrl(uploadPathResult.getFileUploadPath());

        FilePreSignatureUrlResp preSignatureUrlResp = BeanUtil.toBean(preSignatureFileUrl, FilePreSignatureUrlResp.class);
        preSignatureUrlResp.setFilePath(uploadPathResult.getFileUploadPath());
        preSignatureUrlResp.setFileConfigId(fileClient.getClientId());
        preSignatureUrlResp.setFileConfigCode(fileClient.getClientCode());
        return preSignatureUrlResp;
    }

    // endregion

    // region 私有方法

    /**
     * 生成上传路径
     *
     * @param fileName      文件名称
     * @param fileDirectory 文件目录
     * @return 上传路径
     */
    private GenerateUploadPathResult generateUploadPath(String fileName, String fileDirectory) {
        GenerateUploadPathResult result = new GenerateUploadPathResult();

        // 生成前后缀
        String prefix = null;
        if (PATH_PREFIX_DATE_ENABLE) {
            prefix = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), PURE_DATE_PATTERN);
        }
        String suffix = null;
        if (PATH_SUFFIX_TIMESTAMP_ENABLE) {
            suffix = String.valueOf(System.currentTimeMillis());
        }

        // 拼接suffix
        if (StrUtil.isNotBlank(suffix)) {
            String ext = FileUtil.extName(fileName);
            if (StrUtil.isNotBlank(ext)) {
                fileName = FileUtil.mainName(fileName) + StrUtil.C_UNDERLINE + suffix + StrUtil.DOT + ext;
            } else {
                fileName = fileName + StrUtil.C_UNDERLINE + suffix;
            }
        }
        result.setFileObjectName(fileName);

        // 拼接prefix
        if (StrUtil.isNotBlank(prefix)) {
            fileName = prefix + StrUtil.C_SLASH + fileName;
        }
        // 拼接目录
        if (StrUtil.isNotBlank(fileDirectory)) {
            fileName = fileDirectory + StrUtil.C_SLASH + fileName;
        }

        result.setFileUploadPath(fileName);
        return result;
    }

    private FileDO validateFileExists(Long fileId) {
        FileDO file = getById(fileId);
        if (file == null) {
            throw new FileException(FileExceptionEnum.FILE_NOT_EXISTED);
        }
        return file;
    }

    // endregion

}
