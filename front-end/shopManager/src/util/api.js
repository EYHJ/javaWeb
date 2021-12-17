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

let base="/shopManager"
export const sendRequest= (method, url, data=null)=>{
  console.log(`axios ${method} to ${url}`);
  if(data) {
    return axios({
      method: method,
      url: url,
      data:data,
    });
  }
  else{
    return axios({
      method: method,
      url: url
    });
  }
}

export const getRequest= (url, params=null)=>{
    console.log(`axios get to ${url}`);
    if(params) {
      return axios({
        method: 'get',
        url: url,
        params: params,
      });
    }
    else{
      return axios({
        method: 'get',
        url: url
      });
    }
}

axios.interceptors.response.use(success=>{
  if(success.data.code==401){
    router.push('/');
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
