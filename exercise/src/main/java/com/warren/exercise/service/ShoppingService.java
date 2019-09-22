package com.warren.exercise.service;

import com.warren.exercise.entity.Commodity;

/**
 * 购物接口类
 * @author Warren
 */
public interface ShoppingService {
    /**
     * 增加购物车商品
     * @param name 商品名称
     * @param quantity 商品数量
     * @return 是否新增成功
     */
    boolean appendCommodity(String name, double quantity);

    /**
     * 删除购物车商品
     * @param names 要删除的商品列表 String[]
     * @return 是否删除成功
     */
    boolean deleteCommodity(String[] names);

}
