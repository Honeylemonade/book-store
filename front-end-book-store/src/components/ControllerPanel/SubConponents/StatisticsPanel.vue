<template>
    <div>
        <el-row>
            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
                <el-card>
                    <div id="charts1" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
                <el-card>
                    <div id="charts2" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <!--购进规则-->
        <el-alert
                show-icon
                type="info">
            <template slot="title">
                <div>
                    当存在书籍当前库存量少于100时，推荐购进；
                </div>
                <div>
                    当销量前三名的书籍库存量少于200时，推荐购进;
                </div>
            </template>
        </el-alert>
        <!--推荐购进列表-->
        <!--书籍信息表格-->
        <el-table
                :data="recommendedBooks"
                style="width: 100%">
            <el-table-column
                    sortable
                    prop="bookId"
                    label="推荐书籍ID">
            </el-table-column>
            <el-table-column
                    prop="bookName"
                    label="推荐书籍名称">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="bookPrice"
                    label="价格">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="amount"
                    label="当前库存量">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    import echarts from "echarts";

    export default {
        name: "StatisticsPanel",
        data() {
            return {
                source1: [],
                source2: [],
                recommendedBooks: []
            };
        },
        async mounted() {
            var month = new Date().getMonth() + 1;
            var a = [];
            var b = [];
            for (var i = -3; i < 1; i++) {
                await this.axios.get("/monthlyincome/" + i).then(response => {
                    a.push(month + i + "月");
                    b.push(response.data.result);
                });
            }
            this.source1.push(a);
            this.source1.push(b);
            await this.axios.get("/topsaillingbook").then(response => {
                var datas = response.data.result;
                datas.length = 3;
                var a = [];
                var b = [];
                for (var i = 0; i < datas.length; i++) {
                    a.push("《" + datas[i].bookName + "》");
                    b.push(datas[i].soldAmount);
                }
                this.source2.push(a);
                this.source2.push(b);
            });
            this.draw();
            //获取推荐进货的书籍
            this.axios.get("/books/recommendedBooks").then(response => {
                this.recommendedBooks = response.data.result;
            });
        },
        methods: {
            //绘制图表
            draw() {
                /* chart1 */
                let charts1 = echarts.init(document.getElementById("charts1"));
                charts1.setOption({
                    title: {
                        text: '月销售情况'
                    },
                    tooltip: {},
                    legend: {
                        data: ['营业额']
                    },
                    xAxis: {
                        type: "category",
                        data: this.source1[0]
                    },
                    yAxis: {},
                    grid: {bottom: 20},
                    series: [
                        {
                            name: '营业额',
                            data: this.source1[1],
                            type: "line",
                            itemStyle: {color: "#2e4554"},
                        }
                    ]
                });
                /* chart2 */
                let charts2 = echarts.init(document.getElementById("charts2"));
                charts2.setOption({
                    title: {
                        text: '书籍销售排行'
                    },
                    tooltip: {},
                    legend: {
                        data: ['销售量']
                    },
                    xAxis: {
                        type: "category",
                        data: this.source2[0]
                    },
                    yAxis: {},
                    grid: {bottom: 20},
                    series: [
                        {
                            name: '销售量',
                            data: this.source2[1],
                            type: "bar",
                            barCategoryGap: "70%",
                            itemStyle: {color: "#c33432"},
                        }
                    ]
                });
            },
        }
    };
</script>

<style scoped>
    .chart {
        width: 600px;
        height: 400px;
        text-align: center;
        margin: 0 auto;
    }
</style>