import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
/* 引入vue-particles依赖库 (vue粒子特效)*/
import VueParticles from 'vue-particles'
import axios from './axios'
Vue.prototype.$axios = axios//全局注册，使用方法为:this.$axios
import VDistpicker from 'v-distpicker'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';


Vue.use(ElementUI);

Vue.use(VueParticles)
Vue.component('v-distpicker', VDistpicker)
Vue.config.productionTip = false

new Vue({
  router,
  store,

  render: h => h(App)
}).$mount('#app')
