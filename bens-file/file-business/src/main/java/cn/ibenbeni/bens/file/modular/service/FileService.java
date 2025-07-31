package cn.ibenbeni.bens.file.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.FileApi;
import cn.ibenbeni.bens.file.modular.entity.FileDO;
import cn.ibenbeni.bens.file.modular.pojo.request.FileCreateReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FilePageReq;
import cn.ibenbeni.bens.file.modular.pojo.response.FilePreSignatureUrlResp;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * 文件信息-服务类
 *
 * @author: benben
 * @time: 2025/6/20 下午5:02
 */
public interface FileService extends IService<FileDO>, FileApi {

    /**
     * 创建文件
     *
     * @return 文件ID
     */
    Long createFile(FileCreateReq req);

    /**
     * 保存文件
     *
     * @param fileContent   文件内容
     * @param fileName      文件名称;可为空
     * @param fileDirectory 文件目录;可为空
     * @param fileType      文件MIME类型;可为空
     * @return 文件访问路径
     */
    String createFile(@NotEmpty(message = "文件内容不能为空") byte[] fileContent, String fileName, String fileDirectory, String fileType);

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     */
    void deleteFile(Long fileId) throws Exception;

    /**
     * 批量删除文件
     *
     * @param fileIdSet 文件ID集合
     */
    void deleteFile(Set<Long> fileIdSet) throws Exception;

    /**
     * 获取文件内容
     *
     * @param fileConfigCode 文件配置编码
     * @param filePath       文件路径
     * @return 文件内容
     */
    byte[] getFileContent(String fileConfigCode, String filePath) throws Exception;

    /**
     * 获取文件分页列表
     */
    PageResult<FileDO> getFilePage(FilePageReq req);

    /**
     * 获取文件预签名地址
     *
     * @param fileName      文件名称
     * @param fileDirectory 文件目录
     * @return 文件预签名地址
     */
    FilePreSignatureUrlResp getPreSignatureFileUrl(String fileName, String fileDirectory);

}
