// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path'),
    url = require('url')

module.exports = {
    build: {
        env: require('./prod.env'),
        index: path.resolve(__dirname, '../dataweb/src/main/webapp/WEB-INF/web/screen/index.html'),
        loginIndex: path.resolve(__dirname, '../dataweb/src/main/webapp/WEB-INF/web/screen/login.html'),
        assetsRoot: path.resolve(__dirname, '../dataweb/src/main/webapp/WEB-INF/web/screen'),
        assetsSubDirectory: 'static',
        assetsPublicPath: '//data.360jz.com/dataweb/',
        productionSourceMap: false,
        // Gzip off by default as many popular static hosts such as
        // Surge or Netlify already gzip all static assets for you.
        // Before setting to `true`, make sure to:
        // npm install --save-dev compression-webpack-plugin
        productionGzip: false,
        productionGzipExtensions: ['js', 'css'],
        // Run the build command with an extra argument to
        // View the bundle analyzer report after build finishes:
        // `npm run build --report`
        // Set to `true` or `false` to always turn it on or off
        bundleAnalyzerReport: process.env.npm_config_report
    },
    dev: {
        env: require('./dev.env'),
        port: 8081,
        autoOpenBrowser: true,
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        proxyTable: {
            /**
             * 映射到指定主机
             */
            // '/dataweb': {
            //      // target: 'http://123.103.77.81', //生产环境
            //       target: 'http://data.360jz.com', //测试环境
            //      //target: 'http://192.168.46.87',
            //      // target: 'http://192.168.34.179', //张婉如机器
            //      changeOrigin: true
            // }
            /**
             * 映射到本地文件
             */
            '/dataweb': {
                target: 'http://localhost:8081',
                changeOrigin: true,
                pathRewrite: function(path, req) {
                    var urlParsed = url.parse(req.url, true),
                        query = urlParsed.query,
                        pathname = urlParsed.pathname.replace(/\/*$/g,'');
                    pathname = pathname.substring(pathname.lastIndexOf('/'));
                    Object.keys(query).forEach((key) => {
                        pathname += ('-' + key + query[key]);
                    });
                    pathname = '/static/json' + pathname + '.json';
                    console.log('proxy request ' + path + ' to ' + pathname);
                    return pathname;
                }
            }
        },
        // CSS Sourcemaps off by default because relative paths are "buggy"
        // with this option, according to the CSS-Loader README
        // (https://github.com/webpack/css-loader#sourcemaps)
        // In our experience, they generally work as expected,
        // just be aware of this issue when enabling this option.
        cssSourceMap: false
    }
}


