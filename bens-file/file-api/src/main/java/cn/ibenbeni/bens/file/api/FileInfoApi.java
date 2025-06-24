package cn.ibenbeni.bens.file.api;

import cn.ibenbeni.bens.file.api.pojo.request.SysFileInfoRequest;

/**
 * 文件信息API
 *
 * @author: benben
 * @time: 2025/6/20 下午5:03
 */
public interface FileInfoApi {

    /**
     * 获取文件下载地址，生成外网地址
     *
     * @param fileId 文件ID
     * @return 外部系统可以直接访问的URL
     */
    String getFileUrl(Long fileId);

    /**
     * 删除文件信息（不恢复）
     */
    void deleteReally(SysFileInfoRequest sysFileInfoRequest);

}
