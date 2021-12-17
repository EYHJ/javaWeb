<template>
	<el-container>
<!-- 头部状态栏 -->
	      <el-header style="background-color: #42B983;">
			<div class='nav' style="display: flex; justify-content: space-between;">
			    <div class="nav-left">
			    	<h4 id='nav-title'>不知道叫什么商城-管理端</h4>
			    </div>
			      <div class="nav-right">
			
			        <el-popover placement="bottom" trigger="hover">
			          <template #reference>
			            <el-button class="nav-button" circle>
			              <el-icon size=16>
			                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-394d1fd8="">
			                  <path fill="currentColor" d="M512 512a192 192 0 1 0 0-384 192 192 0 0 0 0 384zm0 64a256 256 0 1 1 0-512 256 256 0 0 1 0 512zm320 320v-96a96 96 0 0 0-96-96H288a96 96 0 0 0-96 96v96a32 32 0 1 1-64 0v-96a160 160 0 0 1 160-160h448a160 160 0 0 1 160 160v96a32 32 0 1 1-64 0z">
			                  </path>
			                </svg>
			              </el-icon>
			            </el-button>
			          </template>
			
			          <div v-if="!userinfo">
			            <el-button type="text" @click="this.$router.push('/');">请登录</el-button>
			          </div>
			          <div v-else>
			            <tbody>
			              <tr>
			                <td class='table-label'>账号： </td>
			                <td>{{ userinfo.account }}</td>
			              </tr>
			              <tr>
			                <td class='table-label'>用户名： </td>
			                <td>{{ userinfo.username }}</td>
			              </tr>
			            </tbody>
			          </div>
			        </el-popover>
			      </div>
			</div>
		  </el-header>
	      <el-container>
<!-- 侧边导航 -->
	        <el-aside width="20%">
				<el-menu
				class="aside-menu"
				default-active="manager"
				active-text-color="#ffd04b"
				background-color="#545c64"
				text-color="#fff"
				router>
					<h5 style="padding: 5px;margin: 0px;color: white;">商品管理</h5>
					<el-menu-item index='manager'>
						<el-icon>
							<svg class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-365b8594="">
								<path fill="currentColor" d="M832 512a32 32 0 1164 0v352a32 32 0 01-32 32H160a32 32 0 01-32-32V160a32 32 0 0132-32h352a32 32 0 010 64H192v640h640V512z"></path><path fill="currentColor" d="M469.952 554.24l52.8-7.552L847.104 222.4a32 32 0 10-45.248-45.248L477.44 501.44l-7.552 52.8zm422.4-422.4a96 96 0 010 135.808l-331.84 331.84a32 32 0 01-18.112 9.088L436.8 623.68a32 32 0 01-36.224-36.224l15.104-105.6a32 32 0 019.024-18.112l331.904-331.84a96 96 0 01135.744 0z">
							</path></svg>
						</el-icon>
						<span>管理商品</span>
					</el-menu-item>
					<el-menu-item index='new'>
						<el-icon>
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-394d1fd8="">
								<path fill="currentColor" d="M352 480h320a32 32 0 1 1 0 64H352a32 32 0 0 1 0-64z"></path><path fill="currentColor" d="M480 672V352a32 32 0 1 1 64 0v320a32 32 0 0 1-64 0z"></path><path fill="currentColor" d="M512 896a384 384 0 1 0 0-768 384 384 0 0 0 0 768zm0 64a448 448 0 1 1 0-896 448 448 0 0 1 0 896z">
							</path></svg>
						</el-icon>
						<span>新增商品</span>
					</el-menu-item>
					<el-menu-item index='summary'>
						<el-icon>
							<svg class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-365b8594="">
								<path fill="currentColor" d="M704 192h160v736H160V192h160.064v64H704v-64zM311.616 537.28l-45.312 45.248L447.36 763.52l316.8-316.8-45.312-45.184L447.36 673.024 311.616 537.28zM384 192V96h256v96H384z">
							</path></svg>
						</el-icon>
						<span>查看订单</span>
					</el-menu-item>
				</el-menu>
			</el-aside>
<!-- 主视图 -->
	        <el-main :style="mainStyle">
				<router-view/>
			</el-main>
	      </el-container>
	    </el-container>
</template>

<script>
export default {
	data() {
		return {
			mainStyle: {
				backgroundImage:'url(' + require('./asset/background.jpg') + ')',
				backgroundSize: "100% 100%",
			}
		}
	},
	computed:{
		userinfo(){
			return this.$store.state.userinfo;
		}
	},
}
</script>

<style>
.nav-button{
	margin-top: 5px;
}
.aside-menu{
	text-align: center;
}
.el-aside{
	background-color: #545c64;
}
.el-form-item__label{
	color: black;
}
</style>
