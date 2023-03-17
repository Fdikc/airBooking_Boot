<template>
  <el-tabs class="tabInner" v-model="editableTabsValue" type="card" closable @tab-remove="removeTab">
    <el-tab-pane
        v-for="(item, index) in editableTabs"
        :key="item.name"
        :label="item.title"
        :name="item.name"
    >
    </el-tab-pane>
  </el-tabs>

</template>

<script>
export default {
  name: "Tabs",
  data() {
    return {
      // editableTabsValue: this.$store.state.editableTabsValue,
      // editableTabs: this.$store.state.editableTabs,
    }
  },
  methods: {
    removeTab(targetName) {
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }

      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    }
  },
  computed:{
    editableTabs:{
      get(){
        return this.$store.state.editableTabs;
      },
    },
    editableTabsValue:{
      get(){
        return this.$store.state.editableTabsValue;
      }
    }

  },
}
</script>

<style scoped>

/*.el-tabs__nav {*/
/* !* 设置优先级*!*/
/*  background-color: #a60101 !important;*/

/*}*/

.tabInner{
  /*缩小到左上角*/
  /*position: absolute;*/
  position:relative;
  top: -60px;
  left: -5px;
  height: 50%;


  /*background-color: #41e32f;*/
  padding: 0;
  margin: 0;
}
div.tabInner.el-tabs.el-tabs--card.el-tabs--top{
  background-color: #340101;
  padding: 0;
  margin: 0;
  /*width: 50px;*/
  height: 10px;
}
/deep/.el-tabs__nav-scroll {
  overflow: hidden;
  height: 100px;
}
div.el-tabs__nav-scroll{
  background-color: #340101;
  padding: 0;
  margin: 0;
  /*width: 50px;*/
 height: 10px;
}
</style>