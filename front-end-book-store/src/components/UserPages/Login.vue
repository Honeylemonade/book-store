<template>
    <div>
        <el-row type="flex" justify="center">
            <el-col :xs="22" :sm="20" :md="12" :lg="10" :xl="8">
                <el-card style="margin: 10px">
                    <div class="title">用户登录界面</div>
                    <el-form>
                        <el-form-item label="账号" prop="form.userName">
                            <el-input type="form.userName" v-model="form.userName" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="form.password">
                            <el-input type="form.password" v-model="form.password" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" style="width:100%" @click="submit()">登录</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                form: {
                    userName: "",
                    password: ""
                }
            };
        },
        methods: {
            submit() {
                var user = this.form;
                this.axios.post("/login", user).then(response => {
                    if (response.data.code == 0) {
                        alert("登录成功");
                        //将信息放入localstorage
                        window.localStorage["token"] = "Bearer " + response.data.result.token;
                        window.localStorage["userName"] = response.data.result.userName;
                        window.localStorage["userId"] = response.data.result.userId;
                        window.localStorage["role"] = "普通用户";
                        //跳转到主页
                        this.$router.push("/");
                        location.reload();
                    } else {
                        this.$message.error(response.data.message);
                    }
                });
            }
        }
    };
</script>

<style scoped>
    .title {
        padding: 40px;
        font-size: 35px;
        text-align: center;
        font-weight: lighter;
    }
</style>