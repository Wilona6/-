<template>
  <el-dialog :visible.sync="dialogVisible" class="app-container" :close-on-click-modal="false" :title="formTitle">
    <el-form ref="formCom" :model="form" :rules="rules" label-width="120px" :inline="true" :label-position="labelPosition">
      <el-form-item label="公告标题" prop="announcementTitle">
        <el-input v-model="form.announcementTitle" class="formItem" />
      </el-form-item>
      <el-form-item label="发布人" >
        <el-input v-model="form.announcementPublishers" class="formItem" />
      </el-form-item>

      <el-form-item label="公告详情" prop="announcementDetails">
        <el-input
          v-model="form.announcementDetails"
          type="textarea"
          :rows="3"
          class="textareaItem"
          placeholder="请输入公告详情"
        />
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
import { getAnnouncementById, saveOrUpdate } from '@/api/announcement'

export default {
  data() {
    return {
      formTitle: '',
      dialogVisible: false,
      labelPosition: 'right',
      isAddOPt: true,
      form: {},
      rules: {
        announcementTitle: [
          { required: true, message: '标题不能为空', trigger: 'blur,change' }
        ],
        announcementPublishers: [
          { required: true, message: '用发布人不能为空', trigger: 'change' }
        ],
        announcementDetails: [
          { required: true, message: '详情不能为空', trigger: 'change' }
        ]
      }
    }
  },

  methods: {
    showDialog() {
      this.dialogVisible = true
    },
    async editForm(userId) {
      this.formTitle = '编辑公告'
      const data = await getAnnouncementById(userId)
      this.form = data.data
      console.log(data.data, 'this.form')
      this.isAddOPt = false
      this.dialogVisible = true
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
    onCancel() {
      this.dialogVisible = false
    },
    addForm() {
      this.formTitle = '添加公告'
      this.isAddOPt = true
      this.dialogVisible = true
    },
    async doSave() {
      const pass = this.checkParam('formCom')
      if (!pass) {
        return
      }
      if (this.isAddOPt) {
        const data = await saveOrUpdate(this.form)
        this.dialogVisible = false
        this.$message.success(data.data)
      } else {
        const data = await saveOrUpdate(this.form)
        this.dialogVisible = false
        this.$message.success(data.data)
      }
      this.$emit('refreshDataList')
    }
  }
}
</script>

<style scoped>
  .formItem{
    width: 180px;
  }
  .textareaItem{
    display: inline-block;
    width: 490px;
    margin: 0 auto;
  }
  .btn_opt{
    width: 260px;
    margin: 0 auto;
  }
.line{
  text-align: center;
}
</style>

