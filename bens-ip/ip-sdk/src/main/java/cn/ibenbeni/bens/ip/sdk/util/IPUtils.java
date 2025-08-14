package cn.ibenbeni.bens.ip.sdk.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

/**
 * IP工具类
 * <p>数据来源：ip2region.xdb</p>
 */
@Slf4j
public class IPUtils {

    private final static IPUtils INSTANCE = new IPUtils();

    /**
     * IP查询器
     * <p>加载到内存</p>
     */
    private static Searcher SEARCHER;

    private IPUtils() {
        try {
            long startTime = System.currentTimeMillis();
            byte[] bytes = ResourceUtil.readBytes("ip2region.xdb");
            SEARCHER = Searcher.newWithBuffer(bytes);
            long endTime = System.currentTimeMillis();
            log.info("启动加载IP资源成功,耗时: {}毫秒", endTime - startTime);
        } catch (Exception ex) {
            log.error("启动加载IP资源失败", ex);
        }
    }

    /**
     * 查询IP
     * <p>例如："中国|0|安徽省|合肥市|移动"；仅返回 中国|安徽省|合肥市</p>
     *
     * @param ipStr IP字符串
     * @return 区域名称
     */
    public static String getAreaNameByIp(String ipStr) {
        try {
            String[] parts = StrUtil.splitToArray(SEARCHER.search(ipStr), "|");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                if (i == 1) {
                    continue;
                }
                if (i >= 4) {
                    break;
                }

                str.append(parts[i]);
                if (i < 3) {
                    str.append("|");
                }
            }
            return str.toString();
        } catch (Exception ex) {
            return "未知";
        }
    }

}
