<template>
  <el-dialog :visible.sync="dialogVisible" class="app-container" :close-on-click-modal="false" :title="formTitle" width="60%">
    <el-form ref="formCom" :model="form" :rules="rules" label-width="120px" :inline="true" :label-position="labelPosition">
      <el-form-item label="作业名称" prop="workTitle">
        <el-input v-model="form.workTitle" placeholder="输入作业名称" class="formItem" />
      </el-form-item>
      <!-- <el-form-item label="作业简介" prop="workDes">
        <el-input v-model="form.workDes" class="formItem" />
      </el-form-item> -->
      <el-form-item label="选择课程" prop="courseId">
        <el-select v-model="form.courseId" placeholder="请选择课程" class="formItem">
          <el-option v-for="(item,index) in courses" :key="index" :label="item.courseName" :value="item.id" />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="选择老师">-->
      <!--        <el-select v-model="form.teacherId" placeholder="请选择老师" class="formItem">-->
      <!--          <el-option v-for="(className,index) in classNames" :key="index" :label="className" :value="className" />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <div>
        <el-form-item label="上传作业">
          <el-upload
            v-if="dialogVisible"
            class="upload-demo"
            :action="uploadUrl"
            :on-success="uploadHomeworkSuccess"
            :limit="1"
            :show-file-list="false"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">上传课后作业</el-button>
          </el-upload>
          <div>{{ form.attachPath }}</div>
        </el-form-item>
      </div>
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
import { addHomework, updateHomework, getDetailById } from '@/api/homework'
import { listTeacherClasses } from '@/api/class'
import { queryCourseInfoByCurUser, listPage ,distinctAll} from '@/api/course'

import fileRequest from '@/utils/fileRequest'
export default {
  data() {
    return {
      formTitle: '',
      dialogVisible: false,
      labelPosition: 'right',
      isAddOPt: true,
      classNames: [],
      resourceNum: 0,
      fileList: [],
      uploadUrl: fileRequest.fileUploadUrl,
      dateFormat: 'yyyy-MM-dd hh:mm:ss',
      courses: [],
      form: {
        pptResources: [{}],
        videoResources: [{}],
        attachPath: '',
        courseId: ''
      },
      defaultForm: {
        pptResources: [{}],
        videoResources: [{}]
      },
      rules: {

      }
    }
  },
  mounted() {
    this.listJoinCourse()
  },
  methods: {
    showDialog() {
      this.dialogVisible = true
    },
    async  listJoinCourse() {
      distinctAll().then(res => {
        console.log(res,'distinctAll')
        if (res.code == 200) {
          this.courses = res.data
        }
      })
    },

    uploadHomeworkSuccess(e) {
      this.form.attachPath = e.data
      this.$message.success('上传成功')
      this.fileList = []
    },
    async editForm(homeworkId) {
      this.formTitle = '修改作业'
      const data = await getDetailById(homeworkId)
      this.form = data.data
      this.isAddOPt = false
      this.dialogVisible = true
    },
    addForm() {
      this.formTitle = '发布作业'
      this.isAddOPt = true
      this.dialogVisible = true
      this.form = this.$options.data.call(this).form
    },
    checkParam(formName) {
      let pass = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          pass = true
        } else {
          console.log('请先填写完表单必填选!!')
          this.$message.error('请先填写完表单必填选!!')
          pass = false
        }
      })
      return pass
    },
    async doSave() {
      // const pass = this.checkParam('formCom')
      // if (!pass) {
      //   return
      // }
      if (!this.form.attachPath) {
        this.$message.error('请选择要上传的课后作业!!')
        return
      }
      let data = ''
      if (this.isAddOPt) {
        data = await addHomework(this.form)
        this.$message.success(data.data)
      } else {
        data = await updateHomework(this.form)
        this.$message.success(data.data)
      }

      this.dialogVisible = false
      this.$emit('refreshDataList')
    },

    onCancel() {
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped>
  .formItem {
    width : 200px;
  }
  .line {
    text-align : center;
  }

</style>

