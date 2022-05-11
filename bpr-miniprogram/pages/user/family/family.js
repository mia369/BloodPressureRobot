import familyApi from '../../../utils/familyApi.js'
import requestApi from '../../../utils/requestApi.js'

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    familyInfo: {
      familyId: '',
      familyName: '',
      familyManager: '',
      familyMemberVos: [],
      createTime: '',
      updateTime: '',
    },
    showFamilyRegisterPop: false,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log("app.globalData.userInfo: ", app.globalData.familyInfo)
    this.setData({
      familyInfo: app.globalData.familyInfo,
    })
    if (this.data.familyInfo.familyName === '') {
      
    }
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

  
  dialogInputFamilyName(event) {
    console.log(event.detail)
    var familyInfo = this.data.familyInfo
    familyInfo.familyName = event.detail
    console.log(this.data.familyInfo)
  },

  closePop() {
    this.setData({
      showFamilyRegisterPop: false,
    })
  },

  registerFamily(event) {
    console.log(this.data.familyInfo)
    if (this.data.familyInfo.familyName === '') {
      Dialog.alert({
        message: '创建失败, 家庭名称不能为空, 请重新注册',
      }).then(() => {
        // on close
      });
    } else {
      this.data.familyInfo.familyManager = app.globalData.openId
      console.log(this.data.familyInfo)
      //调用接口
      requestApi.post(familyApi.registerFamily, this.data.familyInfo).then(res => {
        //成功时回调函数
        console.log("返回结果: ", res)
        app.globalData.familyInfo = res.data.result.familyInfo
        this.data.familyInfo = res.data.result.familyInfo
        console.log("this.data.familyInfo: ", this.data.familyInfo)
      }).catch(err => {
        //失败时回调函数
        console.log(err)
      })
      // this.onLoad()
    }
  },
})