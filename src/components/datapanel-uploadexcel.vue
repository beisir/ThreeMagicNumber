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
      exceluser: ''
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
    margin-right: 30px;
  }
.app-excel .el-button{
    width: 35%;
    height: 50px;
    background: #3680ef;
    text-overflow: ellipsis;
    overflow: hidden;
    color: #fff;
    font-size: 18px;
    border: none;
    border-radius: 4px;
    margin-top: 15px;
    cursor: pointer;
  }
</style>
