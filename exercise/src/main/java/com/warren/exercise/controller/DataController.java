package com.warren.exercise.controller;

import com.warren.exercise.service.Impl.ShoppingServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DataController {

    /**
     * 获取商店信息列表
     *
     * @return String
     */
    @GetMapping("/store_list")
    public String getStoreJson() {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        return shoppingService.getStoreJson();
    }

    /**
     * 获取购物车信息列表
     *
     * @return String
     */
    @GetMapping("/cart_list")
    public String getCartJson() {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        return shoppingService.getCartJson();
    }

    /**
     * 清空购物车
     *
     * @return String
     */
    @PostMapping("/clear_cart")
    public String clearCart() {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        return shoppingService.clearCart() ? "ok" : "error";
    }

    /**
     * 结账
     *
     * @return String
     */
    @PostMapping("/bill")
    public String bill() {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        return shoppingService.bill() ? "ok" : "error";
    }

    /**
     * 增加商品到购物车
     *
     * @param name 商品名称
     * @param quantity 商品数量
     * @return Strin
     */
    @PostMapping("/append_cart")
    public String appendCart(@RequestParam(name="name") String name,
                             @RequestParam(name="quantity", defaultValue = "1") double quantity) {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        return shoppingService.appendCommodity(name, quantity) ? "ok" : "error";
    }

    /**
     * 删除商品（一个一个删）
     *
     * @param name 商品名称
     * @param quantity 数量, 默认值 1
     * @return String
     */
    @PostMapping("/delete_commodity")
    public String deleteCommodity(@RequestParam(name="name") String name,
                                  @RequestParam(name="quantity", defaultValue = "1") double quantity) {
        String[] names = new String[1000];
        double[] quantitys = new double[1000];
        names[0] = name;
        quantitys[0] = quantity;
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        return shoppingService.deleteCommodity(names, quantitys) ? "ok" : "error";
    }

}
