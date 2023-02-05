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

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
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
              <span>课程管理 / {{this.originCurriculum.name}}</span>
            </el-row>

            <template>
              <Tabs type="card" style="margin-top: 20px">
                <TabPane label="课程大纲">
<!--                  v-model禁止使用this-->
                  <Input v-model="originCurriculum.outline" type="textarea" :autosize="{minRows: 4}"
                         placeholder="Enter something..." :disabled="!editable"/>
                  <Icon type="md-create" @click="Editable" v-if="!editable"/>
                  <Icon type="md-done-all" @click="EditDone" v-if="editable"/>
                </TabPane>
                <TabPane label="评分办法">
                  <Input v-model="originCurriculum.criterion" type="textarea" :autosize="{minRows: 4}"
                         placeholder="Enter something..." :disabled="!editable2"/>
                  <Icon type="md-create" @click="Editable2" v-if="!editable2"/>
                  <Icon type="md-done-all" @click="EditDone2" v-if="editable2"/>
                </TabPane>
                <TabPane label="课程目标">
                  <el-table
                      :data="curriculumData"
                      max-height="550px"
                      style="width: 100%;">
                    <el-table-column
                        label="课程目标 ID"
                        prop="id">
                    </el-table-column>
                    <el-table-column
                        label="课程目标"
                        prop="name">
                    </el-table-column>
                    <el-table-column
                        label="课程目标描述"
                        prop="description"
                        :show-overflow-tooltip="true">
                    </el-table-column>
                  </el-table>
                </TabPane>
              </Tabs>
            </template>
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
      originCurriculum : {},
      editable: false,
      editable2: false,
      tableData: [
        {
          id: '',
          teacher : '',
          name : '',
          open: '',
          outline: '',
          criterion: ''
        }
      ],
      curriculumData: [{
        id : '',
        name: '',
        description: ''
      }]
    }
  },
  created() {
    this.originCurriculum = this.$route.query.curriculum
    if (this.originCurriculum.id === undefined) {
      this.originCurriculum = JSON.parse(localStorage.getItem("editingCur"))
    }

    if (this.originCurriculum === null)
      this.$router.push("CurriculumList")
  }, methods: {
    Editable () {
      this.editable = !this.editable
    },
    EditDone () {
      this.editable = !this.editable
      this.$axios({
        method : "post",
        url : "/obe/cur/edit",
        data : this.originCurriculum,
      })
    }, Editable2 () {
      this.editable2 = !this.editable2
    },
    EditDone2 () {
      this.editable2 = !this.editable2
      this.$axios({
        method : "post",
        url : "/obe/cur/edit",
        data : this.originCurriculum,
      })
    }
  },

}
</script>
