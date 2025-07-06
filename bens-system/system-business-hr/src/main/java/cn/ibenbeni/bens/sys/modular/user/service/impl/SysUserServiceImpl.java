package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.pojo.password.SaltedEncryptResult;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.SecurityConfigService;
import cn.ibenbeni.bens.sys.api.callback.RemoveUserCallbackApi;
import cn.ibenbeni.bens.sys.api.pojo.user.SimpleUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserInfoDetailDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;
import cn.ibenbeni.bens.sys.modular.user.constants.UserConstants;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.enums.SysUserExceptionEnum;
import cn.ibenbeni.bens.sys.modular.user.mapper.SysUserMapper;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserPageReqVO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserSaveReqVO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserRoleService;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统用户业务实现层
 *
 * @author benben
 * @date 2025/4/19  下午1:34
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

    // region 属性

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncryptionStrategy passwordEncryptionStrategy;

    @Resource
    private SecurityConfigService securityConfigService;

    @Lazy
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource(name = "loginErrorCountCacheApi")
    private CacheOperatorApi<Integer> loginErrorCountCacheApi;

    // endregion

    // -----------------------------------------------------公共方法-------------------------------------------------
    // region 公共方法

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createUser(UserSaveReqVO createReqVO) {
        // 校验请求合法性
        this.validateUserForCreateOrUpdate(null, createReqVO.getAccount());

        SysUserDO user = BeanUtil.toBean(createReqVO, SysUserDO.class);
        user.setStatusFlag(StatusEnum.ENABLE.getCode());
        user.setUserSort(UserConstants.DEFAULT_USER_SORT);

        // 密码及密码盐
        SaltedEncryptResult saltedEncryptResult = passwordEncryptionStrategy.encryptWithSalt(createReqVO.getPassword());
        user.setPassword(saltedEncryptResult.getEncryptPassword());
        user.setPasswordSalt(saltedEncryptResult.getPasswordSalt());

        this.save(user);

        // 分配默认角色
        sysUserRoleService.bindUserDefaultRole(user.getUserId());

        // 记录密码修改记录
        securityConfigService.recordPasswordEditLog(user.getUserId(), saltedEncryptResult.getEncryptPassword(), saltedEncryptResult.getPasswordSalt());

        return user.getUserId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
        // 校验用户是否存在
        SysUserDO user = this.validateUserExist(id);

        // 删除用户
        this.baseRemoveUser(CollectionUtil.set(false, user.getUserId()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserList(List<Long> idList) {
        this.baseRemoveUser(CollectionUtil.newHashSet(idList));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserSaveReqVO updateReqVO) {
        // 密码不允许在此处修改
        updateReqVO.setPassword(null);

        SysUserDO dbUser = this.validateUserForCreateOrUpdate(updateReqVO.getUserId(), updateReqVO.getAccount());

        BeanUtil.copyProperties(updateReqVO, dbUser);
        this.updateById(dbUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        this.validateOldPassword(id, reqVO.getOldPassword());

        // 密码及密码盐
        SaltedEncryptResult saltedEncryptResult = passwordEncryptionStrategy.encryptWithSalt(reqVO.getOldPassword());

        SysUserDO user = SysUserDO.builder()
                .userId(id)
                .password(saltedEncryptResult.getEncryptPassword())
                .passwordSalt(saltedEncryptResult.getPasswordSalt())
                .build();
        this.updateById(user);

        // 记录密码修改记录
        securityConfigService.recordPasswordEditLog(user.getUserId(), saltedEncryptResult.getEncryptPassword(), saltedEncryptResult.getPasswordSalt());
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        this.validateUserExist(id);

        // 密码及密码盐
        SaltedEncryptResult saltedEncryptResult = passwordEncryptionStrategy.encryptWithSalt(password);

        SysUserDO user = SysUserDO.builder().userId(id).password(saltedEncryptResult.getEncryptPassword()).passwordSalt(saltedEncryptResult.getPasswordSalt()).build();
        this.updateById(user);

        // 记录密码修改记录
        securityConfigService.recordPasswordEditLog(user.getUserId(), saltedEncryptResult.getEncryptPassword(), saltedEncryptResult.getPasswordSalt());
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        this.validateUserExist(id);

        SysUserDO user = SysUserDO.builder().userId(id).statusFlag(status).build();
        this.updateById(user);
    }

    @Override
    public SysUserDO getUserById(Long id) {
        return this.getById(id);
    }

    @Override
    public SysUserDO getUserByAccount(String account) {
        return sysUserMapper.selectByAccount(account);
    }

    @Override
    public List<SysUserDO> getUserList(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            return Collections.emptyList();
        }
        return this.listByIds(idSet);
    }

    @Override
    public PageResult<SysUserDO> getUserPage(UserPageReqVO reqVO) {
        // 分页查询
        return sysUserMapper.selectPage(reqVO);
    }

    @Override
    public boolean isPasswordMatch(String encryptBefore, String passwordSalt, String encryptAfter) {
        return passwordEncryptionStrategy.checkPasswordWithSalt(encryptBefore, passwordSalt, encryptAfter);
    }

    @Override
    public SimpleUserDTO getUserInfoByUserId(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        LambdaQueryWrapper<SysUserDO> queryWrapper = Wrappers.lambdaQuery(SysUserDO.class)
                .eq(SysUserDO::getUserId, userId)
                .select(SysUserDO::getAccount, SysUserDO::getAvatar);
        SysUserDO dbSysUser = this.getOne(queryWrapper);
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
    public UserValidateDTO getUserLoginValidateDTO(String account) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = Wrappers.lambdaQuery(SysUserDO.class)
                .eq(SysUserDO::getAccount, account)
                .select(SysUserDO::getUserId, SysUserDO::getPassword, SysUserDO::getPasswordSalt, SysUserDO::getStatusFlag);
        SysUserDO dbSysUser = this.getOne(queryWrapper, false);
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
        this.update(updateWrapper);
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
        SysUserDO dbSysUser = this.getOne(queryWrapper, false);
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

    // endregion

    // -----------------------------------------------------私有方法-------------------------------------------------
    // region 私有方法

    /**
     * 在创建和更新用户信息时，对用户信息校验
     *
     * @param userId      用户ID
     * @param userAccount 用户账号
     * @return 用户信息
     */
    private SysUserDO validateUserForCreateOrUpdate(Long userId, String userAccount) {
        // 校验用户是否存在
        SysUserDO user = this.validateUserExist(userId);
        // 校验用户名唯一
        this.validateAccountUnique(userAccount);
        return user;
    }

    /**
     * 校验用户是否存在
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    private SysUserDO validateUserExist(Long userId) {
        if (userId == null) {
            return null;
        }
        SysUserDO user = this.getById(userId);
        if (user == null) {
            throw new ServiceException(SysUserExceptionEnum.SYS_USER_NOT_EXISTED);
        }
        return user;
    }

    /**
     * 校验用户账号唯一
     *
     * @param account 用户账号
     */
    private void validateAccountUnique(String account) {
        List<SysUserDO> userList = sysUserMapper.listByAccount(account);
        if (CollUtil.isNotEmpty(userList)) {
            throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_EXISTED);
        }
    }

    /**
     * 校验用户旧密码
     *
     * @param userId      用户ID
     * @param oldPassword 用户旧密码
     */
    public void validateOldPassword(Long userId, String oldPassword) {
        SysUserDO dbUser = this.getById(userId);
        if (dbUser == null) {
            throw new ServiceException(SysUserExceptionEnum.SYS_USER_NOT_EXISTED);
        }
        if (!this.isPasswordMatch(oldPassword, dbUser.getPasswordSalt(), dbUser.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_ERROR);
        }
    }

    /**
     * 删除用户操作的基础业务
     *
     * @param userIdList 用户ID集合
     */
    private void baseRemoveUser(Set<Long> userIdList) {
        // 校验是否有其他业务绑定了用户信息
        Map<String, RemoveUserCallbackApi> removeUserCallbackApiMap = SpringUtil.getBeansOfType(RemoveUserCallbackApi.class);
        for (RemoveUserCallbackApi removeUserCallbackApi : removeUserCallbackApiMap.values()) {
            removeUserCallbackApi.validateHaveUserBind(userIdList);
        }

        // 执行删除用户操作
        this.removeBatchByIds(userIdList);

        // 执行删除用户关联业务的操作
        for (RemoveUserCallbackApi removeUserCallbackApi : removeUserCallbackApiMap.values()) {
            removeUserCallbackApi.validateHaveUserBind(userIdList);
        }
    }

    // endregion

}
