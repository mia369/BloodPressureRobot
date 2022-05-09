import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';

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
    record: {
      measureTime: "",
      highBloodPressure: 0,
      lowBloodPressure: 0,
      heartRate: 0,
      usedPills: 0
    },

  },

  /*
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
   * 
   * !! Dialog.confirm只有确认按钮
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

  onMeasureTimeChange(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    picker.setColumnValues(1, dateTime[value[0]]);
    this.setData({
      record: {
        measureTime: value[0] + " " + value[1]
      }
    })
    console.log(this.data.record);
  },

  onPickerConfirm(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    this.setData({
      record: {
        measureTime: value[0] + " " + value[1]
      }
    })
    console.log(this.data.record);
    this.setData({
      showMeasureTimePop: false
    });
  },

  onPickerCancel() {
    this.setData({
      showMeasureTimePop: false
    });
    console.log(this.data.record);
  },

  onHighBloodPressureChange(event) {
    const value = parseInt(event.detail)
    const r = this.data.record
    r.highBloodPressure = value
    if (value) {
      this.setData({
        record: r
      })
    }
    console.log(this.data.record)
  },

  onLowBloodPressureChange(event) {
    const value = parseInt(event.detail)
    const r = this.data.record
    r.lowBloodPressure = value
    if (value) {
      this.setData({
        record: r
      })
    }
    console.log(this.data.record)
  },

  onHeartRateChange(event) {
    const value = parseInt(event.detail)
    const r = this.data.record
    r.heartRate = value
    if (value) {
      this.setData({
        record: r
      })
    }
    console.log(this.data.record)
  },

  onUsedPillsChange(event) {
    const value = parseInt(event.detail)
    const r = this.data.record
    r.usedPills = value
    if (value) {
      this.setData({
        record: r
      })
    }
    console.log(this.data.record)
  },

  onResetForm(event) {},

  async checkLogin() {
    var checkCount = 0
    while (app.globalData.userInfo.nickName === "点击登录" || !app.globalData.familyInfo) {
      console.log("循环")
      await sleep(50)
      if (checkCount < 30 && app.globalData.userInfo.nickName === "点击登录") {
        checkCount = checkCount + 1
        continue
      }
      if (app.globalData.userInfo.nickName === "点击登录") {
        console.log("app.globalData.userInfo: ", app.globalData.userInfo)

        Dialog.confirm({
            message: '请先登录',
          })
          .then(() => {
            // on confirm
            //路由到用户页
            wx.switchTab({
              url: '/pages/user/user'
            })
          })
          .catch(() => {
            // on cancel
          });
        break
      }
      if (!app.globalData.familyInfo) {
        console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
        Dialog.confirm({
            message: '请先创建家庭',
          })
          .then(() => {
            // on confirm
            //路由到用户页
            wx.switchTab({
              url: '/pages/user/user'
            })
          })
          .catch(() => {
            // on cancel
          });
        break
      }
    }
  },
})