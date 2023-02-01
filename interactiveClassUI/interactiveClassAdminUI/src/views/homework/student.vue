<template>
  <div class="app-container">
    <div class="btn_group" />
    <div style="margin-bottom:20px;color:blue"><span style="cursor:pointer;margin-bottom: 20px;" @click="goBack"> 《返回</span></div>
    <div class="add">
      <el-table
        v-loading="listLoading"
        :data="tableData"
        element-loading-text="Loading"
        border
        height="70vh"
        size="mini"
      >

        <el-table-column type="index" width="80" label="序号" align="center" />
        <el-table-column label="作业名称" align="center" prop="workTitle" />
<!--        <el-table-column label="课程" align="center" prop="courseName" />-->
        <el-table-column label="学生姓名" align="center" prop="username" />
        <el-table-column label="学生作业" align="center" prop="homeworkUrl">
          <template slot-scope="scope">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="handleDowload(scope.row)"
            >下载</el-button>
          </template>
        </el-table-column>

      </el-table>
<!--      <div style="text-align:center;margin-top:20px">-->
<!--        <el-pagination-->
<!--          layout="prev, pager, next"-->
<!--          :total="totalSize"-->
<!--          @current-change="handlePage"-->
<!--        />-->
<!--      </div>-->

    </div>
  </div></template>

<script>
import { uploadUrl } from '@/utils/request'
import { listPage } from '@/api/homework'
import { testStudent } from '@/api/paper'
export default {
  components: {
  },
  data() {
    return {
      tableData: [],
      page: 0,
      show: false,
      listLoading: true,
      totalSize: 0
    }
  },
  created() {
    this.courseId = this.$route.query.id
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
      const data = await testStudent({ pageNum: this.page, pageSize: 1000 })
      this.totalSize = data.data.total
      this.tableData = data.data.list.filter(v=>v.homeworkId==this.courseId)
      this.listLoading = false
    },
    refreshDataList() {
      this.listPageData()
    },
    goBack() {
      this.$router.push({ path: '/homework/index' })
    },
    handleDowload(row) {
      const url = uploadUrl + '/file/' + row.homeworkUrl
      const fileName = row.fileName
      const el = document.createElement('a')
      el.style.display = 'none'
      el.setAttribute('target', '_blank')
      fileName && el.setAttribute('download', fileName)
      el.href = url
      console.log(el)
      document.body.appendChild(el)
      el.click()
      document.body.removeChild(el)
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
