<template>
  <el-dialog top="5vh" :visible.sync="dialogVisible" class="app-container" :close-on-click-modal="false" :title="formTitle" width="750px" height="560px" style="">
    <el-form ref="formCom" :model="form" label-width="120px" :inline="true" :label-position="labelPosition">
      <!-- <el-form-item label="试题编号">
        <el-input v-model="form.questionNum" placeholder="试题编号" />
      </el-form-item> -->
      <el-form-item label="课程">
        <el-select v-model="form.courseId" placeholder="请选择课程">
          <el-option
            v-for="item in classList"
            :key="item.id"
            :label="item.courseName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="试题名称">
        <el-input v-model="form.paperName" placeholder="试题名称" type="textarea" :rows="4" class="formItem" />
      </el-form-item>
      <el-form-item label="上传试题">
        <el-upload
          v-if="dialogVisible "
          class="upload-demo"
          :file-list="fileList"
          :show-file-list="false"
          :action="uploadUrl+'/file/upload'"
          :limit="1"
          :on-success="handleSuccess"
        >
          <el-button size="small" type="primary">上传试题</el-button>
        </el-upload>
        <div>{{ form.uploadUrl }}</div>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <div class="btn_opt">
        <el-button @click="onCancel">取 消</el-button>
        <el-button type="primary" @click="doSave">确 定</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import { createPaper } from '@/api/paper'
import { uploadUrl } from '@/utils/request'
import { listPage,distinctAll } from '@/api/course'

export default {
  data() {
    return {
      formTitle: '编辑用户',
      dialogVisible: false,
      labelPosition: 'right',
      isAddOPt: true,
      uploadUrl,
      classList: [],
      fileList: [],
      value: '',
      form: {
        uploadUrl: '',
        paperName: '',
        courseId: '',
        courseName: ''
      }
    }
  },
  created() {
    this.getClasslistPage()
  },
  methods: {
    // 获取课程列表
    getClasslistPage() {
      distinctAll().then(res => {
        console.log(res,'distinctAll')
        if (res.code == 200) {
          this.classList = res.data
          console.log(res, '课程列表')
        }
      })
    },
    // 上传文件
    handleSuccess(e) {
      this.form.uploadUrl = e.data
      this.$message.success('上传成功')
      this.fileList = []
    },

    async editForm(item) {
      this.form = {
    id: item.id,
        uploadUrl: item.uploadUrl,
        paperName: item.paperName,
        courseId: item.courseId,
        courseName: item.courseName
      }
      // this.filelist = [{ name: item.uploadUrl }]
      this.formTitle = '修改课程'
      this.isAddOPt = false
      this.dialogVisible = true
    },
    addRequestForm() {
      this.formTitle = '添加试题'
      this.dialogVisible = true
      this.isAddOPt = true
    },
    // 提交
    doSave() {
      // if (!this.form.questionNum) return this.$message.warning('请输入试题编号')
      if (!this.form.paperName) return this.$message.warning('请输入试题名称')
      if (!this.form.courseId) return this.$message.warning('请上传课程')
      if (!this.form.uploadUrl) return this.$message.warning('请上传试题')
      this.form.courseName = this.classList.find(v => v.id == this.form.courseId).courseName
      createPaper(this.form).then(data => {
        if(this.isAddOPt){
          this.$message.success('添加成功')

        }else{
          this.$message.success('编辑成功')
        }
        this.onCancel()
        if (this.isAddOPt) this.form = this.$options.data.call(this).form
        this.$emit('refreshDataList')
      })
      // addQuestion(this.form).then(data => {
      //   this.$message.success(data.message)
      //   this.onCancel()
      //   // this.form = this.$options.data.call(this).form
      //   this.$emit('refreshDataList')
      // })
    },
    // 取消
    onCancel() {
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped>
  .formItem {
    width : 560px;
  }
  .line {
    text-align : center;
  }

</style>

