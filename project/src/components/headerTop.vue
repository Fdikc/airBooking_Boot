<!--  -->
<template>
    <div class="header">
        <div class="container">
            <div class="img-navbar-logo"></div>
            <div class="img-navbar-right"></div>
        </div>
      <router-link to="/" class="homeView">首页</router-link>
      <router-link to="/register" class="regView">注册</router-link>
      <router-link to="/login" class="loginView">登录</router-link>
      <router-link to="/orderlist" class="orderView">订单</router-link>

    </div>
</template>
<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'

</script>

<style lang='scss' scoped>
.homeView{
  position: absolute;
  top: 0;
  right: 200px;
  width: 100px;
  height: 80px;
  background-color: #1e385a;
  color: #fff;
  text-align: center;
  line-height: 80px;
  font-size: 16px;
  cursor: pointer;
  &:hover{
    background-color: #fff;
    color: #1e385a;
  }
}
.orderView{
  position: absolute;
  top: 0;
  right: 300px;
  width: 100px;
  height: 80px;
  background-color: #1e385a;
  color: #fff;
  text-align: center;
  line-height: 80px;
  font-size: 16px;
  cursor: pointer;
  &:hover{
    background-color: #fff;
    color: #1e385a;
  }
}
.regView{
  position: absolute;
  top: 0;
  right: 0;
  width: 100px;
  height: 80px;
  background-color: #1e385a;
  color: #fff;
  text-align: center;
  line-height: 80px;
  font-size: 16px;
  cursor: pointer;
  &:hover{
    background-color: #fff;
    color: #1e385a;
  }
}

.loginView{
  position: absolute;
  top: 0;
  right: 100px;
  width: 100px;
  height: 80px;
  background-color: #1e385a;
  color: #fff;
  text-align: center;
  line-height: 80px;
  font-size: 16px;
  cursor: pointer;
  &:hover{
    background-color: #fff;
    color: #1e385a;
  }
}

    .header{
       width: 100%;
       height: 80px;
       background: linear-gradient(to right, #fff 20%, #1e385a 80%);
       .container{
            width: 1200px;
            height: 80px;
            margin: 0 auto;
            background-color: #1e385a;
            position: relative;
            .img-navbar-logo{
                width: 260px;
                height: 80px;
                background-color: red;
                background: url('@/assets/img/img_navbar_logo.79b7ac4.png');
                background-size: 260px 80px;
            }
            .img-navbar-right{
                position: absolute;
                top: 0;
                right: -4.5rem;
                width: 488px;
                height: 100%;
                background: url('@/assets/img//img_navbar_right.png');
                background-size: 100% 100%;
            }
       }
    }
</style>
