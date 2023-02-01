<template>
  <div class="app-container">
    <div class="btn_group">
      <el-button type="primary" plain size="small" @click="addForm">添加</el-button>
      <el-button type="danger" size="small" @click="delUsesByIds">批量删除</el-button>
    </div>
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="list"
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
      <el-table-column type="index" width="80" align="center" label="序号" />
      <el-table-column label="公告id" prop="announcementId" width="280px" />
      <el-table-column label="公告标题" prop="announcementTitle" />
      <el-table-column label="公告详情" align="center" prop="announcementDetails" />
      <el-table-column label="公告发布人" prop="announcementPublishers" align="center" min-width="100px" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            plain
            size="mini"
            @click="editForm(scope.row.announcementId)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteRow(scope.row.announcementId)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <form-dialog ref="formDialogCom" @refreshDataList="refreshDataList" @close="listPageData" @cancel="listPageData" />
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
import { listPage, deleteById } from '@/api/announcement'
import formDialog from './formDialog'
export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  components: {
    formDialog
  },
  data() {
    return {
      list: null,
      pageData: {},
      totalSize: 0,
      listLoading: true,
      page: 1
    }
  },
  created() {
    this.listPageData()
  },
  methods: {
    async listPageData() {
      try {
        const data = await listPage({ pageNum: this.page, pageSize: 10 })
        this.pageData = data.data
        this.totalSize = this.pageData.total
        this.list = this.pageData.list
        this.listLoading = false
      } catch (e) {
        this.listLoading = false
      }
    },
    refreshDataList() {
      console.log('refresh')
      this.listPageData()
    },
    editForm(id) {
      this.$refs.formDialogCom.editForm(id)
    },
    addForm() {
      this.$refs.formDialogCom.addForm()
    },
    getRecordId(records) {
      const recordIds = []
      for (let i = 0; i < records.length; i++) {
        recordIds.push(records[i].announcementId)
      }
      return recordIds
    },
    delUsesByIds() {
      const records = this.$refs.multipleTable.selection
      console.log(records, 'records')
      if (records.length === 0) {
        this.$message.error('请选择要删除的公告!')
        return
      }
      this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const recordIds = this.getRecordId(records)
        deleteById(recordIds.join(',')).then((resp) => {
          this.$message.success(resp.data)
          this.refreshDataList()
        })
      })
    },
    handleDeleteRow(recordIds) {
      this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(recordIds, 'ids')
        deleteById(recordIds).then((resp) => {
          this.$message.success(resp.data)
          this.refreshDataList()
        })
      })
    },
    handlePage(e) {
      this.page = e
      this.listPageData()
    }
  }
}
</script>
<style scoped>
  .btn_group {
    float : right;
  }

</style>
