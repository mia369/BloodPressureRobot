import * as echarts from '../../ec-canvas/echarts'
import recordApi from '../../utils/recordApi.js'
import requestApi from '../../utils/requestApi.js'
import util from '../../utils/util'

const app = getApp();

var lineWeekChart = null
var lineMonthChart = null
var lineYearChart = null
var pieHighChart = null
var pieLowChart = null
var pieHeartChart = null


function initLineWeekChart(canvas, width, height, dpr) {
  lineWeekChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // new
  });
  canvas.setChart(lineWeekChart);
  return lineWeekChart;
}

function initLineMonthChart(canvas, width, height, dpr) {
  lineMonthChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // new
  });
  canvas.setChart(lineMonthChart);
  return lineMonthChart;
}

function initLineYearChart(canvas, width, height, dpr) {
  lineYearChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // new
  });
  canvas.setChart(lineYearChart);
  return lineYearChart;
}

function initPieHighChart(canvas, width, height, dpr) {
  pieHighChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // new
  });
  canvas.setChart(pieHighChart);
  return pieHighChart;
}

function initPieLowChart(canvas, width, height, dpr) {
  pieLowChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // new
  });
  canvas.setChart(pieLowChart);
  return pieLowChart;
}

function initPieHeartChart(canvas, width, height, dpr) {
  pieHeartChart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // new
  });
  canvas.setChart(pieHeartChart);
  return pieHeartChart;
}

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
    recordQuery: {
      openId: '',
      startTime: '',
      endTime: '',
    },
    selectedMemberValue: '',
    memberSelector: [],
    lineWeek: {
      avgHighBloodPressure: [],
      avgLowBloodPressure: [],
      avgHeartRate: [],
      maxHighBloodPressure: [],
      maxLowBloodPressure: [],
      maxHeartRate: [],
      measureTime: [],
    },
    lineMonth: {
      avgHighBloodPressure: [],
      avgLowBloodPressure: [],
      avgHeartRate: [],
      maxHighBloodPressure: [],
      maxLowBloodPressure: [],
      maxHeartRate: [],
      measureTime: [],
    },
    lineYear: {
      avgHighBloodPressure: [],
      avgLowBloodPressure: [],
      avgHeartRate: [],
      maxHighBloodPressure: [],
      maxLowBloodPressure: [],
      maxHeartRate: [],
      measureTime: [],
    },
    pieRecord: {
      pieHighBloodPressure: [],
      pieLowBloodPressure: [],
      pieHeartRate: [],
    },
    ecLineWeek: {
      onInit: initLineWeekChart
    },
    ecLineMonth: {
      onInit: initLineMonthChart
    },
    ecLineYear: {
      onInit: initLineYearChart
    },
    ecPieHigh: {
      onInit: initPieHighChart
    },
    ecPieLow: {
      onInit: initPieLowChart
    },
    ecPieHeart: {
      onInit: initPieHeartChart
    },
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
    this.generateMemberSelector()

    const today = new Date(new Date().toLocaleDateString())
    const weekQuery = {
      openId: this.data.openId,
      startTime: util.formatTime(new Date(today.getTime() - 604800000))
    }
    requestApi.post(recordApi.analyzeWeek, weekQuery).then(res => {
      console.log("返回结果: ", res.result)
      this.setData({
        lineWeek: res.result
      })
      this.setLineWeekOption()
    }).catch(err => {
      console.log(err)
    })

    const monthQuery = {
      openId: this.data.openId,
      startTime: util.formatTime(new Date(today.getTime() - 2592000000))
    }
    requestApi.post(recordApi.analyzeMonth, monthQuery).then(res => {
      console.log("返回结果: ", res.result)
      this.setData({
        lineMonth: res.result
      })
      this.setLineMonthOption()
    }).catch(err => {
      console.log(err)
    })

    const yearQuery = {
      openId: this.data.openId,
      startTime: util.formatTime(new Date(today.getTime() - 31536000000))
    }
    requestApi.post(recordApi.analyzeYear, yearQuery).then(res => {
      console.log("返回结果: ", res.result)
      this.setData({
        lineYear: res.result
      })
      this.setLineYearOption()
    }).catch(err => {
      console.log(err)
    })

    requestApi.post(recordApi.analyzeDistribution, monthQuery).then(res => {
      console.log("analyzeDistribution返回结果: ", res.result)
      const highRecords = res.result.pieHighBloodPressure
      for (let i = 0; i < highRecords.length; i++) {
        if (highRecords[i].name === '正常血压') {
          highRecords[i].itemStyle = {
            color: "#58D68D"
          }
        } else if (highRecords[i].name === '低血压') {
          highRecords[i].itemStyle = {
            color: "#5DADE2 "
          }
        } else if (highRecords[i].name === '1级高血压') {
          highRecords[i].itemStyle = {
            color: "#F4D03F "
          }
        } else if (highRecords[i].name === '2级高血压') {
          highRecords[i].itemStyle = {
            color: "#EB984E"
          }
        } else if (highRecords[i].name === '3级高血压') {
          highRecords[i].itemStyle = {
            color: "#EC7063 "
          }
        }
      }
      const lowRecords = res.result.pieLowBloodPressure
      for (let i = 0; i < lowRecords.length; i++) {
        if (lowRecords[i].name === '正常血压') {
          lowRecords[i].itemStyle = {
            color: "#58D68D"
          }
        } else if (lowRecords[i].name === '低血压') {
          lowRecords[i].itemStyle = {
            color: "#5DADE2 "
          }
        } else if (lowRecords[i].name === '1级高血压') {
          lowRecords[i].itemStyle = {
            color: "#F4D03F "
          }
        } else if (lowRecords[i].name === '2级高血压') {
          lowRecords[i].itemStyle = {
            color: "#EB984E"
          }
        } else if (lowRecords[i].name === '3级高血压') {
          lowRecords[i].itemStyle = {
            color: "#EC7063 "
          }
        }
      }
      const heartRecords = res.result.pieHeartRate
      for (let i = 0; i < heartRecords.length; i++) {
        if (heartRecords[i].name === '正常心率') {
          heartRecords[i].itemStyle = {
            color: "#58D68D"
          }
        } else if (heartRecords[i].name === '心动过缓') {
          heartRecords[i].itemStyle = {
            color: "#5DADE2 "
          }
        } else if (heartRecords[i].name === '异常缓慢') {
          heartRecords[i].itemStyle = {
            color: "#AF7AC5 "
          }
        } else if (heartRecords[i].name === '心动过速') {
          heartRecords[i].itemStyle = {
            color: "#F4D03F"
          }
        } else if (heartRecords[i].name === '异常快速') {
          heartRecords[i].itemStyle = {
            color: "#EB984E "
          }
        }
      }

      this.setData({
        pieRecord: res.result
      })
      this.setPieHighOption()
      this.setPieLowOption()
      this.setPieHeartOption()
    }).catch(err => {
      console.log(err)
    })
  },

  setLineWeekOption() {
    var option = {
      title: {
        text: '近一周 血压心率走势图',
        left: 'center',
        textStyle: {
          color: 'black',
          fontWeight: 'normal',
          fontSize: 14
        }
      },
      backgroundColor: "#ffffff",
      legend: {
        data: ['平均高压', '平均低压', '平均心率', '最高高压', '最高低压', '最高心率'],
        top: 25,
        left: 'center',
        backgroundColor: 'white',
        z: -1,
        width: 300
      },
      grid: {
        left: '10%', // 与容器左侧的距离
        right: '10%', // 与容器右侧的距离
        top: '30%', // 与容器顶部的距离
        bottom: '10%', // 与容器底部的距离
      },
      tooltip: {
        show: true,
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: this.data.lineWeek.measureTime,
        // show: false
      },
      yAxis: {
        x: 'center',
        type: 'value',
        splitLine: {
          lineStyle: {
            type: 'dashed'
          }
        }
        // show: false
      },
      series: [{
        name: '平均高压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineWeek.avgHighBloodPressure
      }, {
        name: '平均低压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineWeek.avgLowBloodPressure
      }, {
        name: '平均心率',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineWeek.avgHeartRate
      }, {
        name: '最高高压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineWeek.maxHighBloodPressure
      }, {
        name: '最高低压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineWeek.maxLowBloodPressure
      }, {
        name: '最高心率',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineWeek.maxHeartRate
      }]
    };
    setTimeout(() => {
      lineWeekChart.clear()
      lineWeekChart.setOption(option);
    }, 900)
  },

  setLineMonthOption() {
    var option = {
      title: {
        text: '近一月 血压心率走势图',
        left: 'center',
        textStyle: {
          color: 'black',
          fontWeight: 'normal',
          fontSize: 14
        }
      },
      backgroundColor: "#ffffff",
      legend: {
        data: ['平均高压', '平均低压', '平均心率', '最高高压', '最高低压', '最高心率'],
        top: 25,
        left: 'center',
        backgroundColor: 'white',
        z: -1,
        width: 300
      },
      grid: {
        left: '10%', // 与容器左侧的距离
        right: '10%', // 与容器右侧的距离
        top: '30%', // 与容器顶部的距离
        bottom: '10%', // 与容器底部的距离
      },
      tooltip: {
        show: true,
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: this.data.lineMonth.measureTime,
        // show: false
      },
      yAxis: {
        x: 'center',
        type: 'value',
        splitLine: {
          lineStyle: {
            type: 'dashed'
          }
        }
        // show: false
      },
      series: [{
        name: '平均高压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineMonth.avgHighBloodPressure
      }, {
        name: '平均低压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineMonth.avgLowBloodPressure
      }, {
        name: '平均心率',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineMonth.avgHeartRate
      }, {
        name: '最高高压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineMonth.maxHighBloodPressure
      }, {
        name: '最高低压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineMonth.maxLowBloodPressure
      }, {
        name: '最高心率',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineMonth.maxHeartRate
      }]
    };
    setTimeout(() => {
      lineMonthChart.clear()
      lineMonthChart.setOption(option);
    }, 900)
  },

  setLineYearOption() {
    var option = {
      title: {
        text: '近一年 血压心率走势图',
        left: 'center',
        textStyle: {
          color: 'black',
          fontWeight: 'normal',
          fontSize: 14
        }
      },
      backgroundColor: "#ffffff",
      legend: {
        data: ['平均高压', '平均低压', '平均心率', '最高高压', '最高低压', '最高心率'],
        top: 25,
        left: 'center',
        backgroundColor: 'white',
        z: -1,
        width: 300
      },
      grid: {
        left: '10%', // 与容器左侧的距离
        right: '10%', // 与容器右侧的距离
        top: '30%', // 与容器顶部的距离
        bottom: '10%', // 与容器底部的距离
      },
      tooltip: {
        show: true,
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: this.data.lineYear.measureTime,
        // show: false
      },
      yAxis: {
        x: 'center',
        type: 'value',
        splitLine: {
          lineStyle: {
            type: 'dashed'
          }
        }
        // show: false
      },
      series: [{
        name: '平均高压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineYear.avgHighBloodPressure
      }, {
        name: '平均低压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineYear.avgLowBloodPressure
      }, {
        name: '平均心率',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineYear.avgHeartRate
      }, {
        name: '最高高压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineYear.maxHighBloodPressure
      }, {
        name: '最高低压',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineYear.maxLowBloodPressure
      }, {
        name: '最高心率',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: {
          width: 1
        },
        data: this.data.lineYear.maxHeartRate
      }]
    };
    setTimeout(() => {
      lineYearChart.clear()
      lineYearChart.setOption(option);
    }, 900)
  },

  setPieHighOption() {
    var option = {
      title: {
        text: '近一月 高压/收缩压分布图',
        left: 'center',
        textStyle: {
          color: 'black',
          fontWeight: 'normal',
          fontSize: 14
        }
      },
      backgroundColor: "#ffffff",
      grid: {
        left: '10%', // 与容器左侧的距离
        right: '10%', // 与容器右侧的距离
        top: '20%', // 与容器顶部的距离
        bottom: '10%', // 与容器底部的距离
      },
      legend: {
        data: ['低血压', '正常血压', '1级高血压', '2级高血压', '3级高血压'],
        left: 'center',
        top: '10%',
        left: 'center',
        backgroundColor: 'white',
        z: -1,
        width: 300
      },
      tooltip: {
        trigger: 'item',
        z: 10,
        formatter: function(param)  //自定义弹出框的内容
        {
          //拼接一个字符串
          var res =  '具体日期：' + param.data.detail;
          return res;
        }
      },
      series: [{
        label: {
          normal: {
            fontSize: 12,
            formatter: '{b}:\n{d}%',
          }
        },
        type: 'pie',
        center: ['50%', '60%'],
        radius: ['0%', '40%'],
        data: this.data.pieRecord.pieHighBloodPressure
      }]
    };
    setTimeout(() => {
      pieHighChart.clear()
      pieHighChart.setOption(option);
    }, 900)
  },

  setPieLowOption() {
    var option = {
      title: {
        text: '近一月 低压/舒张压分布图',
        left: 'center',
        textStyle: {
          color: 'black',
          fontWeight: 'normal',
          fontSize: 14
        }
      },
      backgroundColor: "#ffffff",
      grid: {
        left: '10%', // 与容器左侧的距离
        right: '10%', // 与容器右侧的距离
        top: '20%', // 与容器顶部的距离
        bottom: '10%', // 与容器底部的距离
      },
      legend: {
        data: ['低血压', '正常血压', '1级高血压', '2级高血压', '3级高血压'],
        left: 'center',
        top: '10%',
        left: 'center',
        backgroundColor: 'white',
        z: -1,
        width: 300
      },
      tooltip: {
        trigger: 'item',
        z: 10,
        formatter: function(param)  //自定义弹出框的内容
        {
          //拼接一个字符串
          var res =  '具体日期：' + param.data.detail;
          return res;
        }
      },
      series: [{
        label: {
          normal: {
            fontSize: 12,
            formatter: '{b}:\n{d}%',
          }
        },
        type: 'pie',
        center: ['50%', '60%'],
        radius: ['0%', '40%'],
        data: this.data.pieRecord.pieLowBloodPressure
      }]
    };
    setTimeout(() => {
      pieLowChart.clear()
      pieLowChart.setOption(option);
    }, 900)
  },

  setPieHeartOption() {
    var option = {
      title: {
        text: '近一月 心率分布图',
        left: 'center',
        textStyle: {
          color: 'black',
          fontWeight: 'normal',
          fontSize: 14
        }
      },
      backgroundColor: "#ffffff",
      grid: {
        left: '10%', // 与容器左侧的距离
        right: '10%', // 与容器右侧的距离
        top: '20%', // 与容器顶部的距离
        bottom: '10%', // 与容器底部的距离
      },
      legend: {
        data: ['异常缓慢', '心动过缓', '正常心率', '心动过速', '异常快速'],
        left: 'center',
        top: '10%',
        left: 'center',
        backgroundColor: 'white',
        z: -1,
        width: 300
      },
      tooltip: {
        trigger: 'item',
        z: 10,
        formatter: function(param)  //自定义弹出框的内容
        {
          //拼接一个字符串
          var res =  '具体日期：' + param.data.detail;
          return res;
        }
      },
      series: [{
        label: {
          normal: {
            fontSize: 12,
            formatter: '{b}:\n{d}%',
          }
        },
        type: 'pie',
        center: ['50%', '60%'],
        radius: ['0%', '40%'],
        data: this.data.pieRecord.pieHeartRate
      }]
    };
    setTimeout(() => {
      pieHeartChart.clear()
      pieHeartChart.setOption(option);
    }, 900)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    // 获取组件
    this.ecComponent = this.selectComponent('#mychart-dom-line');
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
        value: members[i].openId
      }
      arr.push(member)
      if (members[i].nickName === this.data.userInfo.nickName) {
        this.setData({
          selectedMemberValue: members[i].openId,
        })
      }
    }
    this.setData({
      memberSelector: arr,
    })
  },

  onSelectMember(event) {
    console.log(event.detail)
    this.setData({
      selectedMemberValue: event.detail,
    })
    const recordQuery = this.data.recordQuery
    recordQuery.openId = event.detail
    // const today = new Date(new Date().toLocaleDateString())
    // recordQuery.startTime = util.formatTime(new Date(today.getTime() - 2592000000))
    // recordQuery.endTime = util.formatTime(new Date(today.getTime() + 86399999))
    // this.setData({
    //   recordQuery: recordQuery,
    // })
    // console.log(this.data.recordQuery)
    // requestApi.post(recordApi.analyze, this.data.recordQuery).then(res => {
    //   console.log("返回结果: ", res)
    // }).catch(err => {
    //   console.log(err)
    // })
  },


})