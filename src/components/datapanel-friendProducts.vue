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
                                    <!-- <router-link class="navbar-brand" to="/datapanel/p4pOperatedata" v-if="this.$privileges.user['81']">P4P产品</router-link>
                                    <router-link class="navbar-brand" to="/datapanel/friendProducts" v-if="$privileges.user['82']">友客产品</router-link> -->
                                </div>
                            </div>
                        </nav>

                        <div class="p4pCount">
                            <div class="countCon">
                                <div class="unitPrice"><span>客单价：</span><p>{{formulaData["客单价"]}} 元</p></div>
                                <div class="memberNum"><span><em class="xIco"></em>会员数：</span><p>{{formulaData["用户数"]}} 个</p></div>
                                <div class="salesVolume"><span>销售额：</span><p>{{formulaData["销售额"]}} 元</p></div>
                            </div>
                            <!-- <div class="countCon countCon2">
                                <div class="unitPrice"><span>余额：</span><p>{{formulaData["余额"]}} 元</p></div>
                                <div class="memberNum"><span><em class="jiaIco"></em>消耗：</span><p>{{formulaData["消耗"]}} 元</p></div>
                                <div class="salesVolume"><span>充值：</span><p>{{formulaData["充值"]}} 元</p></div>
                            </div> -->
                        </div>

                        <div class="panel-body tab-content mTop40">
                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pWidenedChart" :timermillisec="0" :service="service.dist.dist383" chartTitle="第一个玫瑰图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart1" :timermillisec="0" :service="service.youkeLine.sale" chartTitle="第二个折线图"></chart-tendency>
                            </div>

                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart2" :timermillisec="0" :service="service.youkeLine.userKey" chartTitle="第三个折线图"></chart-tendency>
                            </div>

                            <!-- <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="cascadingChart" :timermillisec="0" :service="service.cascading" chartTitle="第四个竖状层叠图"></chart-tendency>
                            </div> -->

                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="wordCloudChart" :timermillisec="0" :service="service.dist.dist386" :resetYAxisBeforeRedraw="false" chartTitle="第五个top云词"></chart-tendency>
                            </div>
                            <!-- <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="wordCloudChart2" :timermillisec="0" :service="service.double" :resetYAxisBeforeRedraw="false" chartTitle="第六个top云词"></chart-tendency>
                            </div> -->

                        </div>

                        <div class="panel-body tab-content mTop40">
                            <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">线索</span>
                                    </div>
                                </div>
                            </nav>

                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart3" :timermillisec="0" :service="service.youkeLine.clue" chartTitle="第七个折线图刘海图"></chart-tendency>
                            </div>
                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart4" :timermillisec="0" :service="service.youkeLine.clueKey" chartTitle="第八个折线图"></chart-tendency>
                            </div>

                            <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="wordCloudChart3" :timermillisec="0" :service="service.dist.dist391" :resetYAxisBeforeRedraw="false" chartTitle="第九个top云词"></chart-tendency>
                            </div>
                            <!-- <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="wordCloudChart4" :timermillisec="0" :service="service.double" :resetYAxisBeforeRedraw="false" chartTitle="第十个top云词"></chart-tendency>
                            </div> -->
                        </div>

                        <div class="panel-body tab-content mTop40">
                            <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">匹配</span>
                                    </div>
                                </div>
                            </nav>

                            <!-- <div class="p4pCountLeft" id="vennChart" ref="vennChart" style="height:450px;" title="第十一个韦恩图"></div>-->


                            <div class="p4pCountRig">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart5" :timermillisec="0" :service="service.youkeLine.clueHot" chartTitle="第十二个折线图"></chart-tendency>
                            </div>

                            <!-- <div class="p4pCountLeft">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="pyramidChart" :timermillisec="0" :service="service.jingzita" :resetYAxisBeforeRedraw="false" chartTitle="第十三个金字塔图"></chart-tendency>
                            </div> -->
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
// import Echarts from 'echarts';
// require('echarts/config.js')
// require('echarts/chart/venn.js');
export default {
    data () {
        return {
            linkIndex: 1,
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
                }
            ],
            /**
             * [service 数据服务配置]
             * @type {Object}
             */
            service: {
                youkeLine: {
                    userKey: {
                        url: '/dataweb/youkeLine?flag=userKey'
                    },
                    clue: {
                        url: '/dataweb/youkeLine?flag=clue'
                    },
                    sale: {
                        url: '/dataweb/youkeLine?flag=sale'
                    },
                    clueHot: {
                        url: '/dataweb/youkeLine?flag=clueHot'
                    },
                    clueKey: {
                        url: '/dataweb/youkeLine?flag=clueKey'
                    }
                },
                cascading: {
                    url: '/dataweb/cascading'
                },
                dist: {
                    dist383: {
                        url: '/dataweb/dist?type=383'
                    },
                    dist386: {
                        url: '/dataweb/dist?type=386'
                    },
                    dist391: {
                        url: '/dataweb/dist?type=391'
                    }

                },
                jingzita: {
                    url: '/dataweb/jingzita'
                },
                venn: {
                    url: '/dataweb/venn'
                }
            },
            // 顶层p4p运营数据
            formulaData: {
                "用户数": "",
                "销售额": "",
                "客单价": ""
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
        }
    },
    destroyed () {
        document.querySelector('ul.sub-nav').querySelectorAll('a')[1].classList.remove('sub-nav-checked')
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
        // 第二个折线图
        _this.$refs.p4pLineChart1.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg);
        });
        _this.$refs.p4pLineChart1.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                series.update({
                    yAxis: 0,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    },
                    // zIndex: index
                }, false);
            });
        });

        /*-------------------------------------------------------------------------*/

        /*-------------------------------------------------------------------------*/
            /* 第三个折线图*/
        _this.$refs.p4pLineChart2.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg, {
                title: {
                    text: '订阅关键词'
                },
                yAxis: {
            		title: {
            			text: '数量'
            		}
            	}
            });
        });
        _this.$refs.p4pLineChart2.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                series.update({
                    // yAxis: 0,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    }
                }, false);
            });
        });

        /*-------------------------------------------------------------------------*/
            /*第四个层叠图*/
        // _this.$refs.cascadingChart.$on('beforeRender', function(chartOptions) {
        //     Object.assign(chartOptions, {
        //         chart: {
        //             type: 'column'
        //         },
        //         title: {
        //             text: '用户线索获得情况'
        //         },
        //         colors: ['rgb(124, 181, 236)', 'rgb(67, 67, 72)', 'rgb(144, 237, 125)'],
        //         yAxis: {
        //     		// min: 0,
        //     		title: {
        //     			text: '用户数'
        //     		},
        //     		stackLabels: {  // 堆叠数据标签
        //     			enabled: true,
        //     			style: {
        //     				fontWeight: 'bold',
        //     				color: '#000'
        //     			}
        //     		}
        //     	},
        //     	tooltip: {
        //     		formatter: function () {
        //     			return '<b>' + this.x + '</b><br/>' +
        //     				this.series.name + ': ' + this.y + '<br/>' +
        //     				'总量: ' + this.point.stackTotal;
        //     		}
        //     	},
        //     	plotOptions: {
        //     		column: {
        //     			stacking: 'normal',
        //     			dataLabels: {
        //     				enabled: true,
        //     				color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
        //     				style: {
        //     					// 如果不需要数据标签阴影，可以将 textOutline 设置为 'none'
        //     					textOutline: '1px 1px black'
        //     				}
        //     			}
        //     		}
        //     	}
        //     });
        // });
        // _this.$refs.cascadingChart.$on('beforeRedraw', function(chartEntity) {
        //     chartEntity.series.forEach((series, index) => {
        //         series.update({
        //             yAxis: 0,
        //         }, false);
        //     });
        // });

        /*-------------------------------------------------------------------------*/

        /*-------------------------------------------------------------------------*/
            /*第五个top云关键词图*/
        /**
         * [top50消耗词图 词图云]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.wordCloudChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
            	title: {
            		text: '订阅关键词TOP50'
            	}
            });
        });
        _this.$refs.wordCloudChart.$on('afterGetData', function(data) {
            var _t = this;
            _t.data = data || {};
        });
        _this.$refs.wordCloudChart.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            var resultData = _this.filterWordCloudData(_t.data.data)
            chartEntity.addSeries({
                name: '订阅数',
                type: 'wordcloud',
                data: resultData
            }, false);
        });
        /*-------------------------------------------------------------------------*/

        /*-------------------------------------------------------------------------*/
            /*第六个top云关键词图*/
        /**
         * [top50消耗词图 词图云]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        // _this.$refs.wordCloudChart2.$on('beforeRender', function(chartOptions) {
        //     Object.assign(chartOptions, _this.p4pLineConifg, {
        //     	title: {
        //     		text: '订阅关检词TOP50'
        //     	}
        //     });
        // });
        // _this.$refs.wordCloudChart2.$on('beforeGetData', function(params) {
        //     Object.assign(params, {
        //         params: {
        //             type: '376'
        //         }
        //     });
        // });
        // _this.$refs.wordCloudChart2.$on('afterGetData', function(data) {
        //     var _t = this;
        //     _t.data = data || {};
        // });
        // _this.$refs.wordCloudChart2.$on('beforeRedraw', function(chartEntity) {
        //     var _t = this;
        //     var resultData = _this.filterWordCloudData(_t.data.data)
        //     chartEntity.addSeries({
        //         name: '消耗',
        //         type: 'wordcloud',
        //         data: resultData
        //     }, false);
        // });
        /*-------------------------------------------------------------------------*/

        // p4pLineChart3
        _this.$refs.p4pLineChart3.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                chart: {
                    type: 'area'
                },
                colors: ['rgb(124, 181, 236)', 'rgb(67, 67, 72)', 'rgb(144, 237, 125)'],
                title: {
                    text: '线索漏斗'
                },
                plotOptions: {
                    area: {
                    	stacking: 'normal',
                    	lineColor: '#666666',
                    	lineWidth: 1,
                    	marker: {
                    		lineWidth: 1,
                    		lineColor: '#666666'
                    	}
                    }
                }
            });
        });
        _this.$refs.p4pLineChart3.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                series.update({
                    yAxis: 0,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    }
                }, false);
            });
        });
        /*-------------------------------------------------------------------------*/

        /*-------------------------------------------------------------------------*/
        _this.$refs.p4pLineChart4.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                title: {
                    text: '线索关键词'
                },
                yAxis: {
        			title: {
        				text: '数量'
        			}
        		}
            });
        });
        _this.$refs.p4pLineChart4.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                series.update({
                    yAxis: 0,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    }
                }, false);
            });
        });


        /*-------------------------------------------------------------------------*/
            /*第六个top云关键词图*/
        /**
         * [top50消耗词图 词图云]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.wordCloudChart3.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg, {
            	title: {
            		text: '线索关键词TOP50'
            	}
            });
        });
        _this.$refs.wordCloudChart3.$on('afterGetData', function(data) {
            var _t = this;
            _t.data = data || {};
        });
        _this.$refs.wordCloudChart3.$on('beforeRedraw', function(chartEntity) {
            var _t = this;
            var resultData = _this.filterWordCloudData(_t.data.data)
            chartEntity.addSeries({
                name: '线索数',
                type: 'wordcloud',
                data: resultData
            }, false);
        });
        /*-------------------------------------------------------------------------*/

        /*-------------------------------------------------------------------------*/
            /*第六个top云关键词图*/
        /**
         * [top50消耗词图 词图云]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        // _this.$refs.wordCloudChart4.$on('beforeRender', function(chartOptions) {
        //     Object.assign(chartOptions, _this.p4pLineConifg, {
        //     	title: {
        //     		text: '线索关检词TOP50'
        //     	}
        //     });
        // });
        // _this.$refs.wordCloudChart4.$on('beforeGetData', function(params) {
        //     Object.assign(params, {
        //         params: {
        //             type: '376'
        //         }
        //     });
        // });
        // _this.$refs.wordCloudChart4.$on('afterGetData', function(data) {
        //     var _t = this;
        //     _t.data = data || {};
        // });
        // _this.$refs.wordCloudChart4.$on('beforeRedraw', function(chartEntity) {
        //     var _t = this;
        //     var resultData = _this.filterWordCloudData(_t.data.data)
        //     chartEntity.addSeries({
        //         name: '消耗',
        //         type: 'wordcloud',
        //         data: resultData
        //     }, false);
        // });
        /*-------------------------------------------------------------------------*/

        /*-------------------------------------------------------------------------*/
            // 韦恩图
        // vennChart
        // _this.getVennData();

        /*-------------------------------------------------------------------------*/
        _this.$refs.p4pLineChart5.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, _this.p4pLineConifg, {
                title: {
                    text: '线索热度'
                },
                yAxis: {
                   title: {
                       text: '数量'
                   }
                },
                tooltip: {
                    shared: true,
                    formatter: function() {
                        var _t = this,
                            _total = 0;
                        var _arr = [
                            '<span style="font-size: 10px;">' + _t.x + '</span><br>'
                        ];
                        for (var i = 0; i < _t.points.length; i++) {
                            _arr = _arr.concat([
                                '<span style="color:' + _t.points[i].color + '">\u25CF</span>',
                                '<tspan> ' + _t.points[i].series.name + ': </tspan>',
                                '<tspan style="font-weight:bold">' + _t.points[i].y + ' 个</tspan><br/>'
                            ]);
                        };
                        if (_t.points[0].y && _t.points[1].y) {
                            _total = _t.points[0].y / _t.points[1].y;
                        };
                        return _arr.concat([
                            '<span>\u25CF</span>',
                            '<tspan> 线索热度: </tspan>',
                            '<tspan style="font-weight:bold">' + _total.toFixed(2) + '%</tspan>'
                        ]).join('');
                    }
                }
            });
        });
        _this.$refs.p4pLineChart5.$on('beforeRedraw', function(chartEntity) {
            chartEntity.series.forEach((series, index) => {
                series.update({
                    yAxis: 0,
                    // 将折线设置为有菱角的折线
                    marker: {
                        enabled: true
                    }
                }, false);
            });
        });

        /*-------------------------------------------------------------------------*/
        //
        // // pyramidChart
        // _this.$refs.pyramidChart.$on('beforeRender', function(chartOptions) {
        //     Object.assign(chartOptions, {
        //         chart: {
        //     		type: 'bar'
        //     	},
        //     	title: {
        //     		text: '订阅关键词匹配度'
        //     	},
        //     	xAxis: [{
        //     		reversed: false,
        //     		labels: {
        //     			step: 1
        //     		}
        //     	}, {
        //     		// 显示在右侧的镜像 xAxis （通过 linkedTo 与第一个 xAxis 关联）
        //     		opposite: true,
        //     		reversed: false,
        //     		linkedTo: 0,
        //     		labels: {
        //     			step: 1
        //     		}
        //     	}],
        //     	yAxis: {
        //     		title: {
        //     			text: null
        //     		},
        //     		labels: {
        //     			formatter: function () {
        //     				return (Math.abs(this.value) / 1000000) + 'M';
        //     			}
        //     		}
        //     	},
        //     	plotOptions: {
        //     		series: {
        //     			stacking: 'normal'
        //     		}
        //     	},
        //     	// tooltip: {
        //     	// 	formatter: function () {
        //     	// 		return '<b>' + this.series.name + ', age ' + this.point.category + '</b><br/>' +
        //     	// 			'人口: ' + Highcharts.numberFormat(Math.abs(this.point.y), 0);
        //     	// 	}
        //     	// }
        //     });
        // });
        // _this.$refs.pyramidChart.$on('dataReady', function(data) {
        //     var _t = this;
        //     _t.data = data || {};
        // });
        // _this.$refs.pyramidChart.$on('beforeRedraw', function(chartEntity) {
        //     var categories = this.data.time;
        //     chartEntity.series.forEach((series, index) => {
        //         series.update({
        //             yAxis: 0
        //         }, false);
        //     });
        //     chartEntity.xAxis.forEach((item, index) => {
        //         item.update({categories});
        //     })
        // });
    },
    methods: {
        getVennData () {
            console.log(Echarts)
            var vennEle = Echarts.init(this.$refs.vennChart);
            var options = {
                title : {
                    text: '访问 vs 咨询',
                    x: 'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{b}: {c}"
                },
                calculable : false,
                series : [
                    {
                        name:'韦恩图',
                        type:'venn',
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true
                                },
                                labelLine: {
                                    show: false,
                                    length: 10,
                                    lineStyle: {
                                        // color: 各异,
                                        width: 1,
                                        type: 'solid'
                                    }
                                }
                            },
                            emphasis: {
                                color: '#cc99cc',
                                borderWidth: 3,
                                borderColor: '#996699'
                            }
                        },
                        data:[
                            {value:50, name:'访问'},
                            {value:30, name:'咨询'},
                            {value:20, name:'公共'}
                        ]
                    }
                ]
            };
            vennEle.setOption(options);
            // window.onresize = vennEle.resize;
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
    },
    created () {
        const _this = this;
        this.$http.get('/dataweb/youkeFormula').then((response) => {
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
    components: {
        'chart-tendency': chartTendency
    }
}
</script>

<style lang="css">
</style>
