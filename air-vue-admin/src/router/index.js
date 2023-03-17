import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/login/Login.vue";
import Index from "@/views/container/Index.vue";
import Home from "@/views/Home.vue";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    redirect: '/index',
    children: [
      {
        path: '/index',
        name: 'Index',
        component: Index
      },
      {
        path: '/test',
        name: 'Test',
        component: () => import('@/views/container/Test.vue')
      },
    ]
  },
  {
    path: '/404',
    component: () => import('@/views/NotFound.vue'),
  },
  {
    path: '*',
    redirect: '/404',
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export function resetRout(){
  router.matcher=new VueRouter({routes}).matcher
}


const serverRouters=sessionStorage.getItem('serverRouters')
if (serverRouters){
  const array=JSON.parse(serverRouters);
  addServerRouters(array)
}
//添加路由
export function addServerRouters(array){
  // console.log(router.getRoutes())
  for (const{id,path,component} of array){
    // console.log(id,path,component)
    if (component!==null){
      //动态添加路由
      //参数一：父路由   参数二：路由信息对象
      router.addRoute('Home',{
        path:path,
        name:id,
        component:()=>import('@/views/container/'+component)
      });
    }
  }
  // console.log(router.getRoutes())
  // router.push({path:'/m1/c1'})
  //
  // console.log(this.$router.getRoutes())
}



// 路由守卫
router.beforeEach((to, from, next) => {

  if (to.path === '/') {
    return next()
  }
  else {
    //获取token 如果有token就放行
    let token = localStorage.getItem('token');
    if(token){
      return next()
    }else {
      return next('/')
    }
  }
})
export default router
