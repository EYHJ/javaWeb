<template>
  <div v-if="itemList">
    <el-row :gutter="16">
      <el-col
        :xs="12" :sm="12" :md="8" :lg="4" :xl="4"
        v-for="item in itemList"
        :key="item">
        <el-card
        :body-style="{ padding: '0px' }"
        shadow="hover">
        <router-link :to="'/commodity_'+item.id" class="aClass">
          <el-image class="imageClass" :src='item.thumbnail_raw'/>
          <div class="cardFoot">
            <div class="text-line1" style="font-size: var(--el-font-size-base);">
              {{ item.name }}
            </div>
            <div class="text-line1"
            style="text-align: right;font-size: var(--el-font-size-extra-large);padding-top: 5px;">
              ￥{{ item.price }}
            </div>
          </div>
        </router-link>
        </el-card>
      </el-col>
    </el-row>
  </div>

  <div v-else>
    <p>没有找到商品！</p>
  </div>


</template>

<script>
  export default {
    name: 'commodityList',
    props:{
      itemList: Array,
    },
  }
</script>

<style>
.imageClass {
  width: 100%;
  display: block;
}
.text-line1 {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
}
.cardFoot{
  padding: 5px;
}
.aClass {
  text-decoration: none;
  color: #000000;
}
.aClass:hover {
  color:#0D84FF;
}
</style>
