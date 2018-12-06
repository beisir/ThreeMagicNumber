webpackJsonp([6],{110:function(t,e,a){var i=a(12)(a(202),a(224),null,null);t.exports=i.exports},187:function(t,e,a){t.exports={default:a(188),__esModule:!0}},188:function(t,e,a){a(190),t.exports=a(3).Object.keys},189:function(t,e,a){var i=a(8),n=a(3),r=a(21);t.exports=function(t,e){var a=(n.Object||{})[t]||Object[t],o={};o[t]=e(a),i(i.S+i.F*r(function(){a(1)}),"Object",o)}},190:function(t,e,a){var i=a(42),n=a(41);a(189)("keys",function(){return function(t){return n(i(t))}})},191:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a(104),n=a.n(i),r=a(103),o=a.n(r),s=a(187),c=a.n(s);e.default={name:"chart-tendency",props:{chartFlag:{default:function(){return!0}},chartTitle:{default:function(){return"趋势图"}},isShow:{default:function(){return!1}},navigation:{default:function(){return[]}},enums:{default:function(){return{timelimit:{today:{name:"今天",code:"0"},lastsevensays:{name:"最近七天",code:"1"},lastmonth:{name:"最近一个月",code:"2"},thismonth:{name:"本月数据",code:"8"},all:{name:"累计",code:"3"},weekly:{name:"周度数据",code:"4"},monthly:{name:"月度数据",code:"5"},Splitbyfinancial:{name:"按财务拆分",code:"6"},Splitbysalesorder:{name:"按销售订单拆分",code:"7"},numberPeople:{name:"按竞价人数排序",code:"10"},price:{name:"按竞价价格排序",code:"11"}}}}},service:{default:function(){return{url:""}}},chartOptions:{default:function(){return{title:{text:null},chart:{type:"spline"},colors:["#19c6Ed","#FF7C4D","#2BCC6B","#C275DF"],credits:{enabled:!1},tooltip:{formatter:null,shared:!0}}}},timermillisec:{default:function(){return 66e4}},chartEntityConstructor:{default:function(){return Highcharts.chart}},resetYAxisBeforeRedraw:{default:function(){return!0}},resetSeriesBeforeRedraw:{default:function(){return!0}}},data:function(){var t=this;t.navigation.forEach(function(e,a){e.filterEntities={};var i=(e.filters||{}).timelimit,n=t.enums.timelimit;n&&i&&(e.filterEntities.timelimit=(i||[]).map(function(t,e){if(n[t])return n[t]}))});var e=t.navigation;return c()(t.$privileges.mapping).length&&(e=e.filter(function(e){return t.$privileges.user[(t.$privileges.mapping[e.name]||{}).id]})||[]),{Navigation:e,CurrentNavigation:e[0]||{filterEntities:{}},CurrentTimelimitFilter:(((e[0]||{}).filterEntities||{}).timelimit||[])[0]||{},chartEntity:null,renderTimer:null,swiperOption:{slidesPerView:"auto",freeMode:!0}}},mounted:function(){var t=this;(t.Navigation.length>0||t.isShow)&&(setTimeout(function(){t.render()}),t.timermillisec&&(t.renderTimer=window.setInterval(function(){t.render()},t.timermillisec)))},beforeDestroy:function(){var t=this;window.clearInterval(t.renderTimer)},methods:{render:function(){var t=this,e=t.getData(),a=o()({},t.chartOptions);t.$emit("beforeRender",a),t.chartEntity?t.chartEntity.update(a,!1):t.chartEntity=new t.chartEntityConstructor(t.$refs.chartContainer,a),e.then(function(e){for(;t.resetYAxisBeforeRedraw&&t.chartEntity.yAxis.length>0;)t.chartEntity.yAxis[0].remove(!1);for(;t.resetSeriesBeforeRedraw&&t.chartEntity.series.length>0;)t.chartEntity.series[0].remove(!1);t.$emit("dataReady",e),(e.dataList||[]).forEach(function(i,n){t.chartEntity.addAxis({title:{text:null},labels:{format:"{value}"+(i.unit?i.unit:""),style:{color:a.colors[n]}},opposite:n%2==1},!1,!1),t.chartEntity.addSeries({name:i.name,marker:{enabled:!1},visible:i.isShow,data:i.data,tooltip:{valueSuffix:i.unit},yAxis:n},!1),t.chartEntity.xAxis[0].update({categories:e.time},!1)}),t.$emit("beforeRedraw",t.chartEntity),t.chartEntity.redraw()}),t.$emit("afterRender",t.chartEntity)},getData:function(){var t=this,e={url:t.service.url,params:o()({},t.service.params,{type:t.CurrentNavigation.code,time:t.CurrentTimelimitFilter.code})};return t.$emit("beforeGetData",e),e.promise?e.promise:new n.a(function(a,i){t.$http.get(e.url,{params:e.params}).then(function(e){if(e=e.body||{},0!==parseInt(e.errno))return console.warn("返回数据状态码错误！"),void i(e);t.$emit("afterGetData",e),a(e.data||{})},function(t){console.warn("获取数据请求失败！"),i(t)})})},switchNav:function(t){var e=this;e.CurrentNavigation=t,e.CurrentTimelimitFilter=(e.CurrentNavigation.filterEntities.timelimit||[])[0]||{},e.$emit("navigationSwitched",this),e.render()},switchTimelimitFilter:function(t){var e=this;e.CurrentTimelimitFilter=t,e.$emit("timelimitFilterSwitched",this),e.render()},isActiveNav:function(t){return this.CurrentNavigation.code===t.code},isActiveTimelimitFilter:function(t){return this.CurrentTimelimitFilter.code===t.code}}}},192:function(t,e,a){var i=a(12)(a(191),a(193),null,null);t.exports=i.exports},193:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"show",rawName:"v-show",value:t.Navigation.length>0||t.isShow,expression:"Navigation.length>0||isShow"}],staticClass:"panel panel-default hour-trend"},[t.chartFlag?a("nav",{staticClass:"navbar navbar-default",staticStyle:{background:"#f5f5f5","margin-top":"30px","margin-bottom":"10px"},attrs:{role:"navigation"}},[a("div",{staticClass:"container-fluid"},[a("div",{staticClass:"navbar-header"},[a("span",{staticClass:"navbar-brand",staticStyle:{"font-size":"16px"}},[t._v("\n                    "+t._s(t.chartTitle)+"\n                ")])]),t._v(" "),a("div",{attrs:{id:"titleHead"}},[a("swiper",{attrs:{options:t.swiperOption}},t._l(t.Navigation,function(e){return a("swiper-slide",[a("span",{class:{active:t.isActiveNav(e)},on:{click:function(a){t.switchNav(e)}}},[t._v(t._s(e.name))])])}))],1)])]):t._e(),t._v(" "),a("div",{staticClass:"panel-body tab-content"},[a("div",{staticClass:"navbarRadio"},t._l(t.CurrentNavigation.filterEntities.timelimit,function(e){return a("label",[a("input",{attrs:{type:"radio",name:"timelimit_"+t._uid},domProps:{checked:t.isActiveTimelimitFilter(e)},on:{click:function(a){t.switchTimelimitFilter(e)}}}),t._v(t._s(e.name)+"\n            ")])})),t._v(" "),a("div",{ref:"chartContainer",staticClass:"tab-pane active"},[t._v("图表区域")])])])},staticRenderFns:[]}},202:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a(104),n=a.n(i),r=a(187),o=a.n(r),s=a(103),c=a.n(s),l=a(192),d=a.n(l);e.default={name:"datapanel-effectives",data:function(){return{navigation:[{name:"新增Leads数",code:"99",filters:{timelimit:["today","lastmonth","weekly","monthly"]},todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"Leads来源转化率",code:"297",filters:{timelimit:["monthly"]},todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"3类客户数",code:"62",filters:{timelimit:["today","lastmonth","all"]},todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},,{name:"付费会员",code:"0",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"新增Leads来源",code:"186",filters:{timelimit:["today","lastmonth","weekly","monthly"]}},{name:"已分配Leads数",code:"202",filters:{timelimit:["today","lastmonth","weekly","monthly"]}},{name:"已分配Leads来源",code:"194",filters:{timelimit:["today","lastmonth","weekly","monthly"]}},{name:"库存Leads总数",code:"290",filters:{timelimit:["today","lastmonth"]}},{name:"库存Leads来源",code:"282",filters:{timelimit:["today","lastmonth"]}}],tendencychart_Bar:[{name:"客户满意度",code:"82"},{name:"产品用户贡献",code:"100",filters:{timelimit:["Splitbyfinancial","Splitbysalesorder"]}}],timerMillisec:66e4,service:{url:"/dataweb/feeuserchart"},extend:{"增值产品":[{name:"直销",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"渠道",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"行业",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"电商",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null}],"基本产品":[{name:"直销",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"渠道",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"行业",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null},{name:"电商",todayNum:0,yesterdayNum:0,ratioNum:0,ratioTrend:null}]}}},computed:{colorBlocks:function(){var t=["新增Leads数","3类客户数"];return this.navigation.filter(function(e){return-1!=t.indexOf(e.name)})},headerCircle:function(){var t=["付费会员"];return this.navigation.filter(function(e){return-1!=t.indexOf(e.name)})[0]},tendencychart:function(){var t=["Leads来源转化率","3类客户数"];return this.navigation.filter(function(e){return-1!=t.indexOf(e.name)})},tendencychart_Leads:function(){var t=this,e=["库存Leads总数","库存Leads来源","新增Leads数","新增Leads来源","已分配Leads数","已分配Leads来源"];return t.navigation.filter(function(t){return-1!=e.indexOf(t.name)}).sort(function(t,a){return e.indexOf(t.name)-e.indexOf(a.name)})}},created:function(){var t=this;setTimeout(function(){t.render()}),t.renderTimer=window.setInterval(function(){t.render()},t.timerMillisec),window.VueEntity=this},beforeDestroy:function(){var t=this;window.clearInterval(t.renderTimer)},mounted:function(){var t=this;t.$refs.chartTendencyElement_bar.$on("beforeRender",function(t){c()(t,{chart:{type:"column"},colors:["#4572A7","#AA4643","#89A54E","#80699B","#3D96AE","#DB843D","#92A8CD","#A47D7C","#B5CA92"]})}),t.$refs.chartTendencyElement_bar.$on("beforeRedraw",function(t){t.series.forEach(function(t,e){t.update({yAxis:0},!1)})}),t.$refs.chartTendencyElement.$on("beforeRedraw",function(t){98==this.CurrentNavigation.code&&t.update({yAxis:{tickAmount:5,tickInterval:.01}},!1),62==this.CurrentNavigation.code&&t.series.forEach(function(t,e){t.update({yAxis:0},!1)}),297==this.CurrentNavigation.code?(t.series.forEach(function(t){t.update({yAxis:0},!1)}),t.zoomOut()):t.zoomOut()}),t.$refs.chartTendencyElement.$on("beforeRender",function(t){297==this.CurrentNavigation.code?c()(t,{chart:{type:"column",zoomType:"x"},colors:["#4572A7","#AA4643","#89A54E","#80699B","#3D96AE","#DB843D","#92A8CD","#A47D7C","#B5CA92"]}):c()(t,{chart:{type:"spline",zoomType:""}})}),t.$refs.chartTendencyElement_Leads.$on("beforeRender",function(t){186==this.CurrentNavigation.code||194==this.CurrentNavigation.code||282==this.CurrentNavigation.code?c()(t,{chart:{type:"column",zoomType:"x"},colors:["#4572A7","#AA4643","#89A54E","#80699B","#3D96AE","#DB843D","#92A8CD","#A47D7C","#B5CA92"]}):c()(t,{chart:{type:"spline",zoomType:""}})}),t.$refs.chartTendencyElement_Leads.$on("beforeRedraw",function(t){var e=this;if(186==e.CurrentNavigation.code||194==e.CurrentNavigation.code||282==e.CurrentNavigation.code){if(t.series.forEach(function(t,e){t.update({yAxis:0},!1)}),t.zoomOut(),4==e.CurrentTimelimitFilter.code||5==e.CurrentTimelimitFilter.code||2==e.CurrentTimelimitFilter.code){var a=t.xAxis[0],i=a.categories.length-1,n=a.categories.length-1;a.setExtremes(i,n,!1),t.showResetZoom()}}else t.zoomOut()})},methods:{render:function(){var t=this;t.getData().then(function(e){var a=function(t){var e={};return t.forEach(function(t,a){e[t.name]=t}),e},i=function(t){return(t.toString().split(".")[1]||"").length},n=function(t,e){var a=this,n=!1,r=new RegExp("%$","i");n=r.test(t.num)&&r.test(e.num),n?(a.todayNum=(parseFloat(t.num)||0)+"%",a.yesterdayNum=(parseFloat(e.num)||0)+"%",a.ratioNum=((parseFloat(t.num)||0)-(parseFloat(e.num)||0)).toFixed(2)):(a.todayNum=parseFloat(t.num)||0,a.yesterdayNum=parseFloat(e.num)||0,a.ratioNum=((a.todayNum-a.yesterdayNum)/a.yesterdayNum*100||0).toFixed(2),isFinite(((a.todayNum-a.yesterdayNum)/a.yesterdayNum*100||0).toFixed(2))||(a.ratioNum=100),a.todayNum=Highcharts.numberFormat(a.todayNum,i(a.todayNum),".",","),a.yesterdayNum=Highcharts.numberFormat(a.yesterdayNum,i(a.yesterdayNum),".",",")),a.ratioTrend=a.ratioNum>=0,a.ratioNum=Math.abs(a.ratioNum)+"%",a.numCallback&&a.numCallback.call(a,t,e)},r=a((e.yesterdaydata||{}).main||[]),s=a((e.todaydata||{}).main||[]),c=(e.yesterdaydata||{}).renew||{},l=(e.todaydata||{}).renew||{};o()(c).forEach(function(t,e){c[t]=a(c[t]||[])}),o()(l).forEach(function(t,e){l[t]=a(l[t]||[])}),o()(t.extend).forEach(function(e,a){(t.extend[e]||[]).forEach(function(t,a){n.call(t,(l[e]||{})[t.name]||{},(c[e]||{})[t.name]||{})})}),t.navigation.forEach(function(t,e){n.call(t,s[t.name]||{},r[t.name]||{})})})},getData:function(){var t=this;return new n.a(function(e,a){t.$http.get("/dataweb/feeuserdata",{}).then(function(t){if(t=t.body||{},0!==parseInt(t.errno))return console.warn("返回数据状态码错误！"),void a(t);e(t.data||{})},function(t){console.warn("获取数据请求失败！"),a(t)})})},formatRatioNum:function(t){return t.toString().replace(/\-/gi,"")},formatPercent:function(t){var e,a=[{multiple:1,symbol:"%"},{multiple:10,symbol:"‰"},{multiple:10,symbol:"‱"}],i=new RegExp("%$","i"),n=parseFloat(t)||0,r=a[0];if(!i.test(t))return t;if(0===n)return"0%";for(;Math.abs(n)<1&&(e=a.shift());)r=e,n*=r.multiple;return n.toFixed(2)+r.symbol}},components:{"chart-tendency":d.a}}},224:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-view",attrs:{id:"key-indicators-container"}},[a("div",{staticClass:"padding-md clearfix paddTop50"},[a("div",{staticClass:"row"},[a("div",{staticClass:"col-md-12"},[a("div",{staticClass:"panel panel-default hour-trend"},[t._m(0),t._v(" "),a("div",{staticClass:"panel-body tab-content mTop20"},[a("div",{staticClass:"data2Box"},[a("div",{staticClass:"data2BoxTop"},[a("div",{staticClass:"data2BoxTopCon"},[a("dl",[a("dd",[a("span",{staticClass:"l-01"},[t._v(" ")]),t._v(" "),a("span",{staticClass:"l-02"},[t._v(t._s(t.headerCircle.name))]),t._v(" "),a("span",{staticClass:"l-03"},[t._v(" ")])])])]),t._v(" "),t._m(1)]),t._v(" "),a("div",{staticClass:"data2BoxBot"},[a("div",{staticClass:"data2BoxBotCon"},[a("ul",t._l(t.colorBlocks,function(e){return t.$privileges.user[(t.$privileges.mapping[e.name]||{}).id]?a("li",[a("div",{staticClass:"data2BoxBotList"},[a("dl",[a("dt",[t._v(t._s(e.name))]),t._v(" "),a("dd",[a("span",{staticClass:"l-02"},[t._v(t._s(t.formatPercent(e.todayNum)))]),t._v(" "),a("span",{staticClass:"l-03",class:{"icon-tt-up":e.ratioTrend,"icon-tt-lower":!1===e.ratioTrend}},[t._v(t._s(e.ratioNum))])])])])]):t._e()}))])])]),t._v(" "),t._m(2),t._v(" "),a("div",{staticClass:"xqBox",staticStyle:{overflow:"hidden",padding:"10px",border:"1px dotted #ddd","margin-top":"-11px","border-radius":"0 0 4px 4px",clear:"both"}},t._l(t.extend,function(e,i){return a("div",{staticClass:"tab-contentBox",staticStyle:{padding:"0 15px"}},[a("div",{staticClass:"tabBotCon"},[a("h3",[t._v(t._s(i))]),t._v(" "),a("div",{staticClass:"uxerNumBox"},t._l(e,function(e,n){return t.$privileges.user[(t.$privileges.mapping[i+"-"+e.name]||{}).id]?a("div",{staticClass:"userNumCon"},[a("div",{staticClass:"userNumList"},[a("span",{staticClass:"userLeft"},[t._v(t._s(e.name))]),t._v(" "),a("span",{staticClass:"userRig",class:{rigUp:e.ratioTrend,rigDown:!1===e.ratioTrend}},[t._v(t._s(t.formatRatioNum(e.ratioNum)))]),t._v(" "),a("span",{staticClass:"userRig"},[t._v(t._s(e.todayNum))])])]):t._e()}))])])})),t._v(" "),a("chart-tendency",{ref:"chartTendencyElement_bar",attrs:{navigation:t.tendencychart_Bar,timermillisec:0,service:t.service,chartTitle:"对比图"}}),t._v(" "),a("chart-tendency",{ref:"chartTendencyElement",attrs:{navigation:t.tendencychart,timermillisec:t.timerMillisec,service:t.service}}),t._v(" "),a("chart-tendency",{ref:"chartTendencyElement_Leads",attrs:{navigation:t.tendencychart_Leads,timermillisec:t.timerMillisec,service:t.service}})],1)])])])])])},staticRenderFns:[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("nav",{staticClass:"navbar navbar-default",attrs:{role:"navigation"}},[a("div",{staticClass:"container-fluid"},[a("div",{staticClass:"navbar-header"},[a("span",{staticClass:"navbar-brand"},[t._v("付费会员")])])])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"data2BoxTopArrow"},[a("img",{attrs:{src:"static/img/arrow.png"}})])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("nav",{staticClass:"navbar navbar-default",staticStyle:{background:"rgb(245, 245, 245)","margin-top":"30px","margin-bottom":"10px"},attrs:{role:"navigation"}},[a("div",{staticClass:"container-fluid"},[a("div",{staticClass:"navbar-header"},[a("span",{staticClass:"navbar-brand",staticStyle:{"font-size":"16px"}},[t._v("\n                                            续签率\n                                     ")])])])])}]}}});