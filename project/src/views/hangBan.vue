<!--  -->
<template>
    <HeaderTop></HeaderTop>
  <!--   表格-->
  <el-table
      :data="tableData"
      style="width: 100%"
     >
    <el-table-column type="expand">
      <template v-slot="props">
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
      <template v-slot="slotProps">
        <el-row>
<!--          如果flightBaseNumber>0,显示按钮-->
          <el-button v-if="slotProps.row.flightBaseNumber>0" type="success" size="mini"
                     @click="baseFlight(slotProps.row.flightId)">经济舱</el-button>
<!--          <el-button type="success" size="mini" @click="baseFlight(slotProps.row.flightId)">经济舱</el-button>-->
          <el-button v-if="slotProps.row.flightHighNumber>0" type="danger" size="mini" @click="highFlight(slotProps.row.flightId)">头等舱</el-button>
        </el-row>
      </template>
    </el-table-column>


  </el-table>


<!--  购买对话框-->
  <el-dialog
      v-model="dialogVisible"
      title="购票用户"
      width="30%"
  >
    <el-form
        ref="formRef"
        :model="dynamicValidateForm"
        label-width="80px"
        class="demo-dynamic"
    >
<!--      <el-form-item-->
<!--          prop="name"-->
<!--          label="姓名"-->
<!--          :rules="[-->
<!--        {-->
<!--          required: true,-->
<!--          message: '请输入姓名',-->
<!--          trigger: 'blur',-->
<!--        },-->
<!--      ]"-->
<!--      >-->
<!--        <el-input v-model="dynamicValidateForm.name" />-->
<!--      </el-form-item>-->
      <el-form-item
          v-for="(name, index) in dynamicValidateForm.name"
          :key="name.key"
          :label="'姓名' + index"
          :prop="'name.' + index + '.value'"
          :rules="{
        required: true,
        message: '姓名 can not be null',
        trigger: 'blur',
      }"
      >
        <el-input v-model="name.value" />
<!--        <el-button class="mt-2" @click.prevent="removeDomain(name)"-->
<!--        >Delete</el-button-->
<!--        >-->
      </el-form-item>
      <el-form-item
          v-for="(idCard, index) in dynamicValidateForm.idCard"
          :key="idCard.key"
          :label="'身份证' + index"
          :prop="'idCard.' + index + '.value'"
          :rules="{
        required: true,
        message: '身份证 can not be null',
        trigger: 'blur',
      }"
      >
        <el-input v-model="idCard.value" />
        <el-button class="mt-2" @click.prevent="removeDomain(idCard)"
        >Delete</el-button
        >
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="submitForm(formRef)">Submit</el-button>
        <el-button @click="addDomain">增加用户</el-button>
        <el-button @click="resetForm(formRef)">Reset</el-button>
      </el-form-item>
    </el-form>
<!--    <template #footer>-->
<!--      <span class="dialog-footer">-->
<!--        <el-button @click="dialogVisible = false">Cancel</el-button>-->
<!--        <el-button type="primary" @click="dialogVisible = false">-->
<!--          Confirm-->
<!--        </el-button>-->
<!--      </span>-->
<!--    </template>-->
  </el-dialog>

  </template>
  <script setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue'
  import HeaderTop from '@/components/headerTop.vue'
  import api from '@/api/api'
  import {useStore} from "vuex";

  import {DynamicSizeList, FormInstance} from 'element-plus'
  import { ElForm,ElMessageBox } from 'element-plus'
  let store = useStore()
  let tableData = reactive([])





 //  dialogVisible
  let dialogVisible = ref(false)

  const form = reactive({
    name: '',
    idCard: '',
  })


  const formRef = ref(null)
  //  dynamicValidateForm
  const dynamicValidateForm  = reactive({
    // name: '',
    idCard: [
      {
        key: 1,
        value: '',
      },
    ],
    name: [
      {
        key: 1,
        value: '',
      },
    ],
  })
  //定义一个数组
  const orderList = reactive([])

  //  submitForm   error
  const submitForm=()=>{
    formRef.value.validate((valid) => {
      if (valid) {
        console.log(dynamicValidateForm)
        api.postData('/order/PreparePay',JSON.stringify(dynamicValidateForm)).then(res=>{
          orderList.push(res.data)
          if(res.code===200){
            ElMessageBox.alert('购票成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                dialogVisible = false
              }
            });
          }
        })
      } else {
        console.log('error submit!!')
        return false
      }
    })
  }
  //resetForm  error
  const resetForm=()=>{
    formRef.value.resetFields()
  }


  //  addDomain
  const addDomain = () => {
    dynamicValidateForm.name.push({
      key: Date.now(),
      value: '',
    })

    dynamicValidateForm.idCard.push({
      key: Date.now(),
      value: '',
    })
    //同时添加两个

    // this.form.dynamicItem.push({
    //   name: '',
    //   phone: ''
    // })
  }
  //  removeDomain
  const removeDomain = (item) => {
    const index = dynamicValidateForm.idCard.indexOf(item)
    const indexName = dynamicValidateForm.name.indexOf(item)
    if (index !== -1) {
      dynamicValidateForm.idCard.splice(index, 1)
      dynamicValidateForm.name.splice(indexName, 1)
    }
    // this.form.dynamicValidateForm.splice(index, 1)
  }






















 //  baseFlight
  function baseFlight(flightId){
    api.getData('/flight/user/'+flightId+'/1').then(res=>{
      if(res.code===200) {
        dialogVisible.value = true
      }
  })
  }
  //  highFlight
  function highFlight(flightId){
    api.getData('/flight/user/'+flightId+'/0').then(res=>{
      if(res.code===200) {
        dialogVisible.value = true
      }
    })
  }


 function getSearchFlight(){

    //获取store中的flight数据
   let flight=localStorage.getItem("flight")

    console.log("哈flight哈哈哈哈"+flight)
    //将flight数据转换为json对象
    let flightObj = JSON.parse(decodeURIComponent(flight))
    // console.log("哈哈哈obj哈哈"+flightObj[0].flightStartAirport)
    // //将flightObj中的数据赋值给tableData
    tableData = flightObj

   console.log("哈哈tableData哈哈哈"+tableData)
    // let flight = store.state.flight
    //  console.log("哈111哈哈哈哈"+flight)
   // store.state.flight

   // let flightObj = JSON.parse(decodeURIComponent(flight))
   // console.log("哈哈哈哈哈"+flightObj)

 }
 getSearchFlight()

  </script>
  
  <style scoped>

  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-left: 80px;
    margin-bottom: 0;
    width: 40%;
  }
  .dialog-footer button:first-child {
    margin-right: 10px;
  }
  </style>
  
  <style lang='scss' scoped>
  
  </style>
  