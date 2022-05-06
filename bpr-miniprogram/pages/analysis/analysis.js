import * as echarts from '../../ec-canvas/echarts'
const app = getApp();

 

function initChart(canvas, width, height, dpr) {

  //主要是这个 echarts 的创建

  const chart = echarts.init(canvas, null, {

    width: width,

    height: height,

    devicePixelRatio: dpr // new

  });

  canvas.setChart(chart);

   

  // option 的配置可以根据自己的需求去 echarts 官网查看配置的属性方法

  var option = {

    backgroundColor: "#ffffff",

    series: [{

      label: {

        normal: {

          fontSize: 14

        }

      },

      type: 'pie',

      center: ['50%', '50%'],

      radius: ['70%', '100%'],

      data: [{

        value: 55,

        name: '北京'

      }, {

        value: 20,

        name: '武汉'

      }, {

        value: 10,

        name: '杭州'

      }, {

        value: 20,

        name: '广州'

      }, {

        value: 38,

        name: '上海'

      }]

    }]

  };

 

  chart.setOption(option);

  return chart;

}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    ec: {
      onInit: initChart
    }
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

  
})

