webpackJsonp([9],{144:function(e,t){},148:function(e,t,n){var i=n(10)(n(92),n(150),null,null);e.exports=i.exports},149:function(e,t,n){var i=n(10)(n(93),n(152),null,null);e.exports=i.exports},150:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement;return(e._self._c||t)("div",{class:e.slideClass},[e._t("default")],2)},staticRenderFns:[]}},151:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{directives:[{name:"loading",rawName:"v-loading.fullscreen.lock",value:e.fullscreenLoading,expression:"fullscreenLoading",modifiers:{fullscreen:!0,lock:!0}}]},[n("aside",{class:{asideShow:e.asideVisible}},[n("a",{staticClass:"leftArrow",class:{rigArrow:e.asideVisible},on:{click:e.toggleAside}}),e._v(" "),e._m(0),e._v(" "),n("div",{staticClass:"sidebar-inner"},[n("div",{staticClass:"main-menu"},[n("ul",e._l(e.routes,function(t){return t.hidden?e._e():n("li",[n("router-link",{attrs:{to:t.path,"active-class":"active"}},[n("span",{staticClass:"text"},[e._v(e._s(t.name))])]),e._v(" "),(t.children||[]).length>0?n("ul",{staticClass:"sub-nav"},[e._l(t.children,function(t){return e.showRouter(t)?n("li",[n("router-link",{attrs:{to:t.path,"active-class":"sub-nav-checked"}},[n("span",{staticClass:"text"},[e._v("\n                                    "+e._s(t.name)+"\n                                ")])])],1):e._e()}),e._v(" "),n("li",[n("a",{attrs:{href:"http://data.360jz.com/dataweb/unlogin"},on:{click:e.handleLogout}},[n("span",{staticClass:"text"},[e._v("退出")])])])],2):e._e()],1)}))])])]),e._v(" "),n("div",{ref:"Container",staticClass:"key-indicators",attrs:{id:"main-container"},on:{click:function(t){e.asideVisible=!1},touchstart:function(e){},touchend:function(e){}}},[n("transition",{attrs:{name:"fade",mode:"out-in"}},[n("router-view")],1)],1)])},staticRenderFns:[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"logo"},[n("span",[n("img",{attrs:{src:"static/img/dataImg.png"}})])])}]}},152:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"swiper-container"},[e._t("parallax-bg"),e._v(" "),n("div",{class:e.defaultSwiperClasses.wrapperClass},[e._t("default")],2),e._v(" "),e._t("pagination"),e._v(" "),e._t("button-prev"),e._v(" "),e._t("button-next"),e._v(" "),e._t("scrollbar")],2)},staticRenderFns:[]}},157:function(e,t){},30:function(e,t,n){"use strict";var i=[{name:"根",path:"/",redirect:"/datapanel/",hidden:!0},{name:"数据面板",path:"/datapanel/",redirect:"/datapanel/overview",component:function(e){n.e(7).then(function(){e(n(86))}.bind(null,n)).catch(n.oe)},children:[{name:"总览",path:"/datapanel/overview",component:function(e){n.e(2).then(function(){e(n(90))}.bind(null,n)).catch(n.oe)}},{name:"产品运营数据",path:"/datapanel/operation",component:function(e){n.e(1).then(function(){e(n(89))}.bind(null,n)).catch(n.oe)}},{name:"战斗力",path:"/datapanel/effectives",component:function(e){n.e(4).then(function(){e(n(85))}.bind(null,n)).catch(n.oe)}},{name:"付费会员",path:"/datapanel/member",component:function(e){n.e(3).then(function(){e(n(88))}.bind(null,n)).catch(n.oe)}},{name:"流量分布",path:"/datapanel/map",component:function(e){n.e(0).then(function(){e(n(87))}.bind(null,n)).catch(n.oe)},hidden:!0},{name:"数据字典",path:"/datapanel/readme",component:function(e){n.e(6).then(function(){e(n(91))}.bind(null,n)).catch(n.oe)}},{name:"权限管理",path:"/datapanel/authority",component:function(e){n.e(5).then(function(){e(n(84))}.bind(null,n)).catch(n.oe)}}]}];t.a=i},47:function(e,t,n){"use strict";var i={lang:{contextButtonTitle:"图表导出菜单",decimalPoint:".",downloadJPEG:"下载JPEG图片",downloadPDF:"下载PDF文件",downloadPNG:"下载PNG文件",downloadSVG:"下载SVG文件",drillUpText:"返回 {series.name}",invalidDate:"无效的时间",loading:"加载中...",months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],noData:"没有数据",numericSymbols:null,printChart:"打印图表",resetZoom:"重置缩放比例",resetZoomTitle:"重置为原始大小",shortMonths:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],thousandsSep:",",weekdays:["星期一","星期二","星期三","星期四","星期五","星期六","星期天"],rangeSelectorFrom:"开始时间",rangeSelectorTo:"结束时间",rangeSelectorZoom:"缩放",zoomIn:"缩小",zoomOut:"放大"},global:{useUTC:!0},title:{text:"图表标题"},tooltip:{dateTimeLabelFormats:{millisecond:"%H:%M:%S.%L",second:"%H:%M:%S",minute:"%H:%M",hour:"%H:%M",day:"%Y-%m-%d",week:"%Y-%m-%d",month:"%Y-%m",year:"%Y"}},exporting:{},credits:{text:"Highcharts",href:"https://highcharts.com.cn"},xAxis:{dateTimeLabelFormats:{millisecond:"%H:%M:%S.%L",second:"%H:%M:%S",minute:"%H:%M",hour:"%H:%M",day:"%Y-%m-%d",week:"%Y-%m-%d",month:"%Y-%m",year:"%Y"}}};t.a=i},48:function(e,t,n){"use strict";var i=n(0),a=n.n(i),s=n(154),d=n(30);a.a.use(s.a),t.a=new s.a({routes:d.a})},61:function(e,t){},62:function(e,t){},63:function(e,t){},64:function(e,t){},65:function(e,t){},66:function(e,t){},67:function(e,t){},68:function(e,t){},69:function(e,t){},70:function(e,t){},71:function(e,t){},72:function(e,t){},73:function(e,t){},74:function(e,t){},75:function(e,t){},79:function(e,t,n){var i=n(10)(n(94),n(151),null,null);e.exports=i.exports},92:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"swiper-slide",data:function(){return{slideClass:"swiper-slide"}},ready:function(){this.update()},mounted:function(){this.update(),this.$parent.options.slideClass&&(this.slideClass=this.$parent.options.slideClass)},updated:function(){this.update()},attached:function(){this.update()},methods:{update:function(){this.$parent&&this.$parent.swiper&&this.$parent.swiper.update&&(this.$parent.swiper.update(!0),this.$parent.options.loop&&this.$parent.swiper.reLoop())}}}},93:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i="undefined"!=typeof window;i&&(window.Swiper=n(46),n(144)),t.default={name:"swiper",props:{options:{type:Object,default:function(){return{autoplay:3500}}}},data:function(){return{defaultSwiperClasses:{wrapperClass:"swiper-wrapper"}}},ready:function(){!this.swiper&&i&&(this.swiper=new Swiper(this.$el,this.options))},mounted:function(){var e=this,t=function(){if(!e.swiper&&i){delete e.options.notNextTick;var t=!1;for(var n in e.defaultSwiperClasses)e.defaultSwiperClasses.hasOwnProperty(n)&&e.options[n]&&(t=!0,e.defaultSwiperClasses[n]=e.options[n]);var a=function(){e.swiper=new Swiper(e.$el,e.options)};t?e.$nextTick(a):a()}};this.options.notNextTick?t():this.$nextTick(t)},updated:function(){this.swiper&&this.swiper.update()},beforeDestroy:function(){this.swiper&&(this.swiper.destroy(),delete this.swiper)}}},94:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=n(82),a=n.n(i),s=n(83),d=n.n(s),o=n(30),c=n(98),r=n(0),u=n.n(r),l={startX:0,startY:0,endX:0,endY:0};t.default={name:"app",data:function(){return{routes:[],asideVisible:!1,fullscreenLoading:!1}},created:function(){var e=this;e.$router.beforeEach(function(t,n,i){e.asideVisible=!1,i()}),e.$router.beforeResolve(function(t,n,i){e.interceptRouterRedirect(t,n,i)}),e.$router.afterEach(function(t){e.setRouterPrivileges(t)})},methods:{handleLogout:function(){var e=new Date;e.setMilliseconds(e.getMilliseconds()-864e5),document.cookie=["dataUser","=","",";expires="+e.toUTCString()].join("")},showRouter:function(e){return!e.hidden},toggleAside:function(){var e=this;e.asideVisible=!e.asideVisible},touchStart:function(e){l.startX=e.changedTouches[0].pageX,l.startY=e.changedTouches[0].pageY},touchEnd:function(e){var t=this;l.endX=e.changedTouches[0].pageX,l.endY=e.changedTouches[0].pageY;var n=l.endX-l.startX,i=l.endY-l.startY;Math.abs(n)>Math.abs(i)&&n>200?t.asideVisible=!0:Math.abs(n)>Math.abs(i)&&n<-200&&(t.asideVisible=!1)},interceptRouterRedirect:function(e,t,n){var i=this;if(i.fullscreenLoading=!0,i.$privileges)return i.fullscreenLoading=!1,void n();d.a.all([i.getDataPromise("/dataweb/getAuthDatas")]).then(function(e){var t=e[0],a={user:{}};t.forEach(function(e){a.user[e]=1}),u.a.prototype.$privileges=a,o.a.forEach(function(e){(e.children||[]).forEach(function(e){c.a[e.path]&&c.a[e.path].ID&&!i.$privileges.user[(c.a[e.path].ID||{}).id]&&(e.hidden=!0)})}),i.routes=o.a,i.fullscreenLoading=!1,n()})},setRouterPrivileges:function(e){u.a.prototype.$privileges.mapping=c.a[e.path]||{}},getDataPromise:function(e,t){var n=this;return new d.a(function(i,s){n.$http.get(e,a()({},t)).then(function(e){var t=e.body||{};if(0!==parseInt(t.errno))return console.warn("返回数据状态码错误！"),void s(t);i(t.data||{})},function(e){console.warn("获取数据失败！"),s(e)})})}}}},97:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=n(70),a=(n.n(i),n(56)),s=n.n(a),d=n(69),o=(n.n(d),n(55)),c=n.n(o),r=n(72),u=(n.n(r),n(58)),l=n.n(u),p=n(67),f=(n.n(p),n(54)),h=n.n(f),m=n(62),v=(n.n(m),n(49)),w=n.n(v),g=n(63),b=(n.n(g),n(50)),_=n.n(b),P=n(71),x=(n.n(P),n(57)),y=n.n(x),C=n(73),$=(n.n(C),n(59)),S=n.n($),L=n(74),M=(n.n(L),n(60)),k=n.n(M),D=n(64),T=(n.n(D),n(51)),Y=n.n(T),V=n(65),E=(n.n(V),n(52)),I=n.n(E),R=n(68),H=(n.n(R),n(27)),A=n.n(H),F=n(66),O=(n.n(F),n(61)),X=(n.n(O),n(53)),j=n.n(X),U=n(0),G=n.n(U),N=n(79),z=n.n(N),J=n(48),Z=n(81),B=n(77),q=n.n(B),K=n(47),Q=n(78),W=n.n(Q);G.a.use(Z.a),G.a.use(W.a),G.a.use(j.a),G.a.use(A.a),G.a.use(I.a),G.a.use(Y.a),G.a.use(k.a),G.a.use(S.a),G.a.use(y.a),G.a.use(_.a),G.a.use(w.a),G.a.use(h.a),G.a.use(l.a),G.a.use(c.a.directive),G.a.prototype.$loading=c.a.service,G.a.prototype.$message=s.a,n(75);var ee=new G.a({el:"#app",router:J.a,template:"<App/>",components:{App:z.a}});window.vueEntity=ee,window.Highcharts=q.a,q.a.setOptions(K.a)},98:function(e,t,n){"use strict";t.a={"/datapanel/overview":{IP:{id:53,desc:"实时数据"},PV:{id:54,desc:"实时数据"},UV:{id:55,desc:"实时数据"},"付费会员":{id:47,desc:"实时数据"},"电销":{id:56,desc:"付费会员"},"渠道":{id:57,desc:"付费会员"},"行业":{id:6,desc:"付费会员"},"加盟商":{id:34,desc:"付费会员"},"职能-满编率":{id:11,desc:"战斗力--职能"},"职能-离职率":{id:11,desc:"战斗力--职能"},"职能-在岗人数":{id:11,desc:"战斗力--职能"},"职能-编制人数":{id:11,desc:"战斗力--职能"},"渠道-转正人数":{id:9,desc:"战斗力--渠道"},"渠道-离职率":{id:10,desc:"战斗力--渠道"},"营销中心-新兵连人数":{id:40,desc:"战斗力--营销中心"},"营销中心-部门中未转正":{id:41,desc:"战斗力--营销中心"},"营销中心-部门中已转正":{id:7,desc:"战斗力--营销中心"},"营销中心-销售专员总计":{id:42,desc:"战斗力--营销中心"},"营销中心-离职率":{id:8,desc:"战斗力--营销中心"},"营销中心-管理层人数":{id:43,desc:"战斗力--营销中心"},"营销中心-职能人数":{id:69,desc:"战斗力--营销中心"},"营销中心-营销中心总人数":{id:70,desc:"战斗力--营销中心"}},"/datapanel/operation":{ID:{id:1,desc:"产品运营数据路由权限编号"},IP:{id:53,desc:"产品运营数据"},UV:{id:55,desc:"产品运营数据"},PV:{id:54,desc:"产品运营数据"},"询盘数量":{id:13,desc:"产品运营数据"},"会员注册":{id:18,desc:"产品运营数据"},"P4P消耗":{id:19,desc:"产品运营数据"},"P4P关键词总量":{id:45,desc:"产品运营数据"},"P4P在投词量":{id:45,desc:"产品运营数据"},"百度联盟收入":{id:46,desc:"趋势图1"},"P4P关键词":{id:45,desc:"趋势图2"},"P4P在投词量（P4P关键词）":{id:45,desc:"趋势图2"},"P4P竞价词":{id:58,desc:"趋势图2"}},"/datapanel/effectives":{ID:{id:3,desc:"战斗力路由权限编号"},"职能满编率":{id:11,desc:"战斗力"},"电销转正人数":{id:7,desc:"战斗力"},"渠道转正人数":{id:9,desc:"战斗力"},"百家文章数":{id:27,desc:"战斗力"},"市场部发稿量":{id:28,desc:"战斗力"},"渠道拜访量":{id:22,desc:"战斗力"},"人均在线时长":{id:20,desc:"战斗力"},"人均有效通话次数":{id:21,desc:"战斗力"},"电销":{id:35,desc:"销售预测"},"渠道":{id:38,desc:"销售预测"}},"/datapanel/member":{ID:{id:2,desc:"付费会员路由权限编号"},"付费会员":{id:47,desc:"付费会员"},"新增Leads数":{id:31,desc:"付费会员"},"Leads转化率":{id:30,desc:"付费会员"},"3类客户数":{id:24,desc:"付费会员"},"交易付费会员数":{id:33,desc:"付费会员"},"卖家DAU":{id:14,desc:"付费会员"},"增值产品-电销":{id:61,desc:"续签率-增值产品"},"增值产品-渠道":{id:62,desc:"续签率-增值产品"},"增值产品-行业":{id:63,desc:"续签率-增值产品"},"增值产品-电商":{id:64,desc:"续签率-增值产品"},"基本产品-电销":{id:65,desc:"续签率-基本产品"},"基本产品-渠道":{id:66,desc:"续签率-基本产品"},"基本产品-行业":{id:67,desc:"续签率-基本产品"},"基本产品-电商":{id:68,desc:"续签率-基本产品"},"客户满意度":{id:29,desc:"对比图"},"产品用户贡献":{id:32,desc:"对比图"},"Leads来源转化率":{id:44,desc:"趋势图1"},"库存Leads总数":{id:60,desc:"趋势图2"},"库存Leads来源":{id:39,desc:"趋势图2"},"新增Leads来源":{id:36,desc:"趋势图2"},"已分配Leads数":{id:59,desc:"趋势图2"},"已分配Leads来源":{id:37,desc:"趋势图2"}},"/datapanel/readme":{ID:{id:4,desc:"数字字典路由权限编号"}},"/datapanel/authority":{ID:{id:5,desc:"权限管理路由权限编号"}}}}},[97]);