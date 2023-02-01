<template>
  <div class="app-container">
    <div class="btn_group" />
    <div class="add">
      <el-button
        type="primary"
        plain
        size="mini"
        @click=" addRequestForm"
      >添加试题</el-button>
      <el-button size="small" type="danger" @click="delPaperByIds">批量删除</el-button></div>
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="tableData"
      element-loading-text="Loading"
      border
      height="70vh"
      stripe
      fit
      size="mini"
      highlight-current-row
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column type="index" width="80" label="序号" align="center" />
      <!--      <el-table-column label="用户id" prop="id" width="280px" />-->
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <!-- <el-table-column label="班级名称" align="center" prop="className" /> -->
      <el-table-column label="试题名称" align="center" prop="paperName" />
      <!-- <el-table-column label="试题总时长" align="center" prop="paperTime" />
      <el-table-column label="试题总分" align="center" prop="paperScore" /> -->
      <el-table-column label="操作" min-width="200px" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            plain
            size="mini"
            @click="questionList(scope.row)"
          >预览</el-button>
          <el-button
            type="primary"
            plain
            size="mini"
            @click="editRequestForm(scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteRow(scope.row.id)"
          >删除</el-button>
          <el-button
            size="mini"
            @click="goTest(scope.row)"
          >查看进度</el-button>
        </template>
      </el-table-column>
    </el-table>
    <form-dialog ref="formDialogCom" @refreshDataList="refreshDataList" />
    <add-form-dialog ref="addFormDialogCom" @refreshDataList="refreshDataList" />
    <question-list ref="questionListDialogCom" :privew-url="privewUrl" @refreshDataList="refreshDataList" />
    <div style="text-align:center;margin-top:20px">
      <el-pagination
        layout="prev, pager, next"
        :total="totalSize"
        @current-change="handlePage"
      />
    </div>

  </div>
</template>

<script>
import { listPage, delPaper, deletepaper } from '@/api/paper'
import addFormDialog from './addFormDialog'
import questionList from './questionList'
import formDialog from './formDialog'
import { uploadUrl } from '@/utils/request'
// import Pagination from '@/components/Pagnation/Pagination'
const privewUrl = uploadUrl + '/file/'
export default {
  components: {
    formDialog,
    addFormDialog,
    questionList
    // Pagination
  },
  data() {
    return {
      tableData: [],
      page: 0,
      show: false,
      listLoading: true,
      privewUrl,
      totalSize: 0
    }
  },
  created() {
    this.listPageData()
  },
  methods: {
    // 分页
    handlePage(e) {
      this.page = e
      this.listPageData()
    },
    //  列表
    async listPageData() {
      const data = await listPage({ pageNum: this.page, pageSize: 10 })
      this.totalSize = data.data.total
      this.tableData = data.data.list
      this.listLoading = false
    },
    getRecordId(records) {
      const recordIds = []
      for (let i = 0; i < records.length; i++) {
        recordIds.push(records[i].id)
      }
      return recordIds
    },
    // 批量删除
    delPaperByIds() {
      const records = this.$refs.multipleTable.selection
      if (records.length === 0) {
        this.$message.error('请选择要删除的用户!')
        return
      }
      this.$confirm('此操作将删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const recordIds = this.getRecordId(records)
        deletepaper({ ids: recordIds.join(',') }).then((resp) => {
          this.$message.success(resp.data)
          this.refreshDataList()
        })
      })
    },

    //  删除
    handleDeleteRow(recordIds) {
      this.$confirm('此操作将删除试题, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delPaper({ paperId: recordIds }).then((resp) => {
          this.$message.success('删除成功')
          this.refreshDataList()
        })
      })
    },
    refreshDataList() {
      this.listPageData()
    },
    // 编辑
    editRequestForm(item) {
      this.$refs.addFormDialogCom.editForm(item)
    },
    // 添加

    addRequestForm() {
      this.$refs.addFormDialogCom.addRequestForm()
    },
    // 预览
    questionList(item) {
      this.privewUrl = privewUrl + item.uploadUrl
      this.$refs.questionListDialogCom.questionList()
    },
    goTest(row) {
      this.$router.push({ path: '/testStudent/index?id=' + row.id })
    }
  }
}
</script>
<style scoped>
  .btn_group {
    float : right;
  }
  .add {
    text-align : right;
    margin-bottom : 20px;
  }

</style>
