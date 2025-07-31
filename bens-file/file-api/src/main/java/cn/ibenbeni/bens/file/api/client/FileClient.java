package cn.ibenbeni.bens.file.api.client;

import cn.ibenbeni.bens.file.api.pojo.s3.FilePreSignatureUrlRespDTO;

/**
 * 文件客户端
 */
public interface FileClient {

    /**
     * 获取文件客户端ID
     */
    Long getClientId();

    /**
     * 获取文件客户端编码
     */
    String getClientCode();

    /**
     * 上传文件
     *
     * @param fileContent      文件内容
     * @param fileRelativePath 文件相对路径
     * @param fileType         文件类型
     * @return 完整路径，即HTTP访问地址
     * @throws Exception 上传文件时，抛出Exception异常
     */
    String upload(byte[] fileContent, String fileRelativePath, String fileType) throws Exception;

    /**
     * 删除文件
     *
     * @param fileRelativePath 文件相对路径
     * @throws Exception 删除文件时，抛出Exception异常
     */
    void delete(String fileRelativePath) throws Exception;

    /**
     * 获取文件内容
     *
     * @param fileRelativePath 文件相对路径
     * @return 文件内容
     */
    byte[] getContent(String fileRelativePath) throws Exception;

    /**
     * 获取文件预签名地址
     *
     * @param fileRelativePath 文件相对路径
     * @return 文件预签名地址
     */
    default FilePreSignatureUrlRespDTO getPreSignatureFileUrl(String fileRelativePath) throws Exception {
        throw new UnsupportedOperationException("不支持的操作");
    }

}
