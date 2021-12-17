<template>
<div class="register-back" :style="registerStyle">
  <h2 class="register-title">用户注册</h2>
  <el-form
    class="reg-form"
    label-position="top"
    ref="resForm"
    :rules="rules"
    :model="resForm"
    hide-required-asterisk
  >
    <el-form-item label="账号(用于登录)" prop="account">
      <el-input v-model="resForm.account"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="resForm.username"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="resForm.password" show-password></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="resForm.email"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="location">
      <el-input v-model="resForm.location"></el-input>
    </el-form-item>
    <el-button @click="submitFrom" style="width: 100%;margin-top: 24px;" type="primary" plain>
      注册
    </el-button>
  </el-form>

</div>
</template>

<script>
import { sendRequest, showMessage } from '@/util/api.js';
export default {
  data() {
    return {
      resForm: {
        account: '',
        username: '',
        password: '',
        email: '',
        location: '',
      },
      repeatpw: '',
      rules: {
      	account: [{
      		required: true,
      		message: '账号不能为空',
      		trigger: 'change',},
      	{
      		min: 3,
      		max: 18,
      		message: '账号长度需在3-18之间',
      		trigger: 'blur',},
      	{
      		pattern:/^\w[\w-@&.]*$/,
      		message:'账号需以字母数字或下划线开头，可包含的其他符号有 @ & . -',
      		trigger:'change',},
      	],
      	password: [{
      		required: true,
      		message: '密码不能为空',
      		trigger: 'change',},
      	{
      		min: 6,
      		max: 18,
      		message: '密码长度需在6-18之间',
      		trigger: 'blur',},
        ],
        username: [{
        	required: true,
        	message: '用户名不能为空',
        	trigger: 'change',},
        {
        	max: 18,
        	message: '账号长度需不大于18',
        	trigger: 'blur',},
        ],
        email:[{
        	required: true,
        	message: '邮箱不能为空',
        	trigger: 'change',},
        {
          pattern:/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
          message:'请输入正确的邮箱格式',
          trigger:'blur',},
        ],
        location:[{
          required: true,
          message: '地址不能为空',
          trigger: 'change',},
        ],
      },
      registerStyle:{
				backgroundImage:'url(' + require('./asset/register-background.jpg') + ')',
				backgroundSize: "100% 100%",
      }
    }
  },
  methods:{
    submitFrom(){
    	this.$refs["resForm"].validate((valid) => {
    		if (valid) {
          sendRequest('put','/User', this.resForm)
    			.then(res => {
            if(!res)
              return;
              showMessage("success", res.message);
              this.$router.replace('/login');
    			  })
    			  .catch(error => {
    			    console.log(error);
    			  });
    		}
      })
    }
  }
}
</script>

<style>
  .register-back{
    text-align: center;
  }
  .register-title{
    margin-top: 0px;
    padding-top: 15px;
    padding-bottom: 5px;
    border-bottom: 1px solid black;
  }
  .reg-form{
    max-width: 500px;
    margin: 0 auto;
    padding: 0px 5px 20px 5px;
  }
</style>
