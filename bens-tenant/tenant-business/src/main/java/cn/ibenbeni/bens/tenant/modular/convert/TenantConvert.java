package cn.ibenbeni.bens.tenant.modular.convert;

import cn.ibenbeni.bens.sys.api.pojo.user.CreateUserDTO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantSaveReq;

/**
 * 租户转换器
 */
public class TenantConvert {

    public static CreateUserDTO convertToCreateUserDTO(TenantSaveReq req) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setAccount(req.getUserAccount());
        createUserDTO.setNickName(req.getContactName());
        createUserDTO.setPassword(req.getUserPassword());
        createUserDTO.setPhone(req.getContactMobile());
        return createUserDTO;
    }

}
