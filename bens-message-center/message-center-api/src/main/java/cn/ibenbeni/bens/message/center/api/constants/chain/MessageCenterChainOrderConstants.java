package cn.ibenbeni.bens.message.center.api.constants.chain;

/**
 * 消息中心-责任链属性
 */
public interface MessageCenterChainOrderConstants {

    /**
     * 接入层 Action 顺序
     */
    interface AccessLayer {
        /** 前置校验 */
        int SEND_PRE_CHECK = 100;
        /** 任务创建 */
        int TASK_CREATE = 300;
        /** 任务发布 */
        int TASK_PUBLISH = 400;
    }

    /**
     * 处理层(Handler) Action 顺序 (预留)
     */
    interface HandlerLayer {
        /** 任务前置校验 */
        int TASK_PRE_CHECK = 100;
        /** 参数解析 */
        int TASK_PARAM_PARSE = 200;
        /** 拆分与持久化 */
        int TASK_SPLIT_PERSIST = 300;
        /** 任务分发 */
        int TASK_DISPATCH = 400;
    }

    /**
     * 执行层(Execute) Action 顺序
     */
    interface ExecuteLayer {
        /** 内容解析 */
        int CONTENT_PARSE = 100;
        /** 敏感词检测 */
        int SENSITIVE_CHECK = 110;
        /** 内容快照 */
        int CONTENT_SNAPSHOT = 150;
        /** 路由分发 */
        int ROUTE_DISPATCH = 200;
    }

}
