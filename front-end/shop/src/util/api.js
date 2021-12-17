import axios from 'axios'
import { ElMessage } from 'element-plus'

export const showMessage= (type, msg)=>{
  return ElMessage({
          showClose: true,
          message: msg,
          type: type,
          duration:1800,
        })
}

let base="/shop"
export const sendRequest= (method, url, data=null)=>{
  console.log(`axios ${method} to ${base}${url}`);
  if(data) {
    return axios({
      method: method,
      url:`${base}${url}`,
      data:data,
    });
  }
  else{
    return axios({
      method: method,
      url:`${base}${url}`
    });
  }
}

export const getRequest= (url, params=null)=>{
    console.log(`axios get to ${base}${url}`);
    if(params) {
      return axios({
        method: 'get',
        url:`${base}${url}`,
        params: params,
      });
    }
    else{
      return axios({
        method: 'get',
        url:`${base}${url}`
      });
    }
}

axios.interceptors.response.use(success=>{
  if(success.data.code==401){
    router.push('/login');
    return;
  }
  if(success.data.code>299){
    showMessage('error',success.data.message);
    return;
  }
  return success.data;
}, error=>{
  if(error.message){
    showMessage('error', error.message);
  }
  else{
    showMessage('error', '未知错误');
  }
})
