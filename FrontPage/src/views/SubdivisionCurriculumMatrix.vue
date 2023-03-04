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
              <span>毕业要求细分点-课程支撑矩阵</span>
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

            <el-table :data="matrix" style="width: 100%;margin-top: 30px" border :summary-method="getSummaries" show-summary
                      @cell-click="selectCell">
              <el-table-column fixed prop="assignment.name" label="课程\毕业要求细分点"
                               width="180"></el-table-column>
              <el-table-column v-for="(i, index) in matrix"
                               :label="i.objArray[index].curriculumObj.curriculumObj+'  (%)'" align="center"
                               header-align="center" :key="index">
                <template v-slot="scope">{{ scope.row.objArray[index].weight }}</template>
                <el-dialog title="权重修改" :visible.sync="dialogFormVisible" append-to-body>
                  <el-form>
                    <el-form-item label="比例" label-width="50px">
                      <el-input v-model="cellEdit.weight" autocomplete="off"></el-input>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="doneDialog">确 定</el-button>
                  </div>
                </el-dialog>
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
      matrix: [

      ],
      cellEdit: {},
      dialogFormVisible: false,
      keyWord: '',
    }
  },
  created() {

  },methods: {
    querySearch(queryString, cb) {
      let list = this.fatherList;
      let results = queryString ? list.filter(this.createFilter(queryString)) : list;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (obj) => {
        return (obj.graduationRequirement.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    handleSelect() {
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
    },
    selectCell() {

    },
    doneDialog() {

    }
  }
}
</script>
