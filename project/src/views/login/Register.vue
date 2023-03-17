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
        <el-form :model="Registerer" ref="RegistererRef" :rules="rules" label-width="120px" class="registForm">
          <h1>创建账号</h1>
          <br>
          <br>
          <el-form-item label="登录名" prop="username">
            <el-input v-model="Registerer.username"  />
          </el-form-item>
          <el-form-item label="Email" prop="email">
            <el-input v-model="Registerer.email" @blur="emailBlur" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="Registerer.password"  />
          </el-form-item>
          <el-form-item label="验证码" prop="yzm">
            <el-input v-model="Registerer.yzm" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm()">创建用户</el-button>
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
const Registerer = reactive({
    username: '',
    email: '',
    password: '',
    yzm: ''
})



const rules = reactive({
  username: [
    { required: true, message: '请输入登录名', trigger: 'blur' },
    { min: 2, max: 5, message: '长度在 2到 5个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 2, max: 12, message: '长度在 2到 12个字符', trigger: 'blur' }
  ],
  yzm: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
  ]
})

// 提交表单


const RegistererRef = ref(null);

const submitForm=()=>{
  RegistererRef.value.validate((valid) => {
    if (valid) {
      // alert('提交成功');
    //   发送请求
     api.postData('/user/register',Registerer).then(res=>{
        console.log(res)
        if(res.code===200){
          ElMessage({
            message: '注册成功',
            type: 'success'
          });
          router.push('/login')
        }else{
          ElMessage({
            message: '注册失败',
            type: 'error'
          });
        }
      });

    } else {
      console.log('error submit!11!');
      alert('提交失败');
      return false;
    }
  });
}

//如果email触发了blur事件，就发送请求
let router = useRouter()
const emailBlur=()=>{
  console.log(Registerer.email)
  // api.postData('/user/email',Registerer.email).then(res=>{
  //   console.log(res)
  // });
  api.getDataNoData('/user/email/'+Registerer.email).then(res=>{
    //element-plus的message组件
    console.log("===================="+res)
    if(res.code===200) {
      ElMessage({
        message: '验证码已发送',
        type: 'success'
      });

    }else{
      ElMessage({
        message: '验证码发送失败',
        type: 'error'
      });
    }
},err=>{
  console.log(err)
})
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