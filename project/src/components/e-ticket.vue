<!--  -->
<template>
  <div class="ticket">
    <ul>
      <li v-for="(item,index) in menu" :key="index">
        <img class="icon" src="data:image/png;base64,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" alt="">
        {{ item }}
      </li>
    </ul>
    <el-tabs v-model="activeName" class="demo-tabs">
      <el-tab-pane label="机票预订" name="first">
        <div class="title"><span>嗨，您想去哪里？</span> <span></span></div>
        <div class="input-box">
          <div class="inputs-group">
            <einput ></einput>
            <einput :placeholder="'到达城市'"></einput>
          </div>
          <div class="inputs-group">
            <etime :focusText="'去程日期'"  :placeholder="'去程日期'"></etime>
          </div>
        </div>
        <div class="input-box">
          <div class="inputs-group">
            <eselect></eselect>
          </div>
          <div class="inputs-group">
            <div id="ticket" class="button button-large button-radius button--main f18 mB20" @click="search">搜索
                <!--{en} search-->
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script setup>
  import { ref, reactive, toRefs, computed } from 'vue'
  import { useStore} from 'vuex'
  import { useRouter } from 'vue-router'
  import api from "../api/api";
import einput from './e-input'
import etime from './e-time'
import eselect from './e-select'
const menu = ['预定行程']
const activeName = 'first'
let store = useStore()

let tdata = computed(() => store.state.ticketData)

let router = useRouter()
function search() {
  // console.log(tdata.value)
  // from: '',
  //     to: '',
  //     time: '',
  //     num: '1'
  // console.log("ffffffffffff"+tdata.value.from)
  api.postData('/flight/ticket', tdata.value).then(res => {
    // console.log(res.data)
    store.commit('setSearchData', JSON.stringify(res.data))
  })



  router.push('/hangban')
  // window.open('http://127.0.0.1:8080/#/hanban')
}

</script>
<style >
.el-tabs__item.is-active {
    color: #1e385a !important;
    font-size: 18px;
}
.el-tabs__active-bar {
  background-color: #f4791b !important; 
  height: 4px !important;
}
</style>
<style lang='scss' scoped>

.ticket{
  position: relative;
    width: 1200px;
    min-height: 351px;
    margin: -120px auto 0 auto;
    background-color: #fff;  //f4791b
    padding:  0 30px 30px 30px;
    z-index: 999;
    ul{
      margin-bottom: 10px;
      li{
        display: flex;
        align-items: center;
        list-style: none;
        width: 300px;
        height: 52px;
        color: #1e385a;
        font-size: 18px; 
      }
    }
    .icon{
      width: 26px;
      margin-right: 15.5px;
      
    }
    .title{
      color: #1e385a;
      font-size: 19px; 
      padding: 0 0 15px 0;
    }
    .input-box{
      display: flex;
    }
    .inputs-group{
      display: flex;
      margin-right: 50px;
      margin-bottom: 30px;
    }
    .button--main {
    color: #fff;
    background-color: #f4791b;
    width: 300px;
    height: 64px;
    text-align: center;
    line-height: 64px;
    font-size: 18px;
    cursor: pointer;
}
.button-radius {
    border-radius: 2px 2px 24px 2px;
}

}
</style>