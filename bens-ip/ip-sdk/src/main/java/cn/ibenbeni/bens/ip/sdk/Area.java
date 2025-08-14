package cn.ibenbeni.bens.ip.sdk;

import lombok.*;

import java.util.List;

/**
 * 区域信息
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    /**
     * 区域编号-全球，即根目录
     */
    public static final String ID_GLOBAL = "0";

    /**
     * 区域编号-中国
     */
    public static final String ID_CHINA = "1";

    /**
     * 区域编号
     */
    private String areaCode;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域类型
     */
    private Integer areaType;

    /**
     * 父级区域
     */
    private Area parent;

    /**
     * 子区域
     */
    private List<Area> children;

}
