import { createStore } from 'vuex'

export default createStore({
  state: {
    flight:'',
    token: '',
    ticketData:{
      from: '',
      to: '',
      time: '',
      num: '1'
    }
  },
  getters: {
  },
  mutations: {

    setSearchData(state,data){
      state.flight = data
      localStorage.setItem('flight', data)
    },


    CHANGE_Data(state,data){
      state.ticketData = data
    },
    SET_TOKEN: (state, token) => {
      state.token = token
      localStorage.setItem('token', token)
    },
    restState:(state)=>{
      state.token='';
    },
  },
  actions: {
  },
  modules: {
  }
})
