<template>
  <div class="test">
ttttest11111
  </div>
</template>

<script>
  export default {
    name: "Test",

    /* 定义初始化变量 */
    data() {
      return {

      }
    },

    /* 定义事件 */
    methods: {
      // getTest(){
      //   this.$axios.get('sys/user').then(res=>{
      //     console.log(res)
      //   })
      // }

      getTest(){
        this.$axios.get('/sys/user').then(res=>{
          console.log(res)
        })
      }
      },

    /* vue生命周期函数，
     当页面加载完成就会执行的方法 */
    created() {
        this.getTest()
    }

  }
</script>

<style scoped>

</style>