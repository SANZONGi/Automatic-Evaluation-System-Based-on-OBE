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
              <span>毕业要求细分点管理</span>
              <el-autocomplete
                  clearable
                  class="inline-input"
                  v-model="keyWord"
                  :fetch-suggestions="querySearch"
                  placeholder="请输入内容"
                  @select="handleSelect"
                  style="float :right"
                  value-key="graduationRequirement"
                  @change="Change"
              ></el-autocomplete>
            </el-row>

            <el-table
                :data="tableData"
                max-height="550px"
                style="width: 100%;">
              <el-table-column type="expand" >
                <template slot-scope="props">
                  <el-form label-position="left" inline class="demo-table-expand">
                    <el-form-item label="ID">
                      <span>{{ props.row.id }}</span>
                    </el-form-item>
                    <el-form-item label="毕业要求">
                      <span>{{ props.row.graduationRequirementId.graduationRequirement }}</span>
                    </el-form-item>
                    <el-form-item label="毕业要求细分点" label-width="120px">
                      <span>{{ props.row.subdivisionPoint }}</span>
                    </el-form-item>
                    <el-form-item label="描述" >
                      <span v-show="!edit">{{ props.row.content }}</span>
                    </el-form-item>
                    <el-row type="flex" justify="center" align="middle">
                      <router-link :to="{path: '/SubdivisionEdit', query: {id:props.row.graduationRequirementId.id}}">
                        <el-button type="primary" icon="el-icon-edit"  circle></el-button>
                      </router-link>
                      <div style="width: 120px;"></div>
                      <el-button type="danger" icon="el-icon-delete" @click="Delete(props.row.id)" circle></el-button>
                    </el-row>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                label="毕业要求细分点"
                prop="subdivisionPoint">
            </el-table-column>
              <el-table-column
                  label="毕业要求"
                  prop="graduationRequirementId.graduationRequirement">
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
      keyWord: '',
      edit: false,
      tableData: [{
        id: '',
        graduationRequirementId: {
          id: '',
          graduationRequirement: '',
          content: ''
        },
        content: '',
        subdivisionPoint: ''
      }],
      fatherId: '',
      fatherList: [],
      pickedList: [],
      originList: []
    }
  },
  created() {
    this.fatherId = this.$route.query.id
    this.$axios.get("/obe/subdivision/list").then(res=>{
      if(res.data.status === "success") {
        this.tableData = res.data.data;
        this.originList = this.tableData.concat()
      } else {
        alert("error");
      }
    })
    this.$axios.get("/obe/gradreqs/list").then(res=>{
      if(res.data.status === "success") {
        this.fatherList = res.data.data;
      } else {
        alert("error");
      }
    })

  },methods: {
    Delete(id) {
      this.$axios.delete("/obe/subdivision/delete/" + id).then(res => {
        if(res.data.status === "success") {
          this.reload();
        } else {
          alert("error");
        }
      })
    },
    querySearch(queryString, cb) {
      var restaurants = this.fatherList;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.graduationRequirement.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },handleSelect() {
      this.tableData = this.originList.concat()

      this.pickedList = []
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].graduationRequirementId.graduationRequirement === this.keyWord) {
            this.pickedList.push(this.tableData[i])
          }
        }
      this.tableData = this.pickedList
    },Change () {
      if (this.keyWord === '') {
        this.tableData = this.originList
      }
    }

  },

}
</script>
