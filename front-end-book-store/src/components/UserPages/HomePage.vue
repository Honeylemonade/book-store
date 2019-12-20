<template>
    <div>
        <span style="position: fixed;right: 20px;bottom: 30px;z-index: 99">
            <el-badge :value="shoppingCart.length" class="item">
                <el-button type="primary" icon="el-icon-shopping-cart-1"
                           @click="drawer = true" circle
                           style="font-size: 35px; "
                ></el-button>
            </el-badge>
        </span>

        <el-drawer
                title="购物车"
                :visible.sync="drawer"
                :with-header="false">
            <el-row type="flex" justify="center">
                <el-col :span="12" style="text-align: center">
                    <span class="cart">总价:{{totalPrice}}元</span>
                </el-col>
                <el-col :span="12" style="text-align: center">
                    <span class="cart">
                        <el-button type="success" @click="commit()">确定支付</el-button>
                    </span>
                </el-col>
            </el-row>
            <ul>
                <li v-for="item in shoppingCart" :key="item.bookId">
                    <el-row type="flex" justify="center">
                        <el-col :span="18">{{item.bookName}}*{{item.purchasingNumber}}</el-col>
                        <el-col :span="6">
                            <el-button type="text"
                                       @click="removeFromShoppingCart(item)">移出
                            </el-button>
                        </el-col>
                    </el-row>
                </li>
            </ul>
        </el-drawer>
        <el-row type="flex" justify="center">
            <el-col :span="20">
                <!--书籍列表-->
                <div v-bind:key="book.bookId" v-for="book in bookList">
                    <el-row type="flex" justify="center">
                        <el-col :span="24">
                            <el-card class="bookcard">
                                <div slot="header">
                                    <el-row>
                                        <el-col :span="8">
                                            <span>
                                                <span style="font-weight: bold">书籍编号：</span>
                                                {{book.bookId}}
                                            </span>
                                        </el-col>
                                        <el-col :span="8">
                                            <span>
                                                <span style="font-weight: bold">名称：</span>
                                                《{{book.bookName}}》
                                            </span>
                                        </el-col>
                                        <el-col :span="4" class="title">
                                            <span style="text-align: right">价格¥：{{book.bookPrice}}</span>
                                        </el-col>
                                        <el-col :span="4" class="title" style="text-align: right">
                                            <el-link type="primary">了解更多</el-link>
                                        </el-col>
                                    </el-row>
                                </div>
                                <div>
                                    <el-row>
                                        <el-col :span="8" style="text-align: center">
                                            <img
                                                    style="width: 150px; height: 150px"
                                                    src="https://img12.360buyimg.com/n1/jfs/t5587/350/4618116432/255954/cd4c1951/59521501N4c19726f.jpg"
                                            />
                                        </el-col>
                                        <el-col :span="8">
                                            <ul class="bookInfo">
                                                <li>作者：{{book.author}}</li>
                                                <li>类型：{{book.type}}</li>
                                                <li>出版社：{{book.publisher}}</li>
                                                <li>库存量：{{book.amount}}</li>
                                            </ul>
                                        </el-col>
                                        <el-col :span="8" style="text-align: right">
                                            <el-button
                                                    type="success"
                                                    @click="insertIntoShoppingCart(book)"
                                            >加入购物车
                                            </el-button>
                                            <div style="margin-top: 80px">
                                                <el-rate :value=4></el-rate>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "HomePage",
        data() {
            return {
                drawer: false,
                bookList: [],
                shoppingCart: [],
                bill: []
            };
        },
        computed: {
            totalPrice() {
                var num = 0;
                for (let i = 0; i < this.shoppingCart.length; i++) {
                    num +=
                        this.shoppingCart[i].purchasingNumber * this.shoppingCart[i].bookPrice;
                }
                return num;
            }
        },
        mounted() {
            //获取书籍信息
            this.axios.get("/availablebooks").then(response => {
                this.bookList = response.data.result;
            });
        },
        methods: {
            /*实现购物车表格刷新*/
            refreshShoppingCart() {
                this.shoppingCart.push(1);
                this.shoppingCart.pop(1);
            },
            /*加入购物车*/
            insertIntoShoppingCart(item) {
                //判断是否为用户登录
                if (window.localStorage["role"] != "普通用户") {
                    alert("请以用户身份登录")
                    this.$router.push("/login");
                }
                this.$message({
                    message: '《' + item.bookName + '》成功加入购物车',
                    type: 'success'
                });
                if (this.shoppingCart.indexOf(item) > -1) {
                    this.shoppingCart[this.shoppingCart.indexOf(item)].purchasingNumber++;
                    this.refreshShoppingCart();
                } else {
                    item.purchasingNumber = 1;
                    item.userId = this.$store.state.userId;
                    this.shoppingCart.push(item);
                }
            },
            /*移出购物车*/
            removeFromShoppingCart(item) {
                this.shoppingCart.pop(item);
            },
            /*提交订单*/
            commit() {
                //判断是否为用户登录
                if (window.localStorage["role"] != "普通用户") {
                    alert("请以用户身份登录")
                    this.$router.push("/login");
                }
                /*询问是否确认交易*/
                this.$confirm('请确认订单，是否进行交易?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.shoppingCart.length != 0) {
                        let order = [];
                        for (var item of this.shoppingCart) {
                            for (var i = 0; i < item.purchasingNumber; i++) {
                                order.push(item.bookId);
                            }
                        }
                        this.axios.post("/transaction", order).then(response => {
                            this.$message({
                                type: 'success',
                                message: '交易成功!'
                            });
                            //清空购物车
                            this.shoppingCart = [];
                        });
                    } else {
                        this.$message.error("您的购物车为空");
                    }
                });
            }
        }
    };
</script>

<style scoped>
    .bookInfo {
    }

    #shoppingCart {
        margin: 10px;
    }

    .cart {
        margin: 5px;
        font-size: 20px;
        font-weight: bold;
    }

    .title {
        font-weight: bold;
    }

    .bookcard {
        margin: 10px;
        padding: 0px;
    }

    .clearfix {
        text-align: center;
        font-size: 25px;
    }
</style>