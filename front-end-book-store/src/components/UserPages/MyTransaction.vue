<template>
    <div>
        <el-collapse accordion>
            <el-collapse-item
                    v-for="bill in myBillList"
                    v-bind:key="bill.billId">
                <template slot="title">
                    <el-col :span="8" class="bill">
                        <i class="el-icon-document"></i>
                        账单ID:{{bill.billId}}
                    </el-col>
                    <el-col :span="8" class="bill">总价格:{{bill.billPrice}}</el-col>
                    <el-col :span="8" class="bill">下单时间:{{getFormTime(bill.billTime)}}</el-col>
                </template>
                <div v-for="billItem in bill.billInformationItemArrayList" v-bind:key="billItem.billBookId">
                    <el-row type="flex" justify="center" style="text-align: center">
                        <el-col :span="8" class="billItem"> 书籍ID：{{billItem.bookId}}</el-col>
                        <el-col :span="8" class="billItem">书籍ID：{{billItem.bookName}}</el-col>
                        <el-col :span="8" class="billItem">
                            <el-button v-show="!billItem.flag" type="text" class="billItem"
                                       @click="returnBookByBillBookId(billItem.billBookId)">
                                退货
                            </el-button>
                            <el-button v-show="billItem.flag"  type="text" class="billItem" disabled>
                                已退货
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
    let getFormTime = require('./../utils');
    export default {
        name: "MyTransaction",
        data() {
            return {
                myBillList: []
            };
        },
        mounted() {
            //判断是否为用户登录
            if (window.localStorage["role"] != "普通用户") {
                alert("请以用户身份登录")
                this.$router.push("/login");
            } else {
                this.getMyTransactionList();
            }
        },
        methods: {
            /*时间戳转换*/
            getFormTime(timeStamp) {
                return getFormTime(timeStamp);
            },
            getMyTransactionList() {
                this.axios.get("billinformations").then(response => {
                    this.myBillList = response.data.result.reverse();
                });
            },
            /*根据订单书籍id退货*/
            returnBookByBillBookId(id) {
                //提示是否进行退货
                this.$confirm('是否进行退货?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.patch("/transaction/" + id).then(response => {
                        if (response.data.code == 0) {
                            this.axios.get("billinformations").then(response => {
                                this.myBillList = response.data.result.reverse();
                            });
                            this.$message({
                                type: 'success',
                                message: '退货成功，相应金额稍后将返还至您的账户'
                            });
                        }
                    });
                });
            }
        }
    };
</script>

<style scoped>
    .bill {
        font-size: 20px;
        text-align: center;
    }

    .billItem {
        font-size: 15px;
        text-align: center;
    }
</style>