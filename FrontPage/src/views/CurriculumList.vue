<style>
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 4px;
  overflow: hidden;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand,
.el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>


<template>
  <div class="layout">
    <Layout>
      <MyHeader/>
      <Layout>
        <Sider hide-trigger :style="{background: '#fff'}">
          <MySider/>
        </Sider>
        <Layout :style="{padding: '14px 24px'}">
          <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
            <el-row>
              <span>培养方案管理</span>
              <router-link to="CurriculumListEdit">
                <el-button style="float: right">新增方案</el-button>
              </router-link>

            </el-row>

            <el-table
                :data="tableData"
                max-height="550px"
                style="width: 100%;">
              <el-table-column
                  prop="id"
                  label="课程ID"
                  width="240">
              </el-table-column>
              <el-table-column
                  prop="name"
                  label="课程名"
                  width="260">
              </el-table-column>
              <el-table-column
                  prop="teacher"
                  label="教师姓名"
                  width="260">
              </el-table-column>
              <el-table-column
                  prop="open"
                  label="开课时间">
              </el-table-column>
              <el-table-column
                  label="操作"
                  width="100">
                <template slot-scope="scope">
                  <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                  <el-button @click="EditDiv(scope.row.id)" type="text" size="small">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>


<script>
import MyHeader from '@/components/MyHeader'
import MySider from '@/components/MySider'

export default {
  inject: ['reload'],
  components: {MyHeader, MySider},
  data() {
    return {
      tableData: [
        {
          id: '',
          teacher : '',
          name : '',
          open: '',
          outline: '',
          criterion: ''
        }
      ]
    }
  },
  created() {
    this.$axios.get("/obe/cur/list").then(res=>{
      this.tableData = res.data.data
    })


  }, methods: {
    Delete(id) {
      this.$axios.delete("/obe/cur/delete/" + id).then(res => {
        if (res.data.status === "success") {
          this.reload();
        } else {
          alert("error");
        }
      })
    }, EditDiv(id) {
      this.$router.push({path: '/CurriculumListEdit', query: {id:id }})
    },handleClick(curriculum) {
      localStorage.setItem("editingCur", JSON.stringify(curriculum))
      this.$router.push({path: '/Curriculum', query: {curriculum : curriculum}})

    }

  },

}
</script>
