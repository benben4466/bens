package cn.ibenbeni.bens.sys.modular.login.controller;

import cn.ibenbeni.bens.auth.api.AuthServiceApi;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginReq;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginResp;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 登录相关接口
 *
 * @author benben
 * @date 2025/5/20  下午10:08
 */
@Tag(name = "管理后台 - 认证")
@Slf4j
@RestController
public class LoginController {

    @Resource
    private AuthServiceApi authServiceApi;

    @Operation(summary = "使用账号密码登录")
    @PostMapping("/system/auth/login")
    public ResponseData<AuthLoginResp> login(@RequestBody @Valid AuthLoginReq loginReq) {
        return new SuccessResponseData<>(authServiceApi.login(loginReq));
    }

}
