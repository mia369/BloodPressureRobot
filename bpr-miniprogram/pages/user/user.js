// pages/user/user.js
import userApi from '../../utils/userApi.js'
import familyApi from '../../utils/familyApi.js'
import requestApi from '../../utils/requestApi.js'
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../miniprogram_npm/@vant/weapp/toast/toast';

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
      familyManager: {
        openId: '',
        nickName: '',
        lastRecordTime: '',
      },
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
      userInfo: app.globalData.userInfo,
      familyInfo: app.globalData.familyInfo,
    })
    const params = {
      openId: app.globalData.openId,
    }
    requestApi.get(userApi.searchUser, params).then(res => {
      //成功时回调函数
      console.log('searchUser返回结果: ', res)
      if (res.result) {
        app.globalData.userInfo = res.result
        this.setData({
          userInfo: res.result
        })
      }
    }).catch(err => {
      //失败时回调函数
      console.log(err)
    })
    requestApi.get(familyApi.searchFamily, params).then(res => {
      //成功时回调函数
      console.log('searchFamily返回结果: ', res)
      if (res.result) {
        app.globalData.familyInfo = res.result
        this.setData({
          familyInfo: res.result
        })
      } else {
        app.globalData.familyInfo = {
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
          }
        })
      }
    }).catch(err => {
      //失败时回调函数
      console.log(err)
    })

    //如果有分享信息
    if (app.globalData.shareUserInfo.familyId !== '') {
      //如果未登录
      if (app.globalData.userInfo.nickName === '点击登录') {
        Toast.fail('请先登录');
        //需要在登录方法最后提示加入家庭
        //如果有家庭
      } else if (app.globalData.familyInfo.familyId !== '') {
        Toast.fail('已有家庭, 不能重复加入');
        //清空数据
        app.globalData.shareUserInfo.familyId = ''
        app.globalData.shareUserInfo.shareUser = ''
        //如果登录且无家庭
      } else {
        Dialog.confirm({
            title: '加入家庭',
            message: '是否加入 ' + app.globalData.shareUserInfo.shareUser + ' 的家庭?',
          })
          .then(() => {
            this.joinFamily()
          })
          .catch(() => {
            //清空数据
            app.globalData.shareUserInfo.familyId = ''
            app.globalData.shareUserInfo.shareUser = ''
          });
      }
    }

    if (app.globalData.userInfo.nickName !== '点击登录') {
      this.setData({
        hasUserInfo: true
      })
    }
    if (!app.globalData.familyInfo || app.globalData.familyInfo.familyName === '') {
      this.setData({
        familyCellValue: '点击注册',
      })
    } else {
      this.setData({
        familyCellValue: '查看',
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
        const params = {
          openId: app.globalData.openId,
          nickName: app.globalData.userInfo.nickName,
          avatarUrl: app.globalData.userInfo.avatarUrl,
        }
        requestApi.post(userApi.saveUserInfo, params).then(res => {
          //成功时回调函数
          console.log(res)
          //弹窗加入家庭
          if (app.globalData.shareUserInfo.familyId !== '' && app.globalData.familyInfo.familyId === '') {
            Dialog.confirm({
                title: '加入家庭',
                message: '是否加入 ' + app.globalData.shareUserInfo.shareUser + ' 的家庭?',
              })
              .then(() => {
                this.joinFamily()
              })
              .catch(() => {
                //清空数据
                app.globalData.shareUserInfo.familyId = ''
                app.globalData.shareUserInfo.shareUser = ''
              });
          }
        }).catch(err => {
          //失败时回调函数
          console.log(err)
        })
      }
    })
  },

  joinFamily() {
    console.log("调用joinFamily")
    //调用接口
    const params = {
      openId: app.globalData.openId,
      familyId: app.globalData.shareUserInfo.familyId,
    }
    requestApi.post(familyApi.registerMember, params).then(res => {
      //成功时回调函数
      console.log('registerMember返回结果: ', res)
      //清空数据
      app.globalData.shareUserInfo.familyId = ''
      app.globalData.shareUserInfo.shareUser = ''
      //更新页面数据
      this.setData({
        familyCellValue: '查看',
      })
    }).catch(err => {
      //失败时回调函数
      console.log(err)
    })
  },

})