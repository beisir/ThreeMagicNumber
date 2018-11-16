<template>
    <div id="key-indicators-container" class="app-view" v-loading.fullscreen.lock="fullscreenLoading">
        <div class="hyDataBox">
        	<div class="tab-contentBox">
            	<div class="hyDataCon hyBg1">
                	<h6>MMT</h6>
                    <p>会员：21532516</p>
                    <p>客单价：100000</p>
                </div>
            	<div class="hyDataCon hyBg2">
                	<h6>P4P</h6>
                    <p>会员：21532516</p>
                    <p>客单价：100000</p>
                </div>
            	<div class="hyDataCon hyBg3">
                	<h6>友客</h6>
                    <p>会员：21532516</p>
                    <p>客单价：100000</p>
                </div>
            	<div class="hyDataCon hyBg4">
                	<h6>网站</h6>
                    <p>PV：21532516</p>
                    <p>询盘：100000</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {
            /**
             * [fullscreenLoading 全屏加载遮罩]
             * @type {Boolean}
             */
            fullscreenLoading: false,
        };
    }
}
</script>

<style lang="css">
</style>
