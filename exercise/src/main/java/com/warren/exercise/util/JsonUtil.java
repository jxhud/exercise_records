package com.warren.exercise.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.warren.exercise.entity.Commodity;

import java.io.*;

/**
 * Json 文件操作工具类
 * @author Warren
 */
public class JsonUtil {

    private String json_path;

    public JsonUtil(String path) {
        this.json_path = path;
    }

    public JsonUtil() {
    }

    /**
     * 读取json文件
     *
     * @return JSONArray json对象
     */
    public JSONArray readJsonFile() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(json_path));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                str.append(line);
            }
            return JSON.parseArray(str.toString());
        } catch (IOException e) {
            System.out.println("文件读取错误！");
        }
        return null;
    }

    /**
     *  覆盖写入 json 文件
     *
     * @param str 写入的内容
     */
    public void writeJsonFile(String str) {
        PrintStream stream;
        try {
            stream=new PrintStream(json_path);
            stream.print(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置 json 值
     *
     * @param name name值
     * @param key 键名
     * @param value 更新的键值
     * @param jsonArray 要更新的jsonArray
     * @return JSONArray
     */
    public void setJsonValue(String name, String key, double value, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("name").equals(name)) {
                jsonObject.put(key, value);
                jsonArray.set(i, jsonObject);
            }
        }
        writeJsonFile(jsonArray.toString());
    }

    /**
     * 追加 json 对象
     *
     * @param commodity 商品实体类
     */
    public boolean appendJson(Commodity commodity) {
        String jsonEntity = JSONObject.toJSONString(commodity);
        JSONObject jsonObject = JSON.parseObject(jsonEntity);
        JSONArray jsonArray = readJsonFile();

        // 检查是否有相同的商品名称
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject_item = jsonArray.getJSONObject(i);
            if(jsonObject_item.getString("name").equals(commodity.getName())) {
                jsonObject.put("quantity", jsonObject_item.getDouble("quantity") + commodity.getQuantity());
                jsonArray.remove(i);
                jsonArray.add(jsonObject);
                writeJsonFile(jsonArray.toString());
                return true;
            }
        }
        
        jsonArray.add(jsonObject);
        writeJsonFile(jsonArray.toString());
        return true;
    }

    /**
     * 删除 json 对象
     *
     * @param name 键名
     * @return boolean 删除是否成功
     */
    public boolean deleteJson(String name) {
        JSONArray jsonArray = readJsonFile();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("name").equals(name)) {
                jsonArray.remove(i);
                writeJsonFile(jsonArray.toString());
                return true;
            }
        }
        return false;
    }
    
}
