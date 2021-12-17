<template>
	<div style="justify-content: space-between;">
		<div style="display: inline-block;">
			<el-button @click="this.$router.back()" type="success" circle plain>
			<el-icon :size='27'>
			  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-152cbb9b="">
				<path fill="currentColor" d="M224 480h640a32 32 0 1 1 0 64H224a32 32 0 0 1 0-64z"></path>
				<path fill="currentColor" d="m237.248 512 265.408 265.344a32 32 0 0 1-45.312 45.312l-288-288a32 32 0 0 1 0-45.312l288-288a32 32 0 1 1 45.312 45.312L237.248 512z"></path>
			  </svg>
			</el-icon>
			</el-button>
			<h1 style="margin-top: 0px;padding-top: 0px;display: inline-block;margin-left: 10px;">
				修改商品
			</h1>
		</div>
		<div style="display: inline-block;float: right;">
			<el-button type="danger" @click="delCommodity">删除商品</el-button>
		</div>
	</div>
	
	
	<el-form
		style="max-width: 500px;margin: auto;"
		ref="ncForm"
		:model="ncForm"
		:rules="rules"
		label-width="auto"
		hide-required-asterisk
		>
		<el-form-item label="商品编号:">
			<el-input v-model="ncForm.id" disabled></el-input>
		</el-form-item>
		<el-form-item label="商品名:" prop='name'>
			<el-input v-model="ncForm.name"></el-input>
		</el-form-item>
		<el-form-item label="每件价格:" prop='price'>
			<el-input v-model.number="ncForm.price"></el-input>
		</el-form-item>
		<el-form-item label="商品描述:" prop='describe'>
			<el-input v-model="ncForm.describe"
			 :autosize="{ minRows: 3, maxRows: 10}" 
			 type="textarea"></el-input>
		</el-form-item>
		<el-form-item label="商品图片:">
			<div v-if="ncForm.img_raw">
				<el-button type='danger' @click='imageRemove'>
					删除图片
				</el-button>
				<el-button type='warning' @click='imageShowChange'>
					收起/打开图片
				</el-button>
				
				<el-image
					v-if="imageShow"
					style="margin-top: 5px;"
					:src="ncForm.img_raw"
					fit="scale-down"
					>
				</el-image>
			</div>
			<el-upload
				v-else
			    class="image-uploader"
			    action="#"
				:show-file-list="false"
				list-type="picture-card"
				:on-change="imageChange"
				:multiple="false"
				:auto-upload="false"
				:accept="'image/*'"
				:limit="1"
			  >
			  <el-icon class="avatar-uploader-icon">
				  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-394d1fd8="">
				  	<path fill="currentColor" d="M480 480V128a32 32 0 0 1 64 0v352h352a32 32 0 1 1 0 64H544v352a32 32 0 1 1-64 0V544H128a32 32 0 0 1 0-64h352z">
				  </path></svg>
			  </el-icon>
			  <template #tip>
				<div class="el-upload__tip">
				    仅可上传一张图片，修改请删除图片后重新添加
				</div>
			  </template>
			  </el-upload>
		</el-form-item>
		
		<el-form-item>
			<el-button type="primary" @click="submit">
				提交
			</el-button>
		</el-form-item>
	</el-form>
	
</template>

<script>
import { showMessage, sendRequest, getRequest } from '@/util/api.js';
import { getThumbnailFromRaw } from '@/util/image.js'
import { ElMessageBox } from 'element-plus'
export default {
	props:{
		id: String,
	},
	data() {
		return {
			ncForm: {
				id:'',
				name:'',
				price:'',
				describe:'',
				img_raw:'',
				thumbnail_raw:'',
			},
			imageShow: true,
			rules: {
				name:[{
					required: true,
					message: '不能为空',
					trigger: 'change',
				},{
					max: 64,
					message: '商品名长度需不大于64',
					trigger: 'blur',
				}],
				price:[{
					required: true,
					message: '不能为空',
					trigger: 'change',
				},{
					type: 'number',
					message: '输入数字',
					trigger: 'blur',
				}],
				describe:[{
					required: true,
					message: '不能为空',
					trigger: 'blur',
				}],
			}
		}
	},
	methods:{
		submit() {
			if(!this.ncForm.img_raw) {
				showMessage("error", '未选择图片或图片未加载完成 请稍后重试');
				return;
			}
			
			this.$refs["ncForm"].validate((valid) => {
				if (valid) {
				this.ncForm.thumbnail_raw=getThumbnailFromRaw(this.ncForm.img_raw, 256);
				sendRequest('post','/shopManager/Goods',this.ncForm)
				.then(res => {
					if(!res)
						return;
					  showMessage("success", res.message);
					  this.$store.commit('clearClist');
				})
				.catch(error => {
					console.log(error);
				});
			}})
		},
		delCommodity() {
			ElMessageBox.confirm(
			        '确认删除？',
			        'Warning',
			        {
			          confirmButtonText: '确认',
			          cancelButtonText: '取消',
			          type: 'warning',
			        }
			      )
			.then( ()=>{
				sendRequest('delete','/shopManager/Goods', {id:this.id})
				.then(res => {
					if(!res)
						return;
					  showMessage("success", res.message);
					  this.$store.commit('clearClist');
					  this.$router.replace('/manager');
				})
				.catch(error => {
					console.log(error);
				});
			}
			);
		},
		imageChange(file, filelist) {
			let reader=new FileReader();
			reader.readAsDataURL(file.raw);
			reader.onload = ( e => {
				this.ncForm.img_raw=reader.result;
			})
		},
		imageRemove() {
			this.ncForm.img_raw='';
			this.ncForm.thumbnail_raw='';
		},
		imageShowChange() {
			this.imageShow=!this.imageShow;
		},
	},
	mounted() {
		if(!this.id)
		  return;
		getRequest('/shop/Commodity', {id:this.id})
		.then(res => {
		  if(!res)
		    return;
		  this.ncForm.id=res.data.id;
		  this.ncForm.name=res.data.name;
		  this.ncForm.price=res.data.price;
		  this.ncForm.describe=res.data.describe;
		  this.ncForm.img_raw=res.data.img_raw;
		})
		.catch(err => {
		  console.log(err);
		});
	}
}
</script>

<style>
</style>
