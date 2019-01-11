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
                                <div class="memberNum"><span><em class="xIco"></em>会员数：</span><p>{{formulaData["会员数"]}} 个</p></div>
                                <div class="salesVolume"><span>销售额：</span><p>{{formulaData["销售额"]}} 元</p></div>
                            </div>
                            <div class="countCon countCon2">
                                <div class="unitPrice"><span>余额：</span><p>{{formulaData["余额"]}} 元</p></div>
                                <div class="memberNum"><span><em class="jiaIco"></em>消耗：</span><p>{{formulaData["消耗"]}} 元</p></div>
                                <div class="salesVolume"><span>充值：</span><p>{{formulaData["充值"]}} 元</p></div>
                            </div>
                        </div>

                        <div class="panel-body tab-content mTop40">
                            <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">概况</span>
                                    </div>
                                </div>
                            </nav>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pWidenedChart" :timermillisec="0" :service="service.double" chartTitle="第一个扇形图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pOperatedChart" :timermillisec="0" :service="service.operate" chartTitle="第二个双圆图"></chart-tendency>
                            </div>
                        </div>

                        <div class="panel-body tab-content mTop20">
                            <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">充值情况</span>
                                    </div>
                                </div>
                            </nav>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pColumn3Dchart" :timermillisec="0" :service="service.chart3d" :resetYAxisBeforeRedraw="false" chartTitle="第三个3D柱图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart1" :timermillisec="0" :service="service.p4pline" chartTitle="第四个折线图"></chart-tendency>
                            </div>
                        </div>

                        <div class="panel-body tab-content mTop20">
                            <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">消耗情况</span>
                                    </div>
                                </div>
                            </nav>
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pColumn3Dchart2" :timermillisec="0" :service="service.chart3d" :resetYAxisBeforeRedraw="false" chartTitle="第五个3D柱图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pcombineChart" :timermillisec="0" :service="service.p4pcombine" chartTitle="第六个混合图"></chart-tendency>
                            </div>

                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart2" :timermillisec="0" :service="service.p4pline" chartTitle="第七个折线图"></chart-tendency>
                            </div>

                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="wordCloudChart" :timermillisec="0" :service="service.double" :resetYAxisBeforeRedraw="false" chartTitle="第八个词云图"></chart-tendency>
                            </div>

                        </div>

                        <!-- <div class="panel-body tab-content mTop20">
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pDailyChart" :timermillisec="0" :service="service.double" chartTitle="第九个扇形图"></chart-tendency>
                            </div>
                        </div> -->

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
require('highcharts/highcharts-3d')(Highcharts);
require('highcharts/modules/variable-pie')(Highcharts);
require('highcharts/modules/oldie')(Highcharts);
require('highcharts/modules/wordcloud')(Highcharts);
import chartTendency from './chart-tendency.vue'
export default {
    data () {
        return {
            linkIndex: 0,
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
                double: {
                    url: '/dataweb/dist'
                },
                operate: {
                    url: '/dataweb/twocircle'
                },
                chart3d: {
                    url: '/dataweb/column3d'
                },
                p4pline: {
                    url: '/dataweb/p4pline'
                },
                p4pcombine: {
                    url: '/dataweb/p4pcombine'
                }
            },
            // 顶层p4p运营数据
            formulaData: {
                "会员数": "",
                "余额": "",
                "充值": "",
                "客单价": "",
                "消耗": "",
                "销售额": ""
            },

            semicircleConfig: {
                chart: {
                    type: 'variablepie'
                },
                colors: ['#19c6Ed', '#FF7C4D', '#2BCC6B', '#C275DF','#aa4643', '#B5CA92', '#A47D7C'],
                title: {
                    text: '客单价分布占比'
                },
                legend: {
            		align: 'left',
            		verticalAlign: 'top',
            		y: 65,
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
            column3Dconfig: {
                chart: {
                    type: 'column',
                    options3d: {
                        enabled: true,
                        alpha: 15,
                        beta: 15,
                        viewDistance: 25,
                        depth: 40
                    },
                    marginTop: 40,
                    marginRight: 0,
                    marginLeft: 40
                },
                title: {
                    text: '累计充值情况'
                },
                colors: ['rgba(124,181,236,0.5)', '#434348', '#90ed7d', '#f7a35c'],
                yAxis: {
            		min: 0,
            		stackLabels: {  // 堆叠数据标签
            			enabled: true,
                        rotation: -90,
                        y: 50,
            			style: {
            				fontWeight: 'bold'
            			},
                        // format:'总和 {total} 元'
            		},
                    allowDecimals: false,
                    title: {
                        text: '金额',
                        x: -40,
                        y: -80
                    }
                },
                // tooltip: {
                //     headerFormat: '<b>{point.key}</b><br>',
                //     pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} 元'
                // },
                plotOptions: {
                    column: {
                        stacking: 'normal',
                        depth: 40
                    }
                },
                series: []
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
            }
        };
    },
    destroyed () {
        document.querySelector('ul.sub-nav').querySelectorAll('a')[1].classList.remove('sub-nav-checked')
    },
    created () {
        const _this = this;
        this.$http.get('/dataweb/p4pformula').then((response) => {
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
        /**
         * [概况图->客单价分布占比图开始]
         * [beforeRender 初始化图表配置选项]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pWidenedChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.semicircleConfig);
        });
        /**
         * [概况图->客单价分布占比]
         * [beforeGetData 设置调用接口参数]
         * @param  {[type]} params [description]
         * @return {[type]}        [description]
         */
        _this.$refs.p4pWidenedChart.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    type: 378
                }
            });
        });
        /**
         * [概况图->客单价分布占比]
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
         * [概况图->客单价分布占比图结束]
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
         * [同心圆： 充值=余额+消耗 开始]
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
            		text: '余额 + 消耗 = 充值'
            	},
            	plotOptions: {
            		pie: {
            			shadow: false,
            			center: ['50%', '50%'],
                        allowPointSelect: true,
            			cursor: 'pointer',
            			dataLabels: {
            				enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            				style: {
            					color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
            				}
            			}
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
         * [同心圆： 充值=余额+消耗 结束]
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

        /*-------------------------------------------------------------------------*/



        _this.$refs.p4pColumn3Dchart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.column3Dconfig);
        });
        _this.$refs.p4pColumn3Dchart.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'charge'
                }
            });
        });
        _this.$refs.p4pColumn3Dchart.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        _this.$refs.p4pColumn3Dchart.$on('beforeRedraw', function(chartEntity) {
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

        _this.$refs.p4pColumn3Dchart2.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.column3Dconfig, {
                title: {
                    text: '累计消耗情况'
                }
            });
        });
        _this.$refs.p4pColumn3Dchart2.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'expend'
                }
            });
        });
        _this.$refs.p4pColumn3Dchart2.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        _this.$refs.p4pColumn3Dchart2.$on('beforeRedraw', function(chartEntity) {
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



        /*混合图*/
        _this.$refs.p4pcombineChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                title: {
            		text: '会员情况'
            	},
                yAxis: {
            		title: {
            			text: '会员数'
            		}
            	},
                plotOptions: {
            		pie: {
            			allowPointSelect: true,
            			cursor: 'pointer',
                        stacking: 'normal',
                        dataLabels: {
            				enabled: true,
            				distance: -20,
                            formatter: function () {
            					return (this.y ) + '%';
            				},
            				style: {
            					fontWeight: 'bold',
            					color: 'white',
            					textShadow: '0px 1px 2px black'
            				}
            			},
                        showInLegend: true
            		}
            	}
            });
        });

        _this.$refs.p4pcombineChart.$on('afterGetData', function(data) {
            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });

        _this.$refs.p4pcombineChart.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            chartEntity.series.forEach((series, index) => {
                series.update({
                    yAxis: 0,
                    marker: {
                        enabled: true
                    }
                }, false);
            });
            //
            let {browserData, versionsData} = _this.filterDoubleData(_t.data.circleData, ['#7cb5ec', 'rgba(124,181,236, 0.5)', "rgba(67,67,72, 0.5)"]);
            /**
             * [添加图表序列数据]
             */
            chartEntity.addSeries({
        		type: 'pie',
        		name: '占比',
                tooltip: {
            		valueSuffix: '%'	// 滑动状态时数值之后的单位
            	},
        		data: browserData,
        		center: [80, 20],
        		size: '40%'
            }, false);
            chartEntity.addSeries({
                type: 'pie',
         		name: '占比',
                tooltip: {
            		valueSuffix: '%'	// 滑动状态时数值之后的单位
            	},
         		data: versionsData,
         		center: [80, 20],
        		id: 'versions',
         		size: '80%',
         		innerSize: '50%'
            }, false);
        });







        /**
         * [第二个折线图]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pLineChart2.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg, {
                title: {
            		text: '开启关键词数趋势'
            	},
                yAxis: {
            		title: {
            			text: '关键词数'
            		}
            	}
            });
        });
        _this.$refs.p4pLineChart2.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    flag: 'key'
                }
            });
        });
        _this.$refs.p4pLineChart2.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                var yIndex = (series.name == '无效关键词数' || series.name == '户均关键词数') ?0 : 1;
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
         * [top50消耗词图 词图云]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.wordCloudChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg, {
            	title: {
            		text: 'top50消耗词图'
            	}
            });
        });
        _this.$refs.wordCloudChart.$on('beforeGetData', function(params) {
            Object.assign(params, {
                params: {
                    type: '376'
                }
            });
        });
        _this.$refs.wordCloudChart.$on('afterGetData', function(data) {

            var _t = this;
            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });
        _this.$refs.wordCloudChart.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            var resultData = _this.filterWordCloudData(_t.data.data)
            chartEntity.addSeries({
                name: '消耗',
                type: 'wordcloud',
                data: resultData
            }, false);
        });



        /*-------------------------------------------------------------------------*/
        // _this.$refs.p4pDailyChart.$on('beforeRender', function(chartOptions) {
        //     Object.assign(chartOptions, _this.semicircleConfig, {
        //         title: {
        //             text: '互均消耗分布占比'
        //         }
        //     });
        // });
        // _this.$refs.p4pDailyChart.$on('beforeGetData', function(params) {
        //     Object.assign(params, {
        //         params: {
        //             type: 379
        //         }
        //     });
        // });
        // _this.$refs.p4pDailyChart.$on('dataReady', function(data) {
        //     var _t = this;
        //     /**
        //      * [缓存数据]
        //      */
        //     _t.data = data || {};
        // });
        // _this.$refs.p4pDailyChart.$on('beforeRedraw', function(chartEntity) {
        //     var _t = this;
        //     chartEntity.addSeries({
        //         minPointSize: 10,
        // 		innerSize: '20%',
        // 		zMin: 0,
        // 		name: 'countries',
        // 		data: _t.data
        //     }, false);
        // });
        // /*-------------------------------------------------------------------------*/
    },

    methods: {
        /**
         * [chartEntityConstructor 重新制定图表构造函数]
         * @return {[type]} [description]
         */
        p4pWidenedChartConstructor: function() {
            return Highcharts.Map;
        },

        /**
         * [filterWordCloudData 过滤关键词云的数据]
         * @param  {[type]} data [description]
         * @return {[type]}      [description]
         */
        filterWordCloudData (data) {

            return data.map((item, index) => {
                return {
                    name: item.name,
                    weight: item.z
                }
            });
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
        }
    },

    components: {
        chartTendency
    }
}
</script>

<style lang="css">
</style>
