<template>
	<div class="login-back" :style="loginStyle">
	<div class='login-front'>
		<el-row justify="center">
			<el-col :xs="24" :sm="12" :md="10" :lg="8" :xl="8">
				<el-card class='login-card'>
					<!-- header -->
					<template #header>
							<div class="login-card-header">
								<h3>不知道叫什么商城</h3>
								<span>用户登录</span>
							</div>
					</template>
					<!-- login form with button -->
					<el-form
            ref='loginForm'
            :model='loginForm'
            :rules="rules">
						<el-form-item prop="account">
							<el-input v-model="loginForm.account" placeholder="用户名"/>
						</el-form-item>
						<el-form-item prop='password'>
							<el-input v-model="loginForm.password" placeholder="密码" show-password/>
						</el-form-item>
						<el-form-item>
						      <el-button type="primary" @click="submitFrom" style="width: 100%">登录</el-button>
						</el-form-item>
					</el-form>
					<!-- sub button -->
					<div class='login-sub-button'>
						<el-button type="text" size="mini" @click='regAccount'>注册账号</el-button>
						<el-button type="text" size="mini" @click='$data.aboutDlgVisible=true'>相关信息</el-button>
					</div>

				</el-card>

			</el-col>
		</el-row>
	</div>
	</div>

	<!-- about dialog -->
	<el-dialog v-model="aboutDlgVisible" title="相关信息" width="60%">
		<div>
			<span>网络应用开发课程实验</span>
			<br/>
			<br/>
			<span>19计算机科学与技术2班</span>
			<br/>
			<span>学号：201930344268</span>
			<br/>
			<span>指导老师：布社辉</span>
		</div>
	    <template #footer>
	      <span class="dialog-footer">
	        <el-button type="primary" size="mini" plain @click="aboutDlgVisible = false">知道了</el-button>
	      </span>
	    </template>
	</el-dialog>
</template>

<script>
import { showMessage, sendRequest } from '@/util/api.js';
export default {
	data() {
		return {
			loginForm:{
				account:'',
				password:'',
			},
			aboutDlgVisible:false,
			loginStyle: {
				backgroundImage:'url(' + require('./asset/login-background.jpg') + ')',
				backgroundSize: "100% 100%",
			},
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
					trigger: 'blur',
					},
					],
			},
		}
	},
	methods:{
		submitFrom(){
			this.$refs["loginForm"].validate((valid) => {
				if (valid) {
          sendRequest('post','/Login', this.loginForm)
					.then(res => {
            if(!res)
              return;
              showMessage("success", res.message);
              this.$router.replace('/');
					  })
					  .catch(error => {
					    console.log(error);
					  });
				}
      })
		},
		regAccount(){
      this.$router.push('/register');
		}
	}
}
</script>

<style>
	.login-back{
		width:100%;
		height:100%;
	}
	.login-front{
		width: 99%;
		height: 60%;
		position: absolute;
		margin-top: 80px;
	}
</style>
