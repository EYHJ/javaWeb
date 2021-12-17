<template>
  <el-container>
		<el-header height="40px">
      <topbar title="不知道叫什么名字商城" :backButton='true'></topbar>
		</el-header>

    <el-main>
      <el-row v-if="item.id" :gutter="32">
        <el-col :span="10" :offset="2" style="min-height:300px;max-height:480px;">
          <el-image :src="item.img_raw" style="height: 100%;" fit="scale-down"></el-image>
        </el-col>
        <el-col :span="10">
          <el-card>
            <template #header>
              <div class="nameClass">{{ item.name }}</div>
            </template>
            <el-row>
              <el-col :span="12">
                <div style="text-align: center;">
                  <span class="priceClass">￥{{ item.price }}</span>
                  <br/>
                  <el-input-number controls-position="right" style="width: 80%;" v-model="amount" :min="1" step-strictly/>
                </div>
              </el-col>
              <el-col :span="12">
                <div style="margin-left: 20%;">
                  <el-button @click="buyNow" style="margin-bottom: 5px;" type="primary">
                    立 即 购 买
                  </el-button>
                  <br/>
                  <el-button @click="addCart" type="primary">
                    加入购物车
                  </el-button>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="20" :offset="2" style="margin-top: 10px;">
          <el-card>
            <template #header>商品描述</template>
            <div>
              {{ item.describe }}
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div v-else style="text-align: center;margin-top:10%;">
        没有找到商品信息！
      </div>
    </el-main>
  </el-container>
</template>

<script>
  import { getRequest } from '@/util/api.js';
  import { getThumbnailFromRaw } from '@/util/image.js';
  import { showMessage } from '@/util/api.js';
  import topbar from '@/components/topbar.vue';
  export default {
    props: {
      id: String,
    },
    data() {
      return {
        item: {},
        amount: 1,
      }
    },
    methods:{
      addCart() {
        if(!this.item.id)
          return false;
        let cartItem={
          id: this.item.id,
          name: this.item.name,
          price: this.item.price,
          thumbnail: getThumbnailFromRaw(this.item.img_raw, 128),
          amount: this.amount,
        }
        this.$store.commit('addCartItem', cartItem);
        return true;
      },
      buyNow() {
        if(this.addCart()) {
          this.$router.push('/order');
        }
        else {
          showMessage('error', '状态错误，请刷新！');
        }
      }
    },
    components: {
      topbar,
    },
    mounted(){
      if(!this.id)
        return;
      getRequest('/Commodity', {id:this.id})
      .then(res => {
        if(!res)
          return;
        this.item=res.data;
      })
      .catch(err => {
        console.log(err);
      });
    },
  }
</script>

<style>
  .nameClass{
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    font-size: 36px;
    font-weight: bold;
  }
  .priceClass{
    font-size: 27px;
  }
</style>
