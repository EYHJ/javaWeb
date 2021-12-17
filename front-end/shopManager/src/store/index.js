import { createStore } from 'vuex'

export default createStore({
  state: {
	userinfo:null,
	clist:null,
  },
  mutations: {
    setUserinfo(state, info){
      state.userinfo=info;
    },
    clearUserinfo(state){
      state.userinfo=null;
    },
    setClist(state, data){
      state.clist=data;
    },
	clearClist(state){
	  state.clist=null;
	}
  },
  actions: {
  },
  modules: {
  }
})
