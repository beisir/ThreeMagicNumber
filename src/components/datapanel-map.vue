<template>
    <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default hour-trend">
                        <div class="panel-body tab-content mTop20">
                            <chart-tendency :navigation="mapNavigation" ref="mapElement" :timermillisec="timerMillisec" :service='service.map' chartTitle="地区分布" :chartEntityConstructor="chartEntityConstructor()" :resetYAxisBeforeRedraw="false"></chart-tendency>
                            <chart-tendency :navigation="treemapNavigation" ref="treeMapElement" :timermillisec="timerMillisec" :service="service.treemap" chartTitle="站点分布" :resetYAxisBeforeRedraw="false"></chart-tendency>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import chartTendency from './chart-tendency.vue'

/**
 * 导入 treemap、map 类型图表
 */
require('highcharts/modules/treemap')(Highcharts);

/**
 * [mapMetaData 导入地图地理位置数据]
 * @type {Object}
 */
var mapMetaData = require('../highcharts/highchartsGeoChina');

export default {
    name: 'datapanel-effectives',
    data() {
        return {

            /**
             * [treemapNavigation 图表导航项配置]
             * @type {Array}
             */
            treemapNavigation: [{
                name: 'IP',
                code: '1001'
            }, {
                name: 'PV',
                code: '2001'
            }, {
                name: 'UV',
                code: '3001'
            }, {
                name: '询盘',
                code: '2201'
            }],

            /**
             * [mapNavigation 图表导航项配置]
             * @type {Array}
             */
            mapNavigation: [{
                name: 'IP',
                code: '1002'
            }, {
                name: 'PV',
                code: '2002'
            }, {
                name: 'UV',
                code: '3002'
            }, {
                name: '询盘',
                code: '2202'
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

                /**
                 * [maptree 站点、域名、来源分布]
                 * @type {Object}
                 */
                treemap: {
                    url: '/dataweb/sourcedata'
                },

                /**
                 * [map 地区分布明细]
                 * @type {Object}
                 */
                map: {
                    url: '/dataweb/regiondata'
                }
            }

        };
    },
    mounted() {
        var _this = this;

        /**
         * [监听图表组件 beforeRender 事件]
         */
        _this.$refs.treeMapElement.$on('beforeRender', function(chartOptions) {
            Object.assign(chartOptions, {
                tooltip: {
                    formatter: function() {
                        var _t = this;
                        return [
                            '<span style="font-size: 10px;">' + _t.key + '</span><br>',
                            '<span style="color:' + _t.point.color + '">\u25CF</span>',
                            '<tspan> 今天: </tspan>',
                            '<tspan style="font-weight:bold">' + (_t.point.value) + '</tspan><br/>',
                            '<span style="color:#888;">\u25CF</span>',
                            '<tspan> 昨天: </tspan>',
                            '<tspan style="font-weight:bold">' + (_t.point.yestodayvalue) + '</tspan><br/>'
                        ].join('');
                    }
                }
            });
        });

        /**
         * [监听图表组件 dataReady 事件]
         */
        _this.$refs.treeMapElement.$on('dataReady', function(data) {
            var _t = this;

            /**
             * [缓存数据]
             */
            _t.data = data || {};
        });

        /**
         * [监听图表组件 beforeRedraw 事件]
         */
        _this.$refs.treeMapElement.$on('beforeRedraw', function(chartEntity) {
            var _t = this;

            /**
             * [_data 获取缓存数据]
             */
            var _data = _this.resolveData(_t.data || {});

            /**
             * [添加图表序列数据]
             */
            chartEntity.addSeries({
                type: 'treemap',
                layoutAlgorithm: 'squarified',
                allowDrillToNode: true,
                animationLimit: 1000,
                dataLabels: {
                    enabled: false
                },
                levelIsConstant: false,
                levels: [{
                    level: 1,
                    dataLabels: {
                        enabled: true,
                        style: {
                            color: '#000'
                        }
                    },
                    borderWidth: 3
                }],
                data: _data
            }, false);
        });

        /**
         * [监听图表组件 beforeRender 事件]
         */
        _this.$refs.mapElement.$on('beforeRender', function(chartOptions) {
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
                            '<tspan> 今天: </tspan>',
                            '<tspan style="font-weight:bold">' + (_t.point.value) + '</tspan><br/>',
                            '<span style="color:#888;">\u25CF</span>',
                            '<tspan> 昨天: </tspan>',
                            '<tspan style="font-weight:bold">' + (_t.point.yestodayvalue) + '</tspan><br/>'
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
        _this.$refs.mapElement.$on('dataReady', function(data) {
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
        _this.$refs.mapElement.$on('beforeRedraw', function(chartEntity) {
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
         * [resolveData 将数据解析成图表数据格式]
         * @return {Object} [解析后的数据格式]
         */
        resolveData(data) {
            var _data = data || {},
                _points = [],
                _colors=['#ff7c4d','#19c6e0','#2bcc6b','#c275df','#f45b5b'],
                // _colors=["#7cb5ec", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"],
                _func = function(pid, obj) {
                    var _index = 0;
                    for (var name in obj) {
                        if (obj.hasOwnProperty(name)) {
                            var _point = {
                                id: pid + '_' + _index,
                                name: name,
                                parent: pid,
                                value: obj[name].val || 0,
                                yestodayvalue: obj[name].yesval || 0
                            };

                            /**
                             * [根节点着色]
                             */
                            if (!pid) {
                                _point.color = _colors[_index];
                            }

                            /**
                             * [递归添加子节点]
                             */
                            if (obj[name].child) {
                                _func(pid + '_' + _index, obj[name].child);
                            }
                            _points.push(_point);
                            _index++;
                        }
                    }
                };

            /**
             * 开始创建图标数据
             */
            _func('', data);
            return _points;
        }
    },
    components: {
        'chart-tendency': chartTendency
    }
}
</script>
