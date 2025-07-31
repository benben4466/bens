package cn.ibenbeni.bens.file.api.util;

import cn.hutool.core.io.IoUtil;
import cn.ibenbeni.bens.rule.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件类型工具类
 */
@Slf4j
public class FileTypeUtils {

    private static final Tika TIKA = new Tika();

    /**
     * 推断文件类型
     *
     * @param fileContent 文件内容
     * @param fileName    文件名
     * @return MineType(文件类型);无法识别时会返回“application/octet-stream”
     */
    public static String inferMineType(byte[] fileContent, String fileName) {
        return TIKA.detect(fileContent, fileName);
    }

    /**
     * 根据文件的MineType, 获得文件后缀
     *
     * @param mineType 文件MineType
     * @return 文件后缀
     */
    public static String getFileSuffix(String mineType) {
        try {
            return MimeTypes.getDefaultMimeTypes().forName(mineType).getExtension();
        } catch (MimeTypeException ex) {
            log.warn("[getExtension][获取文件后缀({}) 失败]", mineType, ex);
            return null;
        }
    }

    /**
     * 返回附件
     *
     * @param response    HTTP响应
     * @param fileName    文件名称
     * @param fileContent 文件内容
     */
    public static void writeAttachment(HttpServletResponse response, String fileName, byte[] fileContent) throws IOException {
        // 设置Header和ContentType
        response.setHeader("Content-Disposition", "attachment;filename=" + HttpUtils.encodeUtf8(fileName));
        String contentType = inferMineType(fileContent, fileName);
        response.setContentType(contentType);
        // 输出附件
        IoUtil.write(response.getOutputStream(), false, fileContent);
    }

}
