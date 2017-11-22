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
          pointFormat: "<b>{point.name}:{point.percentage:.1f}%</b>"
         // pointFormat: "<b>{point.percentage:.1f}%</b>"
        },
        plotOptions: {
          pie: {
            dataLabels: {
              enabled: true,
             // distance:-50,
              format:
                "<b>{point.name}</b>:{point.tadayNum}<br>与昨日对比：{point.trend} {point.percentnum}%  ",
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
  mounted() {

    this.$refs.pieChart.highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: '浏览器<br>占比',
            align: 'center',
            verticalAlign: 'middle',
            y: 50
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    distance: -50,
                    style: {
                        fontWeight: 'bold',
                        color: 'white',
                        textShadow: '0px 1px 2px black'
                    }
                },
                startAngle: -90,
                endAngle: 90,
                center: ['50%', '75%']
            }
        },
        series: [{
            type: 'pie',
            name: '浏览器占比',
            innerSize: '50%',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                ['Chrome', 12.8],
                ['Safari',    8.5],
                ['Opera',     6.2],
                {
                    name: '其他',
                    y: 0.7,
                    dataLabels: {
                        // 数据比较少，没有空间显示数据标签，所以将其关闭
                        enabled: false
                    }
                }
            ]
        }]
    });

  }
 
};
</script>

