<template>
  <div>
    <!--
          user用户管理
    -->
    <br>
    <!--   横向表单-->
    <div class="C1View">
      <el-row>
        <el-col :span="8">
          <el-form :inline="true" :model="formInline"  ref="formInline" class="demo-form-inline">
            <el-form-item label="搜索" prop="user">
              <el-input v-model="formInline.user" placeholder="用户名，邮箱，身份证,手机号"></el-input>
            </el-form-item>
<!--            <el-form-item label="搜索" prop="user">-->
<!--              <el-input type="tel" v-model="formInline.phone" placeholder="手机号" maxlength="11" ></el-input>-->
<!--            </el-form-item>-->
            <el-form-item>
              <el-button type="primary" size="medium" @click="onSubmit('formInline')">查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <!--   批量删除/ 新增-->
      <el-row>
        <el-col :span="6">
          <el-button type="danger" size="medium" @click="DeleteUsers">批量删除</el-button>
          <el-button type="primary" size="medium" @click="Add()">新增</el-button>
        </el-col>
      </el-row>
    </div>
    <!--   添加的对话框  新增-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

        <el-form-item label="用户名" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="ruleForm.password" placeholder="不填默认为123"></el-input>
        </el-form-item>

        <el-form-item label="身份证" prop="idCard">
          <el-input v-model="ruleForm.idCard"></el-input>
        </el-form-item>


        <el-form-item label="email" prop="email">
          <el-input type="email" v-model="ruleForm.email"></el-input>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input type="tel" v-model="ruleForm.phone"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
  </span>
    </el-dialog>
    <br>

    <!--   user表格-->
    <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        :row-class-name="tableRowClassName">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="id"
          align="center"
          label="用户id">
      </el-table-column>
      <el-table-column
          prop="username"
          label="姓名"
          align="center">
      </el-table-column>

      <el-table-column
          prop="idCard"
          align="center"
          label="身份证">
      </el-table-column>
      <el-table-column
          prop="phone"
          align="center"
          label="手机号">
      </el-table-column>
      <el-table-column
          prop="email"
          align="center"
          label="邮箱">
      </el-table-column>
<!--      格式化时间 YYYY-MM -->
      <el-table-column
          prop="created"
          label="创建时间"
          align="center"
          width="180">
        <template slot-scope="scope">
          <span>{{dayjs(scope.row.created).format('YYYY-MM-DD HH:mm:ss')}}</span>
        </template>
      </el-table-column>

      <el-table-column
          label="操作"
          align="center">
        <template slot-scope="scope">
          <el-row>
            <el-button type="primary" size="mini" @click="selectOne(scope.row.id)">修改</el-button>
            <el-button type="danger" size="mini" @click="delOne(scope.row.id)">删除</el-button>
          </el-row>
        </template>
      </el-table-column>

    </el-table>

    <!--   分页-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10,15, 20]"
        :page-size="5"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>
</template>
<script>
import dayjs from "dayjs";

export default {
  name: "c3",

  /* 定义初始化变量 */
  data() {
    return {
      tableData: [{
        id: 1,
        username: '王小虎',
        idCard: '123456789',
        phone: '123456789',
        email: '123456789@qq.com',
        created: '2019-10-10 10:10:10',
      }, ],
      addOrUpdate: true,//true为添加，flase为修改
      selectIDS: [],//选中的ID
      //当前页
      currentPage: 1,
      total: 10,
      pageSize: 5,
      dialogVisible: false,//对话框
      //复选框选中的数据
      multipleSelection: [],

      // 搜索表单
      formInline: {
        user: '',

      },
      ruleForm: {
        username: '',
        password: '',
        idCard: '',
        email: '',
        phone: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { message: '请输密码', trigger: 'blur' }
        ],
        idCard: [
          { required: true, message: '请填写身份证', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请填写email', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请填写手机号', trigger: 'blur' }
        ],
      },
    }
  },

  /* 定义事件 */
  methods: {
    dayjs,
    delOne(id){
      this.selectIDS.push(id);
      this.$axios.delete('/user', {
        data: JSON.stringify(this.selectIDS)
      })
          .then((resp) => {
            console.log(resp);
            this.getAllUserPage();
          });
    },
    DeleteUsers() {
      if (this.multipleSelection.length === 0 ) {
        this.$message({
          message: '请先选择要删除的数据',
          type: 'warning'
        });
        return;
      }
      for (let i = 0; i < this.multipleSelection.length; i++) {
        this.selectIDS.push(this.multipleSelection[i].id);
        console.log(this.selectIDS);
        // console.log(JSON.stringify(this.selectIDS));
      }
      //发送axios批量删除 后台以数组方式接受
      this.$axios.delete('/user', {
        data: JSON.stringify(this.selectIDS)
      })
          .then((resp) => {
            console.log(resp);
            this.getAllUserPage();
          });
    },
    selectOne(id){
      this.$axios.get(`/user/${id}`).then((resp) => {
        this.ruleForm = resp.data.data;
        this.dialogVisible = true;
        this.addOrUpdate = false;
      });
    },
    Add(){
      this.dialogVisible = true;
       this.addOrUpdate = true;
    },
    getAllUserPage(){
        this.$axios.get(`/user/${this.currentPage}/${this.pageSize}`).then((resp) => {
          this.tableData=resp.data.data.records;
          this.total=resp.data.data.total;
          // console.log(this.tableData);
        });
      },

    tableRowClassName({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
//    分页
    handleSizeChange(val) {
      //重新设置每页显示的条数
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.getAllUserPage();
    },
    handleCurrentChange(val) {
      //重新设置当前页
      //console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.getAllUserPage();

    },
    // 复选框选中行执行
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(this.multipleSelection);
    },
    onSubmit(formName) {
          this.$axios.get(`/user/${this.currentPage}/${this.pageSize}`,{
            params:{
              username:this.formInline.user,
            }
          }).then((resp) => {
            this.tableData=resp.data.data.records;
            this.total=resp.data.data.total;
            // console.log(this.tableData);
          });
    },
    // DeleteFlight() {
    //
    //
    //   if (this.multipleSelection.length === 0 ) {
    //     this.$message({
    //       message: '请先选择要删除的数据',
    //       type: 'warning'
    //     });
    //     return;
    //   }
    //
    //   for (let i = 0; i < this.multipleSelection.length; i++) {
    //     this.selectIDS.push(this.multipleSelection[i].flightId);
    //     // console.log(this.selectIDS);
    //     // console.log(JSON.stringify(this.selectIDS));
    //   }
    //   //发送axios批量删除 后台以数组方式接受
    //   this.$axios.delete('/flight', {
    //     data: JSON.stringify(this.selectIDS)
    //   })
    //       .then((resp) => {
    //         console.log(resp);
    //         this.getByPageFlight();
    //       });
    //
    // },




    // 添加 修改
    submitForm(formName) {
      if (this.addOrUpdate){//添加
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post('/user',this.ruleForm).then((resp) => {
              // console.log(resp);
              this.dialogVisible = false;
              this.getAllUserPage();
            });
            //清空表单
            this.resetForm(formName);
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }else {
        this.$refs['ruleForm'].validate((valid) => {
          if (valid) {
            this.$axios.put('/user',this.ruleForm).then((resp) => {
              // console.log(resp);
              this.dialogVisible = false;
              this.getAllUserPage();
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      }

    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

  },

  mounted() {
    this.getAllUserPage()
  },

}
</script>

<style scoped>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
.C1View{
  text-align: center;
}



/deep/.el-table .warning-row {
  background: oldlace;
}

/deep/.el-table .success-row {
  background: #f0f9eb;
}

/deep/.demo-table-expand {
  font-size: 0;
}
/deep/.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
/deep/.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>