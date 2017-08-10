/**
 * Created by 姜艳云 on 2017/2/22.
 *  图表配置
 */
export default {
  title: {
    text: null //不显示标题
  },
  chart: {
    type: 'spline' //图表的默认类型
  },
  colors: ['#19c6Ed', '#FF7C4D', '#2BCC6B', '#C275DF','#aa4643'], //图表序列（Series）的默认颜色数组
  credits: {
    enabled: false // 禁用版权信息
  },
  tooltip: {
    shared: true
  },
  xAxis: {
    /**
     * [crosshair 跟随鼠标或鼠标滑过点时的十字准星线]
     */
    crosshair: {
      width: 1,
      color: '#E1E1E1'
    }
  }
}
