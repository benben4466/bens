package cn.ibenbeni.bens.db.mp.util;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisPlus工具类
 *
 * @author: benben
 * @time: 2025/6/27 下午2:55
 */
public class MyBatisPlusUtils {

    /**
     * 将拦截器添加到指定位置
     *
     * @param interceptor 拦截器管理类
     * @param inner       拦截器
     * @param index       位置
     */
    public static void addInterceptor(MybatisPlusInterceptor interceptor, InnerInterceptor inner, int index) {
        List<InnerInterceptor> inners = new ArrayList<>(interceptor.getInterceptors());
        inners.add(index, inner);
        interceptor.setInterceptors(inners);
    }

}
