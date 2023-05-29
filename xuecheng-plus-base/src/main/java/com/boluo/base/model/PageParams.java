package com.boluo.base.model;

import lombok.Data;

/**
 * @author kirito
 * @version 1.0
 * @description:  分页查询参数
 * @date 2023/5/27 18:52
 */
@Data
public class PageParams {

    // 当前页
    private Long pageNo=1L;
    // 每页记录数
    private Long pageSize=30L;

    @Override
    public String toString() {
        return "PageParams{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
    public PageParams() {
    }
    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


}
