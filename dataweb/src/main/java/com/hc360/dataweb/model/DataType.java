package com.hc360.dataweb.model;

/**
 * Created by home on 2017/2/6.
 */
public enum DataType {
    IP("IP", 1, ""),
    PV("PV", 2, ""),
    UV("UV", 3, ""),
    /*直销收费会员*/
    DXFEEUSER("直销", 4, ""),
    /*渠道收费会员**/
    QDFEEUSER("渠道", 5, ""),
    /*行业收费会员*/
    HYFEEUSER("行业", 6, ""),
    /*自助销售收费会员*/
    SELFFEEUSER("自助销售", 17, ""),
    /*加盟商收费会员 : 慧聪控股的公司，主要是产业带在使用*/
    JMFEEUSER("加盟商", 173, ""),
    /*总收费会员数*/
    FEEUSERTOTAL("MMT会员", 4001, ""),
    /**
     * 友客的会员*
     */
    YKUSER("友客会员", 350, ""),
    YKDXUSER("直销", 351, ""),
    YKQDUSER("渠道", 352, ""),
    YKHYUSER("行业", 353, ""),

    /*互通宝的会员***/
    P4PBALANCEUSER("P4P有余额会员", 346, ""),
    P4PDXUSER("直销", 347, ""),
    P4PQDUSER("渠道", 348, ""),
    P4PHYUSER("行业", 349, ""),

    /*直销转正人数**/
    DXCOVENEMPLOYEE("dxcovenemployee", 7, ""),
    /*直销应在职人数（离职+在职）**/
    DXSHOULDEMPLOYEE("dxshouldemployee", 8, ""),
    /*直销离职人数*/
    DXLEAVEEMPLOYEE("dxleaveemployee", 9, ""),

    /*渠道转正人数**/
    QDCOVENEMPLOYEE("qdcovenemployee", 10, ""),
    /*渠道应在职人数（离职+在职）**/
    QDSHOULDEMPLOYEE("qdshouldemployee", 11, ""),
    /*渠道离职人数*/
    QDLEAVEEMPLOYEE("qdleaveemployee", 12, ""),
    /*渠道销售人数**/
    QDSALE("qdsale", 336, ""),
    /*渠道管理层人数**/
    QDMANAGER("qdmanager", 337, ""),
    /*职能离职人数 **/
    ZNSHOULDEMPLOYEE("znshouldemployee", 15, ""),
    /*职能应在职人数（离职+在职）*/
    ZNLEAVEEMPLOYEE("znleaveemployee", 16, ""),
    /*  职能人员在岗人数*/
    ZNONGUARDEMPLOYEE("znonguardemployee", 13, ""),
    /*职能人员编制人数*/
    ZNOBUILDEMPLOYEE("znbuildemployee", 14, ""),

    /*直销新兵连人数*/
    DX_RECRUIT_NOT_POSITIVE("dxRecruitNotPositive", 291, ""),
    /*直销专员未转正人数*/
    DX_ATTACHE_NOT_POSITIVE("dxAttacheNotPositive", 292, ""),
    /*直销专员总人数*/
    DX_TOTAL_COUNT("dxTotalCount", 293, ""),
    /*直销管理层人数*/
    DX_MANAGER_COUNT("dxManagerCount", 294, ""),
    /*直销职能人数*/
    DX_OFFICER_COUNT("dxOfficerCount", 313, ""),
    /*直销总人数*/
    DX_ALL_COUNT("dxAllCount", 314, ""),

    /*有效买家*/
    VALIDBUYER("有效买家", 23, ""),
    /*询盘总量*/
    INQUIRYCOUNT("询盘数量", 22, ""),
    /*卖家DAU*/
    DAUTYPE("卖家DAU", 28, ""),
    /*会员注册*/
    MEMBERREGISTER("会员注册", 44, ""),
    /*粉丝数量增量*/
    FANSINCREMENT("粉丝数量", 4501, ""),
    /*粉丝数量总和(查询色块与累计)*/
    FANSCOUNT("粉丝数量", 545, ""),
    /*P4P消耗金额*/
    P4PCONSUMPTION("P4P消耗", 46, ""),
    P4PCONSUMPTION_CASH("现金消耗", 4601, ""),
    P4PCONSUMPTION_VIRTUAL("虚拟消耗", 4602, ""),

    P4PCONSUMPTION_CASH_TOTAL("累计现金消耗", 4603, ""),

    P4PCONSUMPTION_VIRTUAL_TOTAL("累计虚拟消耗", 4604, ""),
    /*历史累计P4P现金消耗(累计)*/
    P4PCONSUMPTIONTOTAL("P4P消耗", 4605, ""),
    /*人均在线时长*/
    PERCAPITAONLINETIME("人均在线时长", 49, ""),

    /*直销新签人均在线时长*/
    XQPERCAPITAONLINETIME("猎鹰人均在线时长", 328, ""),

    /*直销增值人均在线时长*/
    ZQPERCAPITAONLINETIME("大白鲨人均在线时长", 329, ""),

    /*渠道拜访量*/
    CHANNELVISITCOUNT("渠道拜访量", 54, ""),
    /*微信绑定用户数(色块,今天,最近七天,最近一个月)*/
    WECHATBINDUSERCOUNT("微信绑定用户数", 55, ""),
    /*微信绑定用户总数*/
    WECHATBINDUSERTOTAL("微信绑定用户数", 5501, ""),
    /*粘性买家*/
    VISCIDITYBUYER("粘性买家", 56, ""),
    /*百家文章数 */
    VARIOUS_FAMILIES_ARTICLE("百家文章数", 71, ""),
    /*百家文章日新增*/
    EVERYDAY_NEW("每日新增", 208, ""),
    /*百家文章日收入*/
    EVERYDAY_INCOME("每日收入", 209, ""),

    /*外部发稿数*/
    EXTERNALSENDDRAFT("外部发稿数", 78, ""),
    /*外部链接数*/
    EXTERNALLINK("外部链接数", 79, ""),
    /*自媒体发稿链接*/
    WEMEDIALINK("自媒体发稿链接", 80, ""),
    /*有效通话次数*/
    VALIDCALLNUMBER("全部销售", 74, ""),
    /*直销有效通话次数*/
    DXVALIDCALLNUMBER("直销销售", 50, ""),
    /*渠道有效通话次数*/
    QDVALIDCALLNUMBER("渠道销售", 52, ""),
    /*直销新签人均有效通话次数 **/
    XQDXVALIDCALLNUMBER("猎鹰直销销售", 330, ""),
    /*直销增值人均有效通话次数  **/
    ZQDXVALIDCALLNUMBER("大白鲨直销销售", 331, ""),
    /*LEADS转化率*/
    LEADSTRANSFORM("Leads转化率", 98, ""),

    /*3类客户数*/
    /*3类客户数每天/每小时的量*/
    USER3CLASS("3类客户数", 62, ""),
    /*当天累积到统计时间直销三类客户数*/
    DXUSER3CLASS("直销", 59, ""),
    /*当天累积到统计时间渠道三类客户数*/
    QDUSER3CLASS("渠道", 60, ""),
    /*3类客户数从开始累积到现在的总量*/
    USER3CLASSTOTAL("3类客户数", 205, ""),
    /*从开始累积到现在直销三类客户数*/
    DXUSER3CLASSTOTAL("直销", 203, ""),
    DXXQWADDUSER3CLASSTOTAL("直销猎鹰", 57, ""),
    DXXXWADDUSER3CLASSTOTAL("直销续签", 58, ""),
    DXNEWUSER3CLASSTOTAL("直销猎鹰", 295, ""),
    DXXQUSER3CLASSTOTAL("直销续签", 296, ""),
    /*从开始累积到现在渠道三类客户数*/
    QDUSER3CLASSTOTAL("渠道", 204, ""),

    /*付费会员交易率*/
    FEEUSERTRANSACTION("交易付费会员数", 172, ""),
    /*标王续签率-行业*/
    CHAMPIONHY("行业", 63, ""),
    /*标王续签率-直销*/
    CHAMPIONDX("直销", 64, ""),
    /*标王续签率-渠道*/
    CHAMPIONQD("渠道", 65, ""),
    /*标王续签率-电商*/
    CHAMPIONDS("电商", 66, ""),
    /*买卖通续签率-行业*/
    MMTHY("行业", 67, ""),
    /*买卖通续签率-直销*/
    MMTDX("直销", 68, ""),
    /*买卖通续签率-渠道*/
    MMTQD("渠道", 69, ""),
    /*买卖通续签率-电商*/
    MMTDS("电商", 70, ""),

    /*直销-A类客户数*/
    DXACLASS("直销-A类客户数", 82, ""),
    /*直销-B类客户数*/
    DXBCLASS("直销-B类客户数", 83, ""),
    /*直销-C类客户数*/
    DXCCLASS("直销-C类客户数", 84, ""),
    /*直销-D类客户数*/
    DXDCLASS("直销-D类客户数", 85, ""),
    /*渠道-A类客户数*/
    QDACLASS("渠道-A类客户数", 86, ""),
    /*渠道-B类客户数*/
    QDBCLASS("渠道-B类客户数", 87, ""),
    /*渠道-C类客户数*/
    QDCCLASS("渠道-C类客户数", 88, ""),
    /*渠道-D类客户数*/
    QDDCLASS("渠道-D类客户数", 89, ""),
    /*行业-A类客户数*/
    HYACLASS("行业-A类客户数", 90, ""),
    /*行业-B类客户数*/
    HYBCLASS("行业-B类客户数", 91, ""),
    /*行业-C类客户数*/
    HYCCLASS("行业-C类客户数", 92, ""),
    /*行业-D类客户数*/
    HYDCLASS("行业-D类客户数", 93, ""),
    /*自助销售-A类客户数*/
    ZZXSACLASS("自助销售-A类客户数", 94, ""),
    /*自助销售-B类客户数*/
    ZZXSBCLASS("自助销售-B类客户数", 95, ""),
    /*自助销售-C类客户数*/
    ZZXSCCLASS("自助销售-C类客户数", 96, ""),
    /*自助销售-D类客户数*/
    ZZXSDCLASS("自助销售-D类客户数", 97, ""),
    /*加盟商-A类客户数*/
    JMSHACLASS("加盟商-A类客户数", 174, ""),
    /*加盟商-B类客户数*/
    JMSHBCLASS("加盟商-B类客户数", 175, ""),
    /*加盟商-C类客户数*/
    JMSHCCLASS("加盟商-C类客户数", 176, ""),
    /*加盟商-D类客户数*/
    JMSHDCLASS("加盟商-D类客户数", 177, ""),

    /*产品用户贡献(按财务拆分)*/
    FINANCE_DX_THIRDPARTNAR("dxthirdpartnar", 100, ""),
    FINANCE_DX_ELSE("dxelse", 101, ""),
    FINANCE_DX_FLOW("dxflow", 102, ""),
    FINANCE_DX_MMT("dxmmt", 103, ""),
    FINANCE_DX_BUSINESS("dxbusiness", 104, ""),
    FINANCE_DX_CHAMPION("dxchampion", 105, ""),
    FINANCE_DX_THIRDPARTNAR_NUM("dxthirdpartnarnum", 106, ""),
    FINANCE_DX_ELSE_NUM("dxelsenum", 107, ""),
    FINANCE_DX_FLOW_NUM("dxflownum", 108, ""),
    FINANCE_DX_MMT_NUM("dxmmtnum", 109, ""),
    FINANCE_DX_BUSINESS_NUM("dxbusinessnum", 110, ""),
    FINANCE_DX_CHAMPION_NUM("dxchampionnum", 111, ""),
    FINANCE_QD_THIRDPARTNAR("thirdpartnar", 124, ""),
    FINANCE_QD_ELSE("qdelse", 125, ""),
    FINANCE_QD_FLOW("qdflow", 126, ""),
    FINANCE_QD_MMT("qdmmt", 127, ""),
    FINANCE_QD_BUSINESS("qdbusiness", 128, ""),
    FINANCE_QD_CHAMPION("qdchampion", 129, ""),
    FINANCE_QD_THIRDPARTNAR_NUM("thirdpartnarnum", 130, ""),
    FINANCE_QD_ELSE_NUM("qdelsenum", 131, ""),
    FINANCE_QD_FLOW_NUM("qdflownum", 132, ""),
    FINANCE_QD_MMT_NUM("qdmmtnum", 133, ""),
    FINANCE_QD_BUSINESS_NUM("qdbusinessnum", 134, ""),
    FINANCE_QD_CHAMPION_NUM("qdchampionnum", 135, ""),

    /*产品用户贡献(按销售订单拆分)*/
    ORDER_DX_THIRDPARTNAR("dxthirdpartnar", 210, ""),
    ORDER_DX_ELSE("dxelse", 211, ""),
    ORDER_DX_FLOW("dxflow", 212, ""),
    ORDER_DX_MMT("dxmmt", 213, ""),
    ORDER_DX_BUSINESS("dxbusiness", 214, ""),
    ORDER_DX_CHAMPION("dxchampion", 215, ""),
    ORDER_DX_THIRDPARTNAR_NUM("dxthirdpartnarnum", 216, ""),
    ORDER_DX_ELSE_NUM("dxelsenum", 217, ""),
    ORDER_DX_FLOW_NUM("dxflownum", 218, ""),
    ORDER_DX_MMT_NUM("dxmmtnum", 219, ""),
    ORDER_DX_BUSINESS_NUM("dxbusinessnum", 220, ""),
    ORDER_DX_CHAMPION_NUM("dxchampionnum", 221, ""),
    ORDER_QD_THIRDPARTNAR("thirdpartnar", 234, ""),
    ORDER_QD_ELSE("qdelse", 235, ""),
    ORDER_QD_FLOW("qdflow", 236, ""),
    ORDER_QD_MMT("qdmmt", 237, ""),
    ORDER_QD_BUSINESS("qdbusiness", 238, ""),
    ORDER_QD_CHAMPION("qdchampion", 239, ""),
    ORDER_QD_THIRDPARTNAR_NUM("thirdpartnarnum", 240, ""),
    ORDER_QD_ELSE_NUM("qdelsenum", 241, ""),
    ORDER_QD_FLOW_NUM("qdflownum", 242, ""),
    ORDER_QD_MMT_NUM("qdmmtnum", 243, ""),
    ORDER_QD_BUSINESS_NUM("qdbusinessnum", 244, ""),
    ORDER_QD_CHAMPION_NUM("qdchampionnum", 245, ""),
    /*产品用户贡献*/

    /*销售业绩*/
    /*直销销售业绩实际值*/
    DXTURNOVERZL("直销销售业绩实际值增量", 178, ""),
    /*直销新签销售业绩实际值**/
    XQDXTURNOVERZL("直销猎鹰实际值", 332, ""),
    /*直销增值销售业绩实际值**/
    ZQDXTURNOVERZL("直销大白鲨实际值", 333, ""),

    /*直销销售业绩实际值*/
    DXTURNOVELJ("直销整体实际值", 179, ""),
    /*直销销售业绩预估值*/
    DXTURNOVEYG("直销整体预估值", 180, ""),
    /*直销新签销售业绩预估值***/
    XQDXTURNOVEYG("直销猎鹰预估值", 334, ""),
    /*直销增值销售业绩预估值**/
    ZQDXTURNOVEYG("直销大白鲨预估值", 335, ""),

    /*直销销售业绩完成率*/
    DXTURNOVERWC("直销销售业绩完成率", 181, ""),
    /*渠道销售业绩实际值*/
    QDTURNOVERZL("渠道销售业绩实际值增量", 182, ""),
    /*渠道销售业绩实际值*/
    QDTURNOVERLJ("实际值", 183, ""),
    /*渠道销售业绩预估值*/
    QDTURNOVERYG("预估值", 184, ""),
    /*渠道销售业绩完成率*/
    QDTURNOVERWC("渠道销售业绩完成率", 185, ""),
    /*直销月销售业绩预算值*/
    DXTURNOVERYS("直销整体预算值", 206, ""),
    /*渠道月销售业绩预算值*/
    QDTURNOVERYS("预算值", 207, ""),

    /*直销渠道整体销售实际值*/
    DXQDSJ("销售整体实际值", 345, ""),
    /*直销渠道整体销售预算值*/
    DXQDYS("销售整体预算值", 343, ""),
    /*直销渠道整体销售预估值*/
    DXQDYG("销售整体预估值", 344, ""),
    /*直销新签销售预算值*/
    DXXQYS("直销猎鹰预算值", 341, ""),
    /*直销渠道整体销售预估值*/
    DXZZYS("直销大白鲨预算值", 342, ""),

    /*销售业绩*/

    /*leads相关*/
    /*Leads数*/
    STOCK_LEADS("库存Leads量", 290, ""),
    LEADS("新增Leads数", 99, ""),
    /*可分配leads数来源为-网上注册  */
    K_REGISTER_ONLINE("网上注册", 186, ""),
    /*可分配LEADS数来源为-阿里收费  */
    K_ALIBABA_CHARGE("阿里收费", 187, ""),
    /*可分配LEADS数来源为-网上申请  */
    K_APPLY_ONLINE("网上申请", 188, ""),
    /*可分配LEADS数来源为-重走流程分配*/
    K_PROCESS("重走流程分配", 189, ""),
    /*可分配LEADS数来源为-阿里免费  */
    K_ALIBABA_FREE("阿里免费", 190, ""),
    /*可分配LEADS数来源为-百度抓取  */
    K_BAIDU_GRAB("百度抓取", 191, ""),
    /*可分配LEADS数来源为-扩展抓取  */
    K_EXTEND_GRAB("扩展抓取", 192, ""),
    /*可分配LEADS数来源为-专业网站抓取*/
    K_SPECIALWEBSITE_GRAB("专业网站抓取", 193, ""),

    /*leads转化率来源为-网上注册**/
    Z_REGISTER_ONLINE("网上注册", 297, ""),
    /*leads转化率来源为-阿里收费  */
    Z_ALIBABA_CHARGE("阿里收费", 298, ""),
    /*leads转化率来源为-网上申请  */
    Z_APPLY_ONLINE("网上申请", 299, ""),
    /*leads转化率来源为-重走流程分配*/
    Z_PROCESS("重走流程分配", 300, ""),
    /*leads转化率来源为-阿里免费  */
    Z_ALIBABA_FREE("阿里免费", 301, ""),
    /*leads转化率来源为-百度抓取  */
    Z_BAIDU_GRAB("百度抓取", 302, ""),
    /*leads转化率来源为-扩展抓取  */
    Z_EXTEND_GRAB("扩展抓取", 303, ""),
    /*leads转化率来源为-专业网站抓取*/
    Z_SPECIALWEBSITE_GRAB("专业网站抓取", 304, ""),
    /*leads转化率来源为-自行录入*/
    Z_MANNUL_INPUT("自行录入", 311, ""),
    /*leads转化率来源为-来电咨询*/
    Z_PHONE("来电咨询", 312, ""),

    /*可分配存量leads数来源为-网上注册*/
    KC_REGISTER_ONLINE("网上注册", 282, ""),
    /*可分配存量lleads数来源为-阿里收费*/
    KC_ALIBABA_CHARGE("阿里收费", 283, ""),
    /*可分配存量lleads数来源为-网上申请*/
    KC_APPLY_ONLINE("网上申请", 284, ""),
    /*可分配存量lleads数来源为-重走流程分配*/
    KC_PROCESS("重走流程分配", 285, ""),
    /*可分配存量lleads数来源为-阿里免费*/
    KC_ALIBABA_FREE("阿里免费", 286, ""),
    /*可分配存量lleads数来源为-百度抓取*/
    KC_BAIDU_GRAB("百度抓取", 287, ""),
    /*可分配存量lleads数来源为-扩展抓取*/
    KC_EXTEND_GRAB("扩展抓取", 288, ""),
    /*可分配存量lleads数来源为-专业网站抓取*/
    KC_SPECIALWEBSITE_GRAB("专业网站抓取", 289, ""),

    /*305	互通宝关键词总量*/
    P4P_KEY_SUM("P4P关键词总量", 305, ""),

    P4P_KEY_ZT_COUNT("P4P在投词量", 306, ""),

    P4P_KEY_TOP50_PEOPLE("竞价词top50-人数量", 307, ""),

    P4P_KEY_TOP50_PRICE("竞价词top50-价格", 308, ""),

    /*309	百度联盟日收入*/
    BAIDU_LM_DAY("百度联盟日收入", 309, ""),
    /*310	百度联盟月收入*/
    BAIDU_LM_MONTH("月实际收入", 310, ""),
    /*315	百度联盟月收入预算*/
    BAIDU_LM_MONTH_E("月收入预算", 315, ""),

    /*已分配LEADS数来源为-网上注册  */
    YREGISTERONLINE("网上注册", 194, ""),
    /*/*已分配LEADS数来源为-阿里收费  */
    YALIBABACHARGE("阿里收费", 195, ""),
    /*/*已分配LEADS数来源为-网上申请  */
    YAPPLYONLINE("网上申请", 196, ""),
    /*/*已分配LEADS数来源为-重走流程分配*/
    YPROCESS("重走流程分配", 197, ""),
    /*/*已分配LEADS数来源为-阿里免费  */
    YALIBABAFREE("阿里免费", 198, ""),
    /*/*已分配LEADS数来源为-百度抓取  */
    YBAIDUGRAB("百度抓取", 199, ""),
    /*/*已分配LEADS数来源为-扩展抓取  */
    YEXTENDGRAB("扩展抓取", 200, ""),
    /*/*已分配LEADS数来源为-专业网站抓取*/
    YSPECIALWEBSITEGRAB("专业网站抓取", 201, ""),

    P4PCPC("P4PCPC", 316, ""),

    /*已分配LEADS总数*/
    YLEADSCOUNT("已分配Leads数", 202, ""),
    /*leads相关*/

    /*全网定投相关*/
    P4P_QWDT_TOTAL("累计总消耗", 317, ""),
    P4P_QWDT_WEEK_TOTAL("周度消耗", 318, ""),
    P4P_QWDT_WEEK_JP("竞拍用户数", 319, ""),
    P4P_QWDT_WEEK_JP_KEY("竞拍关键词数", 320, ""),
    P4P_QWDT_USER("符合定投用户数", 321, ""),
    P4P_QWDT_USER_TOTAL("累计定投总用户数", 322, ""),
    /*全网定投相关*/

    /*纯P4P消耗*/
    P4P_CONSUMPTION_HOUR("纯P4P消耗", 323, ""),
    P4P_CONSUMPTION_DAY("纯P4P消耗", 324, ""),
    P4P_CONSUMPTION_TOTAL("纯P4P消耗", 325, ""),
    P4P_QWDT_HOUR("全网定投消耗", 326, ""),
    P4P_QWDT_DAY("全网定投消耗", 327, ""),
    /**
     * 互通宝*
     */
    P4PXIANJINCHARGETOTAL("现金充值", 354, "元"),
    P4PFANDIANJINCHARGETOTAL("返点金充值", 355, "元"),
    P4PXUNICHARGETOTAL("虚拟充值", 356, "元"),
    P4PALLCHARGETOTAL("累计充值金额", 357, "元"),


    P4PXIANJINEXPENDTOTAL("现金消耗", 358, "元"),
    P4PFANDIANJINEXPENDTOTAL("返点金消耗", 359, "元"),
    P4PXUNIEXPENDTOTAL("虚拟消耗", 360, "元"),
    P4PALLEXPENDTOTAL("累计消耗金额", 361, "元"),

    P4PXIANJINBALANCE("现金余额", 362, "元"),
    P4PFANDIANJINBALANCE("返点金余额", 363, "元"),
    P4PXUNIBALANCE("虚拟余额", 364, "元"),
    P4PALLBALANCE("余额", 365, "元"),

    P4PUSER("累计会员数", 366, "个"),
    P4PNOBALANCEUSERS("无余额会员数", 367, "个"),

    P4PXIANJINBALANCEUSERS("有现金余额会员", 368, "个"),
    P4PFANDIANJINBALANCEUSERS("仅返点金余额会员", 369, "个"),
    P4PXUNIBALANCEUSERS("仅虚拟余额会员", 370, "个"),
    P4PBALANCEKEYUSERS("有余额开启关键词用户数", 371, "个"),
    P4PBALANCENOKEYUSERS("有余额未开启关键词用户数", 372, "个"),


    P4PBALANCEKEY("总关键词数", 373, "个"),
    P4PBALANCENOKEY("无效关键词数", 374, "个"),
    P4PAVGKEYS("户均关键词数", 375, "个"),
    P4PTOP50KEY("top50消耗关键词", 376, "个"),//单独存到一个表格里面

    P4PPRICE("客单价", 377, "元"),
    P4PPRICEPERCENT("客单价分布指标", 378, ""),

    //友客的信息
    YOUKEPRICE("客单价", 382, "元"),
    YOUKEUSER("用户数", 380, "个"),
    YOUKETOTOALFEE("销售额", 381, "元"),
    //订阅关键词总数
    YOUKEUSERKEYNSUM("总数", 384, "个"),
    //友客互均订阅关键词数
    YOUKEUSERKEYAVG("户均数", 385, "个"),

    //友客总线索数
    YOUKEXIANSUOSUM("线索总数", 387, "个"),
    YOUKEXIANSUOSEESUM("被查看的线索数", 388, "个"),
    //友客线索被查看的次数
    YOUKEXIANSUOSEETIME("被查看的次数", 389, "次"),
    //友客线索关键词数
    YOUKEXIANSUOSKEYNUM("线索关键词", 390, "个"),
    //友客线索关键词和用户订阅关键词重合数
    YOUKEXIANSUOUSERKEYNUM("匹配数", 392, "个"),
    //订阅关键词线索匹配度
    YOUKEKEYAMATCHB("匹配数", 393, ""),
    //线索关键词订阅匹配度
    YOUKEKEYBMATCHA("匹配数", 394, ""),
    //mmt2018年的客单价
    MMT2018AVGPRICE("客单价", 396, "元"),
    // mmt2018年的累计销售额

    MMT2018TOTALPRICE("销售额", 397, "元"),
    //mmt2018年的成单了的会员数
    MMT2018USER("用户数", 398, "个"),
    //所有会员类型的会员数
    MMTALLUSER("用户数", 399, "个"),
    //不同类型的会员无商机的会员数
    MMTNOPCALLUSER("用户数", 400, "个"),
    //不同类型的会员有商机的会员数
    MMTHASPCALLUSER("用户数", 401, "个"),
    //各个地区的会员数
    MMTPROVINCEUSER("地区会员", 402, "个"),
    //不同主营行业的会员数
    MMTPAREAUSER("行业会员", 403, "个"),

    /*MIP站流量的信息，这些数据是从百度统计抓取过来的**/
    MIP_IP("MIP站IP", 338, ""),
    MIP_PV("MIP站PV", 339, ""),
    MIP_UV("MIP站UV", 340, "");


    private String name;

    private Integer type;
    private String unit;

    private DataType(String name, Integer type, String unit) {
        this.name = name;
        this.type = type;
        this.unit = unit;
    }

    public static String getName(Integer type) {
        for (DataType dataType : DataType.values()) {
            if (dataType.type.intValue() == type.intValue()) {
                return dataType.name;
            }
        }
        return null;
    }

    public static String getUnit(Integer type) {
        for (DataType dataType : DataType.values()) {
            if (dataType.type.intValue() == type.intValue()) {
                return dataType.unit;
            }
        }
        return null;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
