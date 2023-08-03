package com.boluo.base.constant;

/**
 * 内容模块中用到的常见变量
 *
 * @author spongzi
 * @date 2023/07/17
 */
public interface ContentConstant {
    /**
     * 根节点id
     */
    String TREE_ROOT_ID = "1";

    /**
     * 当前审核状态 未提交 代码编号
     */
    String AUDIT_STATUS_NOT_COMMIT = "202002";

    /**
     * 当前发布状态 未发布 代码编号
     */
    String PUBLISH_STATUS_NOT_PUBLISH = "203001";

    /**
     * 课程费用类型  收费
     */
    String COURSE_CHARGE_TYPE_FEE = "201001";

    /**
     * 课程计划移动类型-向下移动
     */
    String TEACH_PLAN_MOVE_TYPE_DOWN = "movedown";

    /**
     * 课程计划移动类型-向上移动
     */
    String TEACH_PLAN_MOVE_TYPE_UP = "moveup";
}
