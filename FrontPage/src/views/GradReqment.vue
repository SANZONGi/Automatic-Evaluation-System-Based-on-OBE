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
.demo-table-expand
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
              <span>毕业要求管理</span>
              <router-link to="GradReqmentEdit">
                <el-button style="float: right">新增要求</el-button>
              </router-link>

            </el-row>

            <el-table
                :data="tableData"
                max-height="550px"
                style="width: 100%;">
              <el-table-column type="expand" >
                <template slot-scope="props">
                  <el-form label-position="left" inline class="demo-table-expand">
                    <el-form-item label="毕业要求 ID">
                      <span>{{ props.row.id }}</span>
                    </el-form-item>
                    <el-form-item label="毕业要求" label-width="100px">
                      <span>{{ props.row.graduationRequirement }}</span>
                    </el-form-item>
                    <el-form-item label="描述">
                      <span>{{ props.row.content }}</span>
                    </el-form-item>
                    <el-row type="flex" justify="center" align="middle">
                      <router-link :to="{path: '/GradReqmentEdit', query: {id:props.row.id}}">
                        <el-button type="primary" icon="el-icon-edit"  circle></el-button>
                      </router-link>
                      <div style="width: 120px;"></div>
                      <el-button type="danger" icon="el-icon-delete" @click="Delete(props.row.id)" circle></el-button>
                    </el-row>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                  label="毕业要求"
                  prop="graduationRequirement">
              </el-table-column>
              <el-table-column
                  label="描述"
                  prop="content"
                  :show-overflow-tooltip="true">
              </el-table-column>
              <el-table-column
                  fixed="right"
                  label="细分点操作"
                  width="100">
                <template slot-scope="scope">
                  <el-button @click="SearchDiv(scope.row.id)" type="text" size="small">查看</el-button>
                  <el-button @click="AddDiv(scope.row.id)" type="text" size="small">新增 </el-button>
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
      tableData: [{
        id: '',
        graduationRequirement: '',
        content: '',
      }]
    }
  },
  created() {
    this.$axios.get("/obe/gradreqs/list").then(res=>{
      if(res.data.status === "success") {
        this.tableData = res.data.data;
      } else {
        alert("error");
      }
    })
  },methods: {
    Delete(id) {
      this.$axios.delete("/obe/gradreqs/delete/" + id).then(res => {
        if(res.data.status === "success") {
          this.reload();
        } else {
          alert("error");
        }
      })
    },SearchDiv(id) {
      this.$router.push({path: '/Subdivision', query: {id:id}})
    },AddDiv(id) {
      this.$router.push({path: '/SubdivisionEdit', query: {id:id}})
    }
  }
}
</script>
