package cn.ibenbeni.bens.file.api.client;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件客户端抽象类
 * <p>模板方法</p>
 */
@Slf4j
public abstract class AbstractFileClient<Config extends FileClientConfig> implements FileClient {

    /**
     * 客户端ID
     * <p>实际是客户端配置ID</p>
     */
    private final Long clientId;

    /**
     * 客户端编码
     * <p>实际是客户端配置编码</p>
     */
    private final String clientCode;

    /**
     * 文件客户端配置
     */
    protected Config config;

    public AbstractFileClient(Long clientId, String clientCode, Config config) {
        this.clientId = clientId;
        this.clientCode = clientCode;
        this.config = config;
    }

    /**
     * 初始化文件客户端
     */
    public final void init() {
        doInit();
        log.debug("[init][配置({}) 初始化完成]", config);
    }

    /**
     * 具体文件客户端初始化逻辑
     */
    protected abstract void doInit();

    /**
     * 刷新配置
     */
    public final void refresh(Config config) {
        if (config.equals(this.config)) {
            return;
        }
        log.info("[refresh][配置({})发生变化，重新初始化]", config);
        this.config = config;
        // 重新初始化
        this.init();
    }

    @Override
    public Long getClientId() {
        return clientId;
    }

    @Override
    public String getClientCode() {
        return clientCode;
    }

    /**
     * 格式化文件访问URL
     *
     * @param domain           域名
     * @param fileRelativePath 文件相对路径
     * @return URL访问地址
     */
    protected String formatFileUrl(String domain, String fileRelativePath) {
        return StrUtil.format("{}/admin-api/infra/file/{}/get/{}", domain, getClientId(), fileRelativePath);
    }

}
