<template>
  <div id="key-indicators-container" class="app-view app-excel" v-loading.fullscreen.lock="fullscreenLoading">
    <div class="padding-md clearfix paddTop50">
      <div class="row">
        <div class="col-md-12">
          <div class="panel panel-default hour-trend upload-file">
            <el-upload
              class="upload-demo"
              action="http://data.360jz.com/dataweb/uploadExcel"
              accept="multipart/form-data"
              list-type="text"
              name="file"
              :on-success="uploadSuccess1"
              :on-error="uploadFail"
              :show-file-list="true"
              :before-upload="beforeUp"
              :file-list="fileList">
            <el-button type="primary" size="medium"><i class="el-icon-upload el-icon--right"></i>任务数据文件上传</el-button>
            </el-upload>
          </div>
          <div class="panel panel-default hour-trend upload-file">
          <el-upload
            class="upload-demo"
            action="http://data.360jz.com/dataweb/uploadOrderExcel"
            accept="multipart/form-data"
            list-type="text"
            name="file"
            :on-success="uploadSuccess2"
            :on-error="uploadFail"
            :show-file-list="true"
            :before-upload="beforeUp"
            :file-list="uploadList">
          <el-button type="primary" size="medium"><i class="el-icon-upload el-icon--right"></i>划转订单数据文件上传</el-button>
          </el-upload>
        </div>
        </div>
        <div class="col-md-12 excel excel2">
            <el-collapse v-model="activeNames" accordion>
                <el-collapse-item title="划转订单填写规则：" name="1">
                  <div> 1、划转日期：时间格式为"yyyy-mm-dd",如“2017-12-01”（不能包含空格）；</div>
                  <div> 2、划出、划入签单业务员必须填写mis名（不能包含空格）；</div>
                  <div> 3、划转金额必须为正数，可以为整数，也可以保留两位小数；</div>
                  <div> 4、备注：填写“订单划转”，“流水划转”，内容必须填写，不能为空，且前、后、中间不能有空格；</div>
                  <div> 备注：所有数据不能有空格，且不能为空</div>
                  <el-table
                    :data="tableData1"
                    border
                    >
                    <!-- :row-class-name="tableRowClassName" -->
                    <el-table-column
                      prop="date"
                      label="划转时间">
                    </el-table-column>
                    <el-table-column
                      prop="name"
                      label="订单编号">
                    </el-table-column>
                    <el-table-column
                      prop="address"
                      label="划出签单业务员（mis名）">
                    </el-table-column>
                    <el-table-column
                      prop="misuser"
                      label="划入签单业务员（mis名）">
                    </el-table-column>
                    <el-table-column
                      prop="price"
                      label="划转金额">
                    </el-table-column>
                    <el-table-column
                      prop="ps"
                      label="备注">
                    </el-table-column>
                  </el-table>
                </el-collapse-item>
                <el-collapse-item title="任务填写规则：" name="2">
                  <div>1、任务时间：以周度数据录入，具体时间格式为：20171201,数据解读为2017年12月第1周；</div>
                  <div>2、部门：整体部门新签、增值部门名称填写：/营销中心/新签、/营销中心/增值营销部，其他小部门，以ehr系统中部门名称为准，各级部门以“/”分割，如：营销中心/增值营销部/第一战队</div>
                  <div>3、任务数：必须为正整数；</div>
                  <div>备注：所有数据不能有空格，且不能为空</div>
                  <el-table
                    :data="tableData2"
                    border
                    >
                    <!-- :row-class-name="tableRowClassName" -->
                    <el-table-column
                      prop="date"
                      label="月自然周期">
                    </el-table-column>
                    <el-table-column
                      prop="name"
                      label="部门名称">
                    </el-table-column>
                    <el-table-column
                      prop="address"
                      label="任务数">
                    </el-table-column>
                  </el-table>
                </el-collapse-item>
            </el-collapse>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// action="http://data.360jz.com/dataweb/uploadExcel"
// action="http://data.360jz.com/dataweb/uploadOrderExcel"
// action="http://192.168.34.65:3366/upload/imgupload"
export default {
  data () {
    return {
      fileList: [],
      uploadList: [],
      /**
       * [fullscreenLoading 全屏加载遮罩]
       * @type {Boolean}
       */
      fullscreenLoading: false,
      exceluser: '',
      activeNames: ['1'],
      tableData1: [{
          date: '2017-12-02',
          name: '652147',
          address: '张文月1',
          misuser: '张立阳',
          price: 5000,
          ps:'流水划转'
        },{
            date: '2017-12-01',
            name: '652147',
            address: '李郭静',
            misuser: '李晓辉2',
            price: 4999,
            ps:'订单划转'
      }],
      tableData2: [{
          date: '20171201',
          name: '营销中心/增值营销部',
          address: '241309',
        }, {
          date: '20171201',
          name: '营销中心/增值营销部/第一战队',
          address: '2413088'
        }, {
          date: '20171201',
          name: '营销中心/增值营销部/第二战队',
          address: '3016360',
        }, {
          date: '20171201',
          name: '营销中心/增值营销部/第三战队',
          address: '4584867'
        }, {
          date: '20171201',
          name: '营销中心/新签',
          address: '388179'
        }, {
          date: '20171201',
          name: '营销中心/新签/新签一部',
          address: '517572'
        }, {
          date: '20171201',
          name: '营销中心/新签/新签二部',
          address: '646965'
        }]
    }
  },
  methods: {
    beforeUp (file){
      if(file.size > 10485760){
        this.$message.error({
          message:'文件超过上传大小限制',
          duration:0,
          showClose:true
        });
        return false;
      };
    },
    uploadSuccess1 (opt,file,flieList){
      if (!opt.errno){
        this.$message.success(opt.msg);
      } else {
        this.fileList = flieList.slice(0,flieList.length-1);
        this.$message.error({
          message:opt.msg,
          duration:0,
          showClose:true
        });
      }
    },
    uploadSuccess2 (opt,file,flieList){
      if (!opt.errno){
        this.$message.success(opt.msg);
      } else {
        this.uploadList = flieList.slice(0,flieList.length-1);
        this.$message.error({
          message:opt.msg,
          duration:0,
          showClose:true
        });
      }
    },
    uploadFail (err){
      this.$message.error({
        message:"上传错误",
        duration:0,
        showClose:true
      });
    }
  }
}
</script>
<style>
.el-upload{
    width: 100%;
  }
.app-excel .upload-file{
    height: 100%;
  }
.app-excel .upload-file input[type="file"] {
    display: none;
  }
.app-excel .el-upload{
    padding: 20px 10px;
  }
.app-excel .el-icon-upload{
    float: left;
  }
.app-excel .el-button{
    width: 30%;
    height: 50px;
    background: #3680ef;
    text-overflow: ellipsis;
    overflow: hidden;
    color: #fff;
    font-size: 16px;
    border: none;
    border-radius: 4px;
    margin-top: 15px;
    cursor: pointer;
  }
.cell{
    text-align: center;
}
</style>
