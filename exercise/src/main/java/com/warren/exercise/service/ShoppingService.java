package com.warren.exercise.service;

/**
 * 购物接口类
 * @author Warren
 */
public interface ShoppingService {

    /**
     * 获取商店 json 字符串
     *
     * @return String
     */
    String getStoreJson();

    /**
     * 获取购物车 json 字符串
     *
     * @return String
     */
    String getCartJson();

    /**
     * 清空购物车
     *
     * @return 是否清空
     */
    boolean clearCart();

    /**
     * 结账
     *
     * @return 是否成功
     */
    boolean bill();

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
    boolean deleteCommodity(String[] names, double[] quantitys);

}
