import { createStore } from 'vuex'

export default createStore({
  state: {
    userinfo:null,
    clist:null,
    cart:[],
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
    clearCart(state){
      state.cart=[];
      sessionStorage.setItem("shopCart", []);
    },
    updateCartFromSto(state){
      var rcart=sessionStorage.getItem("shopCart");
      if(rcart)
        state.cart=JSON.parse(rcart);
      else
        state.cart=[];
    },
    updateCartToSto(state){
      var rcart=JSON.stringify(state.cart);
      if(rcart)
        sessionStorage.setItem("shopCart", rcart);
      else
        sessionStorage.setItem("shopCart", []);
    },
    addCartItem(state, item){
      state.cart.push(item);
      sessionStorage.setItem("shopCart", JSON.stringify(state.cart));
    }
  },
  actions: {
  },
  modules: {
  }
})
