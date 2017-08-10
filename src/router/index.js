import Vue from 'vue';
import Router from 'vue-router';

/**
 * 安装路由插件
 */
Vue.use(Router);

/**
 * [routeConfig 路由配置]
 * @type {Array}
 */
import routerConfig from './mapping';

/**
 * [routes 导出路由实例]
 * @type {Object}
 */
export default new Router({
	routes: routerConfig
});
