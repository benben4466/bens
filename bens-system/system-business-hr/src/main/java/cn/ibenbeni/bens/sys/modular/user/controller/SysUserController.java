package cn.ibenbeni.bens.sys.modular.user.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.user.convert.UserConvert;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.*;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 系统用户控制器
 *
 * @author benben
 * @date 2025/5/3  下午7:25
 */
@Tag(name = "管理后台 - 系统用户")
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping(value = "/system/user/create")
    public ResponseData<Long> createUser(@RequestBody @Validated(BaseRequest.create.class) UserSaveReqVO reqVO) {
        return new SuccessResponseData<>(sysUserService.createUser(reqVO));
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @Parameter(name = "id", description = "用户ID", required = true, example = "10")
    @DeleteMapping("/system/user/delete")
    public ResponseData<Boolean> deleteUser(@RequestParam("id") Long id) {
        sysUserService.deleteUser(id);
        return new SuccessResponseData<>(true);
    }

    /**
     * 批量删除用户
     */
    @Operation(summary = "批量删除用户")
    @Parameter(name = "ids", description = "用户ID列表", required = true, example = "[1,2,3]")
    @DeleteMapping("/system/user/delete-list")
    public ResponseData<Boolean> deleteUserList(@RequestParam("ids") List<Long> ids) {
        sysUserService.deleteUserList(ids);
        return new SuccessResponseData<>(true);
    }

    /**
     * 修改用户
     */
    @Operation(summary = "修改用户")
    @PutMapping(value = "/system/user/update")
    public ResponseData<Boolean> updateUser(@RequestBody @Validated(BaseRequest.update.class) UserSaveReqVO reqVO) {
        sysUserService.updateUser(reqVO);
        return new SuccessResponseData<>(true);
    }

    /**
     * 重置用户密码
     */
    @Operation(summary = "重置用户密码")
    @PutMapping("/system/user/update-password")
    public ResponseData<Boolean> updateUserPassword(@RequestBody UserUpdatePasswordReqVO reqVO) {
        sysUserService.updateUserPassword(reqVO.getUserId(), reqVO.getNewPassword());
        return new SuccessResponseData<>(true);
    }

    /**
     * 修改用户状态
     */
    @Operation(summary = "修改用户状态")
    @PutMapping("/system/user/update-status")
    public ResponseData<Boolean> updateUserStatus(@RequestBody UserUpdateStatusReqVO reqVO) {
        sysUserService.updateUserStatus(reqVO.getUserId(), reqVO.getStatusFlag());
        return new SuccessResponseData<>(true);
    }

    /**
     * 获得用户详情
     *
     * @param id 用户ID
     */
    @Operation(summary = "获得用户详情")
    @Parameter(name = "id", description = "用户ID", required = true, example = "10")
    @GetMapping("/system/user/get")
    public ResponseData<UserRespVO> getUser(@RequestParam("id") Long id) {
        UserRespVO respVO = UserConvert.convert(sysUserService.getUserById(id));
        return new SuccessResponseData<>(respVO);
    }

    /**
     * 获得用户分页列表
     */
    @Operation(summary = "获得用户列表", description = "分页")
    @GetMapping("/system/user/page")
    public ResponseData<PageResult<UserRespVO>> getUserPage(UserPageReqVO reqVO) {
        PageResult<SysUserDO> userPage = sysUserService.getUserPage(reqVO);
        PageResult<UserRespVO> pageResult = new PageResult<>();
        pageResult.setPageNo(userPage.getPageNo());
        pageResult.setPageSize(userPage.getPageSize());
        pageResult.setTotalPage(userPage.getTotalPage());
        pageResult.setTotalRows(userPage.getTotalRows());
        pageResult.setRows(UserConvert.convertList(userPage.getRows()));
        return new SuccessResponseData<>(pageResult);
    }

    /**
     * 导出用户
     */
    @Operation(summary = "导出用户")
    @GetMapping("/system/user/export-excel")
    public void exportUserList(UserPageReqVO exportReqVO, HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE); // 不分页
        List<SysUserDO> list = sysUserService.getUserPage(exportReqVO).getRows();
        ExcelUtils.write(response, "用户数据.xls", "数据", UserRespVO.class, UserConvert.convertList(list));
    }

}
