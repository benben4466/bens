package cn.ibenbeni.bens.easyexcel.util;

import cn.ibenbeni.bens.rule.util.HttpUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Excel工具类
 *
 * @author: benben
 * @time: 2025/7/6 下午4:25
 */
public class ExcelUtils {

    /**
     * 将列表导出 Excel 给前端
     *
     * @param response  HTTP响应
     * @param filename  文件名称
     * @param sheetName Excel Sheet名称
     * @param head      Excel Head头
     * @param data      数据列表
     * @param <T>       泛型, 保证 head 和 data 类型的一致性
     * @throws IOException IO异常
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName, Class<T> head, List<T> data) throws IOException {
        EasyExcel.write(response.getOutputStream(), head)
                .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                // 注册自定义写入处理器
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                // 注册自定义转换器
                .registerConverter(new LongStringConverter()) // 避免 Long 类型丢失精度
                .sheet(sheetName)
                .doWrite(data);

        // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
        response.addHeader("Content-Disposition", "attachment;filename=" + HttpUtils.encodeUtf8(filename));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    }

    /**
     * 读取 Excel 内容为 List
     *
     * @param file Excel文件
     * @param head Excel Head头
     * @param <T>  数据类型
     * @return 数据类型集合
     * @throws IOException IO异常
     */
    public static <T> List<T> read(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }

}
