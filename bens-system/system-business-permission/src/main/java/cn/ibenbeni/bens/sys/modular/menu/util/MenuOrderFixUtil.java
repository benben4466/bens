package cn.ibenbeni.bens.sys.modular.menu.util;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * 菜单排序进行修复
 * <p>对菜单进行再次排序，因为有的菜单是101，有的菜单是10101，需要将位数小的补0，再次排序</p>
 *
 * @author: benben
 * @time: 2025/6/1 下午3:56
 */
public final class MenuOrderFixUtil {

    /**
     * 修复菜单排序
     */
    public static void fixOrder(List<SysMenuDO> sysMenuDOS) {
        if (ObjectUtil.isEmpty(sysMenuDOS)) {
            return;
        }

        // 找到最大的数字位数
        int maxDigitCount = 0;
        for (SysMenuDO sysMenuDO : sysMenuDOS) {
            BigDecimal menuSort = sysMenuDO.getMenuSort();
            if (menuSort == null) {
                continue;
            }
            int digitCount = getPointLeftDigitCount(menuSort);
            if (digitCount > maxDigitCount) {
                maxDigitCount = digitCount;
            }
        }

        // 补充位数
        for (SysMenuDO sysMenuDO : sysMenuDOS) {
            BigDecimal menuSort = sysMenuDO.getMenuSort();
            if (menuSort == null) {
                menuSort = new BigDecimal(0);
            }
            int digitCount = getPointLeftDigitCount(menuSort);
            if (digitCount < maxDigitCount) {
                menuSort = menuSort.multiply(BigDecimal.valueOf(Math.pow(10, maxDigitCount - digitCount)));
            }
            sysMenuDO.setMenuSort(menuSort);
        }

        sysMenuDOS.sort(Comparator.comparing(SysMenuDO::getMenuSort));
    }

    /**
     * 获取一个数字的小数点左边的位数
     */
    private static int getPointLeftDigitCount(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return 0;
        }
        String bigDecimalStr = bigDecimal.toString();
        int decimalIndex = bigDecimalStr.indexOf('.');
        return decimalIndex >= 0 ? decimalIndex : bigDecimalStr.length();
    }

}
