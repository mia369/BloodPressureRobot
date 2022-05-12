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
      familyManager: {
        openId: '',
        nickName: '',
        lastRecordTime: '',
      },
      familyMemberVos: [],
      createTime: '',
      updateTime: '',
    },
    isFamilyManager: false,
    haveFamily: false,
    showFamilyRegisterPop: false,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      familyInfo: app.globalData.familyInfo,
    })
    console.log("this.data.familyInfo: ", this.data.familyInfo)
    if (this.data.familyInfo.familyId !== '') {
      this.setData({
        haveFamily: true,
      })
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
    if (this.data.familyInfo.familyManager.openId === app.globalData.openId) {
      this.setData({
        isFamilyManager: true,
      })
    }
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
    return {
      title: '弹出分享时显示的分享标题',
      desc: '分享页面的内容',
      path: '/pages/add/add?familyId=123&shareUser=hutao',
      // path: '/pages/add/add?familyId='+ app.globalData.familyInfo.familyId +'&shareUser' + app.globalData.userInfo.nickName,
      // path: '/pages/add/add?familyId=' + app.globalData.familyInfo.familyId, // 路径，传递参数到指定页面。
  }
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
    console.log("调用registerFamily: ", this.data.familyInfo)
    if (this.data.familyInfo.familyName === '') {
      Dialog.alert({
        message: '创建失败, 家庭名称不能为空, 请重新注册',
      }).then(() => {
        // on close
      });
    } else {
      var familyInfo = this.data.familyInfo
      familyInfo.familyManager.openId = app.globalData.openId
      console.log("var familyInfo: ", familyInfo)
      //调用接口
      requestApi.post(familyApi.registerFamily, familyInfo).then(res => {
        //成功时回调函数
        console.log("返回结果: ", res)
        app.globalData.familyInfo = res.result
        this.setData({
          familyInfo: res.result,
          haveFamily: true,
        })
        console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
        console.log("this.data.familyInfo: ", this.data.familyInfo)
        this.onShow()
      }).catch(err => {
        //失败时回调函数
        console.log(err)
      })
    }
  },

  showRegisterPop() {
    this.setData({
      showFamilyRegisterPop: true,
    })
  },


})