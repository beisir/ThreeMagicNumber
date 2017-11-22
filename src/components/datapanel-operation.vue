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
                      <dd :key="index" v-for="(item,index) in dataTotal" v-show="dataTotal.length>0" v-on:click="redirect(item)">
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
                      <li :key="index" v-for="(item,index) in dataList" v-show="dataList.length>0" v-on:click="redirect(item)" v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">
                        <div class="dataBoxBotList">
                          <div  v-if="item.name!='P4P消耗'">
                          <dl>
                            <dt>{{ item.name }}</dt>
                            <dd>
                              <span class="l-02">{{ item.num }}</span>
                              <span class="l-03" :class="differ(item.num,item.yesterdayNum)=='up' ? 'icon-tt-up' : 'l-03 icon-tt-lower'">{{ percentum(item.num, item.yesterdayNum)
                                }}</span>
                            </dd>
                          </dl>
                          </div >
                          <div  v-else> 
                            <dl>
                              <a href="javascript:;" title="点击查看更多信息">
                              <dt>{{ item.name }}</dt>
                              <dd>
                                <span class="l-02">{{ item.num }}</span>
                                <span class="l-03" :class="differ(item.num,item.yesterdayNum)=='up' ? 'icon-tt-up' : 'l-03 icon-tt-lower'">{{ percentum(item.num, item.yesterdayNum)
                                  }}</span>
                              </dd>
                              </a>
                            </dl>
                          </div>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <chartTendency :navigation="operationChart" ref="operationChart" :timermillisec="timerMillisec" :service="service"></chartTendency>             
            </div>
          </div>
          <!--近30天的变化趋势-->
        </div>
      </div>
    </div>
     <!-- 遮罩层 -->
        <div class="popupBox" v-show="maskShow">
        	<div class="popupBg"></div>
            <div class="popupCon">
            	<div class="popupClose" @click="maskShow=false"></div>
                <div class="popupData" v-if="P4PData.name">
                	<dl @click="redirect(P4PData)">
                    	<dt>{{P4PData.name}}</dt>
                        <dd>
                        	<span class="l-02">{{ P4PData.num }}</span>
                          <span class="l-03" :class="differ(P4PData.num,P4PData.yesterdayNum)=='up' ? 'icon-tt-up' : 'l-03 icon-tt-lower'" >{{ percentum(P4PData.num, P4PData.yesterdayNum) }}</span>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    <!-- 遮罩层结束 -->
  </div>
</template>

<script  type="text/ecmascript-6">
require('highcharts/js/highcharts-more')(Highcharts);
import chartTendency from './chart-tendency.vue'

export default {
  data() {
    return {
      // 产品运营数据ip和uv汇总数据
      dataTotal: [], 

      //产品运营数据列表
      dataList: [], 

      /**
       * @description 
       * 遮罩的P4P数据
       * */
      P4PData:{},

      maskShow:true,

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
         name:"会员注册"
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
          '询盘数量': '/datapanel/map',
          'P4P消耗':'/datapanel/p4pConsumption'
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
     /** 禁止页面滚动 */
     document.body.style.overflowY="hidden";
     const _that = this;    
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
       *  百度联盟收入的
       */
      if (this.CurrentNavigation.code === '309' && this.CurrentTimelimitFilter.code == '5') {
          if(this.CurrentTimelimitFilter.code == '5'){
            //处理本月数据
            let numArr = [];
            for (var i = 0; i < data.dataList[0].data.length; i++) {
              var num1 = data.dataList[0].data[i],
                num2 = data.dataList[1].data[i],
                num3 = num2 / num1 * 100;
              numArr.push(Number(num3.toFixed(2)))
            }
            data.dataList.splice(1, 0, {
              "name": "月完成率",
              "data": numArr,
              "unit": "%",
              "isShow": true
            });
          }else if(this.CurrentTimelimitFilter.code == '8'){
             //处理月度数据
             let _data = (data.dataList || [])[0],
                 total = 0;
                (_data.data || []).forEach(function(item) {
                  total += item
                });
               this.chartEntity.setTitle(null, { text: '总收入：' + total.toFixed(2) + '元', align: 'right', x: -10 })
          }        
      }else {
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
      } else if (this.CurrentNavigation.code === '309' && this.CurrentTimelimitFilter.code == '5') {
          chartEntity.series.forEach((series, index) => {
            if (index == 1) {
              series.update({
                type: 'spline',
                yAxis: 1,
                zIndex: 100
              }, false);
            } else {
              series.update({
                yAxis: 0
              }, false);
            }
          });
      }
    });

  },
  watch:{
     /**@augments
      * 返回P4P消耗数据对象
      */
     dataList(){
        let p4pDataArr=this.dataList.filter((item)=>{
            return item.name=='P4P消耗'
        })
        this.P4PData=p4pDataArr.length>0?p4pDataArr[0]:{};
    },
    /**@augments
      * 根据遮罩层变化，控制页面是否滚动
      */
    maskShow(){
      if(this.maskShow==false){
        document.body.style.overflowY='auto'
      }
    }
  }
  
}

</script>
