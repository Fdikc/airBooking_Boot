<template>
    <div>
      <!--
           Order订单管理
     -->
      <br>
      <!--   横向表单-->
      <div class="C1View">
        <el-row>
          <el-col :span="13">
            <el-form :inline="true" :model="formInline"  ref="formInline" class="demo-form-inline">
              <el-form-item label="搜索" prop="idCard">
                <el-input v-model="formInline.idCard" placeholder="准确身份证"></el-input>
              </el-form-item>
              <el-form-item label="航班号">
                <el-autocomplete
                    @focus="focus"
                    class="inline-input"
                    v-model="state2"
                    :fetch-suggestions="querySearch"
                    placeholder="请输入内容"
                    @select="handleSelect"
                ></el-autocomplete>
              </el-form-item>
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

          <el-form-item label="订单名" prop="orderName">
            <el-input v-model="ruleForm.orderName"></el-input>
          </el-form-item>

          <el-form-item label="航班号">
              <el-autocomplete
                  class="inline-input"
                  v-model="state1"
                  :fetch-suggestions="querySearch"
                  placeholder="请输入内容"
                  @select="handleSelect"
              ></el-autocomplete>
          </el-form-item>

          <el-form-item label="用户名" prop="userName">
            <el-input v-model="ruleForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="身份证" prop="idCard">
            <el-input v-model="ruleForm.idCard"></el-input>
          </el-form-item>

          <el-form-item label="等级">
            <el-select v-model="ruleForm.grade" placeholder="请选择航班类型">
              <el-option label="经济舱" value="0"></el-option>
              <el-option label="头等舱" value="1"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="价格" prop="price">
            <el-input type="number" v-model="ruleForm.price"></el-input>
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
            prop="orderName"
            label="订单名"
            align="center">
        </el-table-column>
        <el-table-column
            prop="user.username"
            align="center"
            label="用户id">
        </el-table-column>
        <el-table-column
            prop="flight.flightNumber"
            align="center"
            label="航班号">
        </el-table-column>
        <el-table-column
            prop="userName"
            align="center"
            label="用户姓名">
        </el-table-column>
        <el-table-column
            prop="idCard"
            align="center"
            label="身份证">
        </el-table-column>

<!--        如果grade===1 经济舱-->
        <el-table-column
            prop="grade"
            align="center"
            label="等级">
          <template slot-scope="scope">
            <span v-if="scope.row.grade===1">经济舱</span>
            <span v-else-if="scope.row.grade===0">头等舱</span>
          </template>
        </el-table-column>

<!--        <el-table-column-->
<!--            prop="grade"-->
<!--            align="center"-->
<!--            label="等级">-->
<!--        </el-table-column>-->
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
  name: "c4",

  /* 定义初始化变量 */
  data() {
    return {

      flights:[],
      restaurants: [],
      state1: '',
      state2: '',
      searchFlightId: '',

      tableData: [{
        id: '',
        orderName: '北京-天津',
        user: {},
        flight: {},
        userName: '张三',
        idCard: '123456789',
        grade: '0',
        price: '100',
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
        idCard: '',

      },
      ruleForm: {
        orderName: '',
        userName: '',
        idCard: '',
        flightId: '',
        grade: '',
        price: '',
      },
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
        ],
        idCard: [
          { required: true, message: '请填写身份证', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请填写价格', trigger: 'blur' }
        ],
      },
    }
  },

  /* 定义事件 */
  methods: {
    dayjs,
    focus() {
     this.loadAll();
    },
    delOne(id){
      this.selectIDS.push(id);
      this.$axios.delete('/order', {
        data: JSON.stringify(this.selectIDS)
      })
          .then((resp) => {
            console.log(resp);
            this.getAllOrderPage();
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
      this.$axios.delete('/order', {
        data: JSON.stringify(this.selectIDS)
      })
          .then((resp) => {
            console.log(resp);
            this.getAllOrderPage();
          });
    },
    selectOne(id){
      // console.log(id);
      this.loadAll();
      this.$axios.get(`/order/${id}`).then((resp) => {
        this.ruleForm = resp.data.data;
        this.dialogVisible = true;
        this.addOrUpdate = false;
      });
    },
    Add(){
      this.loadAll();
      this.dialogVisible = true;
      this.addOrUpdate = true;
    },
    getAllOrderPage(){
      this.$axios.get(`/order/${this.currentPage}/${this.pageSize}`).then((resp) => {
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
      this.getAllOrderPage();
    },
    handleCurrentChange(val) {
      //重新设置当前页
      //console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.getAllOrderPage();

    },
    // 复选框选中行执行
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(this.multipleSelection);
    },
    onSubmit(formName) {

      if (this.formInline.idCard === '' && this.searchFlightId === '') {
        this.$message({
          message: '请先输入查询条件',
          type: 'warning'
        });
        // this.getAllOrderPage();
        return;
      }
      this.$axios.get(`/order/${this.currentPage}/${this.pageSize}`,{
        params:{
          idCard:this.formInline.idCard,
          flightId:this.searchFlightId,
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
            // console.log("form表单"+this.ruleForm);
            // console.log("state1"+this.state1)
            this.$axios.post('/order',this.ruleForm).then((resp) => {
              // console.log(resp);
              this.dialogVisible = false;
              this.getAllOrderPage();
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
            this.$axios.put('/order',this.ruleForm).then((resp) => {
              // console.log(resp);
              this.dialogVisible = false;
              this.getAllOrderPage();
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
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    loadAll() {
      this.$axios.get('/flight').then((resp) => {
        this.flights = resp.data.data;
        //给loadAll()方法赋值
        //value是flightId address是flightName
        for (let i = 0; i < this.flights.length; i++) {
          this.restaurants[i]={value:this.flights[i].flightNumber,flightId:this.flights[i].flightId};
        }
        // console.log(this.restaurants);
      });

    },
    handleSelect(item) {
      // console.log(item);
      this.ruleForm.flightId = item.flightId;
      this.searchFlightId=item.flightId;
    },



  },

  mounted() {
    this.getAllOrderPage()
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