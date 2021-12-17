<template>
	<h1 style="margin-top: 0px;padding-top: 0px;">管理商品</h1>
	<commodityList :itemList="clist"></commodityList>
</template>

<script>
import { getRequest } from '@/util/api.js';
import commodityList from './components/commodityList.vue';
export default {
	mounted() {
		if(this.clist)
			return;
		this.getClist();
	},
	methods:{
		getClist() {
			getRequest('/shop/CommodityList')
			.then(res => {
				if(!res)
				  return;
				this.$store.commit('setClist', res.data);
			})
			.catch(err => {
				console.log(err);
			})
		}
	},
	computed:{
		clist() {
			return this.$store.state.clist;
		}
	},
	components:{
		commodityList,
	},
}
</script>

<style>
</style>
