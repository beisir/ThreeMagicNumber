<template>
   <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">p4p消耗</span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">                            
                            <div class="data2Box">
                              <pieChart />
                            </div>                         
                           <chartTendency :navigation="operationChart" ref="operationChart" :timermillisec="timerMillisec" :service="service"></chartTendency>
                        
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
require('highcharts/js/highcharts-more')(Highcharts);
import chartTendency from './chart-tendency.vue';
import pieChart from './pieChart.vue'
export default {
   data() {
    return {
      dataTotal: [], // P4P消耗
      dataList: [], //P4P数据列表

      /**
       * [timerMillisec 数据定时更新时间间隔毫秒数]
       * @type {Number}
       */
      timerMillisec: 1000 * 60 * 10,

      /**
       * [operationChart 图表1的配置项]
       * @type {Number}
       */
      operationChart: [
        {
          name: 'P4P关键词',
          code: '305',
          filters: {
            timelimit: ['lastmonth']
          }
        },
        {
          name: 'P4P竞价词',
          code: '307',
          filters: {
            timelimit: ['numberPeople', 'price']
          }
        },
        {
          name: 'P4P消耗',
          code: '323',
          filters: {
            timelimit: ['today', 'lastsevensays', 'lastmonth', 'all']
          }
        },
        {
          name: 'P4PCPC',
          code: '316',
          filters: {
            timelimit: ['lastmonth']
          }
        },
        {
          name: '全网定投',
          code: '317',
          filters: {
            timelimit: ['all','weekly']
          }
        }        
      ],

      /**
       * [service 数据服务配置]
       * @type {Object}
       */
      service: {
        url: '/dataweb/chartdata'
      }     
    }
  },
  components: {
    chartTendency,
    pieChart
  }, 
  mounted() {
    const _that = this;
    let dataList = [];    
    /****
     * 监听初始化图表一对象实例之前事件
     */
    _that.$refs.operationChart.$on('beforeRender', function(chartOptions) {
      /** 判断如果是P4P竞价词改变图标类型为散点图 */
      if (this.CurrentNavigation.code === '307') {
        Object.assign(chartOptions, {        
          chart: {
            type: 'bubble',
          },

          title: {
            text: 'P4P竞价词TOP50散点分布图'
          },
          /****
           * 不显示图例
           */
          legend: {
            enabled: false
          },
          /****
           * x轴配置信息
           */
          xAxis: {
            gridLineWidth: 1,
            title: {
              text: '价格（元）'
            },
            labels: {
              format: '{value}'
            }
          },
          /****
           * 鼠标指上圆点，显示的提示信息
           */
          tooltip: {
            shared: false,
            useHTML: true           
          },
          /****
           * 圆点上显示的关键词信息
           */
          plotOptions: {
            series: {
              dataLabels: {
                enabled: true,
                format: '{point.name}'
              }
            }
          }
        });
      } else {
        Object.assign(chartOptions, {         
          chart: {
            type: 'spline'
          },
          xAxis: {
            gridLineWidth: null,
            title: {
              text: null
            }
          },
          title: {
            text: ''
          },
          legend: {
            enabled: true
          },
          tooltip: {
            shared: true,
            useHTML: true            
          }
        });
      }
    });
    /****
     * 监听图表一渲染开始事件
     */
    _that.$refs.operationChart.$on('dataReady', function(data) {
      dataList = data.dataList;
    });
    /****
     * 监听图表一重绘事件之前
     */
    _that.$refs.operationChart.$on('beforeRedraw', function(chartEntity) {
      /****
       * p4p竞价词改变图表类型
       */
      if (this.CurrentNavigation.code === '307') {
        chartEntity.update({
          tooltip: {
            formatter: function() {
              var _t = this;
              return [
                '<span style="font-size: 10px">' + _t.key + '</span><br>',
                '<tspan> 人数: </tspan>',
                '<tspan style="font-weight:bold">' + _t.y + '个</tspan><br>',
                '<tspan> 金额: </tspan>',
                '<tspan style="font-weight:bold">' + _t.x.toFixed(2) + '元</tspan>'
              ].join('');
            }
          }
        }, false);

        /**
         * 删除y轴坐标轴
         */
        while (chartEntity.yAxis.length > 0) {
          chartEntity.yAxis[0].remove(false);
        }
        /**
         * 删除数据列
         */
        while (chartEntity.series.length > 0) {
          chartEntity.series[0].remove(false);
        }
        /**
         * 增加Y轴坐标
         */
        chartEntity.addAxis({

          title: {
            text: '人数（人）'
          },
          labels: {
            format: '{value}'
          }
        });
        /***
         * 增加series
         */
        chartEntity.addSeries({
          data: dataList
        });

      } else if (this.CurrentNavigation.code === '305') {
        /****
         * p4p关键词公用一个y轴
         */
        chartEntity.series.forEach((series, index) => {
          series.update({
            yAxis: 0
          }, false);
        });
        chartEntity.update({
          tooltip: {
            formatter: function() {
              var _t = this,
                _ret = [
                  '<table>'
                ];
              for (var i = 0; i < _t.points.length; i++) {
                _ret = _ret.concat([
                  '<tr><td style="color: ' + _t.points[i].color + '">' + _t.points[i].series.name + ': </td>',
                  '<td style="text-align: right"><b>' + Highcharts.numberFormat(_t.points[i].y, 0, '.', ',') + '个</b></td></tr>'
                ]);
              }
              _ret.push('</table>')
              return _ret.join('');
            }
          }
        }, false);
      }
    });
  }
}
</script>
