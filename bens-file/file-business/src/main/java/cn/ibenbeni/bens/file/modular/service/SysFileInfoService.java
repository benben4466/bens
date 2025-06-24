package cn.ibenbeni.bens.file.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.FileInfoApi;
import cn.ibenbeni.bens.file.api.pojo.request.SysFileInfoRequest;
import cn.ibenbeni.bens.file.api.pojo.response.SysFileInfoResponse;
import cn.ibenbeni.bens.file.modular.entity.SysFileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息服务类
 *
 * @author: benben
 * @time: 2025/6/20 下午5:02
 */
public interface SysFileInfoService extends IService<SysFileInfo>, FileInfoApi {

    /**
     * 获取文件信息结果集
     *
     * @param fileId 文件ID
     */
    SysFileInfoResponse getFileInfoResult(Long fileId);

    /**
     * 根据附件ids查询附件信息
     * <p>分隔符: ,</p>
     *
     * @param fileIds 文件ID列表字符串
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(String fileIds);

    /**
     * 根据附件ids查询附件信息
     *
     * @param fileIdList 文件ID列表
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(List<Long> fileIdList);

    /**
     * 查看详情文件信息
     */
    SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 查询文件信息表（分页）
     */
    PageResult<SysFileInfo> page(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 上传文件，返回文件的唯一标识
     *
     * @param file               文件
     * @param sysFileInfoRequest 请求
     */
    SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest);

    /**
     * 文件下载
     *
     * @param sysFileInfoRequest 请求
     * @param response           HTTP响应
     */
    void downloadFile(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 存储文件
     *
     * @param file        文件
     * @param sysFileInfo 文件信息
     */
    void storageFile(MultipartFile file, SysFileInfo sysFileInfo);

    /**
     * 获取文件响应信息
     */
    SysFileInfoResponse getFileInfoResponse(SysFileInfo sysFileInfo);

}
