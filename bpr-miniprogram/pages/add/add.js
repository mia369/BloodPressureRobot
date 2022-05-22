import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../miniprogram_npm/@vant/weapp/toast/toast';
import recordApi from '../../utils/recordApi.js'
import requestApi from '../../utils/requestApi.js'

function formatDate(date) {
  // 获取当前月份
  var nowMonth = date.getMonth() + 1;
  // 获取当前是几号
  var strDate = date.getDate();
  // 添加分隔符“-”
  var seperator = "-";
  // 对月份进行处理，1-9月在前面添加一个“0”
  if (nowMonth >= 1 && nowMonth <= 9) {
    nowMonth = "0" + nowMonth;
  }
  // 对月份进行处理，1-9号在前面添加一个“0”
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  // 最后拼接字符串，得到一个格式为(yyyy-MM-dd)的日期
  var nowDate = date.getFullYear() + seperator + nowMonth + seperator + strDate;
  return nowDate;
}

function getDateTime() {
  const times = {}
  var date = new Date()
  date.setTime(new Date().getTime() - 86400000)
  const yesterday = formatDate(date)
  times[yesterday] = ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00']
  const today = formatDate(new Date())
  times[today] = ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00']
  return times
}

const dateTime = getDateTime();

const app = getApp();

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    openId: '',
    userInfo: {
      nickName: '点击登录',
      avatarUrl: '/images/defaultAvatar.png',
    },
    showMeasureTimePop: false,
    columns: [{
        values: Object.keys(dateTime),
        className: 'column1',
        defaultIndex: 1
      },
      {
        values: dateTime[Object.keys(dateTime)[0]],
        className: 'column2',
        defaultIndex: 0
      },
    ],
    measureTime: null,
    highBloodPressure: null,
    lowBloodPressure: null,
    heartRate: null,
    usedPills: null,
  },

  /*
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log("add onload options: ", options)
    if (options && options !== {}) {
      if (options.familyId && options.familyId !== '') {
        app.globalData.familyInfo.familyId = options.familyId
      }
      if (options.shareUser && options.shareUser !== '') {
        app.globalData.shareUser = options.shareUser
      }
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
    this.checkLogin()
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

  //测量时间绑定方法
  showPopup() {
    this.setData({
      showMeasureTimePop: true
    });
  },

  onClose() {
    this.setData({
      showMeasureTimePop: false
    });
  },

  onPickerConfirm(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    this.setData({
      showMeasureTimePop: false,
      measureTime: value[0] + " " + value[1]
    })
  },

  onPickerCancel() {
    this.setData({
      showMeasureTimePop: false
    });
  },

  onHighBloodPressureChange(event) {
    const value = parseInt(event.detail)
    if (value) {
      this.setData({
        highBloodPressure: value
      })
    }
  },

  onLowBloodPressureChange(event) {
    const value = parseInt(event.detail)
    if (value) {
      this.setData({
        lowBloodPressure: value
      })
    }
  },

  onHeartRateChange(event) {
    const value = parseInt(event.detail)
    if (value) {
      this.setData({
        heartRate: value
      })
    }
  },

  onUsedPillsChange(event) {
    const value = event.detail
    if (value) {
      this.setData({
        usedPills: value
      })
    }
  },

  async checkLogin() {
    var checkCount = 0
    while (app.globalData.userInfo.nickName === "点击登录" || app.globalData.familyInfo.familyId === '') {
      console.log("循环")
      await sleep(50)
      if (checkCount < 40 && (app.globalData.userInfo.nickName === "点击登录" || app.globalData.familyInfo.familyId === '')) {
        checkCount = checkCount + 1
        continue
      }
      if (app.globalData.userInfo.nickName === "点击登录") {
        console.log("app.globalData.userInfo: ", app.globalData.userInfo)
        Dialog.alert({
            message: '请先登录',
          })
          .then(() => {
            //路由到用户页
            wx.switchTab({
              url: '/pages/user/user'
            })
          });
        break
      }
      if (app.globalData.familyInfo.familyName === '') {
        console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
        Dialog.alert({
            message: '请先创建或加入家庭',
          })
          .then(() => {
            //路由到用户页
            wx.switchTab({
              url: '/pages/user/user'
            })
          });
        break
      }
    }
    this.setData({
      openId: app.globalData.openId,
      userInfo: app.globalData.userInfo,
    })
  },

  onSubmitRecord() {
    console.log(this.data.openId)
    console.log(this.data.userInfo)
    if (!this.data.measureTime || !this.data.highBloodPressure || !this.data.lowBloodPressure || !this.data.heartRate || !this.data.usedPills) {
      Toast.fail("请填写完整");
    } else {
      const params = {
        openId: this.data.openId,
        measureTime: this.data.measureTime,
        highBloodPressure: this.data.highBloodPressure,
        lowBloodPressure: this.data.lowBloodPressure,
        heartRate: this.data.heartRate,
        usedPills: parseInt(this.data.usedPills)
      }
      console.log(params)
      requestApi.post(recordApi.addRecord, params).then(res => {
        console.log("返回结果: ", res)
        Toast.success("添加成功");
        this.setData({
          measureTime: null,
          highBloodPressure: null,
          lowBloodPressure: null,
          heartRate: null,
          usedPills: null,
        })
        this.onShow()
      }).catch(err => {
        console.log(err)
      })
    }
  },

  onResetForm() {
    this.setData({
      measureTime: null,
      highBloodPressure: null,
      lowBloodPressure: null,
      heartRate: null,
      usedPills: null,
    })
  },
})