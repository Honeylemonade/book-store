import Vue from 'vue'
import Router from 'vue-router'
//引入组件
import MyTransaction from './components/UserPages/MyTransaction'
import HomePage from './components/UserPages/HomePage'
import Login from './components/UserPages/Login'
import Temp from './components/UserPages/Temp'
//管理员模块组件
import AdminLogin from './components/ControllerPanel/AdminLogin'
import MainPanel from "./components/ControllerPanel/MainPanel";
import StatisticsPanel from './components/ControllerPanel/SubConponents/StatisticsPanel'
import BookPanel from './components/ControllerPanel/SubConponents/BookPanel'
import BillPanel from './components/ControllerPanel/SubConponents/BillPanel'

Vue.use(Router);
export default new Router({
    routes: [
        {path: '/', component: HomePage},
        {path: '/MyTransaction', component: MyTransaction},
        {path: '/HomePage', component: HomePage},
        {path: '/Login', component: Login},
        {path: '/Temp', component: Temp},
        {path: '/AdminLogin', component: AdminLogin},
        {
            path: '/MainPanel', component: MainPanel, children: [
                {path: 'StatisticsPanel', component: StatisticsPanel},
                {path: 'BookPanel', component: BookPanel},
                {path: 'BillPanel', component: BillPanel},
            ]
        }
    ],
    mode: 'history'
})