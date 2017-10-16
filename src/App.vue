<template>
    <div v-loading.fullscreen.lock="fullscreenLoading">
        <aside :class="{asideShow:asideVisible}">
            <a class="leftArrow" :class="{rigArrow:asideVisible}" v-on:click="toggleAside"></a>
            <div class="logo">
                <span><img src="static/img/dataImg.png"></span>
            </div>
            <div class="sidebar-inner">
                <div class="main-menu">
                    <ul>
                        <li v-for="router in routes" v-if="!router.hidden">
                            <router-link v-bind:to="router.path" active-class="active">
                                <span class="text">{{router.name}}</span>
                            </router-link>
                            <ul class="sub-nav" v-if="(router.children||[]).length>0">
                                <li v-for="subRouter in router.children" v-if="showRouter(subRouter)">
                                    <router-link v-bind:to="subRouter.path" active-class="sub-nav-checked">
                                        <span class="text">
                                        {{subRouter.name}}
                                    </span>
                                    </router-link>
                                </li>
                                <li>
                                    <a href="//data.360jz.com/dataweb/unlogin" @click="handleLogout">
                                        <span class="text">退出</span>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </aside>
        <div id="main-container" class="key-indicators" v-on:click="asideVisible=false" v-on:touchstart="" v-on:touchend="" ref="Container">
            <transition name="fade" mode="out-in">
                <router-view></router-view>
            </transition>
        </div>
    </div>
</template>
<script>
/**
 * [routeConfig 路由配置]
 * @type {Array}
 */
import routerConfig from './router/mapping';

/**
 * 路由权限映射
 */
import routerAuthMapping from './router/mapping-auth';

/**
 * 导入 VUE 模块，用于向其原型对象上附加权限数据，以便各组件都能访问到
 */
import Vue from 'vue';

/**
 * [touchOffset 触摸位移量]
 * @type {Object}
 */
const touchOffset = {
    startX: 0,
    startY: 0,
    endX: 0,
    endY: 0
};

export default {
    name: 'app',
    data() {
        return {

            /**
             * [routes 指定路由配置，并过滤不显示的一级路由]
             * @type {Array}
             */
            routes: [],

            /**
             * [asideVisible 左边栏显示状态]
             * @type {Boolean}
             */
            asideVisible: false,

            /**
             * [fullscreenLoading 全屏加载遮罩]
             * @type {Boolean}
             */
            fullscreenLoading: false
        }
    },
    created: function() {
        const _this = this;

        /**
         * [注册路由全局钩子，点击路由链接时，隐藏左边栏]
         */
        _this.$router.beforeEach(function(to, from, next) {
            _this.asideVisible = false;
            next();
        });

        /**
         * [拦截路由跳转]
         */
        _this.$router.beforeResolve((to, from, next) => {

            /**
             * 路由跳转前，先加载权限数据，权限数据加载完成后，再执行跳转
             */
            _this.interceptRouterRedirect(to, from, next);
        });

        /**
         * [注册路由跳转后钩子]
         */
        _this.$router.afterEach((route) => {

            /**
             * 设置当前路由权限映射表
             */
            _this.setRouterPrivileges(route);
        });
    },
    methods: {

        /**
         * [handleLogout 退出登录跳转前，清空cookie]
         * @return {[type]} [description]
         */
        handleLogout(){
            var _expires=new Date();
            _expires.setMilliseconds(_expires.getMilliseconds()-864e+5);// 设置cookie过期时间为前一天
            document.cookie=[
                'dataUser','=','',
                ';expires='+_expires.toUTCString()
            ].join('');
        },

        /**
         * [showRouter 是否显示路由]
         * @param  {[type]} subRouter [description]
         * @return {[type]}           [description]
         */
        showRouter(router) {
            const _this = this;

            return !router.hidden;
        },

        /**
         * [toggleAside 切换左边栏显示状态]
         */
        toggleAside: function() {
            const _this = this;
            _this.asideVisible = !_this.asideVisible;
        },

        /**
         * [touchStart 监听 touchstart 事件，记录滑动起始点的坐标]
         */
        touchStart: function(event) {
            touchOffset.startX = event.changedTouches[0].pageX;
            touchOffset.startY = event.changedTouches[0].pageY;
        },

        /**
         * [touchStart 监听 touchend 事件，记录滑动结束点的坐标，并根据计算结果判断向左滑动显示左边栏，向右滑动隐藏左边栏]
         */
        touchEnd: function(event) {
            const _this = this;
            touchOffset.endX = event.changedTouches[0].pageX;
            touchOffset.endY = event.changedTouches[0].pageY;

            var x = touchOffset.endX - touchOffset.startX,
                y = touchOffset.endY - touchOffset.startY;


            if (Math.abs(x) > Math.abs(y) && x > 200) {
                _this.asideVisible = true
            } else if (Math.abs(x) > Math.abs(y) && x < -200) {
                _this.asideVisible = false;
            }
        },

        /**
         * [interceptRouterRedirect 拦截路由跳转]
         * @param  {[type]}   to   [description]
         * @param  {[type]}   from [description]
         * @param  {Function} next [description]
         * @return {[type]}        [description]
         */
        interceptRouterRedirect(to, from, next) {
            const _this = this;

            /**
             * [fullscreenLoading 显示全屏加载遮罩]
             * @type {Boolean}
             */
            _this.fullscreenLoading = true;

            /**
             * [若已加载权限数据，则直接跳转]
             */
            if (_this.$privileges) {

                /**
                 * [fullscreenLoading 显示全屏加载遮罩]
                 * @type {Boolean}
                 */
                _this.fullscreenLoading = false;

                /**
                 * 执行路由跳转
                 */
                next();
                return;
            }

            /**
             * [_promise 获取加载用户权限数据延迟对象、加载所有权限数据延迟对象]
             * @type {[type]}
             */
            let _promise = Promise.all([_this.getDataPromise('/dataweb/getAuthDatas'), /*_this.getDataPromise('/dataweb/getAllRealtimePrivileges')*/ ]);

            /**
             * [延迟对象加载成功后，开始跳转]
             * @param  {[type]} (data [description]
             * @return {[type]}       [description]
             */
            _promise.then((args) => {

                /**
                 * [_userPrivileges 用户权限数据]
                 * @type {[type]}
                 */
                let _userPrivileges = args[0],

                    /**
                     * [_allPrivileges 所有权限数据]
                     * @type {[type]}
                     */
                    // _allPrivileges = args[1],

                    /**
                     * [_res description]
                     * @type {Object}
                     */
                    _privileges = {

                        /**
                         * [user 用户权限数据]
                         * @type {Object}
                         */
                        user: {}

                        /**
                         * [all 所有权限数据]
                         * @type {Object}
                         */
                        // all: {}
                    };

                /**
                 * [按照权限级别排序]
                 */
                // _allPrivileges.sort(function(a, b) {
                //     return a.p_type - b.p_type;
                // });

                /**
                 * [将所有权限数据按照权限级别进行分组]
                 */
                // _allPrivileges.forEach(function(item) {

                //     /**
                //      * [根据一级权限分类对权限数据进行分组]
                //      */
                //     if (item.p_type === 0) {
                //         _privileges.all[item.id.toString()] = [];
                //         return true;
                //     }

                //     /**
                //      * 填充各分组的权限数据
                //      */
                //     _privileges.all[item.p_type.toString()].push(item);
                // });

                /**
                 * [创建权限编号Map，以方便查询当前用户是否有该权限]
                 */
                _userPrivileges.forEach((privilegeid) => {

                    /**
                     * 填充各分组的权限数据
                     */
                    _privileges.user[privilegeid] = 1;
                });

                /**
                 * [$privileges 将权限数据挂载到VUE原型对象上，以便各VUE组件都能访问]
                 * @type {[type]}
                 */
                Vue.prototype.$privileges = _privileges;

                /**
                 * [设置路由权限]  routerConfig(mapping.js)路由列表    routerAuthMapping(mapping-auth.js)路由对应的权限列表
                 */
                routerConfig.forEach((router) => {
                    (router.children || []).forEach((subRouter) => {
                        /**
                         * [若当前路由需要授权才能访问，且用户权限中无该路由名称对应的权限设置，则隐藏它]
                         */
                        if (routerAuthMapping[subRouter.path] && routerAuthMapping[subRouter.path].ID && (!_this.$privileges.user[((routerAuthMapping[subRouter.path].ID) || {}).id])) {
                            subRouter.hidden = true;
                        }
                    });
                });

                /**
                 * [routes 设置]
                 * @type {[type]}
                 */
                _this.routes = routerConfig;

                /**
                 * [fullscreenLoading 显示全屏加载遮罩]
                 * @type {Boolean}
                 */
                _this.fullscreenLoading = false;

                /**
                 * 执行路由跳转
                 */
                next();
            });
        },

        /**
         * [setRouterPrivileges 设置当前路由权限映射表]
         */
        setRouterPrivileges(route) {
            const _this = this;

            /**
             * [mapping 将当前路由权限映射表挂载到VUE原型对象上，以便各VUE组件都能访问]
             * routerConfig(mapping.js)路由列表    routerAuthMapping(mapping-auth.js)路由对应的权限列表
             */
            Vue.prototype.$privileges.mapping = routerAuthMapping[route.path] || {};
        },

        /**
         * [getDataPromise 获取数据延迟对象]
         * @param  {[type]} url    [description]
         * @param  {[type]} params [description]
         * @return {[type]}        [description]
         */
        getDataPromise(url, params) {
            const _this = this;

            /**
             * [返回获取用户权限数据的延迟对象]
             * @param  {[type]} (resolve,reject [description]
             * @return {[type]}                 [description]
             */
            return new Promise((resolve, reject) => {
                _this.$http.get(url, Object.assign({}, params)).then((response) => {
                    let _response = response.body || {};

                    /**
                     * [验证数据状态码]
                     */
                    if (parseInt(_response.errno) !== 0) {
                        console.warn('返回数据状态码错误！');
                        reject(_response);
                        return;
                    }

                    /**
                     * 解决延迟对象
                     */
                    resolve(_response.data || {});
                }, (response) => {
                    console.warn('获取数据失败！');
                    reject(response);
                });
            });
        }
    }
}
</script>
