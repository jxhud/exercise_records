<%--
  Created by IntelliJ IDEA.
  User: Warren
  Date: 2019/9/23
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>cart</title>
    <%-- vue --%>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <style>
        #cartBox {
            width: 100%;
            min-height: 600px;

        }

        .center {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .cartCard {
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

        .btn + .btn {
            margin-left: 20px;
        }
    </style>
</head>
<body>
<div id="cartBox" class="center">
    <div class="cartCard">
        <div class="card_header">
            <i class="el-icon-shopping-cart-2"></i> 购物车
        </div>
        <el-table
                :data="cartList">
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
                    label="删除（-1/次）"
                    align="center">
                <template slot-scope="scope">
                    <el-button type="danger" size="mini" @click="deleteCommodity(scope.row['name'])" round>删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="card_footer">
            <span style="float: left; line-height: 40px">￥ {{sumMoney}}</span>
            <el-button icon="el-icon-goods" class="btn" @click="toStore" plain>继续购物</el-button>
            <el-button class="btn" @click="deleteAll" plain>全部删除</el-button>
            <el-button class="btn" @click="payAll" plain>结账</el-button>
        </div>
    </div>
</div>
</body>
<script>
    var app = new Vue({
        el: '#cartBox',
        data() {
            return {
                cartList: [],
                sumMoney: 0
            }
        },
        mounted() {
            this.getCartList();
        },
        methods: {
            toStore() {
                window.location.href = 'store';
            },
            payAll() {
                let self = this;
                this.$confirm('总花费 ￥' + self.sumMoney + ' 元, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    $.ajax({
                        url: "http://localhost:8080/bill",
                        type: "post",
                        success: function (resp) {
                            if(resp === "ok") {
                                self.getCartList();
                            }
                            else {
                                alert("购物车异常");
                            }
                        },
                        error: function () {
                            alert("请检查网络环境！")
                        }
                    })
                });
            },
            deleteAll() {
                let self = this;
                $.ajax({
                    url: "http://localhost:8080/clear_cart",
                    type: "post",
                    success: function (resp) {
                        if(resp === "ok") {
                            self.getCartList();
                        }
                        else {
                            alert("购物车异常");
                        }
                    },
                    error: function () {
                        alert("请检查网络环境！")
                    }
                })
            },
            deleteCommodity(name) {
                let self = this;
                $.ajax({
                    url: "http://localhost:8080/delete_commodity",
                    data: {
                        name: name
                    },
                    type: "post",
                    success: function (resp) {
                        if(resp === "ok") {
                            self.getCartList();
                        }
                        else {
                            alert("购物车异常");
                        }
                    },
                    error: function () {
                        alert("请检查网络环境！")
                    }
                })
            },
            getCartList() {
                this.cartList = [];
                let self = this;
                $.ajax({
                    url: "http://localhost:8080/cart_list",
                    type: "get",
                    success: function (resp) {
                        self.cartList = JSON.parse(resp);
                        if(resp !== '[]') {
                            self.cartList.forEach(item => {
                                self.sumMoney += item.price * item.quantity;
                            });
                            self.sumMoney = self.sumMoney.toFixed(2);
                        }
                        else {
                            self.sumMoney = 0;
                        }
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
