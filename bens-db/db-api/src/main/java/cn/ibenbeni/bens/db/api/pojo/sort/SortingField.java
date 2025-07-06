package cn.ibenbeni.bens.db.api.pojo.sort;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 排序字段 DTO
 * <p>此类最终需要转换为 {@link OrderItem}</p>
 * 类名加了 ing 的原因是，避免和 ES SortField 重名
 *
 * @author: benben
 * @time: 2025/7/6 上午9:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingField implements Serializable {

    /**
     * 升序
     */
    public static final String ORDER_ASC = "asc";

    /**
     * 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 字段
     */
    private String field;

    /**
     * 顺序
     */
    private String order;

}
