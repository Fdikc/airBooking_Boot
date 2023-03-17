<template>
  <HeaderTop></HeaderTop>
  <div class="out1">
    <!--  粒子特效    -->
    <div class="wrapper">
      <vue-particles
          style="width: 100%;position: absolute;height: 95%"
          color="#dedede"
          :particleOpacity="0.7"
          :particlesNumber="80"
          shapeType="circle"
          :particleSize="4"
          linesColor="#dedede"
          :linesWidth="1"
          :lineLinked="true"
          :lineOpacity="0.4"
          :linesDistance="150"
          :moveSpeed="2"
          :hoverEffect="true"
          hoverMode="grab"
          :clickEffect="true"
          clickMode="push"
      >
      </vue-particles>
      <div class="container">
        <br>
        <br>
        <br>
        <br>
        <el-form :model="LoginForm" ref="LoginFormRef" :rules="rules" label-width="120px" class="registForm">
          <h1>创建账号</h1>
          <br>
          <br>
          <el-form-item label="登录名" prop="username">
            <el-input v-model="LoginForm.username"  />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="LoginForm.password"  />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm()">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>

</template>
<script  setup>
import HeaderTop from "@/components/headerTop.vue";
import { ref, reactive } from "vue";
import { ElNotification , ElMessageBox, ElMessage, ElLoading } from 'element-plus'
import { FormInstance, FormRules } from 'element-plus';
import api from "../../api/api";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
const LoginForm = reactive({
  username: '',
  password: '',
})

const rules = reactive({
  username: [
    { required: true, message: '请输入登录名', trigger: 'blur' },
    { min: 2, max: 15, message: '长度在 2到 15个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 2, max: 12, message: '长度在 2到 12个字符', trigger: 'blur' }
  ],
})

// 提交表单
let router = useRouter()

const LoginFormRef = ref(null);
let store = useStore()
const submitForm=()=>{
  LoginFormRef.value.validate((valid) => {
    if (valid) {
      api.postData('/user/login',LoginForm).then(res=>{
        console.log(res)
        if(res.code===200) {
          ElMessage({
            message: '登录成功',
            type: 'success'
          });
          //获取响应头中的token
          // let jwt = res.headers['authorization']
          //
          // console.log(jwt)
          let jwt=res.data.password
          // console.log(jwt)
         store.commit('SET_TOKEN',jwt);
          //跳转到首页
          router.push({path:'/'})

        }else{
          ElMessage({
            message: '登录失败',
            type: 'error'
          });
        }
      })

    } else {
      console.log('error submit!11!');

      return false;
    }
  });
}





</script>

















<style scoped>
.login {
  width: 100%;
  height: 100%;
  position: relative;
}

.el-form{
  margin-left: 100px;

}
.registForm {

  /*表单左移*/

  width: 400px;
  margin: 0 auto;
  padding: 50px 30px 30px 30px;
  /*background: #c5c6d5;*/
  /*背景半透明*/
  background: rgba(255, 255, 255, 0.5);
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.wrapper {

  /*背景图*/

  position: relative;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("@/assets/img/bg.jpg");
  overflow: hidden;
}

.container {

  border-radius: 10px;
  box-shadow: -5px -5px 10px #fff, 5px 5px 10px #babebc;
  position: absolute;
  width: 768px;
  min-height: 480px;
  overflow: hidden;
}


</style>