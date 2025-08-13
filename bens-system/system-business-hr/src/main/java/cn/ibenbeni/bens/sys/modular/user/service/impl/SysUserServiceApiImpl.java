package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import cn.ibenbeni.bens.sys.api.pojo.user.CreateUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.SimpleUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserInfoDetailDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.enums.SysUserExceptionEnum;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserSaveReqVO;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysUserServiceApiImpl implements SysUserServiceApi {

    @Resource
    private SysUserService userService;

    @Override
    public SimpleUserDTO getUserInfoByUserId(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        LambdaQueryWrapper<SysUserDO> queryWrapper = Wrappers.lambdaQuery(SysUserDO.class)
                .eq(SysUserDO::getUserId, userId)
                .select(SysUserDO::getAccount, SysUserDO::getAvatar);
        SysUserDO dbSysUser = userService.getOne(queryWrapper);
        if (dbSysUser == null) {
            return null;
        }
        return SimpleUserDTO.builder()
                .userId(userId)
                .account(dbSysUser.getAccount())
                .avatarUrl(dbSysUser.getAvatar())
                .build();
    }

    @Override
    public UserValidateDTO getUserLoginValidateInfo(String account) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = Wrappers.lambdaQuery(SysUserDO.class)
                .eq(SysUserDO::getAccount, account)
                .select(SysUserDO::getUserId, SysUserDO::getPassword, SysUserDO::getPasswordSalt, SysUserDO::getStatusFlag);
        SysUserDO dbSysUser = userService.getOne(queryWrapper, false);
        if (dbSysUser == null) {
            // 用户查询不到，提示账号密码错误
            throw new ServiceException(SysUserExceptionEnum.ACCOUNT_NOT_EXIST);
        }

        return UserValidateDTO.builder()
                .userId(dbSysUser.getUserId())
                .userPasswordHexed(dbSysUser.getPassword())
                .userPasswordSalt(dbSysUser.getPasswordSalt())
                .userStatus(dbSysUser.getStatusFlag())
                .account(account).build();
    }

    @Override
    public void updateUserLoginInfo(Long userId, String ip) {
        if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(ip)) {
            return;
        }

        // 更新登录次数，登录ip，登录时间
        LambdaUpdateWrapper<SysUserDO> updateWrapper = Wrappers.lambdaUpdate(SysUserDO.class)
                .eq(SysUserDO::getUserId, userId)
                .set(SysUserDO::getLastLoginIp, ip)
                .set(SysUserDO::getLastLoginTime, new Date());
        userService.update(updateWrapper);
    }

    @Override
    public UserInfoDetailDTO getUserDetail(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        LambdaQueryWrapper<SysUserDO> queryWrapper = Wrappers.lambdaQuery(SysUserDO.class)
                .eq(SysUserDO::getUserId, userId)
                .select(SysUserDO::getUserId, SysUserDO::getNickName, SysUserDO::getAccount, SysUserDO::getBirthday,
                        SysUserDO::getSex, SysUserDO::getEmail, SysUserDO::getPhone, SysUserDO::getStatusFlag,
                        SysUserDO::getUserSort
                );
        SysUserDO dbSysUser = userService.getOne(queryWrapper, false);
        if (dbSysUser == null) {
            return null;
        }
        return UserInfoDetailDTO.builder()
                .userId(userId)
                .nickName(dbSysUser.getNickName())
                .account(dbSysUser.getAccount())
                .birthday(dbSysUser.getBirthday())
                .sex(dbSysUser.getSex())
                .email(dbSysUser.getEmail())
                .phone(dbSysUser.getPhone())
                .statusFlag(dbSysUser.getStatusFlag())
                .userSort(dbSysUser.getUserSort()).build();
    }

    @Override
    public Long createUser(CreateUserDTO createUser) {
        return userService.createUser(BeanUtil.toBean(createUser, UserSaveReqVO.class));
    }

}
