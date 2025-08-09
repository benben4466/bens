package cn.ibenbeni.bens.permission.sdk.data.context;

import cn.ibenbeni.bens.permission.sdk.data.annotation.DataPermission;
import cn.ibenbeni.bens.rule.threadlocal.RemoveThreadLocalApi;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.LinkedList;

/**
 * {@link DataPermission}注解上下文持有者
 */
public class DataPermissionContextHolder implements RemoveThreadLocalApi {

    /**
     * 数据权限注解容器
     * <p>使用List原因：可能存在嵌套</p>
     */
    private static final ThreadLocal<LinkedList<DataPermission>> DATA_PERMISSIONS = TransmittableThreadLocal.withInitial(LinkedList::new);

    /**
     * 获得当前的DataPermission注解
     */
    public static DataPermission get() {
        return DATA_PERMISSIONS.get().peekLast();
    }

    /**
     * 入栈DataPermission注解
     */
    public static void add(DataPermission dataPermission) {
        DATA_PERMISSIONS.get().addLast(dataPermission);
    }

    /**
     * 出栈DataPermission注解
     */
    public static DataPermission remove() {
        DataPermission dataPermission = DATA_PERMISSIONS.get().removeLast();
        // 无元素时，移除ThreadLocal
        if (DATA_PERMISSIONS.get().isEmpty()) {
            DATA_PERMISSIONS.remove();
        }
        return dataPermission;
    }

    /**
     * 清空上下文
     */
    public static void clear() {
        DATA_PERMISSIONS.remove();
    }

    @Override
    public void removeThreadLocalAction() {
        DATA_PERMISSIONS.remove();
    }

}
