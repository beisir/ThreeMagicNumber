<template>
    <div id="key-indicators-container" class="app-view" v-loading.fullscreen.lock="fullscreenLoading">
        运营数据
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
