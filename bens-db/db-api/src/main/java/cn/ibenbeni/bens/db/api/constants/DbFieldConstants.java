package cn.ibenbeni.bens.db.api.constants;

/**
 * 数据库常用字段的枚举
 *
 * @author benben
 * @date 2025/4/18  下午12:54
 */
public interface DbFieldConstants {

    /**
     * 创建时间的字段名
     */
    String CREATE_TIME = "createTime";

    /**
     * 创建用户的字段名
     */
    String CREATE_USER = "createUser";

    /**
     * 更新用户的字段名
     */
    String UPDATE_USER = "updateUser";

    /**
     * 更新时间的字段名
     */
    String UPDATE_TIME = "updateTime";

    /**
     * 删除标记的字段名
     */
    String DEL_FLAG = "delFlag";

    /**
     * 数据状态的字段
     * 状态：1-启用，2-禁用
     */
    String STATUS_FLAG = "statusFlag";

}
