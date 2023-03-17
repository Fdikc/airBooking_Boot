<!--  -->
<template>
    <div class="box">
        <div class="input-group" :class="{'is-fouce': isFocus}">
            <span class="focus-text" v-if="isFocus">{{ focusText }}</span>
            <el-popover placement="bottom" :width="580" trigger="click">
                <template #reference>
                    <input type="text" class="input-text" :value="placeholder == '出发城市' ? tdata.from : tdata.to" :placeholder="placeholder" @focus="getFocus" @blur="loseFocus">
                </template>
                <div class="box">
                    <div class="citySelector">
                        <div class="country-tabs">
                            <div class="country-tab country-tab--active" data-key="gn">国内<!--{en} Domestic--></div>
                            <div class="country-tab country-tab--gw" data-key="gw">国际/中国港澳台 <!--{en} International/Hong Kong,China/Macao,China/Taiwan,China--></div>
                        </div>
                        <ul class="cityTabs">
                            <li class="city-tab on" data-key="hot">热门城市 <!--{en} Popular--></li>
                            <li class="city-tab" data-key="abcd">ABCD</li>
                            <li class="city-tab" data-key="efghij">EFGHIJ</li>
                            <li class="city-tab" data-key="klmno">KLMNO</li>
                            <li class="city-tab" data-key="pqrst">PQRST</li>
                            <li class="city-tab" data-key="uvwxyz">UVWXYZ</li>
                        </ul>
                        <div class="citys">
                            <span class="city-item" v-for="(val, index) in citys" :key="index" @click="choose(val)">{{val}}</span>
                        </div>
                    </div>
                </div>
            </el-popover>
            <div v-if="isFocus" class="close-icon">
                X
            </div>
        </div>
    </div>

</template>
<script setup>
import { ref, reactive, toRefs, onMounted, computed } from 'vue'
import { useStore} from 'vuex'
import { Edit } from '@element-plus/icons-vue'
import { conforms } from 'lodash';
import { useRoute } from 'vue-router';

var props = defineProps({
    focusText:{
        type: String,
        default: '请输入城市名或机场三字码'
    },
    placeholder:{
        type: String,
        default: '出发城市'
    }
})

var { placeholder, focusText } = toRefs(props)

let store = useStore()

let tdata = computed(() => store.state.ticketData)

let citys = reactive([
 '济南', '青岛'
])
function choose(val){
    console.log(placeholder.value)
    placeholder.value == '出发城市' ? tdata.value.from = val : tdata.value.to = val
}
let isFocus = ref(false)
function getFocus() {
    isFocus.value = true
}
function loseFocus() {
    isFocus.value = false
}

</script>
<style lang='scss' scoped>
.box{
    position: relative;
    .citySelector{
        width: 560px;
        height: 342px;
        border: 1px solid ;
        .country-tabs{
            width: 98%;
            margin: 0 auto;
            height: 2.4rem;
            overflow: hidden;
            display: table;
            background: #f2faff;
            border-radius: 2px;
            border: 1px solid #b5ccde;
            .country-tab{
                width: 50%;
                display: table-cell;
                line-height: 1.2rem;
                vertical-align: middle;
                text-align: center;
                padding: 3px 4px;
                cursor: pointer;
                font-size: .7994rem;
                color: rgba(30, 56, 90, .4);
                border-radius: 2px;
                border: 3px solid #fff;
            }
            .country-tab--active {
                background: #1e385a;
                color: #fff;
            }
        }
        .cityTabs{
            list-style: none;
            padding: 0 .6rem;
            line-height: 2.5rem;
            background: #fff;
            border-bottom: 1px solid #b5ccde;
            font-size: .7994rem;
            color: rgba(30, 56, 90, .4);
            white-space: nowrap;
            .city-tab {
                display: inline-block;
                padding-left: .7rem;
                padding-right: .7rem;
                cursor: pointer;
            }
            .on {
                position: relative;
                color: #1e385a;
                border-bottom: 4px solid #f4791b;
            }
        }
        .citys {
            height: 10.6rem;
            padding: .6rem;
            overflow-y: auto;
            .city-item {
                display: inline-block;
                width: 25%;
                text-align: center;
                height: 2.1rem;
                line-height: 2.1rem;
                padding-right: .5rem;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                cursor: pointer;
            }
        }
    }
}

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
            display: inline-block;
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
</style>