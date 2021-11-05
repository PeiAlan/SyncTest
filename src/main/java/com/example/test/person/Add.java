package com.example.test.person;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/5 08:37
 * @since 1.0
 **/
public interface Add {

    /**
     * Employee 员工涨工资 10%
     * Manager 经理涨工资 20%
     * @param wage 员工当前工资
     * @return 涨薪之后的工资
     */
    double add(double wage);

}
