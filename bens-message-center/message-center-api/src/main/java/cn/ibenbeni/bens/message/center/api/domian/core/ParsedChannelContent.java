package cn.ibenbeni.bens.message.center.api.domian.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 渠道解析结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedChannelContent {

    /**
     * 标题（已解析）
     */
    private String title;

    /**
     * 正文（已解析）
     */
    private String mainBodyContent;

}
