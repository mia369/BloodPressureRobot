// app.js
App({
  onLaunch() {
    let that = this
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    
    wx.login({
      success(res) {

        if (res.code) {
          //发起网络请求
          
          console.log(res.code)
          wx.request({
            url: 'http://localhost:8080/user/login',
            data: {
              code: res.code
            },
            method: 'POST',
            success: function (res) {
              console.log(res)
              that.globalData.openId = res.data.result.openId
              that.globalData.sessionKey = res.data.result.sessionKey
            }

          })
          // console.log(response)
          // console.log(response.result.openId)
          // console.log(response.result.sessionKey)
          // this.globalData.openId = response.result.openId
          // this.globalData.sessionKey = response.result.sessionKey
          // console.log(that.globalData)
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  },
  globalData: {
    userInfo: {},
    familyInfo: null,
    openId: '',
    sessionKey: '',
  }
})