import Vue from 'vue'
import App from './App.vue'
//element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
//router
import router from './router'
//axios
import Axios from 'axios'
import VueAxios from 'vue-axios'

Axios.defaults.baseURL = 'http://localhost:8080';
Axios.interceptors.request.use(
    config => {
        //判断token是否存在
        if (localStorage.token) {
            //将token设置成请求头
            config.headers.Authorization = localStorage.token;
        }
        return config;
    },
    err => {
        return Promise.reject(err);
    }
);
Vue.use(VueAxios, Axios)

//vuex
import Vuex from 'vuex';

Vue.use(Vuex);
const store = new Vuex.Store({
    state: {}
})

Vue.config.productionTip = false

new Vue({
    store,
    router,
    render: h => h(App),
}).$mount('#app')
