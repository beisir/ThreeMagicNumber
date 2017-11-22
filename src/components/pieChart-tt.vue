<template>
  <div ref="pieChart" id="pieChartWrap" ></div>
</template>

<script>
require("highcharts/js/highcharts-more")(Highcharts);
export default {
  data() {
    return {
      option:{
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: '浏览器<br>占比',
            align: 'center',
            verticalAlign: 'middle',            
            style:{"fontSize":"16px"},
            y: 50
        },
        tooltip: {
            headerFormat: null,
            pointFormat: "<b>{point.name}:{point.percentage:.1f}%</b>"
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    format:"<b>{point.name}</b>:{point.tadayNum}<br>与昨日对比：{point.trend} {point.percentnum}%  ",
                    distance: -50,
                    style: {
                         color: "black"
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
                ['Firefox',  14556623],
                ['IE',       788526.8]
               
            ]
        }]
    }
    };
  },
  mounted() {
    let that=this;
     Highcharts.chart(that.$refs.pieChart, that.option);

  }
 
};
</script>

