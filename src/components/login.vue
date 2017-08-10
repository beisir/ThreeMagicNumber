<template>
    <div>
        <div class="loginBox">
            <div class="loginLogo"></div>
            <!--用户名和密码结构开始-->
            <div class="loginBoxCon">
                <ul>
                    <li><span>账&nbsp;&nbsp;号：</span>
                        <input type="text" placeholder="请输入账号" v-model="username" @keyup.enter="submitEvent">
                    </li>
                    <li><span>密&nbsp;&nbsp;码：</span>
                        <input type="password" placeholder="请输入密码" v-model="password" @keyup.enter="submitEvent">
                    </li>
                </ul>
                <button type="button" @click="submitEvent">登录</button>
            </div>
            <!--用户名和密码结构结束-->
            <!--错误提示开始-->
            <div class="loginAlert" v-show="errorFlag">
                <div class="loginAlertBox">
                    <a href="#" @click="closePromptBox"></a>
                    <div class="alertCon">
                        <em></em>
                        <h3>账号或密码为空<br>请重新登录！</h3>
                    </div>
                </div>
            </div>
            <!--错误提示结束-->
            <!--隐藏的form开始  -->
            <form action="http://data.360jz.com/" method="post" ref="loginForm">
                <input type="hidden" name="username" id="usernameEle" />
                <input type="hidden" name="password" id="passwordEle" />
            </form>
            <!--隐藏的form结束-->
        </div>
        <div class="loginBg"></div>
    </div>
</template>
<script>
/***
 * 引入加密组件
 */
import LZString from 'lz-string';


export default {
    data() {
            return {
                username: '',
                password: '',
                errorFlag: false
            }

        },
        methods: {
            /***
             * 表单提交
             * @param $event
             */
            submitEvent() {
                let passwordEle = document.getElementById('passwordEle');
                let usernameEle = document.getElementById('usernameEle');

                /***
                 * 用户名密码非空验证
                 */
                if (this.username == '' || this.password == '') {
                    this.errorFlag = true;
                    return false;
                }
                /***
                 * 赋值编码后的密码
                 */
                passwordEle.value = LZString.compressToEncodedURIComponent(this.password);
                usernameEle.value = LZString.compressToEncodedURIComponent(this.username);
                /***
                 * 提交表单
                 */
                this.$refs.loginForm.submit();
            },
            /***
             * 关闭错误提示
             */
            closePromptBox() {
                this.errorFlag = false;
            }
        }
}
</script>
