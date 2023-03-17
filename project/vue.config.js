const { defineConfig } = require('@vue/cli-service')
const path = require('path'); 
const fs = require('fs');

function resolve(dir) { // 拿到文件的函数
  return path.join(__dirname, dir);// 将当前文件和dir参数的路径自动合并成为正确的路径
}

module.exports = defineConfig({
  transpileDependencies: true,
  productionSourceMap: false,
  publicPath: process.env.NODE_ENV === 'production' ? './': '/',
  devServer: {
    // proxy: {
    //   '/api': {
    //     target: 'http://192.168.0.213:9081',
    //     changeOrigin: true,
    //     pathRewrite:{
    //       '^/api':''//这里理解成用‘/api'代替target里面的地址，后面组件中我们掉接口时直接用api代替
    //            //比如我要调用'http://47.93.166.112/user/login'，直接写‘/api/user/login'即可
    //     }
    //   }
    // }
    port: 7070,
    disableHostCheck: true,
  },
  configureWebpack: { 
    resolve: {
      alias: { // 别名配置
        '@': resolve('src') // 这里是views文件夹的路径 src/views
      }
    }
  },
})
