package cn.ibenbeni.bens.sys.modular.login.controller;

import cn.ibenbeni.bens.auth.api.AuthServiceApi;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginRequest;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginResponse;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录相关接口
 *
 * @author benben
 * @date 2025/5/20  下午10:08
 */
@Slf4j
@RestController
public class LoginController {

    @Resource
    private AuthServiceApi authServiceApi;

    /**
     * 系统登录接口
     */
    @PostMapping("/loginApi")
    public ResponseData<LoginResponse> loginApi(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = authServiceApi.login(loginRequest);
        return new SuccessResponseData<>(loginResponse);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logoutAction")
    public ResponseData<?> logoutAction() {
        return new SuccessResponseData<>();
    }

}
