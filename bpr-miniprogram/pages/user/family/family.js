import familyApi from '../../../utils/familyApi.js'
import requestApi from '../../../utils/requestApi.js'
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    openId: '',
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
    showIdentityUpdatePop: false,
    updatingMember: {
      id: '',
      openId: '',
      nickName: '',
      age: 0,
      familyId: '',
      familyIdentity: '',
      lastRecordTime: '',
      createTime: '',
      updateTime: '',
    },
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
    const params = {
      openId: app.globalData.openId
    }
    requestApi.get(familyApi.searchFamily, params).then(res => {
      //成功时回调函数
      console.log("返回结果: ", res)
      var familyInfo = this.getNullFamilyInfo()
      var isFamilyManager = false
      if (res.result) {
        familyInfo = res.result
        isFamilyManager = res.result.familyManager.openId === app.globalData.openId
      }
      app.globalData.familyInfo = familyInfo
      this.setData({
        familyInfo: familyInfo,
        openId: app.globalData.openId,
        haveFamily: res.result !== null,
        isFamilyManager: isFamilyManager
      })
      console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
      console.log("this.data.familyInfo: ", this.data.familyInfo)
    }).catch(err => {
      //失败时回调函数
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
    // var path = '/pages/user/user?familyId=' + app.globalData.familyInfo.familyId + '&shareUser=' + app.globalData.userInfo.nickName
    // var title = '点击加入' + app.globalData.userInfo.nickName + '的家庭'

    var path = '/pages/add/add?familyId=' + '84406089aa8b46bdceae4fc3bc79fc10' + '&shareUser=' + '未完'
    var title = '点击加入' + '未完' + '的家庭'

    return {
      title: title,
      path: path
    }
  },


  dialogInputFamilyName(event) {
    console.log(event.detail)
    var familyInfo = this.data.familyInfo
    familyInfo.familyName = event.detail
    console.log(this.data.familyInfo)
  },

  dialogInputFamilyIdentity(event) {
    console.log(event.detail)
    var updatingMember = this.data.updatingMember
    updatingMember.familyIdentity = event.detail
    console.log(this.data.updatingMember)
  },

  closePop() {
    this.setData({
      showFamilyRegisterPop: false,
      showIdentityUpdatePop: false,
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


  showIdentityPop(e) {
    const member = e.currentTarget.dataset.member;
    console.log(member)
    this.setData({
      showIdentityUpdatePop: true,
      updatingMember: member,
    })
  },

  deleteFamily() {
    Dialog.confirm({
        title: '是否解散家庭?',
      })
      .then(() => {
        // on confirm
        const params = {
          familyId: app.globalData.familyInfo.familyId,
        }
        //调用接口
        requestApi.remove(familyApi.deleteFamily, params).then(res => {
          //成功时回调函数
          console.log("返回结果: ", res)
          this.setData({
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
            haveFamily: false,
            isFamilyManager: false,
          })
          app.globalData.familyInfo = this.data.familyInfo
          console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
          console.log("this.data.familyInfo: ", this.data.familyInfo)
          this.onShow()
        }).catch(err => {
          //失败时回调函数
          console.log(err)
        })
      })
      .catch(() => {
        // on cancel
      });
  },

  deleteMember(e) {
    const member = e.currentTarget.dataset.member;
    console.log(member)

    Dialog.confirm({
        title: '是否将成员移出家庭?',
      })
      .then(() => {
        // on confirm
        const params = {
          familyId: member.familyId,
          openId: member.openId,
        }
        //调用接口
        requestApi.remove(familyApi.deleteMember, params).then(res => {
          //成功时回调函数
          console.log("返回结果: ", res)
          app.globalData.familyInfo.familyMemberVos = res.result.familyMemberVos
          this.setData({
            familyInfo: app.globalData.familyInfo,
          })
          console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
          console.log("this.data.familyInfo: ", this.data.familyInfo)
          this.onShow()
        }).catch(err => {
          //失败时回调函数
          console.log(err)
        })
      })
      .catch(() => {
        // on cancel
      });
  },

  exitFamily() {
    Dialog.confirm({
        title: '是否退出家庭?',
      })
      .then(() => {
        // on confirm
        const params = {
          familyId: this.data.familyInfo.familyId,
          openId: this.data.openId,
        }
        //调用接口
        requestApi.remove(familyApi.exitFamily, params).then(res => {
          //成功时回调函数
          console.log("返回结果: ", res)
          this.setData({
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
            haveFamily: false,
          })
          app.globalData.familyInfo = this.data.familyInfo
          console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
          console.log("this.data.familyInfo: ", this.data.familyInfo)
          this.onShow()
        }).catch(err => {
          //失败时回调函数
          console.log(err)
        })
      })
      .catch(() => {
        // on cancel
      });
  },

  updateFamilyIdentity() {
    console.log(this.data.updatingMember)
    const familyInfo = this.data.familyInfo
    requestApi.put(familyApi.updateFamilyIdentity, this.data.updatingMember).then(res => {
      //成功时回调函数
      console.log("返回结果: ", res)
      familyInfo.familyMemberVos = res.result.familyMemberVos
      this.setData({
        updatingMember: {
          id: '',
          openId: '',
          nickName: '',
          age: 0,
          familyId: '',
          familyIdentity: '',
          lastRecordTime: '',
          createTime: '',
          updateTime: '',
        },
      })
      app.globalData.familyInfo = familyInfo
      console.log("app.globalData.familyInfo: ", app.globalData.familyInfo)
      console.log("this.data.familyInfo: ", this.data.familyInfo)
      this.onLoad()
    }).catch(err => {
      //失败时回调函数
      console.log(err)
    })
  },

  getNullFamilyInfo() {
    return {
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
    }
  }
})