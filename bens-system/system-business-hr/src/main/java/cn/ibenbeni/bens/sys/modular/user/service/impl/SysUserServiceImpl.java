package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.pojo.password.SaltedEncryptResult;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.pojo.dict.SimpleDict;
import cn.ibenbeni.bens.rule.util.SortUtils;
import cn.ibenbeni.bens.sys.api.SecurityConfigService;
import cn.ibenbeni.bens.sys.api.callback.RemoveUserCallbackApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.api.enums.user.UserStatusEnum;
import cn.ibenbeni.bens.sys.api.pojo.user.SimpleUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserInfoDetailDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUser;
import cn.ibenbeni.bens.sys.modular.user.enums.SysUserExceptionEnum;
import cn.ibenbeni.bens.sys.modular.user.factory.SysUserCreateFactory;
import cn.ibenbeni.bens.sys.modular.user.mapper.SysUserMapper;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRequest;
import cn.ibenbeni.bens.sys.modular.user.pojo.response.PersonalInfo;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户业务实现层
 *
 * @author benben
 * @date 2025/4/19  下午1:34
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private PasswordEncryptionStrategy passwordEncryptionStrategy;

    @Resource
    private SecurityConfigService securityConfigService;

    @Resource(name = "loginErrorCountCacheApi")
    private CacheOperatorApi<Integer> loginErrorCountCacheApi;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysUserRequest sysUserRequest) {
//        if (this.isExistUserAccount(sysUserRequest.getAccount())) {
//            throw new ClientException(BaseErrorCode.SERVICE_USER_EXISTED_ERROR);
//        }
        // TODO 账户唯一性校验：暂时使用代码实现，后期使用注解完成 start
        if (this.isExistUserAccount(sysUserRequest.getAccount())) {
            throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_EXISTED);
        }
        // TODO 账户唯一性校验：暂时使用代码实现，后期使用注解完成 end

        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserRequest, sysUser);

        // 校验密码是否符合规则
        String password = sysUserRequest.getPassword().trim();
        securityConfigService.validatePasswordSecurityRule(false, password);

        // 将密码加密存储到库中
        SaltedEncryptResult saltedEncryptResult = passwordEncryptionStrategy.encryptWithSalt(password);
        sysUser.setPassword(saltedEncryptResult.getEncryptPassword());
        sysUser.setPasswordSalt(saltedEncryptResult.getPasswordSalt());

        // 设置用户默认头像
        sysUser.setAvatar(FileConstants.DEFAULT_AVATAR_FILE_PATH);
        this.save(sysUser);

        // 记录日志
        // 记录一个密码修改记录
        securityConfigService.recordPasswordEditLog(sysUser.getUserId(), saltedEncryptResult.getEncryptPassword(), saltedEncryptResult.getPasswordSalt());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(SysUserRequest sysUserRequest) {
        SysUser dbSysUser = this.querySysUser(sysUserRequest);

        // 不能删除超级管理员
        if (YesOrNotEnum.Y.getCode().equals(dbSysUser.getSuperAdminFlag())) {
            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // 删除用户的业务操作
        this.baseRemoveUser(CollectionUtil.set(false, dbSysUser.getUserId()));

        // 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDel(SysUserRequest sysUserRequest) {
        Set<Long> userIdList = sysUserRequest.getUserIdList();
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .in(SysUser::getUserId, userIdList)
                .eq(SysUser::getSuperAdminFlag, YesOrNotEnum.Y.getCode());
        long adminCount = this.count(queryWrapper);
        if (adminCount > 0) {
            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // 删除用户的业务操作
        this.baseRemoveUser(userIdList);

        // 记录日志
    }

    @Override
    public void edit(SysUserRequest sysUserRequest) {
        SysUser dbSysUser = this.querySysUser(sysUserRequest);

        // 记录日志

        // 不能修改admin账号的超级管理员标识和账号
        if (SysConstants.ADMIN_USER_ACCOUNT.equals(dbSysUser.getAccount())) {
            if (!dbSysUser.getAccount().equals(sysUserRequest.getAccount())) {
                throw new ServiceException(SysUserExceptionEnum.CANT_CHANGE_ADMIN_ACCOUNT);
            }

            if (YesOrNotEnum.N.getCode().equals(sysUserRequest.getSuperAdminFlag())) {
                throw new ServiceException(SysUserExceptionEnum.CANT_CHANGE_ADMIN_FLAG);
            }
        }

        // copy属性
        BeanUtil.copyProperties(sysUserRequest, dbSysUser);
        // 编辑不能修改密码
        dbSysUser.setPassword(null);

        // 更新用户详情信息
        this.updateById(dbSysUser);

        // 记录日志
    }

    @Override
    public SysUser detail(SysUserRequest sysUserRequest) {
        // 查询用户个人信息
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, sysUserRequest.getUserId()).select(SysUser::getUserId, SysUser::getAvatar, SysUser::getAccount, SysUser::getUserSort, SysUser::getSuperAdminFlag, SysUser::getRealName, SysUser::getSex, SysUser::getBirthday, SysUser::getEmail, SysUser::getPhone, SysUser::getLastLoginIp, SysUser::getLoginCount, SysUser::getLastLoginTime, SysUser::getStatusFlag, BaseEntity::getCreateTime, BaseEntity::getUpdateTime);
        SysUser dbSysUser = this.getOne(queryWrapper);
        return dbSysUser;
    }

    @Override
    public List<SysUser> findList(SysUserRequest sysUserRequest) {
        return this.list(this.createWrapper(sysUserRequest));
    }

    @Override
    public PageResult<SysUser> findPage(SysUserRequest sysUserRequest) {
        LambdaQueryWrapper<SysUser> queryWrapper = this.createWrapper(sysUserRequest);

        // 只查询需要的字段
        queryWrapper.select(SysUser::getUserId, SysUser::getRealName, SysUser::getAccount, SysUser::getSex, SysUser::getStatusFlag, BaseEntity::getCreateTime);

        // 分页查询
        Page<SysUser> pageCondition = new Page<>(sysUserRequest.getPageNo(), sysUserRequest.getPageSize());
        Page<SysUser> sysUserPage = this.page(pageCondition, queryWrapper);

        return PageResultFactory.createPageResult(sysUserPage);
    }

    @Override
    public void updateStatus(SysUserRequest sysUserRequest) {
        // 如果是将用户禁用，检测判断不能禁用超级管理员用户
        boolean userSuperAdminFlag = this.getUserSuperAdminFlag(sysUserRequest.getUserId());
        if (userSuperAdminFlag) {
            throw new ServiceException(SysUserExceptionEnum.CANT_UPDATE_STATUS);
        }

        // 校验状态传值是否正确
        Integer statusFlag = sysUserRequest.getStatusFlag();
        UserStatusEnum.validateUserStatus(statusFlag);

        // 更新用户状态
        LambdaUpdateWrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
                .set(SysUser::getStatusFlag, sysUserRequest.getStatusFlag())
                .eq(SysUser::getUserId, sysUserRequest.getUserId());
        this.update(updateWrapper);

        // 如果是启用用户，则清除掉用户密码错误次数的缓存
        if (statusFlag.equals(UserStatusEnum.ENABLE.getCode())) {
            // 获取用户id对应的账号
            Long userId = sysUserRequest.getUserId();
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                    .eq(SysUser::getUserId, userId)
                    .select(SysUser::getAccount);
            SysUser dbSysUser = this.getOne(queryWrapper);
            if (dbSysUser != null) {
                loginErrorCountCacheApi.remove(dbSysUser.getAccount());
            }
        }

        // 记录日志
    }

    @Override
    public PersonalInfo getPersonalInfo() {
        // TODO 待实现
        return new PersonalInfo();
    }

    @Override
    public void editInfo(SysUserRequest sysUserRequest) {
        // TODO 从用户登录上下文中获取用户ID
        sysUserRequest.setUserId(-1L);
        SysUser dbSysUser = this.querySysUser(sysUserRequest);

        // 填充更新用户的信息
        SysUserCreateFactory.fillUpdateInfo(sysUserRequest, dbSysUser);

        this.updateById(dbSysUser);
    }

    @Override
    public void editAvatar(SysUserRequest sysUserRequest) {
        String avatar = sysUserRequest.getAvatar();

        // 从用户登录上下文中获取用户ID

        // 更新用户头像
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getUserId, -1L); // TODO 从用户登录上下文中获取用户ID
        wrapper.set(SysUser::getAvatar, avatar);
        this.update(wrapper);
    }

    @Override
    public void editPassword(SysUserRequest sysUserRequest) {
        // 新密码与原密码相同
        if (sysUserRequest.getNewPassword().equals(sysUserRequest.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }

        // TODO 从用户登录上下文中获取用户ID
        sysUserRequest.setUserId(-1L);
        SysUser dbSysUser = this.querySysUser(sysUserRequest);

        // 原密码错误
        if (!passwordEncryptionStrategy.checkPasswordWithSalt(sysUserRequest.getPassword(), dbSysUser.getPasswordSalt(), dbSysUser.getPassword())) {
            throw new ServiceException(SysUserExceptionEnum.USER_PWD_ERROR);
        }

        // 将密码进行空字符串处理
        String newPassword = sysUserRequest.getNewPassword().trim();

        // 校验新密码规则，根据密码策略
        securityConfigService.validatePasswordSecurityRule(true, newPassword);

        // 设置新的加密后密码和盐
        SaltedEncryptResult saltedEncryptResult = passwordEncryptionStrategy.encryptWithSalt(newPassword);
        dbSysUser.setPassword(saltedEncryptResult.getEncryptPassword());
        dbSysUser.setPasswordSalt(saltedEncryptResult.getPasswordSalt());

        this.updateById(dbSysUser);

        // 记录日志
    }

    @Override
    public List<SimpleDict> batchGetName(SysUserRequest sysUserRequest) {
        List<SimpleDict> dictList = new ArrayList<>();

        if (ObjectUtil.isEmpty(sysUserRequest) || ObjectUtil.isEmpty(sysUserRequest.getUserIdList())) {
            return dictList;
        }

        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .in(SysUser::getUserId, sysUserRequest.getUserIdList())
                .select(SysUser::getRealName, SysUser::getUserId);
        List<SysUser> dbList = this.list(queryWrapper);

        if (ObjectUtil.isEmpty(dbList)) {
            return dictList;
        }

        // 排序
        List<SysUser> sortUsers = SortUtils.sortListByObjectKey(dbList, new LinkedList<>(sysUserRequest.getUserIdList()));

        for (SysUser sysUser : sortUsers) {
            dictList.add(new SimpleDict(sysUser.getUserId(), sysUser.getRealName(), null));
        }

        return dictList;
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new ServiceException(SysUserExceptionEnum.SYS_USER_NOT_EXISTED);
        }

        SaltedEncryptResult saltedEncryptResult = passwordEncryptionStrategy.encryptWithSalt(newPassword);
        sysUser.setPassword(saltedEncryptResult.getEncryptPassword());
        sysUser.setPasswordSalt(saltedEncryptResult.getPasswordSalt());
        this.updateById(sysUser);
        // 记录日志
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
            removeUserCallbackApi.validateHaveBind(userIdList);
        }

        // 执行删除用户操作
        this.removeBatchByIds(userIdList);

        // 执行删除用户关联业务的操作
        for (RemoveUserCallbackApi removeUserCallbackApi : removeUserCallbackApiMap.values()) {
            removeUserCallbackApi.removeAction(userIdList);
        }
    }

    /**
     * 获取用户信息
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {
        SysUser dbSysUser = this.getById(sysUserRequest.getUserId());
        if (ObjectUtil.isEmpty(dbSysUser)) {
            throw new ServiceException(SysUserExceptionEnum.SYS_USER_NOT_EXISTED);
        }
        return dbSysUser;
    }

    /**
     * 用户账号是否存在
     *
     * @param account 用户账号
     * @return true=存在；false=不存在
     */
    private Boolean isExistUserAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getAccount, account);
        return this.getOne(queryWrapper) != null;
    }

    /**
     * 创建查询wrapper
     */
    private LambdaQueryWrapper<SysUser> createWrapper(SysUserRequest sysUserRequest) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        // 根据状态进行查询
        Integer statusFlag = sysUserRequest.getStatusFlag();
        queryWrapper.eq(ObjectUtil.isNotEmpty(statusFlag), SysUser::getStatusFlag, statusFlag);

        // TODO 数据权限范围控制

        // 按用户排序字段排序
        queryWrapper.orderByAsc(SysUser::getUserSort);

        return queryWrapper;
    }

    @Override
    public SimpleUserDTO getUserInfoByUserId(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, userId)
                .select(SysUser::getAccount, SysUser::getRealName, SysUser::getAvatar);
        SysUser dbSysUser = this.getOne(queryWrapper);
        if (dbSysUser == null) {
            return null;
        }
        return SimpleUserDTO.builder()
                .userId(userId)
                .account(dbSysUser.getAccount())
                .realName(dbSysUser.getRealName())
                .avatarUrl(dbSysUser.getAvatar())
                .build();
    }

    @Override
    // @Cacheable(value = "users", key = "#userId", unless = "#result.length() == 0")
    public String getUserRealName(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return "";
        }
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, userId)
                .select(SysUser::getRealName);
        SysUser dbSysUser = this.getOne(queryWrapper);
        if (dbSysUser == null) {
            return "";
        }
        return dbSysUser.getRealName();
    }

    @Override
    public UserValidateDTO getUserLoginValidateDTO(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getAccount, account)
                .select(SysUser::getUserId, SysUser::getPassword, SysUser::getPasswordSalt, SysUser::getStatusFlag);
        SysUser dbSysUser = this.getOne(queryWrapper, false);
        if (dbSysUser == null) {
            // 用户查询不到，提示账号密码错误
            throw new ServiceException(SysUserExceptionEnum.ACCOUNT_NOT_EXIST);
        }

        // 更新用户的冻结状态
        this.updateSysUserFreezeStatus(account, dbSysUser);
        return UserValidateDTO.builder()
                .userId(dbSysUser.getUserId())
                .userPasswordHexed(dbSysUser.getPassword())
                .userPasswordSalt(dbSysUser.getPasswordSalt())
                .userStatus(dbSysUser.getStatusFlag())
                .account(account)
                .build();
    }

    @Override
    public void updateUserLoginInfo(Long userId, String ip) {
        if (ObjectUtil.isEmpty(userId) || ObjectUtil.isEmpty(ip)) {
            return;
        }
        // 获取原来的登录次数
        long loginCount = 0;
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, userId)
                .select(SysUser::getLoginCount);
        SysUser dbSysUser = this.getOne(queryWrapper, false);

        // 获取原有的登录次数，准备加1
        if (dbSysUser == null || dbSysUser.getLoginCount() == null) {
            loginCount = 1;
        } else {
            loginCount = dbSysUser.getLoginCount() + 1;
        }

        // 更新登录次数，登录ip，登录时间
        LambdaUpdateWrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
                .eq(SysUser::getUserId, userId)
                .set(SysUser::getLastLoginIp, ip)
                .set(SysUser::getLoginCount, loginCount)
                .set(SysUser::getLastLoginTime, new Date());
        this.update(updateWrapper);
    }

    @Override
    public boolean getUserSuperAdminFlag(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return false;
        }
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, userId)
                .select(SysUser::getSuperAdminFlag);
        SysUser dbSysUser = this.getOne(queryWrapper, false);
        if (dbSysUser == null) {
            return false;
        }
        return YesOrNotEnum.Y.getCode().equals(dbSysUser.getSuperAdminFlag());
    }

    @Override
    public List<Long> queryAllUserIdList() {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .select(SysUser::getUserId);
        List<SysUser> dbSysUserList = this.list(queryWrapper);
        return dbSysUserList.stream()
                .map(SysUser::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean userExist(Long userId) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, userId);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public UserInfoDetailDTO getUserDetail(Long userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return null;
        }
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserId, userId)
                .select(SysUser::getUserId, SysUser::getRealName, SysUser::getNickName, SysUser::getAccount, SysUser::getBirthday, SysUser::getSex, SysUser::getEmail, SysUser::getPhone, SysUser::getStatusFlag, SysUser::getSuperAdminFlag, SysUser::getUserSort);
        SysUser dbSysUser = this.getOne(queryWrapper, false);
        if (dbSysUser == null) {
            return null;
        }
        return UserInfoDetailDTO.builder()
                .userId(userId)
                .realName(dbSysUser.getRealName())
                .nickName(dbSysUser.getNickName())
                .account(dbSysUser.getAccount())
                .birthday(dbSysUser.getBirthday())
                .sex(dbSysUser.getSex())
                .email(dbSysUser.getEmail())
                .phone(dbSysUser.getPhone())
                .superAdminFlag(dbSysUser.getSuperAdminFlag())
                .statusFlag(dbSysUser.getStatusFlag())
                .userSort(dbSysUser.getUserSort())
                .build();
    }

    @Override
    public void lockUserStatus(String account) {
        LambdaUpdateWrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
                .eq(SysUser::getAccount, account)
                .set(SysUser::getStatusFlag, UserStatusEnum.TEMP_FREEZE.getKey())
                .set(SysUser::getFreezeDeadlineTime, DateUtil.offset(new Date(), DateField.DAY_OF_MONTH, 1));
        this.update(updateWrapper);
    }

    /**
     * 更新用户冻结状态，如果用户被冻结了
     *
     * @param account           用户账号
     * @param sysUserServiceOne 系统用户信息
     */
    private void updateSysUserFreezeStatus(String account, SysUser sysUserServiceOne) {
        // 判断用户状态是否被临时冻结，如果超过了临时冻结时间，将用户状态改为正常态
        if (UserStatusEnum.TEMP_FREEZE.getCode().equals(sysUserServiceOne.getStatusFlag())) {
            Date freezeDeadlineTime = sysUserServiceOne.getFreezeDeadlineTime();
            if (freezeDeadlineTime != null && freezeDeadlineTime.before(new Date())) {
                // 修改用户状态为启用
                sysUserServiceOne.setStatusFlag(UserStatusEnum.ENABLE.getCode());

                // 修改库中的状态，并设置截止冻结日期为空
                LambdaUpdateWrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
                        .set(SysUser::getStatusFlag, UserStatusEnum.ENABLE.getCode())
                        .set(SysUser::getFreezeDeadlineTime, null)
                        .eq(SysUser::getAccount, account);
                this.update(updateWrapper);
            }
        }
    }

    // ---------------------------------------------临时方法------------------------------------------------------

}
