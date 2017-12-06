<template>
    <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">战斗力</span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="data3Box">
                                <div class="data3BoxTop">
                                    <div class="data3BoxTopCon">
                                        <dl>
                                            <dd>
                                                <span class="l-01">&nbsp;</span>
                                                <span class="l-02">战斗力</span>
                                                <span class="l-03">&nbsp;</span>
                                            </dd>
                                        </dl>
                                    </div>
                                    <div class="data3BoxTopArrow"><img src="static/img/arrow.png"></div>
                                </div>
                                <div class="data3BoxBot">
                                    <div class="data3BoxBotCon">
                                        <ul>
                                            <li v-for="nav in navigation" v-if="$privileges.user[($privileges.mapping[nav.name]||{}).id]">
                                                <div class="data3BoxBotList">
                                                    <dl>
                                                        <dt>{{nav.name}}</dt>
                                                        <dd>
                                                            <span class="l-02">{{nav.todayNum}}</span>
                                                            <span class="l-03" v-bind:class="{'icon-tt-up':nav.ratioTrend,'icon-tt-lower':nav.ratioTrend===false}">{{nav.ratioNum}}</span>
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <chart-tendency :navigation="navigation" ref="chartTendencyElement" :timermillisec="timerMillisec" :service='service'></chart-tendency>
                            <chart-tendency :navigation="navigation_monthly" ref="chartTendencyElement_monthly" :timermillisec="timerMillisec" :service='service' chartTitle="销售预测"></chart-tendency>
                            <chart-tendency :navigation="navigation_yearly" ref="navigation_yearly" :timermillisec="timerMillisec" :service='service' chartTitle="销售业绩趋势"></chart-tendency>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import chartTendency from './chart-tendency.vue'
export default {
    name: 'datapanel-effectives',
    data() {
        return {

            /**
             * [navigation 图表导航项配置]
             * @type {Array}
             */
            navigation: [{
                name: '职能满编率',
                code: '13',
                filters: {
                    timelimit: ['lastmonth']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '电销转正人数',
                code: '7',
                filters: {
                    timelimit: ['all']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '渠道转正人数',
                code: '10',
                filters: {
                    timelimit: ['all']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            },
            // {
            //     name: '百家文章数',
            //     code: '71',
            //     filters: {
            //         timelimit: ['all']
            //     },
            //     todayNum: 0,
            //     yesterdayNum: 0,
            //     ratioNum: 0,
            //     ratioTrend: null
            // },
             {
                name: '市场部发稿量',
                code: '78',
                filters: {
                    timelimit: ['all']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '渠道拜访量',
                code: '54',
                filters: {
                    timelimit: ['today', 'lastmonth']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '人均在线时长',
                code: '49',
                filters: {
                    timelimit: ['today', 'lastmonth', 'monthly']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null,
                numCallback: function(today, yesterday) {
                    this.todayNum = Highcharts.dateFormat('%H:%M:%S', today.num || 0);
                }
            }, {
                name: '人均有效通话次数',
                code: '74',
                filters: {
                    timelimit: ['today', 'lastmonth', 'monthly']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }],

            /**
             * [navigation_monthly 月度数据]
             * @type {Array}
             */
            navigation_monthly: [{
                name: '电销',
                code: '180',
                filters: {
                    timelimit: ['weekly', 'monthly']
                }
            }, {
                name: '渠道',
                code: '184',
                filters: {
                    timelimit: ['weekly', 'monthly']
                }
            }],

            /**
             * [navigation_yearly 年度数据]
             * @type {Array}
             */
            navigation_yearly: [{
                name: '电销',
                code: '178'
            }, {
                name: '渠道',
                code: '182'
            }],

            /**
             * [timerMillisec 数据定时更新时间间隔毫秒数]
             * @type {Number}
             */
            timerMillisec: 1000 * 60 * 11,

            /**
             * [service 数据服务配置]
             * @type {Object}
             */
            service: {
                url: '/dataweb/fightchart'
            }
        };
    },
    created: function() {
        const _this = this;

        /**
         * [此处做延迟处理，以免首次渲染的相关事件触发时，父组件监听不到]
         */
        setTimeout(function() {

            /**
             * 渲染图表
             */
            _this.render();
        });

        /**
         * [初始化渲染计时器]
         */
        _this.renderTimer = window.setInterval(function() {

            /**
             * 渲染图表
             */
            _this.render();

        }, _this.timerMillisec);

        /**
         * [VueEntity 暴露当前业务对象实例到全局]
         * @type {Object}
         */
        window.VueEntity = this;
    },
    beforeDestroy: function() {
        const _this = this;

        /**
         * 清除渲染计时器
         */
        window.clearInterval(_this.renderTimer);
    },
    mounted: function() {
        const _this = this;

        /**
         * [监听图表组件 dataReady 事件]
         */
        _this.$refs.chartTendencyElement.$on('dataReady', function(data) {

            /**
             * [当渲染 人均有效通话次数 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 74) {

                /**
                 * [只显示 电销销售 ]
                 */
                // data.dataList = (data.dataList || []).filter(function(item) {
                //     return item.name == '电销销售';
                // });
            }
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.chartTendencyElement.$on('beforeRedraw', function(chartEntity) {

            /**
             * [当渲染 人均在线时长 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 49) {
                 /**
                 * [公用Y坐标轴]
                 */
                chartEntity.series.forEach((series, index) => {
                    series.update({
                        yAxis: 0
                    }, false);
                });
                chartEntity.yAxis[0].update({
                    // type: 'datetime',
                    title: {
                        text: null
                    },
                    labels: {
                        formatter: function() {
                            var _t = this,
                                _m = Highcharts.dateFormat('%M:%S', _t.value),
                                _hour = ('00' + parseInt(_t.value / (1000 * 3600))).slice(-2);
                            return _hour + ':' + _m;
                        }
                    }
                }, false);              
                chartEntity.update({
                    tooltip: {
                        shared: true,
                        formatter: function() {
                            var _t = this,
                                toolString='<span style="font-size: 10px">' + this.x + '</span><br>';
                             this.points.forEach((val,i,arr)=>{
                                    toolString+='<span style="color:' + val.color + '">\u25CF</span>';
                                    toolString+='<tspan> ' + val.series.name + ': </tspan>';
                                    toolString+='<tspan style="font-weight:bold">' + (('00' + parseInt(val.y / (1000 * 3600))).slice(-2) + ':' + Highcharts.dateFormat('%M:%S', val.y)) + '</tspan><br>';
                             });
                            return toolString;
                        }
                    }
                }, false);
            }else {
              chartEntity.update({
                tooltip: {
                  formatter: null
                }
              }, false);
            }

            /**
             * [当渲染 人均有效通话次数 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 74) {

                /**
                 * [公用Y坐标轴]
                 */
                chartEntity.series.forEach((series, index) => {
                    series.update({
                        yAxis: 0
                    }, false);
                });
            }
        });

        /**
         * [监听图表组件 beforeRender 事件]
         */
        _this.$refs.chartTendencyElement_monthly.$on('beforeRender', function(chartOptions) {
             /**
             * 当渲染 电销修改图标配置
             */
             if(this.CurrentNavigation.code==180){
                 _this.changeSaleOptions(chartOptions);
             }else{
                 _this.changeChannelOptions(chartOptions)    
             }
        })
        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.chartTendencyElement_monthly.$on('beforeRedraw', function(chartEntity) {
            /**
             * 当渲染 电销修改图标配置
             */
             if(this.CurrentNavigation.code==180){
                  _this.changeSaleSeries(chartEntity)
             }else{
                  _this.changeChannelSeries(chartEntity)    
             }
             
        });

        /**
         * [监听销售业绩趋势图表组件 beforeRedraw 事件 显示一个y轴] 
         */
        _this.$refs.navigation_yearly.$on('beforeRedraw',function(chartEntity){
            if (this.CurrentNavigation.code === '178') {
                chartEntity.series.forEach((series, index) => {                    
                    series.update({
                        yAxis: 0
                    }, false);
                });
            }
        })
    },
    methods: {

        /**
         * [render 渲染页面]
         */
        render: function() {
            const _this = this;

            /**
             * [dataPromise 获取数据延迟对象]
             * @type {Promise}
             */
            const dataPromise = _this.getData();

            /**
             * [description]
             * @param  {Object} [数据对象]
             */
            dataPromise.then((data) => {

                /**
                 * [data 创建今日、昨日数据键值对格式数据]
                 * @type {Object}
                 */
                const _processor = function(arr) {
                        const ret = {};
                        arr.forEach((item, index) => {
                            ret[item.name] = item;
                        });
                        return ret;
                    },

                    /**
                     * [_decimals 获取数字的小数位数]
                     * @param  {Number} num [数字]
                     * @return {Number}     [小数位数]
                     */
                    _decimals = function(num) {
                        return (num.toString().split('.')[1] || '').length;
                    },

                    /**
                     * [_updater 更新指定项的今天、昨天、比率数据]
                     * @param  {Object} todayData     [今天数据]
                     * @param  {Object} yesterdayData [昨天数据]
                     */
                    _updater = function(todayData, yesterdayData) {
                        const _t = this;

                        /**
                         * [ratioNumFlag 是否比率数]
                         * @type {Boolean}
                         */
                        var ratioNumFlag = false,

                            /**
                             * [ratioRegExp 验证比率数的正则]
                             * @type {RegExp}
                             */
                            ratioRegExp = new RegExp('%$', 'i');

                        /**
                         * [初始化当前数据是否比率数]
                         */
                        ratioNumFlag = ratioRegExp.test(todayData.num) && ratioRegExp.test(yesterdayData.num);

                        /**
                         * [根据是否比率数初始化数据]
                         */
                        if (ratioNumFlag) {
                            _t.todayNum = (parseFloat(todayData.num) || 0) + '%';
                            _t.yesterdayNum = (parseFloat(yesterdayData.num) || 0) + '%';
                            _t.ratioNum = ((parseFloat(todayData.num) || 0) - (parseFloat(yesterdayData.num) || 0)).toFixed(2);
                        } else {
                            _t.todayNum = parseFloat(todayData.num) || 0;
                            _t.yesterdayNum = parseFloat(yesterdayData.num) || 0;
                            _t.ratioNum = ((((_t.todayNum - _t.yesterdayNum) / _t.yesterdayNum) * 100) || 0).toFixed(2);

                            /**
                             * [昨天数据中非百分比数据可能存在为 0 的情况，这时候 ratioNum 的值为 Infinity 或 NaN ，需要将该数据处理成 100% ]
                             */
                            if (!isFinite(((((_t.todayNum - _t.yesterdayNum) / _t.yesterdayNum) * 100) || 0).toFixed(2))) {
                                _t.ratioNum = 100;
                            }

                            /**
                             * [对数值进行格式化千分号处理，保留的小数位数]
                             */
                            _t.todayNum = Highcharts.numberFormat(_t.todayNum, _decimals(_t.todayNum), '.', ',');
                            _t.yesterdayNum = Highcharts.numberFormat(_t.yesterdayNum, _decimals(_t.yesterdayNum), '.', ',');
                        }

                        /**
                         * [ratioTrend 比率变化趋势]
                         */
                        _t.ratioTrend = (_t.ratioNum >= 0 ? true : false);

                        /**
                         * [ratioNum 比率添加单位]
                         */
                        _t.ratioNum = Math.abs(_t.ratioNum) + '%';

                        /**
                         * 存在自定义数据setter时，执行它
                         */
                        _t.numCallback && _t.numCallback.call(_t, todayData, yesterdayData);
                    },

                    /**
                     * [_yesterdayData 色块区域的昨天数据]
                     * @type {Object}
                     */
                    _yesterdayData = _processor(data.yesterdaydata || {} || []),

                    /**
                     * [_todaydata 色块区域的今天数据]
                     * @type {Object}
                     */
                    _todaydata = _processor(data.todaydata || {} || []);

                /**
                 * [预处理数据]
                 */
                _this.navigation.forEach((nav, index) => {

                    /**
                     * 更新当前项的今天、昨天、比率数据
                     */
                    _updater.call(nav, _todaydata[nav.name] || {}, _yesterdayData[nav.name] || {});
                });
            });
        },

        /**
         * [getData 获取数据]
         * @return {Promise} [数据延迟对象]
         */
        getData: function() {
            const _this = this;

            /**
             * [返回从远程数据服务中获取数据的延迟对象]
             */
            return new Promise((resolve, reject) => {
                _this.$http.get('/dataweb/fightdata', {}).then((response) => {
                    response = response.body || {};

                    /**
                     * [验证数据状态码]
                     */
                    if (parseInt(response.errno) !== 0) {
                        console.warn('返回数据状态码错误！');
                        reject(response);
                        return;
                    }

                    /**
                     * 解决延迟对象
                     */
                    resolve(response.data || {});
                }, (response) => {
                    console.warn('获取数据请求失败！');
                    reject(response);
                })
            });
        },

        /**
         * 销售预测-修改电销图表配置
         */
        changeSaleOptions(chartOptions){
             Object.assign(chartOptions,{
                    chart: {
                        type: "column"
                    },
                    colors:['#4572A7', "#bb7c2c", '#89A54E'],
                    tooltip: {
                        shared: true,
                        formatter: function() {
                            var toolString = "<b>" + this.x + "</b><br/>";
                            /*** 返回电销新签和电销增值的总和 */
                            let getTotalNumber=(pointNameLis)=>{
                                let pointsArr=this.points.filter((point)=>{
                                    return pointNameLis.includes(point.series.name)
                                })
                                if(pointsArr.length==2){
                                    return pointsArr[0].y+pointsArr[1].y
                                }else{
                                    return 0
                                }
                            }
                            /*** 遍历显示提示信息  */
                            this.points.forEach(function(point, i) {
                                let pointName=point.series.name,
                                    pointNameList=["电销整体","电销新签","电销增值"],
                                    number=point.y;
                                /***@augments
                                *  "电销整体","电销新签","电销增值" 加上预估值
                                *   电销整体预估值和电销整体实际值为0，数值显示等于电销新签预估值和电销增值预估值
                                    电销新签和电销增值如果为0则不显示
                                */ 
                                if(pointNameList.includes(pointName)){
                                    if(pointName=="电销整体"&&number==0){
                                        number=getTotalNumber(["电销新签","电销增值"]);
                                    }else if(number==0){
                                        return;
                                    }
                                    pointName+='预估值';
                                }else{
                                    if(pointName=="电销整体实际值"&&number==0){
                                         number=getTotalNumber(["电销新签实际值","电销增值实际值"]);
                                    }else if(number==0){
                                        return;
                                    }
                                }
                                toolString += pointName + ":" + number + "元" + "<br/>";
                            }, this);
                            return toolString;
                        }
                    },
                    plotOptions: {
                        column: {
                            stacking: "normal"
                        }
                    }
                })
        },

        /**
         * 销售预测-修改电销图表对象Series
         */
        changeSaleSeries(chartEntity){
            chartEntity.series.forEach((series, index) => {
                        /**预算值，预估值和实际值分组 */
                        let stackList=[{stackname:"budgetValue",des:"预算值",color:"#7d6f4c"},{stackname:"forecasts",des:"预估值"},{stackname:"actualValue",des:"实际值"}],
                            stackName=stackList.find((val)=>{
                                return series.name.indexOf(val.des)>=0
                            }),
                            /***月度数据隐藏预算值和实际值，周度数据，隐藏实际值 */
                            showInlegendVal=stackName.stackname!=="forecasts" ? false : true,
                            /***实际图例名称 */
                            seriesName=series.name.replace(/预估值/g,'');
                        /**
                        * [所有 series 单独分组]
                        */
                        series.update({
                            yAxis: 0,
                            // 柱状图分组
                            stack: stackName.stackname,
                            color:stackName.color,
                            name:seriesName,
                            //是否显示图例
                            showInLegend:showInlegendVal,
                            events: {
                                legendItemClick: function(e) {
                                    let _series=e.target.chart.series.filter((val)=>{
                                        return val.name==seriesName+'实际值'||val.name==seriesName+'预算值';
                                    });
                                    if(_series.length>=0){
                                        _series.forEach((val)=>{
                                            if(val.visible){
                                                val.hide();
                                            }else{
                                                val.show();
                                            }
                                        })
                                    }
                                }
                            }
                        }, false);
                    });
        },
         /**
         * 销售预测-修改渠道图表配置
         */
        changeChannelOptions(chartOptions){
            Object.assign(chartOptions, {
                        plotOptions: {
                            column: {
                                stacking: 'normal'
                            }
                        },
                        chart: {
                            type: 'column'
                        },
                        // legend: {//图例反转
                        //     reversed: true
                        // },
                        colors: ['#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92'],
                        tooltip: {
                            shared: true,
                            formatter: function() {
                                var _t = this,
                                    _total = 0,
                                    _predicted = 0;

                                var _arr = [
                                    '<span style="font-size: 10px;">' + _t.x + '</span><br>'
                                ];
                                for (var i = 0; i < _t.points.length; i++) {
                                    _arr = _arr.concat([
                                        '<span style="color:' + _t.points[i].color + '">\u25CF</span>',
                                        '<tspan> ' + _t.points[i].series.name + ': </tspan>',
                                        '<tspan style="font-weight:bold">' + _t.points[i].y + ' 元</tspan><br/>'
                                    ]);

                                    /**
                                     * [获取总量]
                                     */
                                    if ((!_total) && (_t.points[i].series.name === ('实际值'))) {
                                        _total = _t.points[i].total;
                                    }

                                    /**
                                     * [获取预估值]
                                     */
                                    if ((!_predicted) && (_t.points[i].series.name === ('预估值'))) {
                                        _predicted = _t.points[i].total;
                                    }
                                }

                                /**
                                 * [预估值不为0的时候才显示完成比例，以免数据显示为 infinity]
                                 */
                                if (_predicted !== 0) {
                                    _arr = _arr.concat([
                                        '<span>\u25CF</span>',
                                        '<tspan> 完成: </tspan>',
                                        '<tspan style="font-weight:bold">' + ((_total / _predicted) * 100).toFixed(2) + '%</tspan>'
                                    ]);
                                }
                                return _arr.join('');
                            }
                        }
                    });
        },
        /**
         * 销售预测-修改渠道图表对象Series
         */
        changeChannelSeries(chartEntity){
                   /**
                     * [创建分组名列表]
                     * @type {Array}
                     */
                    var stackNameScope = [97, 122],
                        stackNameCodeList = [],
                        stackNameList = [];
                    for (var i = stackNameScope[0]; i <= stackNameScope[1]; i++) {
                        stackNameCodeList.push(i);
                    }
                    stackNameList = String.fromCharCode.apply(null, stackNameCodeList).split('');

                    /**
                     * [series分组]
                     */
                    chartEntity.series.forEach((series, index) => {

                        /**
                         * [所有 series 单独分组]
                         */
                        series.update({
                            yAxis: 0,
                            stack: stackNameList[index]
                        }, false);

                    });
        }
       
    },
    components: {
        'chart-tendency': chartTendency
    }
}
</script>
