/**
 * Created by HC360 on 2017/6/28.
 */

import Vue from 'vue';
import login from './components/login';

/***
 * 引入登录页面的css
 */
require('../src/assets/css/loginStyle.css');


const loginEntity = new Vue({
  el: '#login',
  template: '<login/>',
  components: {
    login
  }
});


