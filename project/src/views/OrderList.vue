<template>
  <HeaderTop></HeaderTop>

  <div class="orderClass">
  <el-table
      :data="tableData"
      style="width: 100%"
  >
<!--    <el-table-column prop="id" label="订单号" width="180" />-->
    <el-table-column prop="orderName" label="订单名" width="180" />
    <el-table-column prop="price" label="价格" />
    <el-table-column prop="userName" label="姓名" />
    <el-table-column prop="idCard" label="身份证" />

<!--    如果statu==1  按钮显示已经支付    如果statu==0-->
    <el-table-column  prop="statu" label="订单状态" >
      <template v-slot="slotProps">
        <el-row>
          <el-button v-if="slotProps.row.statu===0" type="success" size="mini"
                     @click="payZFB(slotProps.row)">支付</el-button>
          <el-button v-if="slotProps.row.statu===1" type="danger" size="mini">已经支付</el-button>
        </el-row>
      </template>
    </el-table-column>
  </el-table>

  </div>

</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import HeaderTop from '@/components/headerTop.vue'
import api from '@/api/api'
import {useStore} from "vuex";

import {DynamicSizeList, FormInstance} from 'element-plus'
import { ElForm,ElMessageBox } from 'element-plus'

let tableData = ref([
    {
      id: '2016-05-03',
      orderName: 'Tom',
      price: 'No. 189, Grove St, Los Angeles',
    }
])
function payZFB(oneData) {
  // console.log('支付宝支付')
  // console.log(oneData)
  api.postData('/order/pay',{
    out_trade_no:oneData.id,
    subject:oneData.orderName,
    total_amount:oneData.price,
    body:"支付宝支付",
  }).then(res=>{
    console.log(res.data)

    document.write(res.data)

  })

}
function getAllOrder() {
  api.getData('/order/orderList').then(res => {
    tableData.value = res.data
    console.log(tableData)
  })
}


getAllOrder()
// onMounted(() => {
//   api.get('/order/orderList').then(res => {
//     tableData = res.data
//   })
// })









</script>

<style scoped>

</style>