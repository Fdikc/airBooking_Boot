<template>
  <div class="login-container">

    <el-row type="flex" class="row-bg" justify="center">
<!--      粒子特效-->
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
      <el-col :span="4"><div class="grid-content bg-purple">

      </div></el-col>
      <el-col :span="10"><div class="grid-content bg-purple-light">

        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-loginForm">
          <h2 class="h">欢迎来到航空公司管理系统</h2>
          <el-form-item  prop="name" style="width: 520px">
            <el-input prefix-icon="el-icon-user-solid" v-model="loginForm.name" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item  prop="password" style="width: 520px">
            <el-input prefix-icon="el-icon-lock"  v-model="loginForm.password" placeholder="请输入密码" type="password"></el-input>
          </el-form-item>

          <el-form-item  prop="yzm" >
            <el-input prefix-icon="el-icon-picture" v-model="loginForm.yzm"  placeholder="请输入验证码">
              <img :src="url" class="captImg" slot="append"  @click="getCode"/>
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('loginForm')">立即创建</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
        </el-form>

      </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
  </div>
</template>

<script>

import router from "@/router";
import {resetRout,addServerRouters} from '@/router/index'

export default {
  name: "Login",
  data() {
    return {

      url: '', //后台验证码图片地址
      loginForm: {
        name: '',
        password: '',
        yzm: '',
        key: '',
        token: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        yzm: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ],
      },
      captchaImg:''
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
            //   发送axios请求
            this.$axios.get('/login', {
              params: {
                name: this.loginForm.name,
                password: this.loginForm.password,
                yzm: this.loginForm.yzm,
                key: this.loginForm.key
              }
            }).then(res => {  //res.data==ResultVo
              if (res.data.code === 200) {  //==不包含类型的比较  ====包含类型的比较
                this.getMenuList()
                //存储名字  共享
                // console.log(this.loginForm.name)
                this.$store.commit('updateName',this.loginForm.name)

                this.$message.success('登录成功');
                //1. 存储用户信息
                const  jwt=res.headers['authorization'];
                this.$store.commit('SET_TOKEN',jwt)

                console.log(res.data.data)
                this.$store.commit('setUser',res.data.data)


                // localStorage.setItem('user',JSON.stringify(res.data.data))
                // console.log(jwt)
                // sessionStorage.setItem('token',JSON.stringify(res.data.data))
                //2. 跳转到首页

                this.$router.push({path:'/home'})
              } else {
                this.$message.error(res.data.msg);
              }
            })
        } else {
          this.$message.error('表单填写有误！请检查');
          return false;
        }
      });
    },
    getCode() {
      this.$axios.get('/getCode', {responseType: 'blob'}).then(res => {
        let blob = new Blob([res.data])
        this.url = window.URL.createObjectURL(blob)
        this.loginForm.key = res.headers.key
        // console.log(res.headers.key)
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getMenuList() {
      resetRout()
      this.$axios.get("/menu").then(res => {
        // console.log(res.data.data);
        const array = res.data.data;
        sessionStorage.setItem('serverRouters',JSON.stringify(array))
        addServerRouters(array)
      });
    }
  },
  created() {
    this.getCode();
  }
}
</script>


<style scoped>
.grid-content bg-purple-light {

  /* 开启弹性布局 */
  display: flex;
  /* 交叉轴居中（垂直居中）*/
  align-items: center;
  /* 主轴居中（水平居中）*/
  justify-content: center;
}


/*背景图*/
  .row-bg{
    background-image: url("@/assets/img/bg.jpg");
    background-size: 100% 100%;
  }

  .el-row{
    /*background-color:#fafafa ;*/
    height: 100%;
    display: flex;
    align-items: center;
  }
  .login-container {
    min-height: 100%;
    width: 100%;
    height: 100%;
    background-color: #fafafa;
    overflow: hidden;
  }

  .demo-loginForm{
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: -5px -10px 50px;

    margin-right: -20px;
    overflow: hidden;
  }
  .h{
    text-align: center;
    font-size: 20px;
    color: #333;
    margin-bottom: 40px;
  }
  .captImg{
    float: left;
    margin-left: -20px;
    margin-right: -20px;
    width: 140px;
    height: 40px;
    border-radius: 5px;
  }



</style>