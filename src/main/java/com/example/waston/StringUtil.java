package com.example.waston;

/**
 * 字符串String工具类
 *
 * @author 肖淞
 */
public class StringUtil {

    /**
     * 判断str是否为空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return isEmpty(obj.toString());
    }

    /**
     * 判断字符串是否是数字
     *
     * @param s
     * @return
     */
    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }
}
