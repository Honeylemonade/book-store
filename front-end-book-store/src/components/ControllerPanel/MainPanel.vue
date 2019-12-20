<template>
    <div>
        <el-row>
            <el-col :xs="24" :sm="24" :md="24" :lg="5" :xl="4">
                <el-menu
                        router
                default-active="/MainPanel/StatisticsPanel">
                    <el-menu-item index="/MainPanel/StatisticsPanel" class="menuItem">
                        <i class="el-icon-s-marketing"></i>
                        <span slot="title">信息统计模块</span>
                    </el-menu-item>
                    <el-menu-item index="/MainPanel/BookPanel" class="menuItem">
                        <i class="el-icon-menu"></i>
                        <span slot="title">书籍管理模块</span>
                    </el-menu-item>
                    <el-menu-item index="/MainPanel/BillPanel" class="menuItem">
                        <i class="el-icon-s-claim"></i>
                        <span slot="title">交易流水记录</span>
                    </el-menu-item>
                </el-menu>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="19" :xl="20">
                <router-view></router-view>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "MainPanel"
    }
</script>

<style scoped>
    .menuItem {
        font-size: 15px;
    }
</style>