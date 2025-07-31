package cn.ibenbeni.bens.file.api.client.local;

import cn.hutool.core.io.FileUtil;
import cn.ibenbeni.bens.file.api.client.AbstractFileClient;

import java.io.File;

/**
 * 本地文件客户端
 */
public class LocalFileClient extends AbstractFileClient<LocalFileClientConfig> {

    public LocalFileClient(Long clientId, String clientCode, LocalFileClientConfig config) {
        super(clientId, clientCode, config);
    }

    @Override
    protected void doInit() {
    }

    @Override
    public String upload(byte[] fileContent, String fileRelativePath, String fileType) throws Exception {
        String fileAbsolutePath = getFilePath(fileRelativePath);
        FileUtil.writeBytes(fileContent, fileAbsolutePath);
        return super.formatFileUrl(config.getDomain(), fileRelativePath);
    }

    @Override
    public void delete(String fileRelativePath) throws Exception {
        String fileAbsolutePath = getFilePath(fileRelativePath);
        FileUtil.del(fileAbsolutePath);
    }

    @Override
    public byte[] getContent(String fileRelativePath) throws Exception {
        String fileAbsolutePath = getFilePath(fileRelativePath);
        return FileUtil.readBytes(fileAbsolutePath);
    }

    private String getFilePath(String fileRelativePath) {
        return config.getBasePath() + File.separator + fileRelativePath;
    }

}
