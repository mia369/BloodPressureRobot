// app.js
import userApi from './utils/userApi'
import requestApi from './utils/requestApi.js'

App({
  onLaunch() {
    console.log("app on launch")
  },

  onShow() {
    this.userLogin().then(res => {
      console.log(res)
    })
  },
  /**
   * 全局变量
   */
  globalData: {
    host: 'https://bpr.flink-dsp.com:2590/',
    // host: 'https://localhost:2590/',
    openId: '',
    sessionKey: '',
    userInfo: {
      nickName: '点击登录',
      avatarUrl: '/images/defaultAvatar.png',
    },
    familyInfo: {
      familyId: '',
      familyName: '',
      familyManager: {
        openId: '',
        nickName: '',
        lastRecordTime: '',
      },
      familyMemberVos: [],
      createTime: '',
      updateTime: '',
    },
    shareUserInfo: {
      familyId: '',
      shareUser: ''
    },
  },
  /**
   * 全局方法
   */
  userLogin() {
    console.log("调用userLogin方法")
    let that = this
    return new Promise(function (resolve, reject) {
      // 静默登录
      wx.login({
        success(res) {
          if (res.code) {
            //发起网络请求
            console.log(res.code)
            //调用wx.request请求传递code凭证换取用户openid，并获取后台用户信息
            wx.request({
              url: `${that.globalData.host}user/login`, // 后台请求用户信息方法【注意，此处必须为https数字加密证书】
              data: {
                code: res.code //code凭证
              },
              header: {
                'content-type': 'application/json' // 默认值
              },
              method: 'POST',
              success(res) {
                //成功时回调函数
                console.log("res: ", res)
                that.globalData.openId = res.data.result.openId
                that.globalData.sessionKey = res.data.result.sessionKey
                if (res.data.result.userInfo) {
                  that.globalData.userInfo = res.data.result.userInfo
                }
                if (res.data.result.familyInfo) {
                  that.globalData.familyInfo = res.data.result.familyInfo
                }
                console.log("openId: ", that.globalData.openId)
                console.log("sessionKey: ", that.globalData.sessionKey)
                console.log("userInfo: ", that.globalData.userInfo)
                console.log("familyInfo: ", that.globalData.familyInfo)
              },
              fail: function (res) {
                //失败时回调函数
                console.log('登录失败！' + err)
              },
            })
          }
        }
      })
    })
  },


})