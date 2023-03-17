import axios from "axios";
import router from "@/router";
import ElementUI from "element-ui";



axios.defaults.baseURL = 'http://localhost:8081/air_booking'
// request拦截器
const request = axios.create({
    timeout: 5000, // 请求超时时间
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    }
});

// request拦截器
request.interceptors.request.use(config => {
        config.headers['Authorization']=localStorage.getItem('token')
        return config
})
request.interceptors.response.use(response=>{
    //不拦截获取验证码
    if(response.config.url.indexOf('getCode')!==-1){
        return response
    }
    let res=response.data
    // console.log(res)
    if(res.code===200){
        return response
    }else {
        ElementUI.Message.error(res.msg?res.msg:'系统异常',{duration:3*1000})
        // router.push('/')
        return Promise.reject(response.data.msg)
    }
},error => {
    if (error.response.data){
        error.message=error.response.data.msg
    }
    if (error.response.status===401){
            router.push('/')
        }
    ElementUI.Message.error(error.message,{duration:3*1000})
    return Promise.reject(error)
    }
)

export default request

