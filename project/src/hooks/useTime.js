import { reactive, toRefs, onMounted, onUnmounted } from 'vue'
const dateTime =  reactive(
    {
        ymd:'',
        hms:''
    }
)

const sumTime = () => {
    const date = new Date()
    const Y = date.getFullYear()
    const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
    const D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
    const h = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
    const m = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
    const s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
    dateTime.ymd = Y + '.' + M + '.' + D
    dateTime.hms = h + ':' + m + ':' + s
}
const useTime = () => {
    let timer;
    sumTime()
    timer = setInterval(()=>{
        sumTime()
    },1000)
    onUnmounted(()=>{
      clearInterval(timer)
    })
    return toRefs(dateTime)
}


export default useTime