<template>
    <div>
        <!--通用header-->
        <el-row type="flex" justify="center">
            <el-col :span="8">
                <el-image :src="logo" style="width:200px;padding-top: 10px"></el-image>
            </el-col>
            <el-col :span="8"></el-col>
            <el-col :span="8" style="text-align: right;margin-top: 10px">
                <i class="el-icon-user"></i>
                {{getName()}}
            </el-col>
        </el-row>
        <div style="margin: 0px;padding: 0px">
            <el-divider style="margin: 0px;padding: 0px"></el-divider>
        </div>
        <!--用户导航-->
        <div v-show="!isAdmin()">
            <el-menu mode="horizontal" router
                     default-active="/">
                <el-menu-item index="/">首页</el-menu-item>
                <el-menu-item index="/MyTransaction">我的账单</el-menu-item>
                <!--当未登录是才显示登录标志-->
                <el-menu-item index="/Login">
                    <el-button size="small" type="primary" v-show="!isUser()">登录</el-button>
                </el-menu-item>
            </el-menu>
        </div>
        <!--管理员导航-->
        <div v-show="isAdmin()">
            <el-menu mode="horizontal" router
                     default-active="/MainPanel/StatisticsPanel">
                <el-menu-item index="/MainPanel/StatisticsPanel">管理员界面</el-menu-item>
                <el-menu-item index="/login">返回用户状态</el-menu-item>
            </el-menu>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data() {
            return {
                logo: require('../assets/logo.png'),
            }
        },
        mounted() {
        },
        methods: {
            isUser() {
                return window.localStorage['role'] == '普通用户';
            },
            isAdmin() {
                return window.localStorage['role'] == '管理员';
            },
            getName() {
                return window.localStorage['userName'];
            },
        }
    }
</script>

<style scoped>
    #loginBtn {
        text-align: right;
    }
    .el-divider--horizontal {
        display: block;
        height: 1px;
        width: 100%;
        margin: 1px 0;
    }
</style>