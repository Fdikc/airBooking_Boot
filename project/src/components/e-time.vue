<!--  -->
<template>
    <div class="input-group" :class="{'is-fouce': isFocus}">
      <span class="focus-text" v-if="isFocus">{{ focusText }}</span>
      <!-- <input type="text" class="input-text" :placeholder="placeholder" @focus="getFocus" @blur="loseFocus"> -->
      <el-date-picker
        v-model="value1"
        value-format="YYYY-MM-DD"
        type="date"
        placeholder="去程日期"
        :clearable="false"
        :disabled-date="disabledDate"
        @focus="getFocus"
        @change="chooseTime"
      />
  </div>
  </template>
  <script setup>
  import { ref, reactive, toRefs, computed } from 'vue'
  import { useStore} from 'vuex'
  var props = defineProps({
      focusText:{
          type: String,
          default: '请输入城市名或机场三字码'
      },
      placeholder:{
          type: String,
          default: '去程日期'
      }
  })
  
  var { placeholder, focusText } = toRefs(props)
  let store = useStore()

  let tdata = computed(() => store.state.ticketData)
  const value1 = ref('')

  const disabledDate = (time) => {
    return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
    }
  const date = new Date()
  const Y = date.getFullYear()
  const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
  const D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  value1.value= Y + '-' + M + '-' + D
  tdata.value.time = value1.value


  let isFocus = ref(true)
  function getFocus() {
      isFocus.value = true
      console.log('123456')
  }
  function chooseTime(time) {
    console.log(time)
    tdata.value.time = time
  }

  </script>
  <style>
   .el-input__wrapper{
        box-shadow: none !important;
        width: 100%;
        height: 100%;
        border: none;
        outline: none;
        box-sizing: border-box;
        padding: 0px 22px 0 10px;
        font-size: 18px;
   }
   .el-input{
    line-height: 100%;
   }
   /* .el-input__wrapper:hover{
    box-shadow: none;
   } */
  /* .is-focus{
        box-shadow: none;
        width: 100%;
            height: 100%;
            border: none;
            outline: none;
            box-sizing: border-box;
            padding: 0px 22px 0 10px;
            font-size: 18px;
   } */

</style>

  <style lang='scss' scoped>
      .inputs-group{
        display: flex;
        .input-group{
          width: 283px;
          height: 64px;
          border: 1px solid #9e9e9e;
          box-sizing: border-box;
          position: relative;
          display: flex;
          flex-direction: column;
          justify-content: center;
          &.is-fouce{
              border: 1px solid #000;
          }
          .focus-text{
              display: block;
              padding: 5px 0px 0 10px;
              color: #386098;
          }
          .input-text{
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
            box-sizing: border-box;
            padding: 0px 22px 0 10px;
            font-size: 18px;
            &:focus{
              height: 60%;
            }
          }
          .close-icon{
              position: absolute;
              right: 10px;
              top: 20px;
              cursor: pointer;
          }
        }
      }
  </style>