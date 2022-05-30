import util from '../../utils/util'
import recordApi from '../../utils/recordApi.js'
import requestApi from '../../utils/requestApi.js'
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    openId: '',
    userInfo: {
      nickName: '',
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
    memberSelector: [],
    selectedMemberValue: '',
    showCalendar: false,
    minDate: new Date(new Date().getFullYear(), new Date().getMonth() - 3, 1).getTime(),
    maxDate: new Date().getTime(),
    selectedDate: '选择日期',
    recordQuery: {
      openId: '',
      startTime: '',
      endTime: '',
    },
    records: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const recordQuery = this.data.recordQuery
    const today = new Date(new Date().toLocaleDateString())
    recordQuery.startTime = util.formatTime(today)
    recordQuery.endTime = util.formatTime(new Date(today.getTime() + 86399999))
    this.setData({
      selectedDate: this.formatDate(today)
    })
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
    this.setData({
      openId: app.globalData.openId,
      userInfo: app.globalData.userInfo,
      familyInfo: app.globalData.familyInfo,
      recordQuery: this.data.recordQuery,
    })
    this.generateMemberSelector()

    requestApi.post(recordApi.selectRecord, this.data.recordQuery).then(res => {
      console.log("返回结果: ", res.result)
      this.setData({
        records: res.result,
      })
    }).catch(err => {
      console.log(err)
    })
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

  generateMemberSelector() {
    const recordQuery = this.data.recordQuery
    recordQuery.openId = app.globalData.openId
    var arr = []
    if (app.globalData.familyInfo.familyId !== '') {
      const members = app.globalData.familyInfo.familyMemberVos
      for (let i = 0; i < members.length; i++) {
        var member = {
          text: members[i].nickName,
          value: members[i].openId
        }
        arr.push(member)
      }
    } else {
      arr = [{
        text: app.globalData.userInfo.nickName === '点击登录'? '尚未登录' : app.globalData.userInfo.nickName,
        value: app.globalData.openId
      }]
    }
    this.setData({
      selectedMemberValue: app.globalData.openId,
      recordQuery: recordQuery,
      memberSelector: arr,
    })
    console.log('recordQuery: ', this.data.recordQuery)
  },

  onShowCalendar() {
    this.setData({
      showCalendar: true,
    })
  },

  onCloseCalendar() {
    this.setData({
      showCalendar: false
    });
    this.selectComponent('#calendar').toggle();
  },

  formatDate(date) {
    date = new Date(date);
    const year = date.getFullYear()
    const month = this.formatNumber(date.getMonth() + 1)
    const day = this.formatNumber(date.getDate())
    return `${year}-${month}-${day}`;
  },

  formatNumber(number) {
    return number >= 10 ? number : `0${number}`
  },

  onConfirmCalendar(event) {
    console.log(event.detail)
    const start = event.detail
    const end = new Date(event.detail.getTime() + 86399999)
    const recordQuery = this.data.recordQuery
    recordQuery.startTime = util.formatTime(start)
    recordQuery.endTime = util.formatTime(end)
    this.setData({
      showCalendar: false,
      selectedDate: this.formatDate(event.detail),
      recordQuery: recordQuery,
    });
    console.log(this.data.recordQuery)
    requestApi.post(recordApi.selectRecord, this.data.recordQuery).then(res => {
      console.log("返回结果: ", res)
      this.setData({
        records: res.result,
      })
      this.onShow()
    }).catch(err => {
      console.log(err)
    })
    this.selectComponent('#calendar').toggle();
  },

  onSelectMember(event) {
    console.log("点击菜单选项")
    console.log(event.detail)
    this.setData({
      selectedMemberValue: event.detail,
    })
    const recordQuery = this.data.recordQuery
    recordQuery.openId = event.detail
    this.setData({
      recordQuery: recordQuery,
    })
    console.log(this.data.recordQuery)
    requestApi.post(recordApi.selectRecord, this.data.recordQuery).then(res => {
      console.log("返回结果: ", res)
      this.setData({
        records: res.result,
      })
      this.onShow()
    }).catch(err => {
      console.log(err)
    })
  },

})