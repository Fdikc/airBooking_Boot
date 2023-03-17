import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    name:'',
    user:{},

    // editableTabsValue: 'Index',
    // editableTabs: [{
    //   title: '首页',
    //   name: 'Index',
    // }],

  },
  getters: {
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
      localStorage.setItem('token', token)
    },
    updateName(state,name){
      state.name=name;
      localStorage.setItem('name', name)
    },
    restState:(state)=>{
      state.token='';
    },
    setUser(state,user){
        state.user=user;
        localStorage.setItem('user', JSON.stringify(user))
    },
    // addTab(state,tab) {
    //   let index=state.editableTabs.findIndex(e=>e.name===tab.name)
    //   if(index===-1){
    //     state.editableTabs.push({
    //       title: tab.name,
    //       name: tab.name,
    //     });
    //   }
    //
    //
    //   state.editableTabsValue = tab.name;
    // },

  },
  actions: {
  },
  modules: {
  }
})
