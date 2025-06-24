package cn.ibenbeni.bens.file.api.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import cn.ibenbeni.bens.file.api.exception.FileException;
import cn.ibenbeni.bens.file.api.exception.enums.FileExceptionEnum;

import javax.servlet.http.HttpServletResponse;

/**
 * Web文件下载工具
 *
 * @author: benben
 * @time: 2025/6/23 下午3:57
 */
public class DownloadUtil {

    /**
     * 根据文件名和文件的字节数组下载文件
     *
     * @param fileName  文件真实名称,最终返回给用户的
     * @param fileBytes 文件字节数组
     * @param response  ServletResponse对象
     */
    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            // reset：清除当前响应所有数据，包括响应头、状态码和响应体。状态码重置默认200
            response.reset();
            // 响应头：Access-Control-Expose-Headers在跨域请求中，明确告知浏览器可以被前端访问的额外请求头
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            // 响应头：Content-Disposition用于指示浏览器如何处理响应内容
            // inline表示浏览器在当前窗口直接打开响应内容。（默认值）
            // attachment表示浏览器将响应内容作为附件下载，通常配合filename属性使用。
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode(fileName) + "\"");
            response.setHeader("Content-Length", String.valueOf(fileBytes.length));
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (Exception ex) {
            throw new FileException(FileExceptionEnum.DOWNLOAD_FILE_ERROR, ex.getMessage());
        }
    }

}
