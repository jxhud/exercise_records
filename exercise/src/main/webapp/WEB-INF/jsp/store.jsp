<%--
  Created by IntelliJ IDEA.
  User: Warren
  Date: 2019/9/23
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>store</title>
    <style>
        #storeBox {
            width: 100%;
            min-height: 600px;

        }

        .center {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .storeCard {
            width: 600px;
            border: 1px solid silver;

        }

        .card_header {
            width: 100%;
            height: 50px;
            line-height: 50px;
            font-size: 20px;
            text-align: center;
            border-bottom: 1px solid silver;
        }

        .card_footer {
            width: 100%;
            height: 70px;
            border-top: 1px solid silver;
            padding: 15px 30px;
            box-sizing: border-box;
            text-align: right;
        }
    </style>
    <%-- vue --%>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
</head>
<body>
    <div id="storeBox" class="center">
        <div class="storeCard">
            <div class="card_header">
                <i class="el-icon-goods"></i> 商店
            </div>
            <el-table
                    :data="commodity">
                <el-table-column
                        prop="name"
                        label="商品"
                        align="center">
                </el-table-column>
                <el-table-column
                        prop="price"
                        label="价格（元）"
                        align="center">
                </el-table-column>
                <el-table-column
                        prop="quantity"
                        label="数量（斤）"
                        align="center">
                </el-table-column>
                <el-table-column
                        label="购买（+1/次）"
                        align="center">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="buyCommodity(scope.row['name'])"
                                   :disabled="scope.row['quantity'] === 0" round>购买</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="card_footer">
                <el-badge :is-dot="red_dot">
                    <el-button icon="el-icon-shopping-cart-2" @click="toCart" plain>购物车</el-button>
                </el-badge>
            </div>
        </div>
    </div>
</body>
<script>
    var app = new Vue({
        el: '#storeBox',
        data() {
            return {
                commodity: [],
                red_dot: false,
            }
        },
        mounted() {
            this.getStoreJson();
            this.getCartList();
        },
        methods: {
            toCart() {
                window.location.href = "cart";
            },
            // 获取商店商品列表
            getStoreJson() {
                this.commodity = [];
                let self = this;
                $.ajax({
                    url: "http://localhost:8080/store_list",
                    type: "get",
                    success: function (resp) {
                        let storeJson = JSON.parse(resp);
                        storeJson.forEach((v) => {
                            let t = {};
                            t.name = v.name;
                            t.price = v.price;
                            t.quantity = v.quantity;
                            self.commodity.push(t);
                        })
                    },
                    error: function () {
                        alert("请检查网络环境！")
                    }
                })
            },
            // 购买
            buyCommodity(name) {
                let self = this;
                $.ajax({
                    url: "http://localhost:8080/append_cart",
                    data: {
                        name: name,
                        quantity: 1
                    },
                    type: "post",
                    success: function (resp) {
                        if(resp === "ok") {
                            self.getStoreJson();
                            this.getCartList();
                        }
                        else {
                            alert("购买异常");
                        }
                    },
                    error: function () {
                        alert("请检查网络环境！")
                    }
                })
            },
            // 获取购物车信息
            getCartList() {
                let self = this;
                $.ajax({
                    url: "http://localhost:8080/cart_list",
                    type: "get",
                    success: function (resp) {
                        self.red_dot = resp !== '[]';
                    },
                    error: function () {
                        alert("请检查网络环境！")
                    }
                })
            }
        }
    });
</script>
</html>
