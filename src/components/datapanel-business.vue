<template lang="html">
    <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default hour-trend">
                        <nav role="navigation" class="navbar navbar-default">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <router-link v-for="(item, index) in navRouterLink" v-if="$privileges.user[item.id]" :class="['new-brand', (index === linkIndex)? 'link-active': '']" :to="item.path">{{item.name}}</router-link>
                                </div>
                            </div>
                        </nav>
                        <div class="p4pCount">
                            <div class="countCon">
                                <div class="unitPrice"><span>客单价：</span><p>{{formulaData["客单价"]}} 元</p></div>
                                <div class="memberNum"><span><em class="xIco"></em>用户数：</span><p>{{formulaData["用户数"]}} 个</p></div>
                                <div class="salesVolume"><span>销售额：</span><p>{{formulaData["销售额"]}} 元</p></div>
                            </div>
                        </div>
                        <div class="panel-body tab-content mTop20">
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart1" :timermillisec="0" :service="service.mmtLine" chartTitle="第四个折线图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pWidenedChart" :timermillisec="0" :service="service.doubles" chartTitle="第一个扇形图"></chart-tendency>
                            </div>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="mapElements" :timermillisec="timerMillisec" :service='service.map' chartTitle="地区分布" :chartEntityConstructor="chartEntityConstructor()"  :resetYAxisBeforeRedraw="false"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pBubble"  :timermillisec="0" :service="service.complex" chartTitle="第一个气泡"></chart-tendency>
                            </div>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pOperatedChart" :timermillisec="0" :service="service.twocircle" chartTitle="第二个双圆图"></chart-tendency>
                            </div>
                        </div>
                        
                        <div class="panel-body tab-content mTop20">
                            <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">服务效果</span>
                                    </div>
                                </div>
                            </nav>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pService" :timermillisec="0" :service="service.mmt_column" :resetSeriesBeforeRedraw="false"  :resetYAxisBeforeRedraw="false" chartTitle="第一个柱状图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pServices" :timermillisec="0" :service="service.column3d"  :resetYAxisBeforeRedraw="false" chartTitle="第二个柱状图"></chart-tendency>
                            </div>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pBrokenLine1" :timermillisec="0" :service="service.mmtLine"  :resetSeriesBeforeRedraw="false"  :resetYAxisBeforeRedraw="false" chartTitle="第四个折线图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pBrokenLine" :timermillisec="0" :service="service.mmtLine"  :resetSeriesBeforeRedraw="false"  :resetYAxisBeforeRedraw="false" chartTitle="第四个折线图"></chart-tendency>
                            </div>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pBrokenLine2" :timermillisec="0" :service="service.mmtLine"  :resetSeriesBeforeRedraw="false"  :resetYAxisBeforeRedraw="false" chartTitle="第四个折线图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pMemberUV" :timermillisec="0" :service="service.mmtLine"  :resetSeriesBeforeRedraw="false"  :resetYAxisBeforeRedraw="false" chartTitle="第四个折线图"></chart-tendency>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import chartTendency from './chart-tendency.vue'
require('highcharts/modules/variable-pie')(Highcharts);
require('highcharts/modules/wordcloud')(Highcharts);
require('highcharts/modules/oldie')(Highcharts);
require('highcharts/highcharts-more')(Highcharts);
// require('highcharts/modules/series-label')(Highcharts);
/**
 * 
 * [mapMetaData 导入地图地理位置数据]
 * @type {Object}
 */
var mapMetaData = require('../highcharts/highchartsGeoChina');
export default {
    data () {
        return {
            linkIndex: 2,
            navRouterLink: [
                {
                    name: 'P4P产品',
                    id: 81,
                    path: '/datapanel/p4pOperatedata'
                },
                {
                    name: '友客产品',
                    id: 82,
                    path: '/datapanel/friendProducts'
                },
                {
                    name: '买卖通产品',
                    id: 83,
                    path: '/datapanel/business'
                }
            ],
            /**
             * [service 数据服务配置]
             * @type {Object}
             */
            service: {
                twocircle: {
                    url: '/dataweb/mmt_twocircle'
                },
                doubles: {
                    url: '/dataweb/dist'
                },
                mmtLine: {
                    url: '/dataweb/mmtLine'
                },
                /**
                 * [map 地区分布明细]
                 * @type {Object}
                 */
                map: {
                    url: '/dataweb/complex?flag=map'
                },
                complex: {
                    url: '/dataweb/complex'
                },
                column3d: {
                    url: '/dataweb/column3d'
                },
                mmt_column: {
                    url: '/dataweb/mmt_column'
                },
                mmt_column: {
                    url: '/dataweb/mmt_column'
                }
            },
            // 顶层p4p运营数据
            formulaData: {
                "用户数": "",
                "客单价": "",
                "销售额": ""
            },
            semicircleConfig: {
                chart: {
                    type: 'variablepie'
                },
                colors: ['#19c6Ed', '#FF7C4D', '#2BCC6B', '#C275DF','#aa4643', '#B5CA92', '#A47D7C'],
                title: {
                    text: '会员类型'
                },
                legend: {
            		align: 'left',
            		verticalAlign: 'top',
            		y: 50,
            		layout: 'vertical',
                    itemMarginTop: 20

            	},
                plotOptions: {
                    variablepie: {
                        stacking: 'normal',
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
            				enabled: true,
            				distance: 20,
                            formatter: function () {
            					return (this.y / 100) + '%';
            				},
            				style: {
            					fontWeight: 'bold',
            					color: 'white',
            					textShadow: '0px 1px 2px black'
            				}
            			},
                        showInLegend: true
                    }
                },
                tooltip: {
                    headerFormat: '',
                    pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/><br/>占比: <b>{point.z}%</b>'
                },
            },
            p4pLineConifg: {
                title: {
            		text: '销售额趋势'
            	},
            	yAxis: {
            		title: {
            			text: null
            		}
            	}/*,
            	legend: {
            		layout: 'vertical',
            		align: 'right',
            		verticalAlign: 'middle'
            	}*/
            },
            /**
             * [timerMillisec 数据定时更新时间间隔毫秒数]
             * @type {Number}
             */
            timerMillisec: 1000 * 60 * 11,
            /**
             * [mapNavigation 图表导航项配置]
             * @type {Array}
             */
            mapNavigation: [],
        };
    },
    destroyed () {
        document.querySelector('ul.sub-nav').querySelectorAll('a')[1].classList.remove('sub-nav-checked')
    },
    created () {
        const _this = this;
        this.$http.get('/dataweb/mmtFormula').then((response) => {
            response = response.body || {};

            /**
             * [验证数据状态码]
             */
            if (parseInt(response.errno) !== 0) {
                console.warn('返回数据状态码错误！');
                reject(response);
                return;
            };
            response.formula.forEach((formulaItem, formulaIndex) => {
                _this.formulaData[formulaItem.name] = formulaItem.num;
            });
        });
    },
    /**
     * [mounted 初始化实例图表]
     * @return {[type]} [description]
     */
    mounted () {

        document.querySelector('ul.sub-nav').querySelectorAll('a')[1].classList.add('sub-nav-checked')
        const _this = this;
        /*-------------------------------------------------------------------------*/

        _this.$refs.p4pLineChart1.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg);
        });
        _this.$refs.p4pLineChart1.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'sale'
                }
            });
        });
        _this.$refs.p4pLineChart1.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                var yIndex = series.name == '客单价' ? 0 : 1;
                series.update({
                    yAxis: yIndex,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    },
                    // zIndex: index
                }, false);
            });
        });

        
        /**
         * [同心圆：会员商机 开始]
         * [beforeRender    默认数据充值 = 消耗 + 余额]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pOperatedChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                chart: {	// 设置是否为圆形
            		type: 'pie'
            	},
            	title: {  // 主标题
            		text: '会员商机'
            	},
                legend: {
            		align: 'left',
            		verticalAlign: 'top',
            		y: 50,
            		layout: 'vertical',
                    itemMarginTop: 20

            	},
            	plotOptions: {
            		pie: {
            			shadow: false,
            			center: ['50%', '50%'],
                        allowPointSelect: true,
            			cursor: 'pointer',
            			dataLabels: {
            				enabled: true,
                            format: '{point.percentage:.1f} %',
            				style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
            				}
            			},
                        showInLegend: true
            		}
            	},
            	tooltip: {
            		valueSuffix: '%'	// 滑动状态时数值之后的单位
            	}
            });
        });
        /**
         * [同心圆： 缓存数据]
         * [beforeRender    默认数据充值 = 消耗 + 余额]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pOperatedChart.$on('dataReady', function(data) {
            var _t = this;
            _t.data = data || {};
        });
        /**
         * [同心圆： 会员商机 结束]
         * [beforeRedraw    组装最后的数据]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pOperatedChart.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            /**
             * [_data 获取缓存数据]
             */
            let {browserData, versionsData} = _this.filterDoubleData(_t.data);
            /**
             * [添加图表序列数据]
             */
            chartEntity.addSeries({
        		data: browserData,
        		size: '60%',
                name: '占比',
        		dataLabels: {
        			formatter: function () {
        				return this.y > 5 ? this.point.name : null;
        			},
        			color: '#ffffff',
        			distance: -30
        		}
            }, false);
            chartEntity.addSeries({
        		data: versionsData,
        		size: '80%',
                name: '占比',
        		innerSize: '60%',
        		dataLabels: {
        			formatter: function () {
        				return this.y > 1 ? '<b>' + this.point.name + ':</b> ' +
        					this.y : null;
        			}
        		},
        		id: 'versions'
            }, false);
        });
        /**
         * [概况图->会员类型占比图开始]
         * [beforeRender 初始化图表配置选项]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pWidenedChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.semicircleConfig);
        });
        /**
         * [概况图->会员类型占比]
         * [beforeGetData 设置调用接口参数]
         * @param  {[type]} params [description]
         * @return {[type]}        [description]
         */
        _this.$refs.p4pWidenedChart.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    type: 395
                }
            });
        });
        /**
         * [概况图->会员类型占比]
         * [dataReady 存储获取的接口数据]
         * @param  {[type]} params [description]
         * @return {[type]}        [description]
         */
        _this.$refs.p4pWidenedChart.$on('dataReady', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /**
         * [概况图->会员类型占比图结束]
         * [beforeRedraw 设置图表生成参数]
         * @param  {[type]} params [description]
         * @return {[type]}        [description]
         */
        _this.$refs.p4pWidenedChart.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            chartEntity.addSeries({
                minPointSize: 10,
        		innerSize: '20%',
        		zMin: 0,
        		data: _t.data
            }, false);
        });
        /*-------------------------------------------------------------------------*/


        /** 
         * 气泡：主营行业开始
        */
        _this.$refs.p4pBubble.$on('beforeRender',function(chartOptions){
            Object.assign(chartOptions, {
                chart: {
                    type: 'packedbubble',
                },
                title: {
                    text: '主营行业'
                },
                tooltip: {
                    useHTML: true,
                    pointFormat: '<b>{point.name}:</b> {point.y}个'
                },
                plotOptions: {
                    packedbubble: {
                        dataLabels: {
                            enabled: true,
                            format: '{point.name}',
                            filter: {
                                property: 'y',
                                operator: '>',
                                value: 250
                            },
                            style: {
                                color: 'black',
                                textOutline: 'none',
                                fontWeight: 'normal'
                            }
                        },
                        minPointSize: 5,
                        showInLegend: false
                    }
                }
            });
        })
        _this.$refs.p4pBubble.$on('beforeGetData',function(params){
            Object.assign(params, {
                params: {
                    flag: 'bubble'
                }
            });
        })
        _this.$refs.p4pBubble.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /** 
         * 气泡：主营行业结束
        */
        _this.$refs.p4pBubble.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            chartEntity.addSeries({
                name: _t.data.data.name,
        		data: _t.data.data.dataList
            }, false);
            
        });
        /*-------------------------------------------------------------------------*/





        /**
         * [地图监听图表组件 beforeRender 事件]
         */
        _this.$refs.mapElements.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                chart: {
                    type: 'map'
                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    formatter: function() {
                        var _t = this;
                        return [
                            '<span style="font-size: 10px;">' + _t.key + '</span><br>',
                            '<span style="color:' + _t.point.color + '">\u25CF</span>',
                            '<tspan> 地区会员: </tspan>',
                            '<tspan style="font-weight:bold">' + (_t.point.value) +(_t.point.unit)+ '</tspan><br/>',
                        ].join('');
                    }
                },
                mapNavigation: {
                    enabled: true,
                    buttonOptions: {
                        verticalAlign: 'bottom'
                    }
                },
                colorAxis: {
                    enabled:false,
                    min: 0,
                    minColor: '#fff',
                    maxColor: '#006cee',
                    labels: {
                        style: {
                            "color": "red",
                            "fontWeight": "bold"
                        },
                        enabled:false
                    }
                }
            });
        });

        /**
         * [监听图表组件 dataReady 事件]
         */
        _this.$refs.mapElements.$on('dataReady', function(data) {
            var _t = this,
                _city = ['北京', '上海', '天津', '重庆'];

            /**
             * [缓存数据，并处理直辖市名称]
             */
            _t.data = (data || []).map((item, index) => {
                if (_city.indexOf(item.fullname) != -1) {
                    item.fullname += '市';
                }
                return item;
            });
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.mapElements.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            /**
             * [添加图表序列数据]
             */
            chartEntity.addSeries({
                data: _t.data,
                mapData: mapMetaData,
                joinBy: 'fullname',
                name: '中国地图',
                states: {
                    hover: {
                        color: '#a4edba'
                    }
                },
                dataLabels: {
                    enabled: true,
                    color: '#000',
                    format: '{point.fullname}'
                }
            }, false);

            /**
             * [每次渲染都重置缩放比例]
             */
            chartEntity.zoomOut();

            window.aa=chartEntity
            
        });

        /*-------------------------------------------------------------------------*/


        /** 
         * 第一个柱状图开始
        */

        /** 
         * 未使用报价服务会员开始
        */
        _this.$refs.p4pService.$on('beforeRender',function(chartOptions){
            Object.assign(chartOptions, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '未使用报价服务会员'
                },
                tooltip: {
                    // head + 每个 point + footer 拼接成完整的 table
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '企业数'
                    }
                }
            });
        })
        _this.$refs.p4pService.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /** 
         * 第一个柱状图结束
        */
        /** 
         * 未使用报价服务会员结束
        */
        _this.$refs.p4pService.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            chartEntity.yAxis[1].remove(false);//删除多余的
            chartEntity.series[0].remove(false);//删除多余的
            chartEntity.xAxis[0].update({
                categories: _t.data.data.time
            }, false);
            _t.data.data.dataList.forEach((columnItem, columnIndex) => {
                chartEntity.addSeries({
                    name: columnItem.name,
                    data: columnItem.data,
                    stack: 'female'
                });
            })
        });
        /*-------------------------------------------------------------------------*/


        /** 
         * 第二个柱状图开始
        */
        /** 
         * 使用报价服务的会员开始
        */
        _this.$refs.p4pServices.$on('beforeRender',function(chartOptions){
            Object.assign(chartOptions, {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '使用报价服务的会员'
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '会员量 (个)'
                    }
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                }
            });
        })
        _this.$refs.p4pServices.$on('beforeGetData',function(params){
            Object.assign(params, {
                params: {
                    flag: 'mmt_price'
                }
            });
        })
        _this.$refs.p4pServices.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /** 
         * 第二个柱状图结束
        */
        /** 
         * 使用报价服务的会员结束
        */
        _this.$refs.p4pServices.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            /**
             * [_data 获取缓存数据]
             */
            chartEntity.xAxis[0].update({
                categories: _t.data.time
            }, false);
            _t.data.data.forEach((columnItem, columnIndex) => {
                chartEntity.addSeries({
                    name: columnItem.name,
                    data: columnItem.data,
                    stack: 'female'
                });
            })
        });
        /*-------------------------------------------------------------------------*/

        /**
         * [会员服务效果开始]
         */
        _this.$refs.p4pBrokenLine.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions,{
                title: {
            		text: '会员PV'
            	},
                colors:["#19c6ed","#ff7c4d","#2bcc6b","#795548","#009688","#3e56d0","#ce5657","#c275df","#E91E63","#FFC107"],
            	yAxis: {
            		title: {
            			text: null
            		}
            	}
            });
        });
        _this.$refs.p4pBrokenLine.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'pv'
                }
            });
        });
        _this.$refs.p4pBrokenLine.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /**
         * [会员服务效果结束]
         */
        _this.$refs.p4pBrokenLine.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            var len = _t.data.data.dataList.length;
            if(len%2 == 1){
                len = len-1
            }
            chartEntity.series.forEach((series, index) => {
                var yIndex = (series.name == 'VIP' || series.name == '进阶' || series.name == '金牌会员' || series.name == '买卖通体验版' || series.name == 'VIP会员' || series.name == '银牌会员' || series.name == '经典') ? 1 : len;
                series.update({
                    yAxis: yIndex,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    },
                    // zIndex: index
                }, false);
            });
        });
        /*-------------------------------------------------------------------------*/

        /**
         * [流量PV开始]
         */
        _this.$refs.p4pBrokenLine1.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions,{
                title: {
            		text: '流量效果'
            	},
            	yAxis: {
            		title: {
            			text: null
            		}
            	}
            });
        });
        _this.$refs.p4pBrokenLine1.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'flow'
                }
            });
        });
        _this.$refs.p4pBrokenLine1.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /**
         * [流量PV结束]
         */
        _this.$refs.p4pBrokenLine1.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            chartEntity.series.forEach((series, index) => {
                
                series.update({
                    yAxis: 1,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    },
                    // zIndex: index
                }, false);
            });
        });
        /*-------------------------------------------------------------------------*/

        /**
         * [询盘开始]
         */
        _this.$refs.p4pBrokenLine2.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions,{
                title: {
            		text: '会员询盘'
            	},
                colors:["#19c6ed","#ff7c4d","#2bcc6b","#795548","#009688","#3e56d0","#ce5657","#c275df","#E91E63","#FFC107"],
            	yAxis: {
            		title: {
            			text: null
            		}
            	}
            });
        });
        _this.$refs.p4pBrokenLine2.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'xp'
                }
            });
        });
        _this.$refs.p4pBrokenLine2.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /**
         * [询盘结束]
         */
        _this.$refs.p4pBrokenLine2.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            var len = _t.data.data.dataList.length;
            if(len%2 == 1){
                len = len-1
            }
            chartEntity.series.forEach((series, index) => {
                var yIndex = (series.name == 'VIP' || series.name == '进阶' || series.name == '金牌会员' || series.name == '买卖通体验版' || series.name == 'VIP会员' || series.name == '银牌会员' || series.name == '经典') ? 1 : len;
                series.update({
                    yAxis: yIndex,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    },
                    name: {
                        
                    }
                    // zIndex: index
                }, false);
            });
        });


        /*-------------------------------------------------------------------------*/

        /**
         * [会员UV开始]
         */
        _this.$refs.p4pMemberUV.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions,{
                title: {
            		text: '会员UV'
            	},
                colors:["#19c6ed","#ff7c4d","#2bcc6b","#795548","#009688","#3e56d0","#ce5657","#c275df","#E91E63","#FFC107"],
            	yAxis: {
            		title: {
            			text: null
            		}
            	}
            });
        });
        _this.$refs.p4pMemberUV.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'uv'
                }
            });
        });
        _this.$refs.p4pMemberUV.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        /**
         * [会员UV结束]
         */
        _this.$refs.p4pMemberUV.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            var len = _t.data.data.dataList.length;
            if(len%2 == 1){
                len = len-1
            }
            chartEntity.series.forEach((series, index) => {
                var yIndex = (series.name == '进阶' || series.name == '金牌会员' || series.name == '买卖通体验版' || series.name == 'VIP会员' || series.name == '银牌会员' || series.name == '经典') ? 1 : len;
                series.update({
                    yAxis: yIndex,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    },
                    name: {
                        
                    }
                    // zIndex: index
                }, false);
            });
        });
    },

    methods: {


        /**
         * [chartEntityConstructor 重新制定图表构造函数]
         * @return {[type]} [description]
         */
        chartEntityConstructor: function() {
            return Highcharts.Map;
        },
        /**
         * [filterDoubleData 过滤双饼图数据组装]
         * @return {[type]} [description]
         */
        filterDoubleData (result, col) {
            var colors;
            if (col) {
                colors = col;
            } else {
                colors = Highcharts.getOptions().colors;
            };
            // var colors = Highcharts.getOptions().colors,
            	var categories = [],
            	data = [];
                result.forEach((item, index) => {
                    data.push({
                        y: item.y,
                        color: colors[item.color],
                        drilldown: item.drillDownBean
                    });
                    categories.push(item.drillDownBean.name);
                });
            	let browserData = [],
                	versionsData = [],
                	i,
                	j,
                	dataLen = data.length,
                	drillDataLen,
                	brightness;
            for (i = 0; i < dataLen; i += 1) {
            	browserData.push({
            		name: categories[i],
            		y: data[i].y,
            		color: data[i].color
            	});
            	drillDataLen = data[i].drilldown.data.length;
            	for (j = 0; j < drillDataLen; j += 1) {
            		brightness = 0.2 - (j / drillDataLen) / 5;
            		versionsData.push({
            			name: data[i].drilldown.categories[j],
            			y: data[i].drilldown.data[j],
            			color: Highcharts.Color(data[i].color).brighten(brightness).get()
            		});
            	}
            }
            return {
                versionsData: versionsData,
                browserData: browserData
            }
        },
    },

    components: {
        chartTendency
    }
}
</script>

<style lang="css">
</style>
