<template>
    <div>
        <el-collapse accordion>
            <el-collapse-item
                    v-for="bill in billList"
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
                        <el-col :span="4" class="billItem">书籍ID：{{billItem.bookId}}</el-col>
                        <el-col :span="8" class="billItem">书籍ID：{{billItem.bookName}}</el-col>
                        <el-col :span="4" class="billItem">用户ID：{{billItem.userId}}</el-col>
                        <el-col :span="4" class="billItem">用户名称：{{billItem.userName}}</el-col>
                        <el-col :span="4" class="billItem">
                            <div v-show="billItem.flag" style="color: #b40010">
                                已退货
                            </div>
                            <div v-show="!billItem.flag" style="color: #21860a">
                                已支付
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
    let getFormTime = require('./../../utils');
    export default {
        name: "BillPanel",
        data() {
            return {
                billList: null,
            }
        },
        mounted() {
            this.axios.get("/allbillinformations").then(response => {
                this.billList = response.data.result;
            })
        },
        methods: {
            /*时间戳转换*/
            getFormTime(timeStamp) {
                return getFormTime(timeStamp);
            },
        }
    }
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