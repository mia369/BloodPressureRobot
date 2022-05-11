// pages/user/user.js
import userApi from '../../utils/userApi.js'
import requestApi from '../../utils/requestApi.js'

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {
      nickName: '',
      avatarUrl: '',
    },
    familyInfo: {
      familyId: '',
      familyName: '',
      familyManager: '',
      familyMemberVos: [],
      createTime: '',
      updateTime: '',
    },
    hasUserInfo: false,
    canIUseGetUserProfile: false,
    familyCellValue: '查看',

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
    console.log("app.globalData.userInfo: ", app.globalData.userInfo)
    console.log("app.globalData.userInfo: ", app.globalData.familyInfo)
    this.setData({
      userInfo: app.globalData.userInfo,
      familyInfo: app.globalData.familyInfo,
    })
    if (this.data.userInfo.nickName !== '点击登录') {
      this.setData({
        hasUserInfo: true
      })
    }
    if (this.data.familyInfo.familyName === '') {
      this.setData({
        familyCellValue: '点击注册',
      })
    }
    console.log("this.data.userInfo: ", this.data.userInfo)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },

  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log("getUserProfile.res: ", res)
        app.globalData.userInfo.nickName = res.userInfo.nickName
        app.globalData.userInfo.avatarUrl = res.userInfo.avatarUrl
        this.setData({
          userInfo: {
            nickName: res.userInfo.nickName,
            avatarUrl: res.userInfo.avatarUrl,
          },
          hasUserInfo: true
        })
        console.log("app.globalData.userInfo: ", app.globalData.userInfo)
        //后台添加或更新user信息
        //调用接口
        const params = {
          openId: app.globalData.openId,
          nickName: app.globalData.userInfo.nickName,
          avatarUrl: app.globalData.userInfo.avatarUrl,
        }
        requestApi.post(userApi.saveUserInfo, params).then(res => {
          //成功时回调函数
          console.log(res)
        }).catch(err => {
          //失败时回调函数
          console.log(err)
        })
      }
    })
  },


})