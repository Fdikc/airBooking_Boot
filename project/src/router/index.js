import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/register',
    name: 'register',
    component: ()=> import('../views/login/Register.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: ()=> import('../views/login/Login.vue')
  },
  {
    path: '/hangban',
    name: 'hangban',
    component: ()=> import('../views/hangBan.vue')
  },
  {
    path: '/orderlist',
    name: 'orderlist',
    component: ()=> import('../views/OrderList.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})
// 路由守卫
router.beforeEach((to, from)=>{
 if (to.name==='login' || to.name==='register') {
   return true
 }else{
   if (localStorage.getItem('token')) {
     return true
   }else{
     return '/login'
   }
 }

})


export default router
