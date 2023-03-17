<template>
  <el-container>

    <el-aside width="200px">
      <div style="height: 60px;background-color: #545C64">
        <img width="160px" height="60px" style="margin-left: 23px;margin-bottom: 35px"  src="air.png">
      </div>

      <el-menu
          router
          default-active="/index"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :unique-opened="true">

<!--&lt;!&ndash;        1&ndash;&gt;@click="selectMenu({name:'Index',title:'首页'})"-->
        <el-menu-item index="/index">
          <span slot="title">
            <i class="el-icon-s-home"></i>
            首页
          </span>
        </el-menu-item>

<!--        2-->
        <el-submenu index="2">
           <span slot="title">
             <i class="el-icon-s-home"></i>
            首页2
           </span>
          <el-menu-item index="/test">
           <span slot="title">
             <i class="el-icon-s-home"></i>
            首页21
           </span>
          </el-menu-item>
          <el-menu-item index="2-2">
            <span slot="title">
              <i class="el-icon-s-home"></i>
            首页22
            </span>
          </el-menu-item>
        </el-submenu>


        <template v-for="m1 of top">
          <el-submenu v-if="m1.children" :key="m1.id" :index="m1.path">
             <span slot="title">
              <i :class="m1.icon"></i>{{m1.name}}
            </span>
<!--            @click="selectMenu(m2)"-->
            <el-menu-item v-for="m2 of m1.children" :key="m2.id" :index="m2.path">
               <span slot="title">
              <i :class="m2.icon"></i>{{m2.name}}
                </span>
            </el-menu-item>
          </el-submenu>


          <el-menu-item v-else :key="m1.id" :index="m1.path">
            <span slot="title">
              <i :class="m1.icon"></i>{{m1.name}}
            </span>
          </el-menu-item>


        </template>


      </el-menu>


    </el-aside>

    <el-container>

      <el-header>
        <strong>航空机票后台管理系统</strong>
        <div class="header-avatar">
          <el-avatar size="medium" :src="avatar"></el-avatar>

          <el-dropdown>
            <span class="el-dropdown-link">
              {{name}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item >
                <router-link to="/m4">个人中心</router-link>
              </el-dropdown-item>
              <el-dropdown-item @click.native="logOut()">退出</el-dropdown-item>

            </el-dropdown-menu>
          </el-dropdown>

        </div>
      </el-header>
      <el-main>
<!--        标签页-->
<!--        <Tabs></Tabs>-->
        <router-view></router-view>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Tabs from "@/components/Tabs";



export default {
  name: "Home",
  components: {
    // Tabs
  },
  data(){
    return{
      avatar:'',
      top:[],
    }
  },
  computed:{
    name(){
      if (localStorage.getItem('name')!==null)
        return localStorage.getItem('name');
      return this.$store.state.name;
    }
  },
  methods: {
    getSessionRoutes(){
      const serversRouters=sessionStorage.getItem('serverRouters');
      const array=JSON.parse(serversRouters)
      // console.log('array')
      // console.log(array)
      const map=new Map()
      for (const obj of array){
        map.set(obj.id,obj);
      }
      const top =[];//存顶层元素
      for (const obj of array){
       const parent=map.get(obj.pid);
       if (parent){
         parent.children ??=[];
         parent.children.push(obj);
       }else {
         top.push(obj);
       }
      }
      // console.log(top)
      this.top=top;
    },
    getUserInfo(){
      let user=localStorage.getItem('user');
      if (user!==null) {
        //转成对象
        user = JSON.parse(user);
        this.avatar = user.avatar;
      }
    },
    logOut(){
      this.$axios.post('/logout').then(res=>{
        localStorage.clear();
        sessionStorage.clear();
        this.$store.commit('restState');
        this.$router.push('/');
      })
    },
    // selectMenu(item){
    //   this.$store.commit('addTab',item);
    // }


  },
  //页面加载完成后执行
  mounted() {
    this.getSessionRoutes();
    this.getUserInfo()
  }

}
</script>

<style scoped>



a{
  text-decoration: none;
}
.el-menu-vertical-demo{
  height: 91%;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}


.header-avatar{
  float: right;
  width: 150px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}


.el-container {
  margin: 0;
  padding: 0;
  height: 100%;
}

.el-header {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #545C64;
  color: #333;
  /*text-align: center;*/
  text-align: left;
  line-height: 130px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  /*text-align: center;*/
  line-height: 30px;
  padding: 0;
}


</style>