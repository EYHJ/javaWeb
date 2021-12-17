<template>
  <el-container>
    <el-header height="40px">
      <topbar title="不知道叫什么名字商城" :backButton='true'></topbar>
    </el-header>

    <el-main>
      <div v-if="this.cart[0]">
        <div v-for="(item, i) in cart" :key="item" class="itemClass">
          <el-row :gutter="8">
            <el-col :span="4">
              <div class="imgClass">
                <router-link :to="'/commodity_'+item.id" class="aClass">
                  <el-image style="width: inherit;height: inherit;" :src="item.thumbnail" fit="fill"></el-image>
                </router-link>
              </div>
            </el-col>

            <el-col :span="16">
              <div class="infoClass">
              <router-link :to="'/commodity_'+item.id" class="aClass">
                <p class="text-line1">{{ item.name }}</p>
              </router-link>
              <p>
                <el-row align="middle" justify="space-between">
                  <el-col :span="12" style="text-align: center;">
                      <el-input-number style="width: auto;" v-model="item.amount" :min="1"/>
                  </el-col>
                  <el-col :span="12" style="font-size: larger;text-align: center;">￥{{ item.price*item.amount }}</el-col>
                </el-row>
              </p>
              </div>
            </el-col>

            <el-col :span="4">
              <div class="delClass">
                <el-button type="danger" @click="delItem(i)" circle plain>
                  <el-icon :size="36">
                    <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-365b8594="">
                      <path fill="currentColor" d="M352 192V95.936a32 32 0 0132-32h256a32 32 0 0132 32V192h256a32 32 0 110 64H96a32 32 0 010-64h256zm64 0h192v-64H416v64zM192 960a32 32 0 01-32-32V256h704v672a32 32 0 01-32 32H192zm224-192a32 32 0 0032-32V416a32 32 0 00-64 0v320a32 32 0 0032 32zm192 0a32 32 0 0032-32V416a32 32 0 00-64 0v320a32 32 0 0032 32z">
                      </path>
                    </svg>
                  </el-icon>
                </el-button>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div v-else style="width: 100%;margin-top: 10%;text-align: center;">
        购物车是空的
      </div>

    </el-main>

    <el-footer height="40px" v-if="this.cart[0]">
      <div class="footer">
        <div style="font-size: 27px;">
          总价：￥{{totalPrice}}
        </div>
        <div>
          <el-button type="primary" @click="preOrder">
            确认购买
          </el-button>
        </div>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
import { showMessage, sendRequest } from '@/util/api.js';
import { ElMessageBox } from 'element-plus'
import topbar from '@/components/topbar.vue';
export default {
  methods:{
    delItem(index) {
      this.cart.splice(index, 1);
      this.$store.commit('updateCartToSto');
    },
    preOrder() {
      if(!this.cart[0]){
        showMessage('error', '购物车是空的！');
        return;
      }
      if(!this.$store.state.userinfo){
        showMessage('error', '未登录！');
        return;
      }
      ElMessageBox.confirm(
              '是否确认购买?',
              'Warning',
              {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
              }
      ).then(()=>{this.order()});
    },
    order() {
      let orderObj={
        uid:this.$store.state.userinfo.account,
        list:[],
      };
      for (var i=0,len=this.cart.length; i<len; i++) {
        orderObj.list.push({
          id: this.cart[i].id,
          name: this.cart[i].name,
          amount: this.cart[i].amount,
        });
      }
      sendRequest('put', '/Order',orderObj)
      .then((res)=>{
        if(!res)
          return;
        showMessage('success', '购买成功，再逛逛吧');
        this.$store.commit('clearCart');
        this.$router.replace('/');
      })
      .catch((err)=>{
        console.log(err);
      })
    }
  },
  mounted() {
    this.$store.commit('updateCartFromSto');
  },
  computed:{
    cart() {
      return this.$store.state.cart;
    },
    totalPrice() {
      let val=0;
      for (let i=0,len=this.cart.length; i<len; i++){
        val+=this.cart[i].price*this.cart[i].amount;
      }
      return val;
    },
  },
  components:{
    topbar
  }
}
</script>

<style>
.footer{
  width: 100%;
  box-shadow: 0px -3px 3px #888888;
  display: flex;
  justify-content: space-between;
}
.itemClass {
  box-shadow: 0px 0px 3px #888888;
  margin-bottom: 7px;
  margin-left: auto;
  margin-right: auto;
  width: 80%;
}
.imgClass{
}
.infoClass{
  vertical-align: top;
  margin-left: 20px;
}
.delClass{
   margin-top: 20px;
}
.text-line1 {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
}
.aClass {
  text-decoration: none;
  color: #000000;
}
.aClass:hover {
  color:#0D84FF;
}
</style>
