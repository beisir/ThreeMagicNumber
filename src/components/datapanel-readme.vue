<template>
    <div id="key-indicators-container" class="app-view">
        <div class="padding-md clearfix paddTop50">
            <div class="row">
                <div class="col-md-12">
                    <!--总览-->
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">
                                                    总览
                                    </span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="explainBox">
                                <ul>
                                    <li>
                                        <div class="titleBox">
                                            <h2>实时数据(全网)</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
                                        <dl>
                                            <dt>IP</dt>
                                            <dd>
                                                定义：访问全网的独立IP数
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;当天截止到当前的独立IP数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;独立IP：非公司内部IP，并且IP排重
                                                <br /> 计算方法：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;昨日环比：(&nbsp;当天独立IP数&nbsp;-&nbsp;昨天相同小时独立IP数&nbsp;)&nbsp;/&nbsp;昨天相同小时独立IP数
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;周同比：(&nbsp;当天独立IP数&nbsp;-&nbsp;周相同小时独立IP数&nbsp;)&nbsp;/&nbsp;周相同小时独立IP数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：用户行为分析平台
                                                <br />
                                            </dd>
                                            <dt>PV</dt>
                                            <dd>
                                                定义：全网的Page View
                                                <br /> 统计规则：去除公司内部IP产生的PV量
                                                <br /> 计算方法：当天截止当前的全网的浏览量&nbsp;-&nbsp;公司内部IP产生的浏览量
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;昨日环比：(&nbsp;当天独立PV数&nbsp;-&nbsp;昨天相同小时独立PV数&nbsp;)&nbsp;/&nbsp;昨天相同小时独立PV数
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;周同比：(&nbsp;当天独立PV数&nbsp;-&nbsp;周相同小时独立PV数&nbsp;)&nbsp;/&nbsp;周相同小时独立PV数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：用户行为分析平台
                                                <br />
                                            </dd>
                                            <dt>UV</dt>
                                            <dd>
                                                定义：访问全网的独立访客数
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;独立访客：非公司内部IP，且相同电脑的浏览终端排重
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;小时数据：小时内独立访客数，相同独立IP最多只记录10个UV
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;天数据：当天内独立访客数，相同独立IP最多只记录10个UV
												<br /> 计算方法：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;昨日环比：(&nbsp;当天独立UV数&nbsp;-&nbsp;昨天相同小时独立UV数&nbsp;)&nbsp;/&nbsp;昨天相同小时独立UV数
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;周同比：(&nbsp;当天独立UV数&nbsp;-&nbsp;周相同小时独立UV数&nbsp;)&nbsp;/&nbsp;周相同小时独立UV数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：用户行为分析平台
                                                <br />
                                            </dd>
                                            <dt>付费会员数</dt>
                                            <dd>
                                                定义：购买公司服务的用户数
                                                <br /> 计算方法：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;数据：截止当前的付费会员数(不包括保护期内的用户)
                                              <br /> &nbsp;&nbsp;&nbsp;&nbsp;昨日同比：(&nbsp;当前付费会员数&nbsp;-&nbsp;昨天最后小时付费会员数&nbsp;)&nbsp;/&nbsp;昨天最后小时付费会员数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：订单数据
                                                <br />
                                            </dd>
                                            <dt>各体系付费会员数</dt>
                                            <dd>
                                                定义：购买公司服务的用户(不包括保护期内的用户)在各体系分布情况
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;体系：电销、渠道、行业、自助销售、加盟商
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;体系划分规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;非自助销售体系：每个用户最后一个非自助销售订单对应的维护体系
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自助销售      ：只有自助销售订单的用户数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加盟商        ：公司控股的公司，当前是产业带用户
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：订单数据
                                                <br />
                                            </dd>
                                        </dl>
                                    </li>									
                                    <li>
                                        <div class="titleBox">
                                            <h2>战斗力</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
                                        <dl>
                                            <dt>电销专员</dt>
                                            <dd>
                                                定义：营销中心在职的销售专员
                                                <br />电销专员分类：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;新兵连未转正：电销各部门新兵连未转正人数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;部门中未转正：除新兵连外各部门电销专员未转正人数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;部门中已转正：除新兵连外各部门电销专员已转正人数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;销售专员总计：新兵连未转正 + 部门中未转正 + 部门中已转正
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;员工状态：正式、实习正式
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;员工职位：电销专员、高端运营销售专员、消耗运营专员、交易员
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：EHR
                                                <br />
                                            </dd>
                                            <dt>电销离职率</dt>
                                            <dd>
                                                定义：截止到统计时间点本月渠道代电销转正人员离职率
                                                <br /> 计算方法：本月电销转正离职人数&nbsp;/&nbsp;(&nbsp;本月电销转正离职数&nbsp;+&nbsp;截止到最后统计时间电销转正人数&nbsp;)
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：EHR
                                                <br />
                                            </dd>
                                            <dt>渠道专员</dt>
                                            <dd>
                                                定义：渠道代理商一线销售人数
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;当前状态：有效
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;销售力级别：排除销售、未转正
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;员工职位：销售人员、续签销售人员、续签销售经理（主管）、销售经理(主管)
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：渠道CRM
                                                <br />
                                            </dd>
                                            <dt>渠道离职率</dt>
                                            <dd>
                                                定义：截止到统计时间点本月渠道代理商转正人员离职率
                                                <br /> 统计规则：本月渠道转正离职人数&nbsp;/&nbsp;(&nbsp;本月渠道转正离职人数&nbsp;+&nbsp;截止到最后统计时间渠道转正人数&nbsp;)
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：渠道CRM
                                                <br />
                                            </dd>
                                            <dt>职能满编率</dt>
                                            <dd>
                                                定义：当前在岗职能人数占本月职能编制人数百分比
                                                <br /> 统计规则：除销售以外都是职能人员，当前不统计营销中心、渠道中心职能员工人数
                                                <br /> 计算方法：当前职能在岗人数&nbsp;/&nbsp;本月职能编制人数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：EHR
                                                <br />
                                            </dd>
                                            <dt>职能离职率</dt>
                                            <dd>
                                                定义：本月离职的职能员工人数占本月全部职能人数百分比
                                                <br /> 统计规则：本月离职的职能员工人数&nbsp;=&nbsp;截止到最后统计时间本月电商公司去除营销中心、渠道中心员工离职人数
                                                <br /> 计算方法：本月离职的职能员工人数&nbsp;/&nbsp;本月离职的职能员工人数&nbsp;+&nbsp;当前在岗职能人数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：EHR
                                                <br />
                                            </dd>
                                            <dt>职能在岗人数</dt>
                                            <dd>
                                                定义：快销品电子商务公司去除营销中心、渠道中心员工人数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：EHR
                                                <br />
                                            </dd>
                                            <dt>职能编制人数</dt>
                                            <dd>
                                                定义：2017年本月快销品电子商务公司去除营销中心、渠道中心员工编制人数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：EHR
                                                <br />
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!--产品线数据-->
					<div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">
                                                    产品线数据
                                                </span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="explainBox">
                                <ul>
									<li>
										<div class="titleBox">
											<h2>P4P数据</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
										<dl>
											<dt>客单价</dt>
											<dd>
												定义：用户平均花费现金金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;客单价 = 累计现金充值金额/有过现金充值记录的用户数
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;由于计算为结果为四舍五入，所以 会员数*客单价 ≈ 销售额，不能完全相等。
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>会员数</dt>
											<dd>
												定义：有过现金充值的用户数
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;有过现金充值记录的用户
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>销售额</dt>
											<dd>
												定义：累计现金充值金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;累计现金充值金额
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;由于客单价结果为四舍五入，所以 会员数*客单价 ≈ 累计现金充值金额，不能完全相等。
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>充值</dt>
											<dd>
												定义：累计充值金额，包括现金、返点金、虚拟
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;用户累计充值金额；金额 = 累计现金+累计返点金+累计虚拟
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>消耗</dt>
											<dd>
												定义：累计消耗，包括现金、返点金、虚拟
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;用户累计消耗金额；消耗 = 累计现金消耗+累计返点金消耗+累计虚拟消耗
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>余额</dt>
											<dd>
												定义：运行时刻的总余额，包括现金、返点金、虚拟
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;运行时刻的总余额；总余额 = 累计现金余额+累计返点金余额+累计虚拟余额
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;总余额 = 累计充值金额-累计消耗金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
										</dl>
									</li>
									<li>
										<div class="titleBox">
											<h2>概况</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
										<dl>
											<dt>客单价分布占比</dt>
											<dd>
												定义：用户现金充值分布占比
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;按照用户现金充值情况，根据充值数值和分布阀值，计算在分布阀值范围内用户数占比。占比 = 某阀值范围内用户数/充值总用户数
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
										</dl>
									</li>
									<li>
										<div class="titleBox">
											<h2>充值情况</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
										<dl>
											<dt>虚拟充值</dt>
											<dd>
												定义：累计虚拟充值金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;从用户虚拟充值订单中统计累计虚拟充值金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
											
											<dt>返点金充值</dt>
											<dd>
												定义：累计返点金充值金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;从用户充值订单中统计累计返点金充值金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>现金充值</dt>
											<dd>
												定义：累计现金充值金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;从用户现金充值订单中统计累计现金充值金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
										</dl>
									</li>
									<li>
										<div class="titleBox">
											<h2>消耗情况</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
										<dl>
											<dt>虚拟消耗</dt>
											<dd>
												定义：累计虚拟消耗金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;从用户消耗详单表中统计累计虚拟消耗金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
											
											<dt>返点金消耗</dt>
											<dd>
												定义：累计返点金消耗金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;从用户消耗详单表中统计累计返点金消耗金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>现金消耗</dt>
											<dd>
												定义：累计现金消耗金额
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;从用户消耗详单表中统计累计现金消耗金额
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>有现金余额会员</dt>
											<dd>
												定义：有现金余额的会员数
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;现金余额>0
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									
											<dt>仅返点金余额会员</dt>
											<dd>
												定义：仅有返点余额的会员数
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;现金余额=0 and 返点金余额>0 and 虚拟余额=0
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
											
											<dt>仅虚拟余额会员</dt>
											<dd>
												定义：仅有虚拟月的会员
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;现金余额=0 and 返点金余额=0 and 虚拟余额>0
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
											<dt>有余额会员</dt>
											<dd>
												定义：有余额的会员数
												<br /> 统计规则：
												<br /> &nbsp;&nbsp;&nbsp;&nbsp;(现金余额+返点金余额+虚拟余额)>0
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
									       <dt>会员情况</dt>
											<dd>
												该图为混合图，描述的是会员变化情况，和不同类型会员的占比情况。											
												<br /> 统计规则：
												<br /> 折线是各个指标的会员数在时间维度的变化情况;
												<br /> 同心圆是整体会员从有余额和无余额（内圈）、开启关键词、未开启关键词和无余额（外圈）两个维度的占比情况
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
											<dt>top50消耗词图</dt>
											<dd>
												消耗最高的TOP50的关键词云。
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
											<dt>开启关键词数趋势</dt>
											<dd>
												各类关键词的变化情况											
												<br /> 统计规则：
												<br /> 户均关键词数：互通宝有余额开启关键数/互通宝有余额开启关键词用户数
												<br /> 总关键词数：互通宝有余额开启关键数
												<br /> 无效关键词数：开启关键词中无消耗的词数
												<br /> 计算频率：小时
												<br /> 数据来源：用户行为分析平台
												<br />
											</dd>
										</dl>
									</li>
								</ul>
							</div>
						</div>
					</div>
                    <!--运营数据-->
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">
                                                    运营数据
                                    </span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="explainBox">
                                <ul>
                                    <li>
                                        <div class="titleBox">
                                            <h2>产品运营数据</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
                                        <dl>
                                            <dt>询盘数量</dt>
                                            <dd>
                                                定义：全网所有页面点击询盘点的总量，去除公司内部IP产生的询盘量
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;询盘点：联系方式、QQ、询价留言、公司留言、加入采购单、立即订购、我要采购、微信、拨号按钮
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：用户行为分析平台
                                                <br />
                                            </dd>
                                            <dt>会员注册</dt>
                                            <dd>
                                                定义：会员自行注册量
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;会员发布，会员新增的注册会员
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：会员中心
                                                <br />
                                            </dd>
											<dt>P4P消耗</dt>
                                            <dd>
                                                定义： p4p广告产生的扣费。
                                                <br /> 计算方法：当天截止到当前P4P消耗金额
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：P4P系统
                                                <br />
                                            </dd>
                                            <!--三级指标-->
                                            <dt>地区分布</dt>
                                            <dd>
                                                定义： 当天截至到当前统计时间的中国各省份的数据分布情况
                                                <br /> 页面入口：点击《产品运营数据》中IP、PV、UV、询盘四个色块均可进入该页面
                                                <br /> 数据纬度：IP、PV、UV、询盘
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;IP: 当天截止到当前统计时间各地区的独立IP数(去除公司内部IP)
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;PV: 当天截止到当前统计时间各地区的流量(去除公司内部IP)
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;UV: 当天截止到当前统计时间各地区的独立访客数(去除公司内部IP；相同独立IP在同一地区最多只记录10个UV)
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;询盘: 当天截止到当前统计时间各地区点击询盘点的总量(去除公司内部IP)
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：用户行为分析平台
                                                <br />
                                            </dd>
                                            <dt>站点分布</dt>
                                            <dd>
                                                定义： 当天截至到当前统计时间的各站点的数据分布情况
                                                <br /> 页面入口：点击《产品运营数据》中IP、PV、UV、询盘四个色块均可进入该页面
                                                <br /> 数据纬度：IP、PV、UV、询盘
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;IP: 当天截止到当前时间各地区的独立IP数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;PV: 当天截止到当前时间各地区的流量
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;UV: 全网各地区的独立访客数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;询盘: 全网所有页面各地区点击询盘点的总量，去除公司内部IP产生的询盘量
                                                <br /> 站点：JS(移动设备端)、M(移动设备端)、PC(电脑端)、WX(微信端)、ZOL(中关村)
                                                <br /> 询盘: 全网所有页面各地区点击询盘点的总量，去除公司内部IP产生的询盘量
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;域名分布：点击某个站点，即可查看当天截至到当前统计时间的各域名分布情况。
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;域名划分规则：根据当前访问地址进行匹配。
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;直接来源分布：点击某个站点内的某个域名，即可查看当天截至到当前统计时间的各直接来源分布情况。
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;直接来源：seo、sem、inner、direct、otherouter、other
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sem：访前地址为 各大搜索引擎的地址，当前地址包含市场投放标识的访问。
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;seo：访前地址为 各大搜索引擎的地址的访问。
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inner：访前地址为 公司产品 的访问
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;direct：没有访前地址的，直接打开公司页面的访问
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;otherouter：无法判断的外网访前地址
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;other：均不是以上信息的访问
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：用户行为分析平台
                                                <br />
                                            </dd>

                                        </dl>
                                    </li>
									<li>
                                        <div class="titleBox">
                                            <h2>P4P消耗数据</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
										<dl>
											<dt>纯P4P消耗</dt>
                                            <dd>
                                                定义：所有消耗中关键词不包含"阿拉丁竞投"的消耗
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：P4P数据
                                              <br />
                                            </dd>
											<dt>全网定投消耗</dt>
                                            <dd>
                                                定义：关键词中包含"阿拉丁竞投"的消耗
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：P4P数据
                                              <br />
                                            </dd>
											<dt>P4P关键词总量</dt>
                                            <dd>
                                                定义：P4P自建关键词总量
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：P4P数据
                                              <br />
                                            </dd>
                                            <dt>P4P在投词量</dt>
                                            <dd>
                                                定义：P4P自建关键词，推广正常，状态开启，停权复权标识正常，并且有余额的关键词
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：P4P数据
                                                <br />
                                            </dd>
                                            <dt>P4P竞价词</dt>
                                            <dd>
                                              定义：P4P在投词，且两个人以上出价的为竞价词
                                              <br /> 计算频率：每小时
                                              <br /> 数据来源：P4P数据
                                              <br />
                                            </dd>
                                            <dt>P4P消耗</dt>
                                            <dd>
                                                定义： p4p广告产生的扣费。
                                                <br /> 计算方法：当天截止到当前P4P消耗金额
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：P4P系统
                                                <br />
                                            </dd>
											<dt>全网定投累计总消耗</dt>
                                            <dd>
                                                定义： 截止前一天的累计总消耗金额。
                                                <br /> 计算方法：截止前一天的累计总消耗金额
                                                <br /> 计算频率：每天
                                                <br /> 数据来源：P4P数据库
                                                <br />
                                            </dd>
                                            <dt>全网定投周度累计消耗</dt>
                                            <dd>
                                                定义： 截止前一天的当周累计总消耗金额。
                                                <br /> 计算方法：截止前一天的当周累计总消耗金额
                                                <br /> 计算频率：每天
                                                <br /> 数据来源：P4P数据库
                                                <br />
                                            </dd>
                                            <dt>全网定投周度竞拍过的用户数</dt>
                                            <dd>
                                                定义： 截止前一天的当周竞拍过的用户数。
                                                <br /> 计算方法：截止前一天的当周竞拍过的用户数（用户排重统计）
                                                <br /> 计算频率：每天
                                                <br /> 数据来源：P4P数据库
                                                <br />
                                            </dd>
                                            <dt>全网定投周度竞拍过的关键词数</dt>
                                            <dd>
                                                定义： 截止前一天的当周竞拍过的关键词数。
                                                <br /> 计算方法：截止前一天的当周竞拍过的关键词数（关键词排重统计）
                                                <br /> 计算频率：每天
                                                <br /> 数据来源：P4P数据库
                                                <br />
                                            </dd>
                                            <dt>开通全网定投累计总用户数</dt>
                                            <dd>
                                                定义： 截止前一天的有过充值的用户数。
                                                <br /> 计算方法：截止前一天的充值累计金额  >= 10000 的用户数，用户排重统计，充值金额为 现金充值+返点金额
                                                <br /> 计算频率：每天
                                                <br /> 数据来源：P4P数据库
                                                <br />
                                            </dd>
										</dl>
									</li>
								</ul>
                            </div>
                        </div>
                    </div>
                    <!--战斗力-->
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">
                                                    战斗力
                                                </span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="explainBox">
                                <ul>
                                    <li>
                                        <div class="titleBox">
                                            <h2>战斗力</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
                                        <dl>
                                            <!-- <dt>百家文章数</dt>
                                            <dd>
                                                定义：百度百家10个账号的已发布的文章总数
                                                <br /> 计算频率：一小时抓取一次
                                                <br /> 数据来源：抓取百度百家号后台数据
                                                <br />
                                            </dd> -->
                                            <dt>市场部发稿量</dt>
                                            <dd>
                                                定义：外部媒体发布稿件的数量
                                                <br /> 计算方法：每周外部发稿数、外部链接数、自媒体发稿链接总和
                                                <br /> 计算频率：每周
                                                <br /> 数据来源：市场部手工提供
                                                <br />
                                            </dd>
                                            <dt>渠道拜访量</dt>
                                            <dd>
                                                定义：填写了联系小计的面访客户
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：渠道CRM
                                                <br />
                                            </dd>
                                            <dt>人均在线时长</dt>
                                            <dd>
                                                定义：电销销售人员人均在线时间长度
                                                <br /> 计算方法：战斗力人数在线总时长&nbsp;/&nbsp;战斗力中有通话人数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：CRM(天润)
                                                <br />
                                            </dd>
                                            <dt>人均有效通话次数</dt>
                                            <dd>
                                                定义：销售人员人均打电话的有效次数
                                                <br /> 统计规则：有效通话(通话时长大于等于1分钟)且有小计
                                                <br /> 计算方法：战斗力人数当天累计有效通话次数&nbsp;/&nbsp;战斗力中有通话人数
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：CRM(天润)
                                                <br />
                                            </dd>
                                            <dt>销售预测</dt>
                                            <dd>
                                                定义：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;预估值：电销、渠道周度、月度预估销售金额
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp; 预算值：由财务部提供的电销、渠道月度预算销售金额
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;实际值：电销、渠道周度实际销售金额
                                                <br /> 计算频率：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;预估值：每周
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;实际值：每小时
                                                <br /> 数据来源：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;预估值：电销、渠道部门
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;实际值：订单系统
                                                <br />
                                            </dd>
                                            <dt>销售业绩趋势</dt>
                                            <dd>
                                                定义：电销、渠道每天的实际销售金额
                                                <br /> 计算频率：每小时
                                                <br /> 数据来源：订单系统
                                                <br />
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!--收费会员-->
                    <div class="panel panel-default hour-trend">
                        <nav class="navbar navbar-default" role="navigation">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <span class="navbar-brand">
                                                    付费会员
                                    </span>
                                </div>
                            </div>
                        </nav>
                        <div class="panel-body tab-content mTop20">
                            <div class="explainBox">
                                <ul>
                                    <li>
                                        <div class="titleBox">
                                            <h2>付费会员</h2><a class="eHide" v-on:click="toggleDescription($event)">收起<s></s></a></div>
                                        <dl>
                                            <dt>Leads数</dt>
                                            <dd>
                                                定义：进入电销CRM库的所有可分配客户资源
                                                <br /> 数据来源：CRM
                                                <br />
                                            </dd>
                                            <dt>Leads转化率</dt>
                                            <dd>
                                                定义：每月实际分配的Leads数转化为收费客户的转化率
                                                <br /> 计算方法：新签用户&nbsp;/&nbsp;实际分配的Leads数
                                                <br /> 统计规则：网注，网申3个月，其他6个月的转化情况
                                                <br /> 统计频率：天
                                                <br /> 数据来源：CRM
                                                <br />
                                            </dd>
                                            <dt>3类客户数</dt>
                                            <dd>
                                                定义：根据签单成熟度将客户由深至浅分为一类、二类、三类等。三类是指未签单但条件好，意向高，客户质量高的客户
                                              <br /> 统计方法：每天新增3类客户数及每天3类客户数库存量
                                                <br /> 数据维度：三类客户数分为电销三类客户数、渠道三类客户数及三类客户数(电销和渠道三类客户数之和)
                                                <br /> 数据来源：CRM
                                                <br />
                                            </dd>
                                            <dt>交易付费会员数</dt>
                                            <dd>
                                                定义：付费会员中交易完成的会员数
                                                <br /> 统计规则：在线付款，或者线下闭环，且状态为完成的
                                                <br /> 数据来源：交易系统
                                                <br />
                                            </dd>
                                            <dt>卖家DAU</dt>
                                            <dd>
                                                定义：每日活跃卖家数
                                                <br /> 计算规则：当天登录商务中心的会员、未登录商务中心但有登录cookie记录访客的会员、使用微商城微信公众号的会员，三类会员数据排重汇总之和
                                                <br /> 数据来源：用户行为分析系统，商务中心
                                                <br />
                                            </dd>
                                            <dt>客户满意度</dt>
                                            <dd>
                                                定义：最新收费会员的级别分类，按体系拆分
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;类别： A、B、C、D类
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;天度数据：截止到前一天客户满意度级别分类结果
                                                <br /> 数据来源：商务中心
                                                <br />
                                            </dd>
                                            <dt>产品用户贡献</dt>
                                            <dd>
                                                定义：各销售产品客单价
                                                <br /> 计算规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;按财务拆分：营销中心、渠道按财务拆分产品线 &nbsp本月订单金额/客户数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;按销售订单拆分：营销中心、渠道按下订单产品线拆分 &nbsp本月订单金额/客户数
                                                <br /> 统计规则：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;商业产品：基本产品，增值产品，深度运营，流量宝，初级运营
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;客户：按公司名称排重
                                                <br /> 数据来源：订单
                                                <br /> 统计周期：月度，年度
                                                <br /> 计算频率：天
                                                <br />
                                            </dd>
                                            <dt>增值产品续签率</dt>
                                            <dd>
                                                定义：增值产品续签率&nbsp;=&nbsp;已续签增值产品客户数&nbsp;/&nbsp;应续签增值产品客户数
                                                <br /> 统计方法：
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;电销、渠道、行业、电商(电销、渠道)增值产品续签率&nbsp;=&nbsp;体系增值产品已续签客户数&nbsp;/&nbsp;体系增值产品应续签客户数
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;已续签客户：2017年到期并续签且关键词的到期日期在2018年的增值产品用户
                                                <br /> &nbsp;&nbsp;&nbsp;&nbsp;应续签客户：2017年到期的增值产品用户
                                                <br /> 数据来源：订单
                                                <br /> 统计周期：年度
                                                <br /> 计算频率：天
                                                <br />
                                            </dd>
                                            <dt>库存Leads总数</dt>
                                            <dd>
                                                定义：CRM潜在库中检测通过的所有待分配客户资源
                                                <br /> 统计规则：截止当前时刻所有的库存leads
                                                <br /> 统计频率：每小时
                                                <br /> 数据来源：CRM
                                            </dd>
                                            <dt>库存Leads来源</dt>
                                            <dd>
                                               统计规则：按照来源统计截止当前时刻所有的库存leads
                                                <br /> 数据来源：网上注册、阿里收费、网上申请、重走流程、阿里免费、百度抓取、扩展抓取、专业网站抓取
                                                <br /> 统计频率：每小时
                                                <br /> 数据来源：CRM
                                            </dd>
                                            <dt>新增Leads数</dt>
                                            <dd>
                                                定义：CRM潜在库中检测通过且新增的待分配客户资源
                                                <br /> 统计规则：统计每天所有的库存leads
                                                <br /> 统计频率：每小时
                                                <br /> 数据来源：CRM
                                            </dd>
                                            <dt>新增Leads来源</dt>
                                            <dd>
                                                统计规则：按照来源统计每天所有的新增leads
                                                <br /> 数据来源：网上注册、阿里收费、网上申请、重走流程、阿里免费、百度抓取、扩展抓取、专业网站抓取
                                                <br /> 统计频率：每小时
                                                <br /> 数据来源：CRM
                                            </dd>
                                            <dt>已分配Leads数</dt>
                                            <dd>
                                                定义：CRM潜在库中检测通过且分配到销售的客户资源
                                                <br /> 统计频率：每小时
                                                <br /> 数据来源：CRM
                                            </dd>
                                            <dt>已分配Leads来源</dt>
                                            <dd>
                                                统计规则：按照来源统计每天所有的已分配leads
                                                <br /> 数据来源：网上注册、阿里收费、网上申请、重走流程、阿里免费、百度抓取、扩展抓取、专业网站抓取
                                                <br /> 统计频率：每小时
                                                <br /> 数据来源：CRM
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'datapanel-readme',
    methods: {

        /**
         * [toggleDescription $event]
         */
        toggleDescription($event) {
            var target = $event.target || $event.srcElement,
                wrap = target.parentNode.nextElementSibling;

            if (wrap.style.display == 'none') {
                wrap.style.display = 'block';
                target.innerHTML = '收起<s></s>';
                target.className = 'eHide';
            } else {
                wrap.style.display = 'none';
                target.innerHTML = '展开<s></s>';
                target.className = 'eShow';
            }
        }
    }
}
</script>
