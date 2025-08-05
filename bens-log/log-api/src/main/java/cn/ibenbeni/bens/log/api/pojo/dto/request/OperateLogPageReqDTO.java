package cn.ibenbeni.bens.log.api.pojo.dto.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 操作日志分页入参-DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OperateLogPageReqDTO extends PageParam {

    /**
     * 操作模块编号
     */
    private String moduleNo;

    /**
     * 操作模块业务ID
     */
    private Long bizId;

    /**
     * 用户ID
     */
    private Long userId;

}
