<template>
  <div id="key-indicators-container" class="app-view">
    <div class="padding-md clearfix paddTop50">
      <div class="row">
        <div class="col-md-12">
          <!--用户平台数据-->
          <div class="panel panel-default hour-trend">
            <nav class="navbar navbar-default" role="navigation">
              <div class="container-fluid">
                <div class="navbar-header">
                  <span class="navbar-brand">产品运营数据</span>
                </div>
              </div>
            </nav>
            <div class="panel-body tab-content mTop20">
              <div class="dataBox">
                <div class="dataBoxTop">
                  <div class="dataBoxTopCon">
                    <dl>
                      <dd v-for="(item,index) in dataTotal" v-show="dataTotal.length>0" v-on:click="redirect(item)">
                        <span class="l-01">{{ item.name }}</span>
                        <span class="l-02"><template v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">{{ item.num }}</template></span>
                        <span class="l-03" :class="differ(item.num,item.yesterdayNum)=='up' ? 'icon-tt-up' : 'l-03 icon-tt-lower' "><template v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">{{ percentum(item.num, item.yesterdayNum)
                          }}</template></span>
                      </dd>
                    </dl>
                  </div>
                  <div class="dataBoxTopArrow"><img src="static/img/arrow.png"></div>
                </div>
                <div class="dataBoxBot">
                  <div class="dataBoxBotCon">
                    <ul>
                      <li v-for="(item,index) in dataList" v-show="dataList.length>0" v-on:click="redirect(item)" v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">
                        <div class="dataBoxBotList">
                          <dl>
                            <dt>{{ item.name }}</dt>
                            <dd>
                              <span class="l-02">{{ item.num }}</span>
                              <span class="l-03" :class="differ(item.num,item.yesterdayNum)=='up' ? 'icon-tt-up' : 'l-03 icon-tt-lower'">{{ percentum(item.num, item.yesterdayNum)
                                }}</span>
                            </dd>
                          </dl>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <chartTendency :navigation="operationChart" ref="operationChart" :timermillisec="timerMillisec" :service="service"></chartTendency>
              <chartTendency :navigation="chartOperationKeword" ref="chartOperationKeword" :timermillisec="timerMillisec" :service="serviceKeword"></chartTendency>
            </div>
          </div>
          <!--近30天的变化趋势-->
        </div>
      </div>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
require('highcharts/js/highcharts-more')(Highcharts);
import chartTendency from './chart-tendency.vue'

export default {
  data() {
    return {
      dataTotal: [], // 产品运营数据ip和uv汇总数据
      dataList: [], //产品运营数据列表

      /**
       * [timerMillisec 数据定时更新时间间隔毫秒数]
       * @type {Number}
       */
      timerMillisec: 1000 * 60 * 10,

      /**
       * [operationChart 图表1的配置项]
       * @type {Number}
       */
      operationChart: [{
          name: '百度联盟收入',
          code: '309',
          filters: {
            timelimit: ['thismonth', 'monthly']
          }
        },
        {
          name: 'PV',
          code: '2',
          filters: {
            timelimit: ['today', 'lastsevensays', 'lastmonth']
          }
        },
        {
          name: '询盘数量',
          code: '22',
          filters: {
            timelimit: ['today', 'lastsevensays', 'lastmonth']
          }
        },
        {
          name: '会员注册',
          code: '44',
          filters: {
            timelimit: ['today', 'lastsevensays', 'lastmonth']
          }
        },
        {
          name: 'P4P消耗',
          code: '46',
          filters: {
            timelimit: ['today', 'lastsevensays', 'lastmonth', 'all']
          }
        }
      ],

      /**
       * [service 数据服务配置]
       * @type {Object}
       */
      service: {
        url: '/dataweb/chartdata'
      },
      /**
       * [operationChart 图表2的配置项]
       * @type {Number}
       */
      chartOperationKeword: [

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
        }
      ],
      /**
       * [service 数据服务配置]
       * @type {Object}
       */
      serviceKeword: {
        url: '/dataweb/secondchartdata'
      }
    }
  },
  components: {
    chartTendency
  },
  methods: {
    /*
     *计算实时数据今日和昨日的差
     * @param  todayData 昨天的数据
     * @param  yesterdayData 今天的数据
     */
    differ(todayData, yesterdayData) {
      var num = '';
      if (todayData.indexOf('%') > 0 && yesterdayData.indexOf('%') > 0) {
        num = todayData.replace(/%/g, '') - yesterdayData.replace(/%/g, '');
      } else {
        num = todayData.replace(/,/g, '') - yesterdayData.replace(/,/g, '');
      }
      if (num >= 0) {
        return 'up';
      } else {
        return 'down';
      }
    },
    /** 计算百分比 （今日截至到当前数据-昨天同一时间数据）/  昨天同一时间数据**/
    percentum(todayData, yesterdayData) {
      var todayNum = todayData.replace(/,/g, ''),
        yesterdayNum = yesterdayData.replace(/,/g, ''),
        num = (todayNum - yesterdayNum) / yesterdayNum;
      if (yesterdayNum == 0) {
        return '100.00%';
      }
      return (Math.abs(num) * 100).toFixed(2) + '%';
    },

    /**
     * 获取用户平台数据
     */
    getPlatformData() {
      this.$http.get('/dataweb/realtimedata/').then((response) => {
        response = response.body;
        if (response && response.errno == 0) {
          const dataTotal = response.data.dataTotal;
          const dataList = response.data.dataList;
          if (dataTotal.todaydata && dataTotal.yesterdaydata) {
            this.dataTotal = this.processData(dataTotal.todaydata, dataTotal.yesterdaydata);
          }
          if (dataList.todaydata && dataList.yesterdaydata) {
            this.dataList = this.processData(dataList.todaydata, dataList.yesterdaydata);
          }
        }
      }, (response) => {
        console.log('用户平台数据接口获取失败')
      })
    },
    /**
     * 根据后台返回的数据处理成
     * [
     *  {
           *    name:"会员注册"
                num:"1,692" 今天的数据
                yestadayNum:"1,804" 昨天的数据
               }
     * ]
     */
    processData(todaydata, yesterdaydata) {
      var dataArr = [];
      todaydata.forEach(function(item) {
        const _yesterdayData = yesterdaydata.filter(function(data) {
          return data.name == item.name
        });
        if (_yesterdayData.length > 0) {
          item.yesterdayNum = _yesterdayData[0].num;
          dataArr.push(item);
        }
      });
      return dataArr
    },
    /**
     * [redirect 重定向到新路由]
     */
    redirect: function(item) {

      /**
       * [redirectMapping 重定向映射]
       * @type {Object}
       */
      const redirectMapping = {
          'IP': '/datapanel/map',
          'PV': '/datapanel/map',
          'UV': '/datapanel/map',
          '询盘数量': '/datapanel/map'
        },

        /**
         * [redirectPath 重定向路径]
         * @type {String}
         */
        redirectPath = redirectMapping[item.name.toUpperCase()];

      if (redirectPath) {
        this.$router.push(redirectPath);
      }
    }
  },
  created() {
    this.getPlatformData();
  },
  mounted() {
    const _that = this;
    let dataList = [];
    /****
     * 监听趋势图2创建之前
     */
    _that.$refs.chartOperationKeword.$on('dataReady', function(data) {
      dataList = data.dataList;
    });
    /****
     * 监听趋势图2初始化图表对象实例之前事件
     */
    _that.$refs.chartOperationKeword.$on('beforeRender', function(chartOptions) {
      if (this.CurrentNavigation.code === '307') {
        Object.assign(chartOptions, {
          /****
           * 更改图表类型
           */
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
            useHTML: true,
            // headerFormat: '<table>',
            // pointFormat: '<tr><th colspan="2"><p>{point.name}</p></th></tr>' +
            //   '<tr><th>人数:</th><td>{point.y:,.0f}个</td></tr>' +
            //   '<tr><th>金额:</th><td>{point.x:.2f}元</td></tr>',
            // footerFormat: '</table>',
            // followPointer: true,
            // valueDecimals: 2
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
          /****
           * 更改图表类型
           */
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
            useHTML: true,
            // headerFormat: '<small></small><table>',
            // pointFormat: '<tr><td style="color: {series.color}">{series.name}: </td>' +
            //   '<td style="text-align: right"><b>{point.y}</b></td></tr>',
            // footerFormat: '</table>',
            // valueDecimals: 2
          }
        });
      }
    });

    /****
     * 监听趋势图2重新绘制之前
     */
    _that.$refs.chartOperationKeword.$on('beforeRedraw', function(chartEntity) {
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

    /****
     * 图表一创建之前
     */
    _that.$refs.operationChart.$on('beforeRender', function(chartOptions) {
      /****
       *  百度联盟收入，则修改图表类型
       */
      if (this.CurrentNavigation.code === '309') {
        Object.assign(chartOptions, {
          chart: {
            type: 'column',
            zoomType: 'x'
          },
          colors: ['#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92']
        });
      } else {
        Object.assign(chartOptions, {
          chart: {
            type: 'spline',
            zoomType: '',
            subtitle: null
          }
        });
      }
    });

    /**
     * 监听-触发渲染开始事件
     */
    _that.$refs.operationChart.$on('dataReady', function(data) {
      /***
       *  百度联盟收入的本月数据，增加总收入副标题
       */
      if (this.CurrentNavigation.code === '309' && this.CurrentTimelimitFilter.code == '8') {
        var _data = (data.dataList || [])[0],
          total = 0;
        (_data.data || []).forEach(function(item) {
          total += item
        });
        this.chartEntity.setTitle(null, { text: '总收入：' + total.toFixed(2) + '元', align: 'right', x: -10 })
      } else {
        this.chartEntity.setTitle(null, { text: null })
      }
    });

    /****
     * 监听趋势图1图标重新绘制之前
     */
    _that.$refs.operationChart.$on('beforeRedraw', function(chartEntity) {
      /**
       * pv和询盘数量公用一个Y坐标轴
       */
      if (this.CurrentNavigation.code === '2' || this.CurrentNavigation.code === '22') {
        chartEntity.series.forEach((series, index) => {
          series.update({
            yAxis: 0
          }, false);
        });
      } else if (this.CurrentNavigation.code === '44' || this.CurrentNavigation.code === '46') {
        /****
         * 会员注册，P4P消耗：
         * 今天：IP、UV 一个y轴 今天，昨天，七天前一个y轴
         * 最近七天、最近一个月：IP、UV 一个y轴 P4P消耗一个y轴
         * 累计：一个y轴
         */
        if (this.CurrentTimelimitFilter.code === '3') {
          chartEntity.series[0].update({
            yAxis: 0
          }, false);
        } else {
          chartEntity.series.forEach((series, index) => {
            var yIndex = (series.name == 'IP' || series.name == 'UV') ? 1 : 2;
            series.update({
              yAxis: yIndex
            }, false);
          });
        }
      }

    });

  }
}

</script>
