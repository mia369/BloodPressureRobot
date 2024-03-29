const request = (url, options) => {
  return new Promise((resolve, reject) => {
    // wx.request封装
    const app = getApp()
    wx.request({
      url: `${app.globalData.host}${url}`, //获取域名接口地址
      method: options.method, //配置method方法
      data: options.method === 'GET' ? options.data : JSON.stringify(options.data),
      //如果是GET,GET自动让数据成为query String,其他方法需要让options.data转化为字符串
      header: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      success(response) {
        //监听成功后的操作
        if (response.data.code === 0) {
          //此处0是项目中数据获取成功后返回的值,成功后将request.data传入resolve方法中
          resolve(response.data)
        } else {
          //如果没有获取成功返回值,把request.data传入到reject中
          reject(response.data)
        }
      },
      fail(error) {
        //返回失败也同样传入reject()方法
        reject(error.data)
      }
    })
  })
}
//封装get方法
const get = (url, options = {}) => {
  return request(url, {
    method: 'GET',
    data: options
  })
}
//封装post方法
const post = (url, options) => {
  return request(url, {
    method: 'POST',
    data: options
  })
}
//封装put方法
const put = (url, options) => {
  return request(url, {
    method: 'PUT',
    data: options
  })
}
//封装remove方法
// 不能声明DELETE（关键字）
const remove = (url, options) => {
  return request(url, {
    method: 'DELETE',
    data: options
  })
}
//抛出wx.request的post,get,put,remove方法
module.exports = {
  get,
  post,
  put,
  remove
}