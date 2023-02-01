<template>
  <el-dialog top="5vh" :visible.sync="dialogVisible" :title="formTitle" width="1250px" @close="close">
    <iframe v-if="dialogVisible" :src="privewUrl" width="1200px" height="520px" border="0" />
  </el-dialog>
</template>

<script>
// import { queryPaperQuestionById } from '@/api/paper'
export default {
  name: '',
  props: {
    privewUrl: {
      type: String,
      default: ''
    }

  },
  data() {
    return {
      formTitle: '试题列表',
      dialogVisible: false,
      questionArr: []
    }
  },
  mounted() {

  },
  methods: {
    close() {
      this.dialogVisible = false
    },
    showDialog() {
      this.dialogVisible = true
    },
    questionList(paperId) {
      // var params = {
      //   paperId: paperId
      // }
      this.dialogVisible = true
      // const data = await queryPaperQuestionById(params)
      // this.questionArr = data.data
    }
  }

}
</script>

<style scoped>
.ques{
  margin: 10px 4px;
}
  .ques_ask{
    margin-left: 12px;
  }
.ques_ask div{
  margin: 4px 0;
}

.el-dialog__header{
  padding: 8px 20px;
}

/deep/   .el-dialog__body {
  padding: 10px 16px;
}
</style>
