<template>
 <div>
<!--
      航班管理
-->
   <br>
<!--   横向表单-->
<div class="C1View">
   <el-row>
     <el-col :span="18">
   <el-form :inline="true" :model="formInline"  :rules="rules_serach" ref="formInline" class="demo-form-inline">

     <el-form-item label="搜索" prop="user">
       <el-input v-model="formInline.user" placeholder="航班号"></el-input>
     </el-form-item>

     <el-form-item label="航班时间" required>
       <el-col :span="12">
         <el-form-item prop="date1">
           <el-date-picker type="date"   placeholder="选择日期" v-model="formInline.date1" style="width: 100%;"></el-date-picker>
         </el-form-item>
       </el-col>
       <el-col :span="12">
         <el-form-item prop="date2">
           <el-date-picker type="date" placeholder="选择日期" v-model="formInline.date2" style="width: 100%;"></el-date-picker>
         </el-form-item>
       </el-col>
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
       <el-button type="danger" size="medium" @click="DeleteFlight">批量删除</el-button>
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

       <el-form-item label="航班号" prop="flightNumber">
         <el-input v-model="ruleForm.flightNumber"></el-input>
       </el-form-item>

       <el-form-item label="起点城市">
         <v-distpicker :province="Prov"  :city="city"  @province="onChangeProv" @city="onChangeCity"></v-distpicker>
       </el-form-item>

       <el-form-item label="终点城市">
         <v-distpicker :province="ProvEnd"  :city="cityEnd" @province="onChangeProvEnd" @city="onChangeCityEnd"></v-distpicker>
       </el-form-item>
       <el-form-item label="始发机场" prop="flightStartAirport">
         <el-input v-model="ruleForm.flightStartAirport"></el-input>
       </el-form-item>

       <el-form-item label="到达机场" prop="flightEndAirport">
         <el-input v-model="ruleForm.flightEndAirport"></el-input>
       </el-form-item>


       <el-form-item label="开始时间：" prop="flightStartTime">
         <el-date-picker
             type="datetime"
             placeholder="选择日期"
             v-model="ruleForm.flightStartTime"
             value-format="yyyy-MM-dd HH:mm:ss">
         </el-date-picker>
       </el-form-item>

       <el-form-item label="到达时间：" prop="flightEndTime">
         <el-date-picker
             type="datetime"
             placeholder="选择日期"
             v-model="ruleForm.flightEndTime"
             value-format="yyyy-MM-dd HH:mm:ss">
         </el-date-picker>
       </el-form-item>
<!--       头等舱数量-->
        <el-form-item label="头等舱数量" prop="flightHighNumber">
          <el-input type="number" v-model="ruleForm.flightHighNumber"></el-input>
        </el-form-item>
<!--       头等舱价格-->
        <el-form-item label="头等舱价格" prop="flightHighPrice">
          <el-input type="number" v-model="ruleForm.flightHighPrice"></el-input>
        </el-form-item>
<!--       经济舱数量-->
        <el-form-item label="经济舱数量" prop="flightBaseNumber">
          <el-input type="number" v-model="ruleForm.flightBaseNumber"></el-input>
        </el-form-item>
<!--       经济舱价格-->
        <el-form-item label="经济舱价格" prop="flightBasePrice">
          <el-input type="number" v-model="ruleForm.flightBasePrice"></el-input>
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

<!--   表格-->
   <el-table
       :data="tableData"
       style="width: 100%"
       @selection-change="handleSelectionChange">
     <el-table-column type="expand">
       <template slot-scope="props">
         <el-form label-position="left" inline class="demo-table-expand">

           <el-form-item label="始发机场"  align="center">
             <span>{{ props.row.flightStartAirport }}</span>
           </el-form-item>
           <el-form-item label="到达机场"  align="center">
             <span>{{ props.row.flightEndAirport }}</span>
           </el-form-item>
           <el-form-item label="起飞时间" align="center">
             <span>{{ props.row.flightStartTime }}</span>
           </el-form-item>
           <el-form-item label="到达时间" align="center">
             <span>{{ props.row.flightEndTime }}</span>
           </el-form-item>
           <el-form-item label="头等舱价格" align="center">
             <span>{{ props.row.flightHighPrice }}</span>
           </el-form-item>
           <el-form-item label="经济舱价格" align="center">
             <span>{{ props.row.flightBasePrice }}</span>
           </el-form-item>
           <el-form-item label="ID" align="center">
             <span>{{ props.row.flightId }}</span>
           </el-form-item>

         </el-form>
       </template>
     </el-table-column>

     <el-table-column
         type="selection"
         width="55">
     </el-table-column>

     <el-table-column
         label="航班号"
         align="center"
         prop="flightNumber">
     </el-table-column>
     <el-table-column
         label="起点"
         align="center"
         prop="flightStartPlace">
     </el-table-column>
     <el-table-column
         label="终点"
         align="center"
         prop="flightEndPlace">
     </el-table-column>
     <el-table-column
         label="头等舱余票"
         align="center"
         prop="flightHighNumber">
     </el-table-column>
     <el-table-column
         label="经济舱余票"
         align="center"
         prop="flightBaseNumber">
     </el-table-column>


     <el-table-column
         label="操作"
         align="center">
       <template slot-scope="scope">
       <el-row>
         <el-button type="primary" size="mini" @click="updateFlight(scope.row.flightId)">修改</el-button>
          <el-button type="danger" size="mini" @click="delOne(scope.row.flightId)">删除</el-button>
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
import VDistpicker from 'v-distpicker'
import dayjs from "dayjs";

export default {
  name: "c1",
  components: { VDistpicker },

  /* 定义初始化变量 */
  data() {
    return {
      addOrUpdate: true,//true为添加，flase为修改


      addProv: '',//省
      addProvEnd: '',
      addCity: '',//市
      addCityEnd: '',
      Prov: '',//省
      ProvEnd: '',
      city: '',//市
      cityEnd: '',

      selectIDS: [],//选中的ID


      //当前页
      currentPage: 1,
      total: 10,
      pageSize: 5,

      dialogVisible: false,//对话框
      //复选框选中的数据
      multipleSelection: [],
      tableData: [{
        flightId: '11',
        flightNumber: '11',//航班号
        flightStartPlace: '11',//起点
        flightEndPlace: '11',//终点
        flightStartAirport: '11',
        flightEndAirport: '11',
        flightStartTime: '11',
        flightEndTime: '11',
        flightHighPrice: '11',
        flightHighNumber: '11',//头等舱余票
        flightBasePrice: '11',
        flightBaseNumber: '11',//经济舱余票
      },],
      // 搜索表单
      formInline: {
        user: '',
        date1: '',
        date2: '',
      },
      rules_serach: {
        user: [
          { required: true, message: '请输入', trigger: 'blur' },
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ]
      },
      ruleForm: {
        flightNumber: '',
        flightStartPlace: '',
        flightEndPlace: '',
        flightStartAirport: '',
        flightEndAirport: '',
        flightHighPrice: '',
        flightHighNumber: '',
        flightBasePrice: '',
        flightBaseNumber: '',
        flightStartTime: '',
        flightEndTime: '',
      },
      rules: {
        flightNumber: [
          { required: true, message: '请输入航班号', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        flightStartTime1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        flightStartTime2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        flightEndTime1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        flightEndTime2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        flightHighPrice: [
          { required: true, message: '请填写价格', trigger: 'blur' }
        ],
        flightHighNumber: [
          { required: true, message: '请填写余票', trigger: 'blur' }
        ],
        flightBasePrice: [
          { required: true, message: '请填写价格', trigger: 'blur' }
        ],
        flightBaseNumber: [
          { required: true, message: '请填写余票', trigger: 'blur' }
        ],
      },
    }
  },

  /* 定义事件 */
  methods: {
    Add(){
      this.dialogVisible=true
      this.addOrUpdate=true
    },

    updateFlight(flightId){
      this.dialogVisible = true;
      this.$axios.get('/flight/'+flightId).then((resp) => {
        // console.log(resp);
        this.ruleForm = resp.data.data;
        this.Prov= resp.data.data.flightStartPlace.split('-')[0];
        this.ProvEnd= resp.data.data.flightEndPlace.split('-')[0];
        this.city= resp.data.data.flightStartPlace.split('-')[1];
        this.cityEnd= resp.data.data.flightEndPlace.split('-')[1];
        this.ruleForm.flightStartTime = dayjs(resp.data.flightStartTime).format('YYYY-MM-DD HH:mm:ss');
        this.ruleForm.flightEndTime = dayjs(resp.data.flightEndTime).format('YYYY-MM-DD HH:mm:ss');
      // console.log(this.ruleForm);
      //   console.log('update'+this.ruleForm.flightStartPlace);
        this.addOrUpdate=false;
      });
      // this.$router.push({path:'/updateFlight',query:{flightId:flightId}});
    },

    delOne(id){
      this.selectIDS.push(id);
      this.$axios.delete('/flight', {
        data: JSON.stringify(this.selectIDS)
      })
          .then((resp) => {
            console.log(resp);
            this.getByPageFlight();
          });
    },

    //省市联动
    onChangeProv (e) {
      // console.log("省", e);
      this.addProv = e.value+'-';
      // this.Prov = e.value+'-'; //赋值给表单
    },
    onChangeCity (e) {
      this.ruleForm.flightStartPlace =this.addProv+e.value;
      // console.log("市", e.value);
    },
    onChangeArea (e) {
      // console.log("县", e);
    },
    onChangeProvEnd (e) {
      this.addProvEnd = e.value+'-';
      // console.log("省", e);
    },
    onChangeAreaEnd (e) {
      // console.log("县", e);
    },
    onChangeCityEnd (e) {
      this.ruleForm.flightEndPlace =this.addProvEnd+ e.value;
      // console.log("市", e);
    },

//    分页
    handleSizeChange(val) {
      //重新设置每页显示的条数
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.getByPageFlight();
    },
    handleCurrentChange(val) {
      //重新设置当前页
      //console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.getByPageFlight();

    },


    // 复选框选中行执行
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(this.multipleSelection);
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
      this.$axios.get(`/flight/${this.currentPage}/${this.pageSize}`,{
        params:{
          flightNumber:this.formInline.user,
          flightStartPlace:this.formInline.date1,
          flightEndPlace:this.formInline.date2,
         }
      }).then((resp) => {
        this.tableData=resp.data.data.records;
        this.total=resp.data.data.total;
        // console.log(this.tableData);
      });
      }});

    },
    DeleteFlight() {


          if (this.multipleSelection.length === 0 ) {
                  this.$message({
                    message: '请先选择要删除的数据',
                    type: 'warning'
                  });
                  return;
                }

      for (let i = 0; i < this.multipleSelection.length; i++) {
        this.selectIDS.push(this.multipleSelection[i].flightId);
        // console.log(this.selectIDS);
        // console.log(JSON.stringify(this.selectIDS));
      }
        //发送axios批量删除 后台以数组方式接受
        this.$axios.delete('/flight', {
          data: JSON.stringify(this.selectIDS)
        })
            .then((resp) => {
          console.log(resp);
          this.getByPageFlight();
        });

    },
    submitForm(formName) {
      if (this.addOrUpdate){//添加
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/flight',this.ruleForm).then((resp) => {
            // console.log(resp);
            this.dialogVisible = false;
            this.getByPageFlight();
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
            this.$axios.put('/flight',this.ruleForm).then((resp) => {
              // console.log(resp);
              this.dialogVisible = false;
              this.getByPageFlight();
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
    getAllFilght(){
      this.$axios.get('/flight').then((resp) => {
        this.tableData = resp.data.data;
        console.log(this.tableData);
      });
    },

    getByPageFlight(){
      this.$axios.get(`/flight/${this.currentPage}/${this.pageSize}`).then((resp) => {
         this.tableData=resp.data.data.records;
         this.total=resp.data.data.total;
        // console.log(this.tableData);
      });
    },
    },

  mounted() {
      this.getByPageFlight();
    // this.getAllFilght();
  },
  /* vue生命周期函数，
   当页面加载完成就会执行的方法 */
  created() {

  }

}
</script>

<style scoped>

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