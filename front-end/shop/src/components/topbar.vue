<template>
		<div class='nav' style="display: flex; justify-content: space-between;">
      <div class="nav-left" v-if="backButton">
        <el-avatar @click="this.$router.back()" class="aClass">
          <el-icon :size='27' style="margin-top: 15px;">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-152cbb9b="">
              <path fill="currentColor" d="M224 480h640a32 32 0 1 1 0 64H224a32 32 0 0 1 0-64z"></path>
              <path fill="currentColor" d="m237.248 512 265.408 265.344a32 32 0 0 1-45.312 45.312l-288-288a32 32 0 0 1 0-45.312l288-288a32 32 0 1 1 45.312 45.312L237.248 512z"></path>
            </svg>
          </el-icon>
        </el-avatar>
      </div>
      <div class="nav-mid">
        <h4 id='nav-title'>{{ title }}</h4>
      </div>
      <div class="nav-right">
        <el-popover placement="bottom" trigger="hover">
          <template #reference>
            <el-button class="nav-button" circle>
              <el-icon :size='16'>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-394d1fd8="">
                  <path fill="currentColor" d="M512 512a192 192 0 1 0 0-384 192 192 0 0 0 0 384zm0 64a256 256 0 1 1 0-512 256 256 0 0 1 0 512zm320 320v-96a96 96 0 0 0-96-96H288a96 96 0 0 0-96 96v96a32 32 0 1 1-64 0v-96a160 160 0 0 1 160-160h448a160 160 0 0 1 160 160v96a32 32 0 1 1-64 0z">
                  </path>
                </svg>
              </el-icon>
            </el-button>
          </template>

          <div v-if="!userinfo">
            <el-button type="text" @click="this.$router.push('/login');">请登录</el-button>
            <div>
              <span>没有账号? </span>
              <el-button type="text" @click="this.$router.push('/register');">点此注册</el-button>
            </div>
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
              <tr>
                <td class='table-label'>邮箱： </td>
                <td>{{ userinfo.email }}</td>
              </tr>
              <tr>
                <td class='table-label'>地址： </td>
                <td>{{ userinfo.location }}</td>
              </tr>
            </tbody>
            <el-link type="primary" @click="logout">退出登录</el-link>
          </div>
        </el-popover>

        <el-button class="nav-button" circle @click="toOrder">
          <el-icon :size='16'>
            <svg class="icon" width="200" height="200" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-365b8594="">
              <path fill="currentColor" d="M432 928a48 48 0 110-96 48 48 0 010 96zm320 0a48 48 0 110-96 48 48 0 010 96zM96 128a32 32 0 010-64h160a32 32 0 0131.36 25.728L320.64 256H928a32 32 0 0131.296 38.72l-96 448A32 32 0 01832 768H384a32 32 0 01-31.36-25.728L229.76 128H96zm314.24 576h395.904l82.304-384H333.44l76.8 384z">
              </path>
            </svg>
          </el-icon>
        </el-button>

      </div>
		</div>
</template>

<script>
  import { getRequest, sendRequest, showMessage } from '@/util/api.js';
  import { ElMessageBox } from 'element-plus'
  export default {
    name: 'topbar',
    props:{
      title: String,
      backButton: Boolean,
    },
    mounted() {
      if(!this.userinfo){
        this.getUserinfo();
      }
    },
    methods: {
      getUserinfo(){
        getRequest('/User')
        .then(res => {
          if(!res)
            return;
          this.$store.commit('setUserinfo',res.data);
        })
        .catch(err => {
          console.log(err);
        });
      },
      logout() {
        ElMessageBox.confirm(
                '是否退出登录?',
                'Warning',
                {
                  confirmButtonText: '确认',
                  cancelButtonText: '取消',
                  type: 'warning',
                }
              )
              .then(() => {
                sendRequest('delete','/Login')
                .then(res => {
                  if(!res)
                    return;
                  this.$store.commit('clearUserinfo');
                  this.$router.replace('/');
                  showMessage('message', '登出成功');
                })
                .catch(err => {
                  console.log(err);
                });
              });
      },
      toOrder() {
        if(this.$route.path=='/order'){
          this.$router.back();
          return;
        }
        this.$router.push('/order');
      },
    },
    computed: {
      userinfo(){
        return this.$store.state.userinfo;
      },
    },
  }
</script>

<style>
  .aClass{
    color: #000000;
    background-color: #FFFFFF;
  }
  .aClass:hover{
    color: #0D84FF;
  }
  .nav-button{
    bottom: 0px;
    margin-top: 5px;
  }
  .table-label{
    text-align: right;
  }
</style>
