<template>
  <div id="key-indicators-container" class="app-view" v-loading.fullscreen.lock="fullscreenLoading">
    <div class="padding-md clearfix paddTop50">
      <div class="row">
        <div class="col-md-12">
          <!--权限管理-->
          <div class="panel panel-default hour-trend">
            <nav class="navbar navbar-default" role="navigation">
              <div class="container-fluid">
                <div class="navbar-header">
                  <span class="navbar-brand">权限管理</span>
                </div>
              </div>
            </nav>
            <div class="powerBox">
              <!--用户选择-->
              <div class="userSeleBox">
                <div class="userSele">
                  <div class="userSeleCon">
                    <el-select v-model="user" filterable placeholder="请选择用户" @change="handleUserSelectedChange">
                      <el-option v-for="item in users" :key="item.id" :label="item.misname" :value="item">
                      </el-option>
                    </el-select>
                  </div>
                  <el-button-group>
                    <el-button icon="plus" size="big" @click="dialogFormVisible=true"></el-button>
                  </el-button-group>
                </div>
                <div class="userRigBox">
                  <div class="userState">
                    <dl>
                      <dt>用户状态：</dt>
                      <dd>
                        <el-switch v-model="user.state" on-text="开启" off-text="关闭" :on-value="0" :off-value="1" :disabled="!user.id" @change="handleUserStateChange">
                        </el-switch>
                      </dd>
                    </dl>
                  </div>
                  <el-button type="primary" :disabled="!user.id" @click="handleSaveUserPrivileges" :loading="savePrivilegesLoadingState">权限保存</el-button>
                </div>
              </div>
              <!--用户选择END-->
              <!--权限选择-->
              <div class="seleBoxConList">
                <dl v-for="privilegeGroup in allPrivilegeGroups">
                  <dt>
                    <el-checkbox :indeterminate="(userPrivileges[privilegeGroup.id]||{}).indeterminate" v-model="(userPrivileges[privilegeGroup.id]||{}).checkall" @change="handleGroupChange($event,privilegeGroup.id)" :disabled="!user.id">{{privilegeGroup.privilege}}
                    </el-checkbox>
                  </dt>
                  <dd>
                    <el-checkbox-group v-model="(userPrivileges[privilegeGroup.id]||{}).data" @change="handlePrivilegeChange($event,privilegeGroup.id)">
                      <el-checkbox v-for="privilege in allPrivileges[privilegeGroup.id]" :label="privilege.id" :key="privilege.id" :disabled="!user.id">{{privilege.privilege}}</el-checkbox>
                    </el-checkbox-group>
                  </dd>
                </dl>
              </div>
              <!--权限选择END-->
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog title="添加用户" :visible.sync="dialogFormVisible" :before-close="handleDialogClose">
      <el-input v-model="form.username" auto-complete="off" placeholder="请输入用户名" @keyup.enter.native="handleDialogConfirm"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleDialogClose">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {

      /**
       * [users 用户列表]
       * @type {Array}
       */
      users: [],

      /**
       * [user 当前用户对象]
       * @type {Object}
       */
      user: {},

      /**
       * [userPrivileges 当前用户分组后的已设置权限]
       * @type {Array}
       */
      userPrivileges: {},

      /**
       * [allPrivileges 所有分组后的可设置权限]
       * @type {Array}
       */
      allPrivileges: {},

      /**
       * [allPrivilegeGroups 所有可设置权限的分组列表]
       * @type {Array}
       */
      allPrivilegeGroups: [],

      /**
       * [allPrivilegesHashData 所有可设置权限数据的HASH数据对象，便于在获取到用户已设置权限数据后能迅速定位各权限对应的分组]
       * @type {Object}
       */
      allPrivilegesHashData: {},

      /**
       * [fullscreenLoading 全屏加载遮罩]
       * @type {Boolean}
       */
      fullscreenLoading: false,

      /**
       * [dialogFormVisible 表单对话框显示状态]
       * @type {Boolean}
       */
      dialogFormVisible: false,

      /**
       * [form 表单]
       * @type {Object}
       */
      form: {

        /**
         * [username 用户名]
         * @type {String}
         */
        username: ''
      },

      /**
       * [savePrivilegesLoadingState 保存权限按钮加载状态]
       * @type {Boolean}
       */
      savePrivilegesLoadingState: false
    }
  },
  methods: {

    /**
     * [handleSaveUserPrivileges 保存用户权限]
     * @return {[type]} [description]
     */
    handleSaveUserPrivileges() {
      const _this = this;

      /**
       * [loading 设置保存用户权限按钮状态]
       * @type {Boolean}
       */
      _this.savePrivilegesLoadingState = true;

      /**
       * [_promise 获取更新用户权限数据延迟对象]
       * @type {[type]}
       */
      let _promise = _this.updateAuthData();

      /**
       * [更新用户权限后，重置按钮状态]
       */
      _promise.then((data) => {

        /**
         * [loading 设置保存用户权限按钮状态]
         * @type {Boolean}
         */
        _this.savePrivilegesLoadingState = false;

        /**
         * 显示成功信息
         */
        _this.$message.success('权限保存成功！');
      }, (response) => {

        /**
         * [loading 设置保存用户权限按钮状态]
         * @type {Boolean}
         */
        _this.savePrivilegesLoadingState = false;

        /**
         * 显示错误信息
         */
        _this.$message.error(response.msg);
      });
    },

    /**
     * [handleUserStateChange 切换用户状态]
     * @return {[type]} [description]
     */
    handleUserStateChange() {
      const _this = this;

      /**
       * [_state 修改后的状态值]
       * @type {[type]}
       */
      let _state = arguments[0],

        /**
         * [_promise 获取更新用户状态延迟对象]
         * @type {[type]}
         */
        _promise = _this.updateUserState(_state);

      /**
       * [添加用户回调函数]
       */
      _promise.then((data) => {}, (response) => {

        /**
         * 显示错误信息
         */
        _this.$message.error(response.msg);
      });
    },

    /**
     * [handleDialogClose 弹出框关闭]
     * @return {[type]} [description]
     */
    handleDialogClose() {
      const _this = this;

      /**
       * [dialogFormVisible 隐藏弹出框]
       * @type {Boolean}
       */
      _this.dialogFormVisible = false;
    },

    /**
     * [handleDialogConfirm 弹出框确定]
     * @return {[type]} [description]
     */
    handleDialogConfirm() {
      const _this = this;

      /**
       * [验证用户名非空]
       */
      if (!_this.form.username.trim().length) {
        _this.$message.error('请输入用户名！');
        return;
      }

      /**
       * [_promise 获取添加用户延迟对象]
       * @type {[type]}
       */
      let _promise = _this.addAuthUser();

      /**
       * [添加用户回调函数]
       */
      _promise.then((data) => {

        /**
         * [dialogFormVisible 隐藏弹出框]
         * @type {Boolean}
         */
        _this.dialogFormVisible = false;

        /**
         * [username 清空用户名输入框]
         * @type {String}
         */
        _this.form.username = '';
      }, (response) => {

        /**
         * 显示错误信息
         */
        _this.$message.error(response.msg);
      });
    },

    /**
     * [handleGroupChange 权限分组复选框选中状态改变事件处理函数]
     * @param  {[type]} event [description]
     * @param  {[type]} id    [description]
     * @return {[type]}       [description]
     */
    handleGroupChange(event, id) {
      const _this = this;

      /**
       * [_userPrivileges 获取当前用户的当前分组权限数据]
       * @type {Array}
       */

      let _userPrivileges = _this.userPrivileges[id],

        /**
         * [_allPrivileges 获取所有权限数据的当前分组的权限数据]
         * @type {[type]}
         */
        _allPrivileges = _this.allPrivileges[id].map((item) => {
          return item.id;
        }),

        /**
         * [_checked 选中状态]
         * @type {[type]}
         */
        _checked = event.target.checked;

      /**
       * [设置组选中状态]
       */
      _userPrivileges.data = _checked ? _allPrivileges : [];
      _userPrivileges.indeterminate = false;
    },

    /**
     * [handlePrivilegeChange 权限复选框选中状态改变事件处理函数]
     * @param  {[type]} event [description]
     * @param  {[type]} pid   [description]
     * @return {[type]}       [description]
     */
    handlePrivilegeChange(event, pid) {
      const _this = this;

      /**
       * [_userPrivileges 获取当前用户的当前分组权限数据]
       * @type {Array}
       */
      let _userPrivileges = _this.userPrivileges[pid],

        /**
         * [_allPrivileges 获取所有权限数据的当前分组的权限数据]
         * @type {[type]}
         */
        _allPrivileges = _this.allPrivileges[pid];

      /**
       * [设置组选中状态]
       */
      _userPrivileges.checkall = (_allPrivileges.length === _userPrivileges.data.length);
      _userPrivileges.indeterminate = _userPrivileges.checkall ? false : (_userPrivileges.data.length > 0);
    },

    /**
     * [handleUserSelectedChange 选择用户操作]
     * @param  {[type]} user [description]
     * @return {[type]}      [description]
     */
    handleUserSelectedChange(user) {
      const _this = this;

      /**
       * [获取用户权限数据]
       */
      _this.getAuthData();
    },

    /**
     * [getAllRealtimePrivileges 获取所有可设置权限数据]
     * @return {[type]} [description]
     */
    getAllRealtimePrivileges() {
      const _this = this;

      /**
       * [_promise 创建延迟对象]
       * @type {[type]}
       */
      let _promise = _this.getDataPromise('/dataweb/getAllRealtimePrivileges', {});

      /**
       * [延迟对象回调]
       * @param  {Function} (data) [description]
       * @return {[type]}          [description]
       */
      _promise.then((data) => {

        /**
         * [_data 获取所有权限数据]
         * @type {[type]}
         */
        let _data = data || [];

        /**
         * [按照权限层级排序，确保在循环到子权限时，其父权限数据已经存在]
         */
        _data.sort(function(a, b) {
          return a.p_type - b.p_type;
        });

        /**
         * [初始化权限]
         */
        _data.forEach(function(item) {

          /**
           * 填充可设置权限数据的HASH数据对象
           */
          _this.allPrivilegesHashData[item.id.toString()] = item;

          /**
           * [根据一级权限分类对权限数据进行分组]
           */
          if (item.p_type === 0) {
            _this.allPrivilegeGroups.push(item);
            _this.allPrivileges[item.id.toString()] = [];
            return true;
          }

          /**
           * 填充各分组的权限数据
           */
          _this.allPrivileges[item.p_type.toString()].push(item);
        });
      });

      /**
       * 返回延迟对象
       */
      return _promise;
    },

    /**
     * [getUsers 获取用户数据]
     * @return {[type]} [description]
     */
    getAuthUsers() {
      const _this = this;

      /**
       * [_promise 创建延迟对象]
       * @type {[type]}
       */
      let _promise = _this.getDataPromise('/dataweb/getAuthUsers', Object.assign({}, _this.user));

      /**
       * [延迟对象回调]
       * @param  {Function} (data) [description]
       * @return {[type]}          [description]
       */
      _promise.then((data) => {
        _this.users = data || [];
      });

      /**
       * 返回延迟对象
       */
      return _promise;
    },

    /**
     * [getAuthData 根据用户编号获取用户权限数据]
     * @param  {[type]} params [description]
     * @return {[type]}        [description]
     */
    getAuthData() {
      const _this = this;

      /**
       * [_promise 创建延迟对象]
       * @type {[type]}
       */
      let _promise = _this.getDataPromise('/dataweb/getAuthData', Object.assign({}, _this.user));

      /**
       * [延迟对象回调]
       * @param  {Function} (data) [description]
       * @return {[type]}          [description]
       */
      _promise.then((data) => {
        let _data = data || [];
        /**
         * 将当前用户的权限数据进行分组
         */
        Object.keys(_this.allPrivileges).forEach((key) => {
          _this.$set(_this.userPrivileges, key, {
            data: [],
            checkall: false,
            indeterminate: false
          });
        });

        /**
         * [填充当前用户的权限数据]
         */
        _data.forEach((id) => {
          if (_this.allPrivilegesHashData[id]) {

            /**
             * [_tempData 获取当前id对应的权限数据信息]
             * @type {Object}
             */
            let _tempData = _this.allPrivilegesHashData[id],

              /**
               * [_tempId 权限id，处理一级权限数据和二级权限数据]
               * @type {[type]}
               */
              _tempId = _tempData.p_type === 0 ? id : _tempData.p_type,

              /**
               * [_userPrivileges 获取当前用户的当前分组权限数据]
               *  {
                    data: [],
                    checkall: false,
                    indeterminate: false
                  }
               * @type {Array}
               */
              _userPrivileges = _this.userPrivileges[_tempId],

              /**
               * [_allPrivileges 获取所有权限数据的当前分组的权限数据]
               * @type {[type]}
               */
              _allPrivileges = _this.allPrivileges[_tempId];

            /**
             * 填充当前用户的权限数据
             */
            if (_tempData.p_type) {
              _userPrivileges.data.push(id);
            }

            /**
             * [设置组选中状态]
             */
            _userPrivileges.checkall = (_allPrivileges.length === _userPrivileges.data.length);
            _userPrivileges.indeterminate = _userPrivileges.checkall ? false : (_userPrivileges.data.length > 0);


          }
        });
            console.log(_this.userPrivileges)
      });

      /**
       * 返回延迟对象
       */
      return _promise;
    },

    /**
     * [addAuthUser 添加权限用户]
     * @param {[type]} params [description]
     */
    addAuthUser() {
      const _this = this;

      /**
       * [_promise 创建延迟对象]
       * @type {[type]}
       */
      let _promise = _this.getDataPromise('/dataweb/addAuthUser', {
        misname: _this.form.username
      });

      /**
       * [数据延迟对象回调]
       */
      _promise.then((data) => {

        /**
         * [_data 获取添加用户后返回的该用户数据]
         * @type {[type]}
         */
        let _data = data || {};

        /**
         * 将新用户添加到用户列表中
         */
        _this.users.push(data);

        /**
         * [user 设置新用户为当前选中用户]
         */
        _this.user=data;
      }, (response) => {
        // console.log(response);
      });

      /**
       * 返回延迟对象
       */
      return _promise;
    },

    /**
     * [updateAuthData 根据用户编号更新用户权限数据]
     * @param  {[type]} params   [description]
     * @return {[type]}          [description]
     */
    updateAuthData() {
      const _this = this;

      /**
       * [_userPrivileges 初始化当前用户权限数据]
       * @type {Array}
       */
      let _userPrivileges = [];
      Object.keys(_this.userPrivileges).forEach((key) => {

        /**
         * [若一级权限为选中状态，则将一级权限数据记录到权限数据集中]
         */
        if (_this.userPrivileges[key].checkall || _this.userPrivileges[key].indeterminate) {
          _userPrivileges = _userPrivileges.concat([parseInt(key)]);
        }

        /**
         * [_userPrivileges 组合二级权限数据]
         */
        _userPrivileges = _userPrivileges.concat(_this.userPrivileges[key].data);
      });


      /**
       * [_promise 创建延迟对象]
       * @type {[type]}
       */
      let _promise = _this.getDataPromise('/dataweb/updateAuthData', Object.assign({}, _this.user, {
        privileges: _userPrivileges
      }));

      /**
       * [返回获取数据延迟对象]
       */
      return _promise;
    },

    /**
     * [updateUserState 更新用户状态]
     * @return {[type]} [description]
     */
    updateUserState(state) {
      const _this = this;

      /**
       * [_promise 创建延迟对象]
       * @type {[type]}
       */
      let _promise = _this.getDataPromise('/dataweb/updateUserState', Object.assign({}, _this.user, {
        state: state
      }));

      /**
       * [延迟对象回调]
       * @param  {[type]} (data)    [description]
       * @param  {[type]} (response [description]
       * @return {[type]}           [description]
       */
      _promise.then((data) => {}, (response) => {

        /**
         * [更新失败置回用户状态]
         * @type {[type]}
         */
        _this.user.state = _this.user.state ? 0 : 1;
      });

      /**
       * [返回获取数据延迟对象]
       */
      return _promise;
    },
    /**
     * [getDataPromise 获取数据延迟对象]
     * @param  {[type]} url    [description]
     * @param  {[type]} params [description]
     * @return {[type]}        [description]
     */
    getDataPromise(url, params) {
      const _this = this;

      /**
       * [fullscreenLoading 显示全屏加载遮罩]
       * @type {Boolean}
       */
      // _this.fullscreenLoading = true;

      /**
       * [返回获取用户权限数据的延迟对象]
       * @param  {[type]} (resolve,reject [description]
       * @return {[type]}                 [description]
       */
      return new Promise((resolve, reject) => {
        _this.$http.post(url, Object.assign({}, params), {
          emulateJSON: true
        }).then((response) => {
          let _response = response.body || {};

          /**
           * [fullscreenLoading 关闭全屏加载遮罩]
           * @type {Boolean}
           */
          // _this.fullscreenLoading = false;

          /**
           * [验证数据状态码]
           */
          if (parseInt(_response.errno) !== 0) {
            console.warn('返回数据状态码错误！');
            reject(_response);
            return;
          }

          /**
           * 解决延迟对象
           */
          resolve(_response.data || {});
        }, (response) => {
          /**
           * [fullscreenLoading 关闭全屏加载遮罩]
           * @type {Boolean}
           */
          // _this.fullscreenLoading = false;
          console.warn('获取数据失败！');
          reject(response);
        });
      });
    }
  },

  /**
   * [created description]
   * @return {[type]} [description]
   */
  created() {},

  /**
   * [mounted description]
   * @return {[type]} [description]
   */
  mounted() {
    const _this = this;

    /**
     * 获取权限用户列表
     */
    _this.getAuthUsers();

    /**
     * 获取所有可设置权限数据
     */
    _this.getAllRealtimePrivileges();
  }
}

</script>
