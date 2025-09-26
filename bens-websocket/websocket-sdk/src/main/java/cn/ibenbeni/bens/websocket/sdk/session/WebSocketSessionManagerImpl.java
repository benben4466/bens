package cn.ibenbeni.bens.websocket.sdk.session;

import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import cn.ibenbeni.bens.websocket.api.session.WebSocketSessionManager;
import cn.ibenbeni.bens.websocket.sdk.util.WebSocketFrameworkUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认的 {@link WebSocketSessionManager} 实现类
 */
public class WebSocketSessionManagerImpl implements WebSocketSessionManager {

    /**
     * SessionID 与 WebSocketSession 映射
     * Key=SessionID
     */
    private final ConcurrentMap<String, WebSocketSession> idSessions = new ConcurrentHashMap<>();

    /**
     * 用户 与 WebSocket Session 映射
     * 外层：key=用户类型, Value=相同用户类型集合
     * 内层：key=用户ID, Value=WebSocket Session
     */
    private final ConcurrentMap<Integer, ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>>> userSessions = new ConcurrentHashMap<>();

    @Override
    public void addSession(WebSocketSession session) {
        // 添加到 idSessions 中
        idSessions.put(session.getId(), session);
        // 添加到 userSessions 中
        LoginUser loginUser = WebSocketFrameworkUtils.getLoginUser(session);
        if (loginUser == null) {
            return;
        }
        // TODO 系统暂时无用户类型
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(1);
        if (CollUtil.isEmpty(userSessionsMap)) {
            userSessionsMap = new ConcurrentHashMap<>();
            // ConcurrentMap#putIfAbsent() 方法，如果 key 不存在，则添加，如果 key 存在，则返回 key 对应的 value
            if (userSessions.putIfAbsent(1, userSessionsMap) != null) {
                userSessionsMap = userSessions.get(1);
            }
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessionsMap.get(loginUser.getUserId());
        if (sessions == null) {
            sessions = new CopyOnWriteArrayList<>();
            if (userSessionsMap.putIfAbsent(loginUser.getUserId(), sessions) != null) {
                sessions = userSessionsMap.get(loginUser.getUserId());
            }
        }
        sessions.add(session);
    }

    @Override
    public void removeSession(WebSocketSession session) {
        // 移除从 idSessions 中
        idSessions.remove(session.getId());
        // 移除从 idSessions 中
        LoginUser loginUser = WebSocketFrameworkUtils.getLoginUser(session);
        if (loginUser == null) {
            return;
        }
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(1);
        if (CollUtil.isEmpty(userSessionsMap)) {
            return;
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessionsMap.get(loginUser.getUserId());
        sessions.removeIf(item -> item.getId().equals(session.getId()));
        if (CollUtil.isEmpty(sessions)) {
            userSessionsMap.remove(loginUser.getUserId(), sessions);
        }
    }

    @Override
    public WebSocketSession getSession(String id) {
        return idSessions.get(id);
    }

    @Override
    public Collection<WebSocketSession> getSessionList(Integer userType) {
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(userType);
        if (CollUtil.isEmpty(userSessionsMap)) {
            return new ArrayList<>();
        }
        LinkedList<WebSocketSession> result = new LinkedList<>(); // 避免扩容
        Long contextTenantId = TenantContextHolder.getTenantId();
        for (List<WebSocketSession> sessions : userSessionsMap.values()) {
            if (CollUtil.isEmpty(sessions)) {
                continue;
            }
            // 特殊：如果租户不匹配，则直接排除
            if (contextTenantId != null) {
                Long userTenantId = WebSocketFrameworkUtils.getTenantId(sessions.get(0));
                if (!contextTenantId.equals(userTenantId)) {
                    continue;
                }
            }
            result.addAll(sessions);
        }

        return result;
    }

    @Override
    public Collection<WebSocketSession> getSessionList(Integer userType, Long userId) {
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(userType);
        if (CollUtil.isEmpty(userSessionsMap)) {
            return new ArrayList<>();
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessionsMap.get(userId);
        return CollUtil.isNotEmpty(sessions) ? new ArrayList<>(sessions) : new ArrayList<>();
    }

}
