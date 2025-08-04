package cn.ibenbeni.bens.log.api;

import cn.ibenbeni.bens.log.api.pojo.dto.LoginLogCreateReqDTO;

import javax.validation.Valid;

/**
 * 登录日志api接口
 *
 * @author benben
 * @date 2025/5/20  下午9:03
 */
public interface LoginLogServiceApi {

    /**
     * 创建登录日志
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO req);

}
