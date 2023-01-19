<style>
.layout{
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
.demo-table-expand .el-form-item {
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
              <router-link to="ProgramEdit">
                <el-button style="float: right">新增方案</el-button>
              </router-link>

            </el-row>

              <el-table
                  :data="tableData"
                  style="width: 100%;">
                <el-table-column type="expand">
                  <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-form-item label="培养方案 ID">
                        <span>{{ props.row.id }}</span>
                      </el-form-item>
                      <el-form-item label="培养方案名称" label-width="100px">
                        <span>{{ props.row.name }}</span>
                      </el-form-item>
                      <el-form-item label="适用年级">
                        <span>{{ props.row.grade }}</span>
                      </el-form-item>
                      <el-form-item label="描述">
                        <span>{{ props.row.content }}</span>
                      </el-form-item>
                      <el-row>
                        <router-link :to="{path: '/ProgramEdit', query: {id:props.row.id}}">
                          <el-button type="primary" icon="el-icon-edit"  circle></el-button>
                        </router-link>

                        <el-button type="danger" icon="el-icon-delete" @click="Delete(props.row.id)" circle></el-button>
                      </el-row>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column
                    label="培养方案名称"
                    prop="name">
                </el-table-column>
                <el-table-column
                    label="适用年级"
                    prop="grade">
                </el-table-column>
                <el-table-column
                    label="描述"
                    prop="content"
                    :show-overflow-tooltip="true">
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
      tableData: [{
        id: '',
        name: '',
        grade: '',
        description: ''
      }]
    }
  },
  created() {
    this.$axios.get("/obe/program/list").then(res=>{
      if(res.data.status === "success") {
        this.tableData = res.data.data;
      } else {
        alert("error");
      }
    })
  },methods: {
    Delete(id) {
      this.$axios.delete("/obe/program/delete/" + id).then(res => {
        if(res.data.status === "success") {
          this.reload();
        } else {
          alert("error");
        }
      })
    }
  }
}
</script>
