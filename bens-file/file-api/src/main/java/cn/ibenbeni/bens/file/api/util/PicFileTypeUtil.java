package cn.ibenbeni.bens.file.api.util;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片文件类型工具类
 *
 * @author: benben
 * @time: 2025/6/23 下午4:36
 */
public class PicFileTypeUtil {

    private static final List<String> PIC_TYPES;

    static {
        PIC_TYPES = new ArrayList<>();
        PIC_TYPES.add("jpg");
        PIC_TYPES.add("png");
        PIC_TYPES.add("jpeg");
        PIC_TYPES.add("tif");
        PIC_TYPES.add("gif");
        PIC_TYPES.add("bmp");
    }

    /**
     * 根据文件名称获取文件是否为图片类型
     *
     * @param fileName 文件名称
     * @return true=图片类型；false=非图片类型
     */
    public static boolean getFileImgTypeFlag(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return false;
        }

        for (String picType : PIC_TYPES){
            if (fileName.toLowerCase().endsWith(picType)) {
                return true;
            }
        }

        return false;
    }

}
