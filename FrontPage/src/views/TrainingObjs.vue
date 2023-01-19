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

          <Content v-if="show" :style="{padding: '24px', minHeight: '280px', background: '#fff' }">
            <el-row>
              <span>培养目标管理</span>
              <router-link to="TrainingObjsEdit">
                <el-button style="float: right">新增目标</el-button>
              </router-link>
            </el-row>

            <el-table
                :data="tableData"
                style="width: 100%;">
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form label-position="left" inline class="demo-table-expand">
                    <el-form-item label="培养目标 ID">
                      <span>{{ props.row.id }}</span>
                    </el-form-item>
                    <el-form-item label="培养目标" label-width="100px">
                      <span>{{ props.row.objective }}</span>
                    </el-form-item>
                    <el-form-item label="描述">
                      <span>{{ props.row.content }}</span>
                    </el-form-item>
                    <el-row>
                      <router-link :to="{path: '/TrainingObjsEdit', query: {id:props.row.id}}">
                        <el-button type="primary" icon="el-icon-edit"  circle></el-button>
                      </router-link>
                      <el-button type="danger" icon="el-icon-delete" @click="Delete(props.row.id)" circle></el-button>
                    </el-row>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                  label="培养目标ID"
                  prop="id">
              </el-table-column>
              <el-table-column
                  label="培养目标"
                  prop="objective">
              </el-table-column>
              <el-table-column
                  label="描述"
                  prop="content"
                  :show-overflow-tooltip="true">
              </el-table-column>
            </el-table>
          </Content>
          <Content v-if="!show" :style="{padding: '24px', minHeight: '280px', background: '#fff' }">
            <el-row>
              <span>培养目标管理</span>
            </el-row>
            <h1 >请选择培养方案</h1>
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
      show: false,
      tableData: [{
        id: '',
        objective: '',
        content: '',
        fid: ''
      }]
    }
  },
  created() {
    this.show = localStorage.getItem("program") !== null;
    this.$axios.get("/obe/objective/list/" + JSON.parse(localStorage.getItem("program")).id).then(res=>{
      if(res.data.status === "success") {
        this.tableData = res.data.data;
      } else {
        alert("error");
      }
    })
  },methods: {
    Delete(id) {
      this.$axios.delete("/obe/objective/delete/" + id).then(res => {
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
