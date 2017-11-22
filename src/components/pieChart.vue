<template>
  <div ref="pieChart" id="pieChartWrap" ></div>
</template>

<script>
require("highcharts/js/highcharts-more")(Highcharts);
export default {
  data() {
    return {
      /**
       * [timerMillisec 数据定时更新时间间隔毫秒数]
       * @type {Number}
       */
      timerMillisec: 1000 * 60 * 10,

      /**图表定时器 */
      renderTimer: null,

      /**图表实例对象 */
      chartEntity: null,

      /**图表配置 */
      chartOption: {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false
        },
        credits: {
          enabled: false // 禁用版权信息
        },
        title: {
          align: "center",
          verticalAlign: "middle",
          style:{"fontSize":"16px"},
          y: 50
        },
        colors: ['#19c6Ed', '#FF7C4D', '#2BCC6B', '#C275DF'], //图表序列（Series）的默认颜色数组
        tooltip: {
          headerFormat: null,
         //pointFormat: "<b>{point.name}:{point.percentage:.1f}%</b>"
          pointFormat: "<b>{point.name}</b>:{point.tadayNum}<br>与昨日对比：{point.trend} {point.percentnum}%"
        },
        plotOptions: {
          pie: {
            dataLabels: {
              enabled: true,
              distance:-50,
              //format:"<b>{point.name}</b>:{point.tadayNum}<br>与昨日对比：{point.trend} {point.percentnum}%",            
              style: {
                color: "black"
              }
            },
            startAngle: -90,
            endAngle: 90,
            center: ["50%", "75%"]
          }
        }
      }
    };
  },
  created() {
    setTimeout(() => {
      this.render();
    });
  },
  methods: {
    /*
    * 渲染图表
     */
    render() {
      let that = this,
        chartPromise = that.getData();
      if (!this.chartEntity) {
        this.chartEntity = Highcharts.chart("pieChartWrap", that.chartOption);
      }
      /** 监听接口返回数据 */
      chartPromise.then(response => {
        let chartData = that.formChartData(response),
          total = chartData.dataTotal[0];

        /**删除数据列**/
        while (that.chartEntity.series.length > 0) {
          that.chartEntity.series[0].remove();
        }

        /***增加数据列 */
        that.chartEntity.addSeries({
          type: "pie",
          innerSize: "50%",
          data: chartData.dataList
        });

        /***更新标题 */
        that.chartEntity.title.update({
          text: `${total.name}${total.y}<br>与昨日对比：${total.trend}${total.percentnum}%`
        });
      });
    },
    /**
     * 重新组合后台返回的数据 
     */
    formChartData(data) {
      let chartData = {};
      for (let key in data) {
        let dataObj = data[key];
        /**组合数据 */
        dataObj.todaydata.forEach((item, i) => {
          /**昨天数据，百分比 */
          let yesterdaydata = dataObj.yesterdaydata[i].num,
            percentun = this.percentum(item.num, yesterdaydata),
            seriesData = {
              name: item.name,
              //今天的数据
              tadayNum:item.num,
               //今天的数据
              y:Number(item.num.replace(/,/g,'')),
              //今天和昨天的百分比
              percentnum:percentun.num,             
              //上升下降
              trend: percentun.direction 
            };
          if (!chartData[key]) {
            chartData[key] = [];
          }
          chartData[key].push(seriesData);
        });
      }
      return chartData;
    },
    /**
     * 计算百分比 
     * （今日截至到当前数据-昨天同一时间数据）/  昨天同一时间数据
     */
    percentum(todayData, yesterdayData) {
      var todayNum = todayData.replace(/,/g, ""),
          yesterdayNum = yesterdayData.replace(/,/g, ""),
          differ = todayNum - yesterdayNum,
          percentObj = {
            direction: differ >= 0 ? "↑" : "↓",
            num:yesterdayNum == 0 ? 100 : (Math.abs(differ / yesterdayNum) * 100).toFixed(2)
          };      
      return percentObj;
    },
    /**
     * 获取延迟对象
     */
    getData() {
      return new Promise((resolve, reject) => {
        this.$http.get("/dataweb/realtimepfpdata/").then(
          response => {
            response = response.body || {};

            /**验证状态码**/

            if (response.errno != 0) {
              console.warn("返回状态码错误！");
              reject(response);
            }

            /**解决延迟对象 */
            resolve(response.data || {});
          },
          response => {
            console.warn("获取请求数据失败！");
            reject(response);
          }
        );
      });
    }
  },
  mounted() {
    this.renderTimer = () =>
      setInterval(() => {
        this.render();
    }, this.timerMillisec);
  },
  destoryed(){
    /***清除定时器 */
    if(this.renderTimer){
      clearInterval(this.renderTimer)
    }
  }
};
</script>

