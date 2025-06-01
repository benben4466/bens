package cn.ibenbeni.bens.rule.constants;

/**
 * pid结构构造过程中用到的配置
 *
 * @author: benben
 * @time: 2025/6/1 下午4:28
 */
public interface PidBuildConstants {

    /**
     * 顶级节点的父级id
     */
    String TOP_FLAG = "-1";

    /**
     * 顶级节点的父级ids
     */
    String TOP_PIDS = "[-1],";

    /**
     * pids的拼接符号
     */
    String SEPARATOR = SymbolConstant.COMMA;

}
