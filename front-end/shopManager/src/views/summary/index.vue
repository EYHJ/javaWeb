<template>
	<h1 style="margin-top: 0px;padding-top: 0px;">查看订单</h1>
	<div v-if="slist[0]">
		<el-collapse style="width: 70%;">
		      <el-collapse-item
			  v-for="item in slist" :key="item"
			  :title="'&nbsp&nbsp&nbsp订单号：'+item.sid+'&nbsp&nbsp&nbsp用户：'+item.id">
		        <div style="white-space: pre-line;margin-left: 5px;">
					{{item.log}}
		        </div>
		      </el-collapse-item>
		</el-collapse>
	</div>
	<div v-else>
		无订单信息
	</div>
</template>

<script>
import { getRequest, showMessage } from '@/util/api.js';
export default{
	data(){
		return {
			slist:[],
		}
	},
	mounted() {
		getRequest('/shopManager/OrderList')
		.then((res)=>{
			if(!res)
				return;
			if(res.data){
				this.slist=res.data;
			}
			else{
				showMessage('success', res.message);
			}
		})
		.catch((err)=>{
			console.log(err);
		})
	}
}
</script>

<style>
</style>
