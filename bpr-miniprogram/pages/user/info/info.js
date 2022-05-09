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
    this.setData({
      userInfo: app.globalData.userInfo,
    })
    console.log(this.data.userInfo)
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
    console.log("userInfo: ", this.data.userInfo)
    this.setData({
      userInfo: {
        nickName: event.detail,
      }
    })
    console.log("nickName: ", this.data.userInfo.nickName)
  },

  dialogInputAge(event) {
    console.log(event.detail)
    console.log("userInfo: ", this.data.userInfo)
    this.setData({
      userInfo: {
        age: event.detail,
      }
    })
    console.log("age: ", this.data.userInfo.age)
  },

  
  dialogInputHeight(event) {
    console.log(event.detail)
    console.log("userInfo: ", this.data.userInfo)
    this.setData({
      userInfo: {
        height: event.detail,
      }
    })
    console.log("height: ", this.data.userInfo.height)
  },

  
  dialogInputWeight(event) {
    console.log(event.detail)
    console.log("userInfo: ", this.data.userInfo)
    this.setData({
      userInfo: {
        weight: event.detail,
      }
    })
    console.log("weight: ", this.data.userInfo.weight)
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
  },
})