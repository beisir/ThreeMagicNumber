<template>
    <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">付费会员</span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="data2Box">
                                <div class="data2BoxTop">
                                    <div class="data2BoxTopCon">
                                        <dl>
                                            <dd>
                                                <span class="l-01">{{headerCircle.name}}</span>
                                                <span class="l-02"><template v-if="$privileges.user[($privileges.mapping[headerCircle.name]||{}).id]">{{headerCircle.todayNum}}</template></span>
                                                <span class="l-03" v-bind:class="{'icon-tt-up':headerCircle.ratioTrend,'icon-tt-lower':headerCircle.ratioTrend===false}"><template v-if="$privileges.user[($privileges.mapping[headerCircle.name]||{}).id]">{{headerCircle.ratioNum}}</template></span>
                                            </dd>
                                        </dl>
                                    </div>
                                    <div class="data2BoxTopArrow"><img src="static/img/arrow.png"></div>
                                </div>
                                <div class="data2BoxBot">
                                    <div class="data2BoxBotCon">
                                        <ul>
                                            <li v-for="nav in colorBlocks" v-if="$privileges.user[($privileges.mapping[nav.name]||{}).id]">
                                                <div class="data2BoxBotList">
                                                    <dl>
                                                        <dt>{{nav.name}}</dt>
                                                        <dd>
                                                            <span class="l-02">{{formatPercent(nav.todayNum)}}</span>
                                                            <span class="l-03" v-bind:class="{'icon-tt-up':nav.ratioTrend,'icon-tt-lower':nav.ratioTrend===false}">{{nav.ratioNum}}</span>
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <nav role="navigation" class="navbar navbar-default" style="background: rgb(245, 245, 245); margin-top: 30px; margin-bottom: 10px;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size: 16px;">
                                                续签率
                                         </span>
                                    </div>
                                </div>
                            </nav>
                            <div class="xqBox" style="overflow:hidden;padding:10px;border:1px dotted #ddd;margin-top: -11px;border-radius: 0 0 4px 4px;clear:both;">
                                <div class="tab-contentBox" style="padding:0 15px;" v-for="(item,itemName) in extend">
                                    <div class="tabBotCon">
                                        <h3>{{itemName}}</h3>
                                        <div class="uxerNumBox">
                                            <div class="userNumCon" v-for="(subItem,subItemIndex) in item" v-if="$privileges.user[($privileges.mapping[itemName+'-'+subItem.name]||{}).id]">
                                                <div class="userNumList">
                                                    <span class="userLeft">{{subItem.name}}</span>
                                                    <span class="userRig" v-bind:class="{'rigUp':subItem.ratioTrend,'rigDown':subItem.ratioTrend===false}">{{formatRatioNum(subItem.ratioNum)}}</span>
                                                    <span class="userRig">{{subItem.todayNum}}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <chart-tendency :navigation="tendencychart_Bar" ref="chartTendencyElement_bar" :timermillisec="0" :service="service" chartTitle="对比图"></chart-tendency>
                            <chart-tendency :navigation="tendencychart" ref="chartTendencyElement" :timermillisec="timerMillisec" :service='service'></chart-tendency>
                            <chart-tendency :navigation="tendencychart_Leads" ref="chartTendencyElement_Leads" :timermillisec="timerMillisec" :service='service'></chart-tendency>
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
                name: '新增Leads数',
                code: '99',
                filters: {
                    timelimit: ['today', 'lastmonth', 'weekly', 'monthly']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, /*{
                name: 'Leads转化率',
                code: '98',
                filters: {
                    timelimit: ['monthly']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            },*/ {
                name: 'Leads来源转化率',
                code: '297',
                filters: {
                    timelimit: ['monthly']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '3类客户数',
                code: '62',
                filters: {
                    timelimit: ['today', 'lastmonth', 'all']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            },/* {
                name: '交易付费会员数',
                code: '172',
                filters: {
                    timelimit: ['lastmonth']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }*/, {
                name: '卖家DAU',
                code: '28',
                filters: {
                    timelimit: ['lastmonth']
                },
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '付费会员',
                code: '0',
                todayNum: 0,
                yesterdayNum: 0,
                ratioNum: 0,
                ratioTrend: null
            }, {
                name: '新增Leads来源',
                code: '186',
                filters: {
                    timelimit: ['today', 'lastmonth', 'weekly', 'monthly']
                }
            }, {
                name: '已分配Leads数',
                code: '202',
                filters: {
                    timelimit: ['today', 'lastmonth', 'weekly', 'monthly']
                }
            }, {
                name: '已分配Leads来源',
                code: '194',
                filters: {
                    timelimit: ['today', 'lastmonth', 'weekly', 'monthly']
                }
            }, {
                name: '库存Leads总数',
                code: '290',
                filters: {
                    timelimit: ['today', 'lastmonth']
                }
            }, {
                name: '库存Leads来源',
                code: '282',
                filters: {
                    timelimit: ['today', 'lastmonth']
                }
            }],

            /**
             * [tendencychart_Bar 柱状图导航项配置]
             * @type {Array}
             */
            tendencychart_Bar: [{
                name: '客户满意度',
                code: '82'
            }, {
                name: '产品用户贡献',
                code: '100',
                filters: {
                    timelimit: ['Splitbyfinancial', 'Splitbysalesorder']
                }
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
                url: '/dataweb/feeuserchart'
            },

            /**
             * [标王、MMT数据]
             * @type {Object}
             */
            extend: {
                '增值产品': [{
                    name: '电销',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }, {
                    name: '渠道',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }, {
                    name: '行业',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }, {
                    name: '电商',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }],
                '基本产品': [{
                    name: '电销',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }, {
                    name: '渠道',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }, {
                    name: '行业',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }, {
                    name: '电商',
                    todayNum: 0,
                    yesterdayNum: 0,
                    ratioNum: 0,
                    ratioTrend: null
                }]
            }
        };
    },
    computed: {

        /**
         * [colorBlocks 色块区域数据]
         * @return {Array} [色块区域数据]
         */
        colorBlocks: function() {
            const _this = this,

                /**
                 * [_names 色块区域显示数据名称列表]
                 * @type {Array}
                 */
                 // _names = ['新增Leads数', 'Leads转化率', '3类客户数', '交易付费会员数', '卖家DAU'];
                _names = ['新增Leads数', '3类客户数', '卖家DAU'];

            /**
             * [从总体数据中过滤出色块区域数据]
             */
            return _this.navigation.filter(function(nav) {
                return _names.indexOf(nav.name) != -1;
            });
        },

        /**
         * [headerCircle 顶部圆形区域数据]
         * @return {Object} [顶部圆形区域数据]
         */
        headerCircle: function() {
            const _this = this,

                /**
                 * [_names 顶部圆形区域显示数据名称列表]
                 * @type {Array}
                 */
                _names = ['付费会员'];

            /**
             * [从总体数据中过滤出色块区域数据]
             */
            return _this.navigation.filter(function(nav) {
                return _names.indexOf(nav.name) != -1;
            })[0];
        },

        /**
         * [tendencychart 趋势图数据]
         * @return {Array} [趋势图数据]
         */
        tendencychart: function() {
            const _this = this,

                /**
                 * [_names 色块区域显示数据名称列表]
                 * @type {Array}
                 */
                // _names = ['Leads转化率', 'Leads来源转化率', '3类客户数', '交易付费会员数', '卖家DAU'];
            _names = ['Leads来源转化率', '3类客户数', '卖家DAU'];

            /**
             * [从总体数据中过滤出色块区域数据]
             */
            return _this.navigation.filter(function(nav) {
                return _names.indexOf(nav.name) != -1;
            });
        },

        /**
         * [tendencychart 趋势图Leads数据]
         * @return {Array} [趋势图Leads数据]
         */
        tendencychart_Leads: function() {
            const _this = this,

                /**
                 * [_names 色块区域显示数据名称列表]
                 * @type {Array}
                 */
                _names = ['库存Leads总数', '库存Leads来源', '新增Leads数', '新增Leads来源', '已分配Leads数', '已分配Leads来源'];

            /**
             * [从总体数据中过滤出色块区域数据]
             */
            return _this.navigation.filter(function(nav) {
                return _names.indexOf(nav.name) != -1;
            }).sort(function(a, b) {
                return _names.indexOf(a.name) - _names.indexOf(b.name);
            });
        }
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
         * [监听图表组件 beforeRender 事件]
         */
        _this.$refs.chartTendencyElement_bar.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                chart: {
                    type: 'column'
                },
                colors: ['#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92']
            });
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.chartTendencyElement_bar.$on('beforeRedraw', function(chartEntity) {

            /**
             * [公用Y坐标轴]
             */
            chartEntity.series.forEach((series, index) => {
                series.update({
                    yAxis: 0
                }, false);
            });
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.chartTendencyElement.$on('beforeRedraw', function(chartEntity) {

            /**
             * [当渲染 Leads转化率 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 98) {

                /**
                 * [更新Y坐标轴刻度数]
                 */
                chartEntity.update({
                    yAxis: {
                        tickAmount: 5,
                        tickInterval: 0.01
                    }
                }, false);
            }

            /**
             * [当渲染 3类客户数 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 62) {

                /**
                 * [公用Y坐标轴]
                 */
                chartEntity.series.forEach((series, index) => {
                    series.update({
                        yAxis: 0
                    }, false);
                });
            }

            /**
             * [当渲染 leads来源转化率时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 297) {
                /**
                 * [公用Y坐标轴]
                 */
                chartEntity.series.forEach((series) => {
                    series.update({
                        yAxis: 0
                    }, false);
                });

                /**
                 * [每次渲染都重置缩放比例]
                 */
                chartEntity.zoomOut();

            } else {

                /**
                 * [每次渲染都重置缩放比例]
                 */
                chartEntity.zoomOut();
            }
        });


        /**
         * [监听图表组件 beforeRender 事件]
         */
        _this.$refs.chartTendencyElement.$on('beforeRender', function(chartOptions) {

            /**
             * [当渲染 新增Leads数来源 已分配Leads来源 库存Leads来源 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 297) {
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
                        zoomType: ''
                    }
                });
            }

        });
        /**
         * [监听图表组件 beforeRender 事件]
         */
        _this.$refs.chartTendencyElement_Leads.$on('beforeRender', function(chartOptions) {

            /**
             * [当渲染 新增Leads数来源 已分配Leads来源 库存Leads来源 图表时，修改图表配置]
             */
            if (this.CurrentNavigation.code == 186 || this.CurrentNavigation.code == 194 || this.CurrentNavigation.code == 282) {
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
                        zoomType: ''
                    }
                });
            }
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.chartTendencyElement_Leads.$on('beforeRedraw', function(chartEntity) {
            const _t = this;

            /**
             * [当渲染 新增Leads数来源 已分配Leads数来源 图表时，修改图表配置]
             */
            if (_t.CurrentNavigation.code == 186 || _t.CurrentNavigation.code == 194 || _t.CurrentNavigation.code == 282) {

                /**
                 * [公用Y坐标轴]
                 */
                chartEntity.series.forEach((series, index) => {
                    series.update({
                        yAxis: 0
                    }, false);
                });

                /**
                 * [每次渲染都重置缩放比例]
                 */
                chartEntity.zoomOut();

                /**
                 * [显示 周度数据 月度数据 最近一个月 图表时，修改图表配置]
                 */
                if (_t.CurrentTimelimitFilter.code == 4 || _t.CurrentTimelimitFilter.code == 5 || _t.CurrentTimelimitFilter.code == 2) {

                    /**
                     * 显示最后一组数据
                     */
                    var xAxis = chartEntity.xAxis[0],
                        min = xAxis.categories.length - 1,
                        max = xAxis.categories.length - 1;
                    xAxis.setExtremes(min, max, false);

                    /**
                     * 显示重置图表比例按钮
                     */
                    chartEntity.showResetZoom();
                }
            } else {

                /**
                 * [每次渲染都重置缩放比例]
                 */
                chartEntity.zoomOut();
            }
        });
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
                    _yesterdayData = _processor((data.yesterdaydata || {}).main || []),

                    /**
                     * [_todaydata 色块区域的今天数据]
                     * @type {Object}
                     */
                    _todaydata = _processor((data.todaydata || {}).main || []),

                    /**
                     * [_yesterdayData_extend 扩展数据的昨天数据]
                     * @type {Object}
                     */
                    _yesterdayData_extend = (data.yesterdaydata || {}).renew || {},

                    /**
                     * [_todaydata_extend 扩展数据的今天数据]
                     * @type {Object}
                     */
                    _todaydata_extend = (data.todaydata || {}).renew || {};

                /**
                 * [处理扩展数据的今天、昨天数据]
                 */
                Object.keys(_yesterdayData_extend).forEach((item, index) => {
                    _yesterdayData_extend[item] = _processor(_yesterdayData_extend[item] || []);
                });
                Object.keys(_todaydata_extend).forEach((item, index) => {
                    _todaydata_extend[item] = _processor(_todaydata_extend[item] || []);
                });

                /**
                 * [更新扩展数据]
                 */
                Object.keys(_this.extend).forEach((key, index) => {
                    const _arr_temp = _this.extend[key] || [];
                    _arr_temp.forEach((item, itemIndex) => {

                        /**
                         * 更新当前项的今天、昨天、比率数据
                         */
                        _updater.call(item, (_todaydata_extend[key] || {})[item.name] || {}, (_yesterdayData_extend[key] || {})[item.name] || {});
                    });
                });

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
                _this.$http.get('/dataweb/feeuserdata', {}).then((response) => {
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
         * [formatRatioNum 格式化比率]
         * @param  {String} ratioNum [比率]
         * @return {String}          [比率]
         */
        formatRatioNum: function(ratioNum) {
            return ratioNum.toString().replace(/\-/ig, '');
        },

        /**
         * [formatPercent 根据百分比数据，设置数值的百分号、千分号、万分号]
         * @param  {String} percent [百分比数据]
         * @return {String}         [比率]
         */
        formatPercent: function(percent) {
            var symbols = [{
                    multiple: 1,
                    symbol: '%'
                }, {
                    multiple: 10,
                    symbol: '‰'
                }, {
                    multiple: 10,
                    symbol: '\u2031' // 防止'‱'乱码，转换成\u2031
                }],
                ratioRegExp = new RegExp('%$', 'i'),
                num = parseFloat(percent) || 0,
                tempSymbol,
                symbol = symbols[0];

            /**
             * [只处理百分号结尾的数字]
             */
            if (!ratioRegExp.test(percent)) {
                return percent;
            }

            /**
             * [只处理非0的数字]
             */
            if (num === 0) {
                return '0%';
            }

            while ((Math.abs(num) < 1) && (tempSymbol = symbols.shift())) {
                symbol = tempSymbol;
                num = num * symbol.multiple;
            }
            return num.toFixed(2) + symbol.symbol;
        }
    },
    components: {
        'chart-tendency': chartTendency
    }
}
</script>
