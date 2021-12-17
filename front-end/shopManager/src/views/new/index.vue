<template>
	<h1 style="margin-top: 0px;padding-top: 0px;">新增商品</h1>
	<el-form
		style="max-width: 500px;margin: auto;"
		ref="ncForm"
		:model="ncForm"
		:rules="rules"
		label-width="auto"
		hide-required-asterisk
		>
		<el-form-item label="商品编号:" prop='id'>
			<el-input v-model="ncForm.id"></el-input>
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
import { showMessage, sendRequest } from '@/util/api.js';
import { getThumbnailFromRaw } from '@/util/image.js'
export default {
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
				id:[{
					required: true,
					message: '不能为空',
					trigger: 'change',
				},{
					max: 16,
					message: '编号长度需不大于16',
					trigger: 'blur',
				}],
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
				sendRequest('put','/shopManager/Goods',this.ncForm)
				.then(res => {
					if(!res)
						return;
					  showMessage("success", res.message);
					  this.$refs["ncForm"].resetFields();
					  this.ncForm.img_raw='';
					  this.$store.commit('clearClist');
				})
				.catch(error => {
					console.log(error);
				});
			}})
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
}
</script>

<style>
</style>
