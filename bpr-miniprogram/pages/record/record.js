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
    selectedMemberValue: 0,
    showCalendar: false,
    selectedDateInterval: '选择日期区间',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      openId: app.globalData.openId,
      userInfo: app.globalData.userInfo,
      familyInfo: app.globalData.familyInfo,
    })
    console.log("this.data.familyInfo: ", this.data.familyInfo)
    this.generateMemberSelector()
    console.log(this.data.memberSelector)
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

  generateMemberSelector() {
    const members = app.globalData.familyInfo.familyMemberVos
    var arr = []
    for (let i = 0; i < members.length; i++) {
      var member = {
        text: members[i].nickName,
        value: i,
      }
      arr.push(member)
      if (members[i].nickName === this.data.userInfo.nickName) {
        this.setData({
          selectedMemberValue: i,
        })
      }
    }
    this.setData({
      memberSelector: arr,
    })
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
    return `${date.getMonth() + 1}/${date.getDate()}`;
  },
  onConfirmCalendar(event) {
    const [start, end] = event.detail;
    this.setData({
      showCalendar: false,
      selectedDateInterval: `${this.formatDate(start)} - ${this.formatDate(end)}`,
    });
    this.selectComponent('#calendar').toggle();
  },

})