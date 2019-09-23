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
    public String getStoreJson() {
        JsonUtil store = new JsonUtil("src/main/resources/static/store.json");
        JSONArray storeJson = store.readJsonFile();
        return storeJson.toJSONString();
    }

    @Override
    public String getCartJson() {
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        JSONArray cartJson = cart.readJsonFile();
        return cartJson.toJSONString();
    }

    @Override
    public boolean clearCart() {
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        JSONArray cartJson = cart.readJsonFile();
        String[] names = new String[9000];
        double[] quantitys = new double[9000];

        for (int i = 0; i < cartJson.size(); i++) {
            JSONObject jsonObject = cartJson.getJSONObject(i);
            names[i] = jsonObject.getString("name");
            quantitys[i] = jsonObject.getDouble("quantity");
        }

        return deleteCommodity(names, quantitys);
    }

    @Override
    public boolean bill() {
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        cart.writeJsonFile("[]");
        return true;
    }

    @Override
    public boolean appendCommodity(String name, double quantity) {
        JsonUtil store = new JsonUtil("src/main/resources/static/store.json");
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        JSONArray storeJson = store.readJsonFile();
        System.out.println("+开始进行追加");
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
            System.out.println("+追加购物车商品" + name);
            break;
        }
        return true;
    }

    @Override
    public boolean deleteCommodity(String[] names, double[] quantitys) {
        JsonUtil store = new JsonUtil("src/main/resources/static/store.json");
        JsonUtil cart = new JsonUtil("src/main/resources/static/shopping_cart.json");
        JSONArray cartJson = cart.readJsonFile();
        JSONArray storeJson = store.readJsonFile();

        System.out.println("开始进行删除操作");
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < cartJson.size(); j++) {
                JSONObject jsonObject = cartJson.getJSONObject(j);
                if (!jsonObject.getString("name").equals(names[i])) {
                    continue;
                }
                System.out.println("在购物车里找到" + names[i]);
                // 购物车里要删除的商品的数量
                double cart_quantity = jsonObject.getDouble("quantity");
                double __quantity = cart_quantity - quantitys[i];
                // 删除购物车商品
                if(__quantity <= 0) {
                    cart.deleteJson(names[i]);
                }
                else {
                    cart.setJsonValue(names[i], "quantity", __quantity, cartJson);
                }

                System.out.println("删除购物车里的" + names[i]);
                // 在商店里找这个商品，补回去
                for (int k = 0; k < storeJson.size(); k++) {
                    JSONObject jsonObject_store = storeJson.getJSONObject(k);
                    if (!jsonObject_store.getString("name").equals(names[i])) {
                        continue;
                    }
                    double new_quantity = jsonObject_store.getDouble("quantity") + quantitys[i];
                    store.setJsonValue(names[i], "quantity", new_quantity, storeJson);
                    System.out.println("补回商店里" + quantitys[i] + "个" + names[i]);
                    break;
                }
                break;
            }
        }
        return true;
    }
}
