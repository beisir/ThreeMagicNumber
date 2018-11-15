<template>
    <div id="app-overview-container" class="app-view app-overview-container">
        <div class="padding-md clearfix">
            <div class="panel panel-default panel-view realtime-counter">
                <!--标题开始-->
                <nav class="navbar navbar-default" role="navigation">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <span class="navbar-brand">
                                <i class="icon-tt-timely"></i>实时数据<i class="icon-tt-help tooltip-view"></i>
                            </span>
                        </div>
                        <div class="collapse navbar-collapse navbar-right">
                            <ul class="nav navbar-nav fields">
                                <li class="app-count" style="display: none">
                                    <a value="app" class="field" href="#app-overview-appRealtime-table">累计</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <!--标题结束-->
                <div id="app-overview-todayRealtime">
                    <div class="tab-contentBox">
                        <div class="tabBotCon">
                            <h3 style="margin-bottom:10px;">本网  <span style="float:right;font-weight:200;">{{getNowFormatDate(0)}}</span></h3>
                            <div class="col-sm-6 col-md-3" :key="index" v-for="(item,index) in realTimeData.main" v-on:click="redirect(item)" v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">
                                <div class="panel-stat realtime-source" :class="getClass(index)">
                                    <h5> {{ item.name }} </h5>
                                    <h1 class="realtime-value"> {{ item.num }} </h1>
                                    <span class="realtime-delta" style="display: block">{{ differ(item.num, item.yesterdayNum).num }}</span>
                                    <!--环比-->
                                    <div class="realtime-predict">
                                        <span> 昨日环比:</span>
                                        <span class="value"> {{ item.yesterdayNum}}</span>
                                        <span class="pull-right">
                                        <i
                                          :class="differ(item.num,item.yesterdayNum).state=='up'?'icon-tt-up':'icon-tt-lower'"></i>
                                        <span class="delta">{{ percentum(item.num, item.yesterdayNum) }}</span></span>
                                    </div>
                                    <!--环比结束-->
                                    <!--周比-->
                                    <div class="realtime-week">
                                        <span> 周同比:</span>
                                        <span class="value"> {{ realTimeData.weekdata[index].num}}</span>
                                        <span class="pull-right">
                                        <i
                                          :class="differ(realTimeData.weekdata[index].today, realTimeData.weekdata[index].num).state=='up'?'icon-tt-up':'icon-tt-lower'"></i>
                                        <span class="delta">{{ percentum( realTimeData.weekdata[index].today, realTimeData.weekdata[index].num) }}</span></span>
                                    </div>
                                    <!--周比结束-->
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- <div class="tab-contentBox">
                        <div class="tabBotCon">
                            <h3 style="margin-bottom:10px;">MIP站  <span style="float:right;font-weight:200;">{{getNowFormatDate(-1)}}</span></h3>
                            <div class="col-sm-6 col-md-4" :key="index" v-for="(item,index) in realTimeData.mipdata" v-on:click="redirect(item)" v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">
                                <div class="panel-stat realtime-source" :class="getClass(index)">
                                    <h5> {{ item.name }} </h5>
                                    <h1 class="realtime-value" style="padding-bottom:20px;"> {{ item.num }} </h1>
                                </div>
                            </div>
                        </div>
                    </div> -->
                </div>
                <!--付费会员-->
                <div class="tab-contentBox">
                    <div class="tabBotCon">
                        <h3>付费会员</h3>
                        <div class="uxerNumBox">
                            <div class="jgBox">
                            	<div class="jgCon">
                                    <div class="jgTop">
                                        <dl>
                                            <dt>友客会员<span>100000</span></dt>
                                            <dd>昨日环比：<span class="rigDown2">0.001%</span></dd>
                                        </dl>
                                    </div>
                                    <div class="jgRigBox">
                                    	<div class="jgRig dxCon">
                                            <dl>
                                                <dt>电销<span>100000</span></dt>
                                                <dd><span class="rigUp2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    	<div class="jgRig qdCon">
                                            <dl>
                                                <dt>渠道<span>100000</span></dt>
                                                <dd><span class="rigDown2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    	<div class="jgRig hyCon">
                                            <dl>
                                                <dt>行业<span>100000</span></dt>
                                                <dd><span class="rigDown2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    </div>
                                </div>

                                <div class="jgCon">
                                    <div class="jgTop">
                                        <dl>
                                            <dt>P4P会员<span>100000</span></dt>
                                            <dd>昨日环比：<span class="rigUp2">0.001%</span></dd>
                                        </dl>
                                    </div>
                                    <div class="jgRigBox">
                                    	<div class="jgRig dxCon">
                                            <dl>
                                                <dt>电销<span>100000</span></dt>
                                                <dd><span class="rigUp2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    	<div class="jgRig qdCon">
                                            <dl>
                                                <dt>渠道<span>100000</span></dt>
                                                <dd><span class="rigDown2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    	<div class="jgRig hyCon">
                                            <dl>
                                                <dt>行业<span>100000</span></dt>
                                                <dd><span class="rigDown2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    </div>
                                </div>

                            	<div class="jgCon">
                                    <div class="jgTop">
                                        <dl>
                                            <dt>MMT会员<span>100000</span></dt>
                                            <dd>昨日环比：<span class="rigDown2">0.001%</span></dd>
                                        </dl>
                                    </div>
                                    <div class="jgRigBox">
                                    	<div class="jgRig dxCon">
                                            <dl>
                                                <dt>电销<span>100000</span></dt>
                                                <dd><span class="rigUp2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    	<div class="jgRig qdCon">
                                            <dl>
                                                <dt>渠道<span>100000</span></dt>
                                                <dd><span class="rigDown2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    	<div class="jgRig hyCon">
                                            <dl>
                                                <dt>行业<span>100000</span></dt>
                                                <dd><span class="rigDown2">0.001%</span></dd>
                                            </dl>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- <div class="userNumCon" :key="index" v-for="(item,index) in realTimeData.feeuser" v-if="$privileges.user[($privileges.mapping[item.name]||{}).id]">
                                <div class="userNumList">
                                    <span class="userLeft">{{item.name}}</span>
                                    <span class="userRig" :class="differ(item.num,item.yesterdayNum).state=='up'?'rigUp':'rigDown'">{{ percentum(item.num, item.yesterdayNum)}}</span>
                                    <span class="userRig">{{item.num}}</span>
                                </div>
                            </div> -->
                        </div>
                    </div>
                </div>
                <!--&lt;!&ndash;战斗力&ndash;&gt;-->
                <div class="tab-contentBox2" v-show="realTimeData.fight.length>0">
                    <div class="tabBotCon">
                        <h3>战斗力</h3>
                        <div class="uxerNumBox">
                            <div class="userNumCon znBoxList" :key="index" v-for="(item,index) in realTimeData.fight">
                                <div class="borderNone">
                                    <h4>{{ item.name }}</h4>
                                    <div class="uxerNumBox">
                                        <div class="userNumCon"  :key="i" v-for="(data,i) in item.fightInfo" v-if="$privileges.user[($privileges.mapping[item.name+'-'+data.name]||{}).id]">
                                            <div class="userNumList">
                                                <span class="userLeft">{{data.name}}</span>
                                                <span class="userRig" :class="differ(data.num,data.yesterdayNum).state=='up'?'rigUp':'rigDown'">{{ differ(data.num, data.yesterdayNum, true).num}}</span>
                                                <span class="userRig">{{data.num}}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--今日趋势结构开始-->
            <chartTendency :navigation="todayOverChart" ref="todayChartElement" :timermillisec="timerMillisec" :service="todayService" chartTitle="今日趋势"></chartTendency>
            <!--今日趋势结构END-->
            <!--整体趋势结构开始-->
            <chartTendency :timermillisec="timerMillisec" :service="overallService" ref="overallChartElement" chartTitle="整体趋势" isShow="true"></chartTendency>
            <!--整体趋势结构END-->
        </div>
    </div>
</template>
<script type="text/ecmascript-6">
import chartTendency from './chart-tendency.vue'
export default {
    data() {
            return {
                /**
                 * [timerMillisec 数据定时更新时间间隔毫秒数]
                 * @type {Number}
                 */
                timerMillisec: 1000 * 60 * 9,

                /**
                 * [service 整体趋势数据服务配置]
                 * @type {Object}
                 */
                overallService: {
                    url: '/dataweb/daychart/'
                },

                /**
                 * [operationChart 图表1的配置项]
                 * @type {Number}
                 */
                todayOverChart: [{
                    name: 'IP',
                    code: '1'
                }, {
                    name: 'PV',
                    code: '2'
                }, {
                    name: 'UV',
                    code: '3'
                }, {
                    name: '付费会员',
                    code: '4001'
                }],
                /**
                 * [service 整体趋势数据服务配置]
                 * @type {Object}
                 */
                todayService: {
                    url: '/dataweb/hourchart/'
                },
                /***
                 * 总览数据集合
                 */
                realTimeData: {
                    fight: [],
                    feeuser: [],
                    main: []
                }
            }
        },
        components: {
            chartTendency
        },
        methods: {
            /** 获得实时数据每个色块的css样式 */
            getClass(index) {
                var className = '';
                switch (index) {
                    case 0:
                        className = 'bg-one';
                        break;
                    case 1:
                        className = "bg-two";
                        break;
                    case 2:
                        className = "bg-three";
                        break;
                    default:
                        className = "bg-four";
                }
                return className;
            },
            /*
             *计算实时数据今日和昨日的差
             * @param  todayData 昨天的数据
             * @param  yesterdayData 今天的数据
             * @param  isAbs  是否取绝对值  实时数据是不需要取绝对值的，战斗力里面的率的都是用的减法并且需要取绝对值
             */
            differ(todayData, yesterdayData, isAbs) {
                var num = todayData.replace(/[%,]/g, '') - yesterdayData.replace(/[%,]/g, ''),
                    resultData = {
                        num: '',
                        state: (num >= 0) ? 'up' : 'down'
                    };
                if (todayData.indexOf('%') > 0 && yesterdayData.indexOf('%') > 0) {
                    resultData.num = isAbs ? Math.abs(num.toFixed(2)) + '%' : num.toFixed(2) + '%'
                } else {
                    if (num >= 0) {
                        resultData.num = isAbs ? this.toCurrencyString(num) : '+' + this.toCurrencyString(num);
                    } else {
                        resultData.num = isAbs ? this.toCurrencyString(Math.abs(num)) : this.toCurrencyString(num);
                    }
                }
                return resultData;
            },
            /***添加，号分割符*/
            toCurrencyString: function(num) {
                return String(num).replace(/\d(?=(?:\d{3})+\b)/g, '$&,');
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
            // 获取实时数据的接口数据
            getRealDate() {
                const that = this;
                that.$http.get('/dataweb/abovedata').then((response) => {
                    response = response.body;
                    if (response.errno == 0) {
                        that.formatData(response.data.todaydata, response.data.yesterdaydata, response.data.weekdata, response.data.mipdata);
                    }
                }, (response) => {
                    console.log('获取实时数据的接口数据失败！')
                })
            },
            /** 重新组装后台返回的数据 */
            formatData(todaydata, yesterdaydata, weekdata, mipdata) {
                const that = this;
                const fightArr = [];
                that.realTimeData.feeuser = that.processData(todaydata.feeuser, yesterdaydata.feeuser);
                console.log(that.realTimeData.feeuser);
                that.realTimeData.main = that.processData(todaydata.main, yesterdaydata.main);
                that.realTimeData.weekdata = that.processWeekdata(todaydata.main, weekdata.main);
                // that.realTimeData.mipdata = mipdata.main;
                // console.log(that.realTimeData);
                todaydata.fight.forEach(function(item, index) {
                    const yesterdayFightItem = yesterdaydata.fight[index].fightInfo;
                    const resultData = that.processData(item.fightInfo, yesterdayFightItem);
                    const fightItem = {
                        name: item.name,
                        fightInfo: resultData
                    };
                    fightArr.push(fightItem)
                });
                fightArr.length > 0 ? that.realTimeData.fight = fightArr : "";
            },
            getNowFormatDate(AddDayCount) {
                    var dd = new Date();
                    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
                    var y = dd.getFullYear();
                    var m = dd.getMonth()+1;//获取当前月份的日期
                    var d = dd.getDate();
                    if (d<=9){
                        d = '0' + d
                    };
                    if (m<=9){
                        m = '0' + m
                    }
                    return y+"-"+m+"-"+d;
            },
            processWeekdata (todaydata, weekdata){
                weekdata = weekdata.map((res,index) => {
                    return {
                        name: res.name,
                        num: res.num,
                        today: todaydata[index].num
                    };
                });
                return weekdata;
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
                        'IP': '/datapanel/operation',
                        'PV': '/datapanel/operation',
                        'UV': '/datapanel/operation',
                        '付费会员': '/datapanel/member'
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
            console.log(this.$privileges)
            this.getRealDate();
        },
        mounted() {
            const _this = this;
            /***
             * 监听今日趋势图表重新绘制之前
             * @type {number}
             */
            this.$refs.todayChartElement.$on('beforeRedraw', (chartEntity) => {
                /**
                 * [公用Y坐标轴]
                 */
                chartEntity.series.forEach((series, index) => {
                    series.update({
                        yAxis: 0
                    }, false);
                });
            });

            /***
             * 监听整体趋势数据就绪事件，对数据进行权限控制
             * @type {number}
             */
            this.$refs.overallChartElement.$on('dataReady', (data) => {
                data.dataList = (data.dataList || []).filter((item) => {
                    return !!_this.$privileges.user[(_this.$privileges.mapping[item.name] || {}).id];
                });
            });

            /***
             * 监听整体趋势图表重新绘制之前
             * @type {number}
             */
            this.$refs.overallChartElement.$on('beforeRedraw', (chartEntity) => {
                /****
                 * 整体趋势：PV UV IP 一个y轴  付费会员一个y轴
                 */
                chartEntity.series.forEach((series, index) => {
                    var yIndex = (series.name == 'PV' || series.name == 'IP' || series.name == 'UV') ? 1 : 0;
                    series.update({
                        yAxis: yIndex
                    }, false);
                });
            })

        }

}
</script>
