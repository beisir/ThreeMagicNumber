webpackJsonp([6],{182:function(v,n,_){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default={name:"datapanel-readme",methods:{toggleDescription:function(v){var n=v.target||v.srcElement,_=n.parentNode.nextElementSibling;"none"==_.style.display?(_.style.display="block",n.innerHTML="收起<s></s>",n.className="eHide"):(_.style.display="none",n.innerHTML="展开<s></s>",n.className="eShow")}}}},190:function(v,n){v.exports={render:function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("div",{staticClass:"app-view",attrs:{id:"key-indicators-container"}},[_("div",{staticClass:"padding-md clearfix paddTop50"},[_("div",{staticClass:"row"},[_("div",{staticClass:"col-md-12"},[_("div",{staticClass:"panel panel-default hour-trend"},[v._m(0),v._v(" "),_("div",{staticClass:"panel-body tab-content mTop20"},[_("div",{staticClass:"explainBox"},[_("ul",[_("li",[_("div",{staticClass:"titleBox"},[_("h2",[v._v("实时数据")]),_("a",{staticClass:"eHide",on:{click:function(n){v.toggleDescription(n)}}},[v._v("收起"),_("s")])]),v._v(" "),v._m(1)]),v._v(" "),_("li",[_("div",{staticClass:"titleBox"},[_("h2",[v._v("战斗力")]),_("a",{staticClass:"eHide",on:{click:function(n){v.toggleDescription(n)}}},[v._v("收起"),_("s")])]),v._v(" "),v._m(2)])])])])]),v._v(" "),_("div",{staticClass:"panel panel-default hour-trend"},[v._m(3),v._v(" "),_("div",{staticClass:"panel-body tab-content mTop20"},[_("div",{staticClass:"explainBox"},[_("ul",[_("li",[_("div",{staticClass:"titleBox"},[_("h2",[v._v("产品运营数据")]),_("a",{staticClass:"eHide",on:{click:function(n){v.toggleDescription(n)}}},[v._v("收起"),_("s")])]),v._v(" "),v._m(4)])])])])]),v._v(" "),_("div",{staticClass:"panel panel-default hour-trend"},[v._m(5),v._v(" "),_("div",{staticClass:"panel-body tab-content mTop20"},[_("div",{staticClass:"explainBox"},[_("ul",[_("li",[_("div",{staticClass:"titleBox"},[_("h2",[v._v("战斗力")]),_("a",{staticClass:"eHide",on:{click:function(n){v.toggleDescription(n)}}},[v._v("收起"),_("s")])]),v._v(" "),v._m(6)])])])])]),v._v(" "),_("div",{staticClass:"panel panel-default hour-trend"},[v._m(7),v._v(" "),_("div",{staticClass:"panel-body tab-content mTop20"},[_("div",{staticClass:"explainBox"},[_("ul",[_("li",[_("div",{staticClass:"titleBox"},[_("h2",[v._v("付费会员")]),_("a",{staticClass:"eHide",on:{click:function(n){v.toggleDescription(n)}}},[v._v("收起"),_("s")])]),v._v(" "),v._m(8)])])])])])])])])])},staticRenderFns:[function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("nav",{staticClass:"navbar navbar-default",attrs:{role:"navigation"}},[_("div",{staticClass:"container-fluid"},[_("div",{staticClass:"navbar-header"},[_("span",{staticClass:"navbar-brand"},[v._v("\n                                                总览\n                                ")])])])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("dl",[_("dt",[v._v("IP")]),v._v(" "),_("dd",[v._v("\n                                            定义：访问全网的独立IP数\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     当天截止到当前的独立IP数\n                                            "),_("br"),v._v("     独立IP：非公司内部IP，并且IP排重\n                                            "),_("br"),v._v(" 计算方法：\n                                            "),_("br"),v._v("     昨日同比：( 当天独立IP数 - 昨天相同小时独立IP数 ) / 昨天相同小时独立IP数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：用户行为分析平台\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("PV")]),v._v(" "),_("dd",[v._v("\n                                            定义：全网的Page View\n                                            "),_("br"),v._v(" 统计规则：去除公司内部IP产生的PV量\n                                            "),_("br"),v._v(" 计算方法：当天截止当前的全网的浏览量 - 公司内部IP产生的浏览量\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：用户行为分析平台\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("UV")]),v._v(" "),_("dd",[v._v("\n                                            定义：访问全网的独立访客数\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     独立访客：非公司内部IP，且相同电脑的浏览终端排重\n                                            "),_("br"),v._v("     小时数据：小时内独立访客数，相同独立IP最多只记录10个UV\n                                            "),_("br"),v._v("     天数据：当天内独立访客数，相同独立IP最多只记录10个UV\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：用户行为分析平台\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("付费会员数")]),v._v(" "),_("dd",[v._v("\n                                            定义：购买公司服务的用户数\n                                            "),_("br"),v._v(" 计算方法：\n                                            "),_("br"),v._v("     数据：截止当前的付费会员数(不包括保护期内的用户)\n                                          "),_("br"),v._v("     昨日同比：( 当前付费会员数 - 昨天最后小时付费会员数 ) / 昨天最后小时付费会员数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：订单数据\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("各体系付费会员数")]),v._v(" "),_("dd",[v._v("\n                                            定义：购买公司服务的用户(不包括保护期内的用户)在各体系分布情况\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     体系：电销、渠道、行业、自助销售、加盟商\n                                            "),_("br"),v._v("     体系划分规则：\n                                            "),_("br"),v._v("         非自助销售体系：每个用户最后一个非自助销售订单对应的维护体系\n                                            "),_("br"),v._v("         自助销售      ：只有自助销售订单的用户数\n                                            "),_("br"),v._v("         加盟商        ：公司控股的公司，当前是产业带用户\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：订单数据\n                                            "),_("br")])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("dl",[_("dt",[v._v("电销专员")]),v._v(" "),_("dd",[v._v("\n                                            定义：营销中心在职的销售专员\n                                            "),_("br"),v._v("电销专员分类：\n                                            "),_("br"),v._v("     新兵连未转正：电销各部门新兵连未转正人数\n                                            "),_("br"),v._v("     部门中未转正：除新兵连外各部门电销专员未转正人数\n                                            "),_("br"),v._v("     部门中已转正：除新兵连外各部门电销专员已转正人数\n                                            "),_("br"),v._v("     销售专员总计：新兵连未转正 + 部门中未转正 + 部门中已转正\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     员工状态：正式、实习正式\n                                            "),_("br"),v._v("     员工职位：电销专员、高端运营销售专员、消耗运营专员、交易员\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：EHR\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("电销离职率")]),v._v(" "),_("dd",[v._v("\n                                            定义：截止到统计时间点本月渠道代电销转正人员离职率\n                                            "),_("br"),v._v(" 计算方法：本月电销转正离职人数 / ( 本月电销转正离职数 + 截止到最后统计时间电销转正人数 )\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：EHR\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("渠道专员")]),v._v(" "),_("dd",[v._v("\n                                            定义：渠道代理商一线销售人数\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     当前状态：有效\n                                            "),_("br"),v._v("     销售力级别：排除销售、未转正\n                                            "),_("br"),v._v("     员工职位：销售人员、续签销售人员、续签销售经理（主管）、销售经理(主管)\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：渠道CRM\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("渠道离职率")]),v._v(" "),_("dd",[v._v("\n                                            定义：截止到统计时间点本月渠道代理商转正人员离职率\n                                            "),_("br"),v._v(" 统计规则：本月渠道转正离职人数 / ( 本月渠道转正离职人数 + 截止到最后统计时间渠道转正人数 )\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：渠道CRM\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("职能满编率")]),v._v(" "),_("dd",[v._v("\n                                            定义：当前在岗职能人数占本月职能编制人数百分比\n                                            "),_("br"),v._v(" 统计规则：除销售以外都是职能人员，当前不统计营销中心、渠道中心职能员工人数\n                                            "),_("br"),v._v(" 计算方法：当前职能在岗人数 / 本月职能编制人数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：EHR\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("职能离职率")]),v._v(" "),_("dd",[v._v("\n                                            定义：本月离职的职能员工人数占本月全部职能人数百分比\n                                            "),_("br"),v._v(" 统计规则：本月离职的职能员工人数 = 截止到最后统计时间本月电商公司去除营销中心、渠道中心员工离职人数\n                                            "),_("br"),v._v(" 计算方法：本月离职的职能员工人数 / 本月离职的职能员工人数 + 当前在岗职能人数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：EHR\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("职能在岗人数")]),v._v(" "),_("dd",[v._v("\n                                            定义：快销品电子商务公司去除营销中心、渠道中心员工人数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：EHR\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("职能编制人数")]),v._v(" "),_("dd",[v._v("\n                                            定义：2017年本月快销品电子商务公司去除营销中心、渠道中心员工编制人数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：EHR\n                                            "),_("br")])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("nav",{staticClass:"navbar navbar-default",attrs:{role:"navigation"}},[_("div",{staticClass:"container-fluid"},[_("div",{staticClass:"navbar-header"},[_("span",{staticClass:"navbar-brand"},[v._v("\n                                                产品运营数据\n                                ")])])])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("dl",[_("dt",[v._v("询盘数量")]),v._v(" "),_("dd",[v._v("\n                                            定义：全网所有页面点击询盘点的总量，去除公司内部IP产生的询盘量\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     询盘点：联系方式、QQ、询价留言、公司留言、加入采购单、立即订购、我要采购、微信、拨号按钮\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：用户行为分析平台\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("会员注册")]),v._v(" "),_("dd",[v._v("\n                                            定义：会员自行注册量\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     会员发布，会员新增的注册会员\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：会员中心\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("P4P关键词总量")]),v._v(" "),_("dd",[v._v("\n                                            定义：P4P自建关键词总量\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：P4P数据\n                                          "),_("br")]),v._v(" "),_("dt",[v._v("P4P在投词量")]),v._v(" "),_("dd",[v._v("\n                                            定义：P4P自建关键词，推广正常，状态开启，停权复权标识正常，并且有余额的关键词\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：P4P数据\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("P4P竞价词")]),v._v(" "),_("dd",[v._v("\n                                          定义：P4P在投词，且两个人以上出价的为竞价词\n                                          "),_("br"),v._v(" 计算频率：每小时\n                                          "),_("br"),v._v(" 数据来源：P4P数据\n                                          "),_("br")]),v._v(" "),_("dt",[v._v("P4P消耗")]),v._v(" "),_("dd",[v._v("\n                                            定义： p4p广告产生的扣费。\n                                            "),_("br"),v._v(" 计算方法：当天截止到当前P4P消耗金额\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：P4P系统\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("地区分布")]),v._v(" "),_("dd",[v._v("\n                                            定义： 当天截至到当前统计时间的中国各省份的数据分布情况\n                                            "),_("br"),v._v(" 页面入口：点击《产品运营数据》中IP、PV、UV、询盘四个色块均可进入该页面\n                                            "),_("br"),v._v(" 数据纬度：IP、PV、UV、询盘\n                                            "),_("br"),v._v("     IP: 当天截止到当前统计时间各地区的独立IP数(去除公司内部IP)\n                                            "),_("br"),v._v("     PV: 当天截止到当前统计时间各地区的流量(去除公司内部IP)\n                                            "),_("br"),v._v("     UV: 当天截止到当前统计时间各地区的独立访客数(去除公司内部IP；相同独立IP在同一地区最多只记录10个UV)\n                                            "),_("br"),v._v("     询盘: 当天截止到当前统计时间各地区点击询盘点的总量(去除公司内部IP)\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：用户行为分析平台\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("站点分布")]),v._v(" "),_("dd",[v._v("\n                                            定义： 当天截至到当前统计时间的各站点的数据分布情况\n                                            "),_("br"),v._v(" 页面入口：点击《产品运营数据》中IP、PV、UV、询盘四个色块均可进入该页面\n                                            "),_("br"),v._v(" 数据纬度：IP、PV、UV、询盘\n                                            "),_("br"),v._v("     IP: 当天截止到当前时间各地区的独立IP数\n                                            "),_("br"),v._v("     PV: 当天截止到当前时间各地区的流量\n                                            "),_("br"),v._v("     UV: 全网各地区的独立访客数\n                                            "),_("br"),v._v("     询盘: 全网所有页面各地区点击询盘点的总量，去除公司内部IP产生的询盘量\n                                            "),_("br"),v._v(" 站点：JS(移动设备端)、M(移动设备端)、PC(电脑端)、WX(微信端)、ZOL(中关村)\n                                            "),_("br"),v._v(" 询盘: 全网所有页面各地区点击询盘点的总量，去除公司内部IP产生的询盘量\n                                            "),_("br"),v._v("     域名分布：点击某个站点，即可查看当天截至到当前统计时间的各域名分布情况。\n                                            "),_("br"),v._v("          域名划分规则：根据当前访问地址进行匹配。\n                                            "),_("br"),v._v("     直接来源分布：点击某个站点内的某个域名，即可查看当天截至到当前统计时间的各直接来源分布情况。\n                                            "),_("br"),v._v("          直接来源：seo、sem、inner、direct、otherouter、other\n                                            "),_("br"),v._v("            sem：访前地址为 各大搜索引擎的地址，当前地址包含市场投放标识的访问。\n                                            "),_("br"),v._v("            seo：访前地址为 各大搜索引擎的地址的访问。\n                                            "),_("br"),v._v("            inner：访前地址为 公司产品 的访问\n                                            "),_("br"),v._v("            direct：没有访前地址的，直接打开公司页面的访问\n                                            "),_("br"),v._v("            otherouter：无法判断的外网访前地址\n                                            "),_("br"),v._v("            other：均不是以上信息的访问\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：用户行为分析平台\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("全网定投累计总消耗")]),v._v(" "),_("dd",[v._v("\n                                            定义： 截止前一天的累计总消耗金额。\n                                            "),_("br"),v._v(" 计算方法：截止前一天的累计总消耗金额\n                                            "),_("br"),v._v(" 计算频率：每天\n                                            "),_("br"),v._v(" 数据来源：P4P数据库\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("全网定投周度累计消耗")]),v._v(" "),_("dd",[v._v("\n                                            定义： 截止前一天的当周累计总消耗金额。\n                                            "),_("br"),v._v(" 计算方法：截止前一天的当周累计总消耗金额\n                                            "),_("br"),v._v(" 计算频率：每天\n                                            "),_("br"),v._v(" 数据来源：P4P数据库\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("全网定投周度竞拍中用户数")]),v._v(" "),_("dd",[v._v("\n                                            定义： 截止前一天的当周竞拍中的用户数。\n                                            "),_("br"),v._v(" 计算方法：截止前一天的当周竞拍中的用户数（用户排重统计）\n                                            "),_("br"),v._v(" 计算频率：每天\n                                            "),_("br"),v._v(" 数据来源：P4P数据库\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("全网定投周度竞拍中关键词数")]),v._v(" "),_("dd",[v._v("\n                                            定义： 截止前一天的当周竞拍中的关键词数。\n                                            "),_("br"),v._v(" 计算方法：截止前一天的当周竞拍中的关键词数（关键词排重统计）\n                                            "),_("br"),v._v(" 计算频率：每天\n                                            "),_("br"),v._v(" 数据来源：P4P数据库\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("开通全网定投累计总用户数")]),v._v(" "),_("dd",[v._v("\n                                            定义： 截止前一天的有过充值的用户数。\n                                            "),_("br"),v._v(" 计算方法：截止前一天的充值累计金额  >= 10000 的用户数，用户排重统计，充值金额为 现金充值+返点金额\n                                            "),_("br"),v._v(" 计算频率：每天\n                                            "),_("br"),v._v(" 数据来源：P4P数据库\n                                            "),_("br")])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("nav",{staticClass:"navbar navbar-default",attrs:{role:"navigation"}},[_("div",{staticClass:"container-fluid"},[_("div",{staticClass:"navbar-header"},[_("span",{staticClass:"navbar-brand"},[v._v("\n                                                战斗力\n                                            ")])])])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("dl",[_("dt",[v._v("百家文章数")]),v._v(" "),_("dd",[v._v("\n                                            定义：百度百家10个账号的已发布的文章总数\n                                            "),_("br"),v._v(" 计算频率：一小时抓取一次\n                                            "),_("br"),v._v(" 数据来源：抓取百度百家号后台数据\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("市场部发稿量")]),v._v(" "),_("dd",[v._v("\n                                            定义：外部媒体发布稿件的数量\n                                            "),_("br"),v._v(" 计算方法：每周外部发稿数、外部链接数、自媒体发稿链接总和\n                                            "),_("br"),v._v(" 计算频率：每周\n                                            "),_("br"),v._v(" 数据来源：市场部手工提供\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("渠道拜访量")]),v._v(" "),_("dd",[v._v("\n                                            定义：填写了联系小计的面访客户\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：渠道CRM\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("人均在线时长")]),v._v(" "),_("dd",[v._v("\n                                            定义：电销销售人员人均在线时间长度\n                                            "),_("br"),v._v(" 计算方法：战斗力人数在线总时长 / 战斗力中有通话人数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM(天润)\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("人均有效通话次数")]),v._v(" "),_("dd",[v._v("\n                                            定义：销售人员人均打电话的有效次数\n                                            "),_("br"),v._v(" 统计规则：有效通话(通话时长大于等于1分钟)且有小计\n                                            "),_("br"),v._v(" 计算方法：战斗力人数当天累计有效通话次数 / 战斗力中有通话人数\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM(天润)\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("销售预测")]),v._v(" "),_("dd",[v._v("\n                                            定义：\n                                            "),_("br"),v._v("     预估值：电销、渠道周度、月度预估销售金额\n                                            "),_("br"),v._v("      预算值：由财务部提供的电销、渠道月度预算销售金额\n                                            "),_("br"),v._v("     实际值：电销、渠道周度实际销售金额\n                                            "),_("br"),v._v(" 计算频率：\n                                            "),_("br"),v._v("     预估值：每周\n                                            "),_("br"),v._v("     实际值：每小时\n                                            "),_("br"),v._v(" 数据来源：\n                                            "),_("br"),v._v("     预估值：电销、渠道部门\n                                            "),_("br"),v._v("     实际值：订单系统\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("销售业绩趋势")]),v._v(" "),_("dd",[v._v("\n                                            定义：电销、渠道每天的实际销售金额\n                                            "),_("br"),v._v(" 计算频率：每小时\n                                            "),_("br"),v._v(" 数据来源：订单系统\n                                            "),_("br")])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("nav",{staticClass:"navbar navbar-default",attrs:{role:"navigation"}},[_("div",{staticClass:"container-fluid"},[_("div",{staticClass:"navbar-header"},[_("span",{staticClass:"navbar-brand"},[v._v("\n                                                付费会员\n                                ")])])])])},function(){var v=this,n=v.$createElement,_=v._self._c||n;return _("dl",[_("dt",[v._v("Leads数")]),v._v(" "),_("dd",[v._v("\n                                            定义：进入电销CRM库的所有可分配客户资源\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("Leads转化率")]),v._v(" "),_("dd",[v._v("\n                                            定义：每月实际分配的Leads数转化为收费客户的转化率\n                                            "),_("br"),v._v(" 计算方法：新签用户 / 实际分配的Leads数\n                                            "),_("br"),v._v(" 统计规则：网注，网申3个月，其他6个月的转化情况\n                                            "),_("br"),v._v(" 统计频率：天\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("3类客户数")]),v._v(" "),_("dd",[v._v("\n                                            定义：根据签单成熟度将客户由深至浅分为一类、二类、三类等。三类是指未签单但条件好，意向高，客户质量高的客户\n                                          "),_("br"),v._v(" 统计方法：每天新增3类客户数及每天3类客户数库存量\n                                            "),_("br"),v._v(" 数据维度：三类客户数分为电销三类客户数、渠道三类客户数及三类客户数(电销和渠道三类客户数之和)\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("交易付费会员数")]),v._v(" "),_("dd",[v._v("\n                                            定义：付费会员中交易完成的会员数\n                                            "),_("br"),v._v(" 统计规则：在线付款，或者线下闭环，且状态为完成的\n                                            "),_("br"),v._v(" 数据来源：交易系统\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("卖家DAU")]),v._v(" "),_("dd",[v._v("\n                                            定义：每日活跃卖家数\n                                            "),_("br"),v._v(" 计算规则：当天登录商务中心的会员、未登录商务中心但有登录cookie记录访客的会员、使用微商城微信公众号的会员，三类会员数据排重汇总之和\n                                            "),_("br"),v._v(" 数据来源：用户行为分析系统，商务中心\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("客户满意度")]),v._v(" "),_("dd",[v._v("\n                                            定义：最新收费会员的级别分类，按体系拆分\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     类别： A、B、C、D类\n                                            "),_("br"),v._v("     天度数据：截止到前一天客户满意度级别分类结果\n                                            "),_("br"),v._v(" 数据来源：商务中心\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("产品用户贡献")]),v._v(" "),_("dd",[v._v("\n                                            定义：各销售产品客单价\n                                            "),_("br"),v._v(" 计算规则：\n                                            "),_("br"),v._v("     按财务拆分：营销中心、渠道按财务拆分产品线  本月订单金额/客户数\n                                            "),_("br"),v._v("     按销售订单拆分：营销中心、渠道按下订单产品线拆分  本月订单金额/客户数\n                                            "),_("br"),v._v(" 统计规则：\n                                            "),_("br"),v._v("     商业产品：基本产品，增值产品，深度运营，流量宝，初级运营\n                                            "),_("br"),v._v("     客户：按公司名称排重\n                                            "),_("br"),v._v(" 数据来源：订单\n                                            "),_("br"),v._v(" 统计周期：月度，年度\n                                            "),_("br"),v._v(" 计算频率：天\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("增值产品续签率")]),v._v(" "),_("dd",[v._v("\n                                            定义：增值产品续签率 = 已续签增值产品客户数 / 应续签增值产品客户数\n                                            "),_("br"),v._v(" 统计方法：\n                                            "),_("br"),v._v("     电销、渠道、行业、电商(电销、渠道)增值产品续签率 = 体系增值产品已续签客户数 / 体系增值产品应续签客户数\n                                            "),_("br"),v._v("     已续签客户：2017年到期并续签且关键词的到期日期在2018年的增值产品用户\n                                            "),_("br"),v._v("     应续签客户：2017年到期的增值产品用户\n                                            "),_("br"),v._v(" 数据来源：订单\n                                            "),_("br"),v._v(" 统计周期：年度\n                                            "),_("br"),v._v(" 计算频率：天\n                                            "),_("br")]),v._v(" "),_("dt",[v._v("库存Leads总数")]),v._v(" "),_("dd",[v._v("\n                                            定义：CRM潜在库中检测通过的所有待分配客户资源\n                                            "),_("br"),v._v(" 统计规则：截止当前时刻所有的库存leads\n                                            "),_("br"),v._v(" 统计频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                        ")]),v._v(" "),_("dt",[v._v("库存Leads来源")]),v._v(" "),_("dd",[v._v("\n                                           统计规则：按照来源统计截止当前时刻所有的库存leads\n                                            "),_("br"),v._v(" 数据来源：网上注册、阿里收费、网上申请、重走流程、阿里免费、百度抓取、扩展抓取、专业网站抓取\n                                            "),_("br"),v._v(" 统计频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                        ")]),v._v(" "),_("dt",[v._v("新增Leads数")]),v._v(" "),_("dd",[v._v("\n                                            定义：CRM潜在库中检测通过且新增的待分配客户资源\n                                            "),_("br"),v._v(" 统计规则：统计每天所有的库存leads\n                                            "),_("br"),v._v(" 统计频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                        ")]),v._v(" "),_("dt",[v._v("新增Leads来源")]),v._v(" "),_("dd",[v._v("\n                                            统计规则：按照来源统计每天所有的新增leads\n                                            "),_("br"),v._v(" 数据来源：网上注册、阿里收费、网上申请、重走流程、阿里免费、百度抓取、扩展抓取、专业网站抓取\n                                            "),_("br"),v._v(" 统计频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                        ")]),v._v(" "),_("dt",[v._v("已分配Leads数")]),v._v(" "),_("dd",[v._v("\n                                            定义：CRM潜在库中检测通过且分配到销售的客户资源\n                                            "),_("br"),v._v(" 统计频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                        ")]),v._v(" "),_("dt",[v._v("已分配Leads来源")]),v._v(" "),_("dd",[v._v("\n                                            统计规则：按照来源统计每天所有的已分配leads\n                                            "),_("br"),v._v(" 数据来源：网上注册、阿里收费、网上申请、重走流程、阿里免费、百度抓取、扩展抓取、专业网站抓取\n                                            "),_("br"),v._v(" 统计频率：每小时\n                                            "),_("br"),v._v(" 数据来源：CRM\n                                        ")])])}]}},96:function(v,n,_){var r=_(12)(_(182),_(190),null,null);v.exports=r.exports}});