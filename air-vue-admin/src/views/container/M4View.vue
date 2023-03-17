<template>
  <div>

    <el-row :gutter="14">
      <el-col :span="8">
      <el-card class="box-card" shadow="hover">
        <div slot="header" class="clearfix">
          <span class="spanClass">个人信息</span>
        </div>
        <el-form :model="dataForm" label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="dataForm.name" readonly></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="dataForm.email"></el-input>
          </el-form-item>
          <el-form-item label="城市">
            <el-input v-model="dataForm.city"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="dataForm.created" readonly></el-input>
          </el-form-item>

        </el-form>
      </el-card>
      </el-col>


      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span class="spanClass">修改密码</span>
          </div>

          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

            <input type="hidden" v-model="ruleForm.id" autocomplete="off"></input>

            <el-form-item label="原密码">
              <el-input type="password" v-model="ruleForm.OriginalPass" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="确认密码" prop="checkPass">
              <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>

          </el-form>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>
<script>
import dayjs from 'dayjs'
export default {
  name: "m4",

  /* 定义初始化变量 */
  data(){


    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return{
      dataForm:{
        name:'',
        phone:'',
        email:'',
        city:'',
        created:''
      },
      ruleForm: {
        OriginalPass: '',
        pass: '',
        checkPass: '',
        id:''
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
      }
    };

  },

  /* 定义事件 */
  methods: {
    getUserInfo() {
      let user=localStorage.getItem('user');
      if (user!==null){
        //转成对象
        user=JSON.parse(user);
        // console.log(user)
        this.dataForm.name=user.username;
        this.dataForm.email=user.email;
        this.dataForm.city=user.city;
        this.dataForm.created=dayjs(user.created).format('YYYY-MM-DD');
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let user=localStorage.getItem('user');
          user=JSON.parse(user);
          this.ruleForm.id=user.id;
          //发送axios请求
          this.$axios.post('/sys/user/updatePassword',this.ruleForm).then(res=>{
            console.log(this.ruleForm)
            if (res.data.code===200){
              this.$message({
                message: '修改成功',
                type: 'success'
              });
            }else {
              this.$message.error(res.data.msg);
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }





  },

  /* vue生命周期函数，
   当页面加载完成就会执行的方法 */
  created() {

    this.getUserInfo();
  }

}
</script>

<style scoped>

.clearfix{
  overflow: hidden;
/*  向上移动*/
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}

.spanClass{
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
  height: 120px;
}
</style>