<template>
    <div class="panel panel-default hour-trend" v-show="Navigation.length>0||isShow">
        <nav v-if="chartFlag" class="navbar navbar-default" role="navigation" style="background:#f5f5f5; margin-top:30px; margin-bottom:10px;">
            <div class="container-fluid">
                <div class="navbar-header">
                    <span class="navbar-brand" style="font-size:16px;">
                        {{chartTitle}}
                    </span>
                </div>
                <div id="titleHead">
                    <swiper :options="swiperOption">
                        <swiper-slide v-for="nav in Navigation">
                            <span :class="{active:isActiveNav(nav)}" v-on:click="switchNav(nav)">{{nav.name}}</span>
                        </swiper-slide>
                    </swiper>
                </div>
            </div>
        </nav>
        <div class="panel-body tab-content">
            <div class="navbarRadio">
                <label v-for="filterItem in CurrentNavigation.filterEntities.timelimit">
                    <input type="radio" :name="'timelimit_'+_uid" :checked="isActiveTimelimitFilter(filterItem)" v-on:click="switchTimelimitFilter(filterItem)">{{filterItem.name}}
                </label>
            </div>
            <div class="tab-pane active" ref="chartContainer">图表区域</div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'chart-tendency',
    props: {
        chartFlag: {
            default: function() {
                return true;
            }
        },
        /**
         * [chartTitle 图表标题]
         * @type {String}
         */
        chartTitle: {
            default: function() {
                return '趋势图';
            }
        },

        isShow:{
           default:function () {
               return false
           }
        },

        /**
         * [navigation 导航项配置]
         * @type {Object}
         */
        navigation: {
            default: function() {
                return [];
            }
        },

        /**
         * [enums 过滤项配置]
         * @type {Object}
         */
        enums: {
            default: function() {
                return {

                    /**
                     * [timelimit 时限过滤条件枚举对象]
                     * @type {Object}
                     */
                    timelimit: {
                        'today': {
                            name: '今天',
                            code: '0'
                        },
                        'lastsevensays': {
                            name: '最近七天',
                            code: '1'
                        },
                        'lastmonth': {
                            name: '最近一个月',
                            code: '2'
                        },
                        'thismonth': {
                            name: "本月数据",
                            code: '8'
                        },
                        'all': {
                            name: '累计',
                            code: '3'
                        },
                        'weekly': {
                            name: '周度数据',
                            code: '4'
                        },
                        'monthly': {
                            name: '月度数据',
                            code: '5'
                        },
                        'Splitbyfinancial': {
                            name: '按财务拆分',
                            code: '6'
                        },
                        'Splitbysalesorder': {
                            name: '按销售订单拆分',
                            code: '7'
                        },
                        'numberPeople': {
                            name: '按竞价人数排序',
                            code: '10'
                        },
                        'price': {
                            name: '按竞价价格排序',
                            code: '11'
                        }
                    }
                }
            }
        },

        /**
         * [service 数据服务配置]
         * @type {Object}
         */
        service: {
            default: function() {
                return {
                    url: ''
                }
            }
        },

        /**
         * [chartOptions 图表公共配置]
         * @type {Object}
         */
        chartOptions: {
            default: function() {
                return {
                    title: {
                        text: null //不显示标题
                    },
                    chart: {
                        type: 'spline' //图表的默认类型
                    },
                    colors: ['#19c6Ed', '#FF7C4D', '#2BCC6B', '#C275DF'], //图表序列（Series）的默认颜色数组
                    credits: {
                        enabled: false // 禁用版权信息
                    },
                    tooltip: {
                        formatter: null, //因为有些情况可能该配置项会被重写，为了不影响其他图表，需要指定该配置的默认值
                        shared: true //当提示框被共享时，整个绘图区都将捕捉鼠标指针的移动。提示框文本显示有序数据（不包括饼图，散点图和标志图等）的系列类型将被显示在同一个提示框中。推荐在单一系列的图表和平板/手机优化的图表中使用。
                    }
                }
            }
        },

        /**
         * [timermillisec 数据定时更新时间间隔毫秒数]
         * @type {Number}
         */
        timermillisec: {
            default: function() {
                return 1000 * 60 * 11
            }
        },

        /**
         * [chartEntityConstructor 图表实例构造函数]
         * @type {Object}
         */
        chartEntityConstructor: {
            default: function() {
                return Highcharts.chart;
            }
        },

        /**
         * [resetSeriesBeforeRedraw 重绘是否删除所有 YAxis ]
         * @type {Boolean}
         */
        resetYAxisBeforeRedraw: {
            default: function() {
                return true;
            }
        },

        /**
         * [resetSeriesBeforeRedraw 重绘是否删除所有 Series ]
         * @type {Boolean}
         */
        resetSeriesBeforeRedraw: {
            default: function() {
                return true;
            }
        }
    },
    data() {
        /**
         * [_this 当前对象实例]
         * @type {Object}
         */
        var _this = this;

        /**
         * [解析导航时限过滤项数据]
         */
        _this.navigation.forEach((nav, navIndex) => {
            /**
             * [filterEntities 导航项过滤项数据]
             * @type {Array}
             */
            nav.filterEntities = {};

            /**
             * [初始化导航项时限过滤项数据]
             */
            const timelimitFilterName = 'timelimit',

                /**
                 * [timelimitFilterConfig 当前导航项对应的时限过滤项配置]
                 * @type {Array}
                 */
                timelimitFilterConfig = (nav.filters || {})[timelimitFilterName], //['today', 'lastmonth', 'weekly', 'monthly']

                /**
                 * [timelimitFilterEnums 时限过滤条件枚举对象]
                 * @type {[type]}
                 *
                 * 'today': {
                      name: '今天',
                      code: '0'
                    },
                 */
                timelimitFilterEnums = _this.enums[timelimitFilterName];
            /**
             * [存在指定过滤项枚举列表]
             */
            if (timelimitFilterEnums && timelimitFilterConfig) {

                /**
                 * [创建指定过滤项列表]
                 */
                nav.filterEntities[timelimitFilterName] = (timelimitFilterConfig || []).map((filterItem, filterItemIndex) => {
                    if (timelimitFilterEnums[filterItem]) {
                        return timelimitFilterEnums[filterItem];
                    }
                });
            }
        });

        /**
         * [过滤无权限导航]
         */
        let _tempNavigation = _this.navigation;
        /**
         * [若无权限定义的页面，不过滤导航]
         */
        if(Object.keys(_this.$privileges.mapping).length){
            _tempNavigation=_tempNavigation.filter((item) => {
                return _this.$privileges.user[(_this.$privileges.mapping[item.name] || {}).id];
            })||[];

        };
        return {

            /**
             * [Navigation 导航数据]
             * @type {Object}
             */
            Navigation: _tempNavigation,

            /**
             * [CurrentNavigation 当前选中导航项]
             * @type {Object}
             */
            CurrentNavigation: _tempNavigation[0] || {
                filterEntities: {}
            },

            /**
             * [CurrentTimelimitFilter 当前选中时限过滤项]
             * @type {Object}
             */
            CurrentTimelimitFilter: (((_tempNavigation[0] || {}).filterEntities || {}).timelimit || [])[0] || {},

            /**
             * [chartEntity 图标对象实例]
             * @type {Object}
             */
            chartEntity: null,

            /**
             * [renderTimer 渲染计时器]
             * @type {Object}
             */
            renderTimer: null,

            /**
             * [swiper配置项]
             * @type {Object}
             */
            swiperOption: {
                slidesPerView: 'auto', //设置slider容器能够同时显示的slides数量
                //centeredSlides: true,  //设定为true时，活动块会居中，而不是默认状态下的居左。
                freeMode: true, // 默认为false，普通模式：slide滑动时只滑动一格，并自动贴合wrapper，设置为true则变为free模式，slide会根据惯性滑动且不会贴合。
            }
        };

    },
    mounted: function() {
        const _this = this;

        if(_this.Navigation.length>0||_this.isShow){

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
          _this.timermillisec && (_this.renderTimer = window.setInterval(function() {

            /**
             * 渲染图表
             */
            _this.render();

          }, _this.timermillisec));
        }

    },
    /****
     * 组件销毁前
     */
    beforeDestroy: function() {
        const _this = this;

        /**
         * 清除渲染计时器
         */
        window.clearInterval(_this.renderTimer);
    },
    methods: {

        /**
         * [render 渲染图表]
         */
        render: function() {

            const _this = this;

            /**
             * [dataPromise 获取数据延迟对象]
             * @type {Promise}
             */
            const dataPromise = _this.getData(),
                /**
                 * [chartOptions 图表基础配置]
                 * @type {Object}
                 */
                chartOptions = Object.assign({}, _this.chartOptions);

            /**
             * 触发初始化图表对象实例之前事件
             */
            _this.$emit('beforeRender', chartOptions);

            /**
             * [初始化图表对象实例]
             */
            if (!_this.chartEntity) {
                _this.chartEntity = new _this.chartEntityConstructor(_this.$refs.chartContainer, chartOptions);

            } else {
                _this.chartEntity.update(chartOptions, false);
            }

            /**
             * [description]
             * @param  {Object} [数据对象]
             */
            dataPromise.then((data) => {
                /**
                 * [删除坐标轴]
                 */
                while (_this.resetYAxisBeforeRedraw && _this.chartEntity.yAxis.length > 0) {
                    _this.chartEntity.yAxis[0].remove(false);
                }

                /**
                 * [删除数据列]
                 */
                while (_this.resetSeriesBeforeRedraw && _this.chartEntity.series.length > 0) {
                    _this.chartEntity.series[0].remove(false);
                }

                /**
                 * 触发渲染开始事件
                 */
                _this.$emit('dataReady', data);
                /**
                 * [根据数据创建图标数据]
                 */


                (data.dataList || []).forEach((item, index) => {
                    /**
                     * [创建坐标轴]
                     */
                    _this.chartEntity.addAxis({
                        title: {
                            text: null
                        },
                        labels: {
                            format: '{value}' + item.unit,
                            style: {
                                color: chartOptions.colors[index]
                            }
                        },
                        opposite: ((index % 2) == 1) //多个Y轴时，奇数项的Y轴在左边，偶数项的Y轴在右
                    }, false, false);
                    /**
                     * [创建数据列]
                     */
                      _this.chartEntity.addSeries({
                          name: item.name,
                          marker: {
                              enabled: false
                          },
                          visible: item.isShow,
                          data: item.data,
                          tooltip: {
                              valueSuffix: item.unit
                          },
                          yAxis: index
                      }, false);
                    /**
                     * [更新坐标轴]
                     */
                    _this.chartEntity.xAxis[0].update({
                        categories: data.time
                    }, false);
                });

                /**
                 * 触发准备图表重绘事件
                 */
                _this.$emit('beforeRedraw', _this.chartEntity);

                /**
                 * 开始重绘
                 */
                _this.chartEntity.redraw();
            });

            /**
             * 触发渲染结束事件
             */
            _this.$emit('afterRender', _this.chartEntity);
        },

        /**
         * [getData 获取数据]
         * @return {Promise} [数据延迟对象]
         */
        getData: function() {

            const _this = this,

                /**
                 * [_serviceConfig 创建数据服务相关配置]
                 * @type {Object}
                 */
                _serviceConfig = {
                    url: _this.service.url,
                    params: Object.assign({}, _this.service.params, {
                        type: _this.CurrentNavigation.code,
                        time: _this.CurrentTimelimitFilter.code
                    })
                };

            /**
             * 触发开始获取数据事件
             */
            _this.$emit('beforeGetData', _serviceConfig);
            /**
             * [若数据服务配置中包含数据延迟对象，则直接返回该延迟对象，主要用于不用从远程数据服务中获取数据的应用场景]
             */
            if (_serviceConfig.promise) {
                return _serviceConfig.promise;
            }

            /**
             * [返回从远程数据服务中获取数据的延迟对象]
             */
            return new Promise((resolve, reject) => {
                _this.$http.get(_serviceConfig.url, {
                    params: _serviceConfig.params
                }).then((response) => {
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
                     * 触发结束获取数据事件
                     */
                    _this.$emit('afterGetData', response);

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
         * [switchNav 切换图表导航项]
         * @param {Object} nav [图表导航项]
         */
        switchNav: function(nav) {
            const _this = this;
            _this.CurrentNavigation = nav;

            /**
             * 切换图表导航项后，重置图表过滤项
             */
            _this.CurrentTimelimitFilter = (_this.CurrentNavigation.filterEntities.timelimit || [])[0] || {};

            /**
             * 触发切换图表导航事件
             */
            _this.$emit('navigationSwitched', this);

            /**
             * 渲染图表
             */
            _this.render();
        },

        /**
         * [switchTimelimitFilter 切换图表时限过滤项]
         * @param  {Object}  filterItem [时限过滤项]
         */
        switchTimelimitFilter: function(filterItem) {
            const _this = this;
            _this.CurrentTimelimitFilter = filterItem;

            /**
             * 触发切换图表时限过滤项事件
             */
            _this.$emit('timelimitFilterSwitched', this);

            /**
             * 渲染图表
             */
            _this.render();
        },

        /**
         * [isActiveNav 是否当前选中图表导航项]
         * @param  {Object}  nav [图表导航项]
         * @return {Boolean}     [description]
         */
        isActiveNav: function(nav) {
            const _this = this;
            return _this.CurrentNavigation.code === nav.code;
        },

        /**
         * [isActiveTimelimitFilter 是否当前选中图表过滤项]
         * @param  {Object}  filterItem [过滤项]
         * @return {Boolean}            [description]
         */
        isActiveTimelimitFilter: function(filterItem) {
            const _this = this;
            return _this.CurrentTimelimitFilter.code === filterItem.code;
        }
    }
}
</script>
