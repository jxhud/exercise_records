package com.warren.exercise.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.warren.exercise.entity.Commodity;
import com.warren.exercise.service.ShoppingService;
import com.warren.exercise.util.JsonUtil;

/**
 * 购物接口实现类
 *
 * @author Warren
 */
public class ShoppingServiceImpl implements ShoppingService {

    @Override
    public boolean appendCommodity(String name, double quantity) {
        JsonUtil store = new JsonUtil("src/main/resources/static/store.json");
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        JSONArray storeJson = store.readJsonFile();
        System.out.println("开始进行追加");
        for (int i = 0; i < storeJson.size(); i++) {
            JSONObject jsonObject = storeJson.getJSONObject(i);
            System.out.println(jsonObject.getString("name"));
            if(!jsonObject.getString("name").equals(name)) {
                continue;
            }
            System.out.println("在商店查找到该商品");
            double new_quantity = jsonObject.getDouble("quantity") - quantity;
            // 如果要购买的数量不够就false
            if(new_quantity < 0 ) {
                return false;
            }
            // 减去商店商品
            store.setJsonValue(name ,"quantity", new_quantity, storeJson);
            System.out.println("删除商店商品" + name);
            // 追加购物车商品
            Commodity commodity = new Commodity(name, jsonObject.getDouble("price"), quantity);
            cart.appendJson(commodity);
            System.out.println("追加购物车商品" + name);
            break;
        }
        return false;
    }

    @Override
    public boolean deleteCommodity(String[] names) {
        JsonUtil store = new JsonUtil("src/main/resources/static/store.json");
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        JSONArray cartJson = cart.readJsonFile();
        JSONArray storeJson = store.readJsonFile();

        System.out.println("开始进行删除操作");
        for (String name : names) {
            for (int j = 0; j < cartJson.size(); j++) {
                JSONObject jsonObject = cartJson.getJSONObject(j);
                if (!jsonObject.getString("name").equals(name)) {
                    continue;
                }
                System.out.println("在购物车里找到" + name);
                // 购物车里要删除的商品的数量
                double quantity = jsonObject.getDouble("quantity");
                // 删除购物车商品
                cart.deleteJson(name);
                System.out.println("删除购物车里的" + name);
                // 在商店里找这个商品，补回去
                for (int k = 0; k < storeJson.size(); k++) {
                    JSONObject jsonObject_store = storeJson.getJSONObject(k);
                    if (!jsonObject_store.getString("name").equals(name)) {
                        continue;
                    }
                    double new_quantity = jsonObject_store.getDouble("quantity") + quantity;
                    store.setJsonValue(name, "quantity", new_quantity, storeJson);
                    System.out.println("补回商店里" + quantity + "个" + name);
                    break;
                }
                break;
            }
        }
        return false;
    }
}
