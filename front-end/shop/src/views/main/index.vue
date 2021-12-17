<template>
	<el-container>
		<el-header height="40px">
      <topbar title="不知道叫什么名字商城" :backButton='false'></topbar>
		</el-header>

		<el-main>
      <!-- <div style="width: 100%;text-align: center;">
          <el-input
            v-model="searchText"
            placeholder="搜索商品..."
            class="input-bar"
          >
            <template #append>
              <el-button>
                <el-icon color="black" :size='20'>
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-394d1fd8="">
                    <path fill="currentColor" d="m795.904 750.72 124.992 124.928a32 32 0 0 1-45.248 45.248L750.656 795.904a416 416 0 1 1 45.248-45.248zM480 832a352 352 0 1 0 0-704 352 352 0 0 0 0 704z">
                    </path>
                  </svg>
                </el-icon>
              </el-button>
            </template>
          </el-input>
        </div> -->

      <commodityList :itemList="clist"></commodityList>
		</el-main>
	</el-container>
</template>

<script>
  import { getRequest } from '@/util/api.js';
  import topbar from '@/components/topbar.vue';
  import commodityList from '@/views/main/components/commodityList.vue';
  export default {
    data(){
      return {
        searchText:'',
      }
    },
    mounted() {
      if(!this.clist)
        this.refreshClist();
    },
    methods: {
      refreshClist() {
        getRequest('/CommodityList')
        .then(res => {
          if(!res)
            return;
          this.$store.commit('setClist', res.data);
        })
        .catch(err => {
          console.log(err);
        })
      },
    },
    computed: {
      clist() {
        return this.$store.state.clist;
      }
    },
    components:{
      topbar,
      commodityList,
    }
  }
</script>

<style>
  .input-bar{
    border: 1px solid #c5c5c5;
    border-radius: 20px;
    background: #f4f4f4;
    max-width: 500px;
    margin-bottom: 10px;
  }
  .input-bar .el-input-group__prepend {
    border: none;
    background-color: transparent;
    padding: 0 10px 0 30px;
  }
  .input-bar .el-input-group__append {
    border: none;
    background-color: transparent;
  }
  .input-bar .el-input__inner {
    height: 36px;
    line-height: 36px;
    border: none;
    background-color: transparent;
  }
  .input-bar .el-icon-search{
    font-size: 16px;
  }
  .input-bar .centerClass {
    height: 100%;
    line-height: 100%;
    display: inline-block;
    vertical-align: middle;
    text-align: right;
  }
  .input-bar .line {
    width: 1px;
    height: 26px;
    background-color: #c5c5c5;
    margin-left: 14px;
  }
  .input-bar:hover{
    border: 1px solid #D5E3E8;
    background: #fff;
  }
  .input-bar:hover .line {
    background-color: #D5E3E8;
  }
  .input-bar:hover .el-icon-search{
    color: #409eff;
    font-size: 16px;
  }
</style>
