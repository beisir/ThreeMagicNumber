<template lang="html">
    <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default hour-trend">
                        <nav role="navigation" class="navbar navbar-default">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">产品线数据</span>
                                </div>
                            </div>
                        </nav>
                        <div class="p4pCount">
                            <div class="countCon">
                                <div class="unitPrice"><span>客单价：</span><p>{{formulaData["客单价"]}}元</p></div>
                                <div class="memberNum"><span><em class="xIco"></em>会员数：</span><p>{{formulaData["会员数"]}}万</p></div>
                                <div class="salesVolume"><span>销售额：</span><p>{{formulaData["销售额"]}}万</p></div>
                            </div>
                            <div class="countCon countCon2">
                                <div class="unitPrice"><span>充值：</span><p>{{formulaData["充值"]}}元</p></div>
                                <div class="memberNum"><span><em class="jiaIco"></em>消耗：</span><p>{{formulaData["消耗"]}}元</p></div>
                                <div class="salesVolume"><span>余额：</span><p>{{formulaData["余额"]}}元</p></div>
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
                            <div class="" style="width: 50%;float:left;border-right:solid #ccc 1px;">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pWidenedChart" :timermillisec="0" :service="service.double"></chart-tendency>
                            </div>
                            <div class="" style="width: 50%;float:left;">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pOperatedChart" :timermillisec="0" :service="service.operate"></chart-tendency>
                            </div>
                        </div>

                        <div class="panel-body tab-content mTop20">
                            <!-- <nav class="navbar navbar-default" role="navigation" style="background:#f5f5f5;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <span class="navbar-brand" style="font-size:16px;">充值情况</span>
                                    </div>
                                </div>
                            </nav>
                            <div class="" style="width: 50%;float:left;border-right:solid #ccc 1px;">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pColumn3Dchart" :timermillisec="0" :service="service.chart3d" :resetYAxisBeforeRedraw="false" chartTitle=""></chart-tendency>
                            </div> -->
                            <!-- <div class="" style="width: 50%;float:left;">
                                <chart-tendency :isShow="true" :chartFlag="false" ref="p4pLineChart1" :timermillisec="0" :service="service.chart3d" chartTitle="对比图"></chart-tendency>
                            </div> -->

                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
require('highcharts/highcharts-3d')(Highcharts);
require('highcharts/modules/variable-pie')(Highcharts);
import chartTendency from './chart-tendency.vue'
export default {
    data () {
        return {
            /**
             * [service 数据服务配置]
             * @type {Object}
             */
            service: {
                double: {
                    url: '/dataweb/dist',
                    params: {
                        type: 378
                    }
                },
                operate: {
                    url: '/dataweb/twocircle'
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
            }
        };
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
        // console.log(Highcharts)
        const _this = this;
        /**
         * [概况图->客单价分布占比图开始]
         * [beforeRender 初始化图表配置选项]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pWidenedChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                chart: {
                    type: 'variablepie'
                },
                colors: ['#19c6Ed', '#FF7C4D', '#2BCC6B', '#C275DF','#aa4643', '#B5CA92', '#A47D7C'],
                title: {
                    text: '客单价分布占比'
                },
                plotOptions: {
            		variablepie: {
            			allowPointSelect: true,
            			cursor: 'pointer',
            			dataLabels: {
            				enabled: false
            			},
            			showInLegend: true
            		}
            	},
                tooltip: {
                    headerFormat: '',
                    pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/><br/>占比: <b>{point.z}%</b>'
                },
            });
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
        		name: 'countries',
        		data: _t.data
            }, false);
        });





        /**
         * [同心圆： 充值=余额+消耗 开始]
         * @param  {[type]} chartOptions [description]
         * @return {[type]}              [description]
         */
        _this.$refs.p4pOperatedChart.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                chart: {	// 设置是否为圆形
            		type: 'pie'
            	},
            	title: {  // 主标题
            		text: '充值 = 消耗 + 余额'
            	},
            	plotOptions: {
            		pie: {
            			shadow: false,
            			center: ['50%', '50%']
            		}
            	},
            	tooltip: {
            		valueSuffix: '%'	// 滑动状态时数值之后的单位
            	}
            });
        });
        /**
         * [监听图表组件 dataReady 事件]
         */
        _this.$refs.p4pOperatedChart.$on('dataReady', function(data) {
            var _t = this;

            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
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
        		name: '第一比例',
        		data: browserData,
        		size: '60%',
        		dataLabels: {
        			formatter: function () {
        				return this.y > 5 ? this.point.name : null;
        			},
        			color: '#ffffff',
        			distance: -30
        		}
            }, false);
            chartEntity.addSeries({
        		name: '第二比例',
        		data: versionsData,
        		size: '80%',
        		innerSize: '60%',
        		dataLabels: {
        			formatter: function () {
        				// display only if larger than 1
        				return this.y > 1 ? '<b>' + this.point.name + ':</b> ' +
        					this.y : null;
        			}
        		},
        		id: 'versions'
            }, false);
        });


        //
        //
        // _this.$refs.p4pColumn3Dchart.$on('beforeRender', function(chartOptions) {
        //     Object.assign(chartOptions, {
        //         chart: {
        //     		type: 'column',
        //     		options3d: {
        //     			enabled: true,
        //     			alpha: 15,
        //     			beta: 15,
        //     			viewDistance: 25,
        //     			depth: 40
        //     		},
        //     		marginTop: 80,
        //     		marginRight: 40
        //     	},
        //     	title: {
        //     		text: '以性别划分的水果消费总量'
        //     	},
        //     	xAxis: {
        //     		categories: ['苹果', '橘子', '梨', '葡萄']
        //     	},
        //     	yAxis: {
        //     		allowDecimals: false,
        //     		min: 0,
        //     		title: {
        //     			text: '水果数量'
        //     		}
        //     	},
        //     	tooltip: {
        //     		headerFormat: '<b>{point.key}</b><br>',
        //     		pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
        //     	},
        //     	plotOptions: {
        //     		column: {
        //     			stacking: 'normal',
        //     			depth: 40
        //     		}
        //     	},
        //         series: []
        //     });
        // });
        //
        // _this.$refs.p4pColumn3Dchart.$on('dataReady', function(data) {
        //     var _t = this;
        //
        //     /**
        //      * [缓存数据]
        //      */
        //     _t.data = data || {};
        // });
        //
        // _this.$refs.p4pColumn3Dchart.$on('beforeRedraw', function(chartEntity) {
        //     var _t = this;
        //     /**
        //      * [_data 获取缓存数据]
        //      */
        //     // // var _data = _this.resolveData(_t.data || {});
        //     // let {browserData, versionsData} = _this.filterDoubleData();
        //     // /**
        //     //  * [添加图表序列数据]
        //     //  */
        //     // console.log(chartEntity.series);
        //     chartEntity.addSeries({
        //         name: '小男',
        //         data: [5, 3, 4, 7, 2],
        //         stack: 'males'
        //     });
        //     //
        //     //
        //     chartEntity.addSeries({
        //         name: '小王',
        //         data: [3, 4, 4, 2, 5],
        //         stack: 'male'
        //     });
        //
        //     chartEntity.addSeries({
        //         name: '小彭',
        //         data: [2, 5, 6, 2, 1],
        //         stack: 'female'
        //     });
        //     chartEntity.addSeries({
        //         name: '小潘',
        //         data: [3, 0, 4, 4, 3],
        //         stack: 'female'
        //     });
        //
        //
        //
        // });


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
         * [filterDoubleData 过滤双饼图数据组装]
         * @return {[type]} [description]
         */
        filterDoubleData (result) {
            var colors = Highcharts.getOptions().colors,
            	categories = [],
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
            	// add version data
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
