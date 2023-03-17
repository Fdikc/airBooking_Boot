<!--  -->
<template>
    <div class="box">
        <div class="input-group" :class="{'is-fouce': isFocus}">
            <span class="focus-text">{{ '乘客' }}</span>
            <el-popover placement="bottom" :width="400" trigger="click">
                <template #reference>
                   <div  class="input-text">
                     {{ people }} 成人
                   </div>
                </template>
                <div class="box">
                  成人  <el-input-number v-model="people" :min="1"  @change="handleChange" />
                </div>
            </el-popover>
            <div  class="close-icon">
                >
            </div>
        </div>
    </div>

</template>
<script setup>
  import { ref, reactive, toRefs, computed } from 'vue'
  import { useStore} from 'vuex'
let people = ref(0)
let store = useStore()
let tdata = computed(() => store.state.ticketData)
let isFocus = ref(false)
function handleChange() {
    tdata.value.num = people.value
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
        width: 390px;
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