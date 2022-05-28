import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import userApi from '../../../utils/userApi'
import requestApi from '../../../utils/requestApi.js'

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {
      nickName: null,
      age: null,
      height: null,
      weight: null,
    },
    showNamePop: false,
    showAgePop: false,
    showHeightPop: false,
    showWeightPop: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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
    const userInfo = app.globalData.userInfo
    if (userInfo.nickName === '点击登录') {
      userInfo.nickName = '尚未登录'
    }
    this.setData({
      userInfo: userInfo,
    })
    console.log("onload: ", this.data.userInfo)
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

  popName() {
    this.setData({
      showNamePop: true
    })
  },

  popAge() {
    this.setData({
      showAgePop: true
    })
  },

  popHeight() {
    this.setData({
      showHeightPop: true
    })
  },

  popWeight() {
    this.setData({
      showWeightPop: true
    })
  },

  closePop() {
    this.setData({
      showNamePop: false,
      showAgePop: false,
      showHeightPop: false,
      showWeightPop: false
    })
  },

  dialogInputName(event) {
    console.log(event.detail)
    var userInfo = this.data.userInfo
    userInfo.nickName = event.detail
  },

  dialogInputAge(event) {
    console.log(event.detail)
    var userInfo = this.data.userInfo
    userInfo.age = event.detail
  },

  
  dialogInputHeight(event) {
    console.log(event.detail)
    var userInfo = this.data.userInfo
    userInfo.height = event.detail
  },

  
  dialogInputWeight(event) {
    console.log(event.detail)
    var userInfo = this.data.userInfo
    userInfo.weight = event.detail
  },

  updateUserDetails(event) {
    console.log("updateUserDetails");
    console.log(this.data.userInfo)
    //调用接口
    requestApi.post(userApi.saveUserDetails, this.data.userInfo).then(res => {
      //成功时回调函数
      console.log("返回结果: ", res)
      app.globalData.userInfo = this.data.userInfo
      console.log("this.data.userInfo: ", this.data.userInfo)
    }).catch(err => {
      //失败时回调函数
      console.log(err)
    })
    this.onLoad()
  },
})