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
              <Tabs type="card" style="margin-top: 20px" v-model="modelValue" :value="modelValue">
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
                        prop="curriculumObj">
                    </el-table-column>
                    <el-table-column
                        label="课程目标描述"
                        prop="description"
                        >
                    </el-table-column>
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="50px"
                        :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <el-button @click="details(scope.row)" type="text" size="small">详情</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </TabPane>
                <TabPane label="课程目标新增">
                  <Form ref="curriculumObj" :model="curriculumObj" label-position="top">
                    <FormItem label="课程目标">
                      <Input v-model="curriculumObj.obj"/>
                    </FormItem>
                    <FormItem label="课程目标描述">
                      <Input v-model="curriculumObj.description"/>
                    </FormItem>
                  </Form>
                  <Button type="primary" @click="handleSubmit()">Submit</Button>
                </TabPane>
                <TabPane label="课程目标详情" disabled="disabled" name="detail">
                  <Form :model="curriculumObj" label-position="top">
                    <FormItem label="课程目标 ID" disabled="disabled">
                      <Input v-model="curriculumObjDetail.id"/>
                    </FormItem>
                    <FormItem label="课程目标">
                      <Input v-model="curriculumObjDetail.curriculumObj"/>
                    </FormItem>
                    <FormItem label="课程目标描述">
                      <Input v-model="curriculumObjDetail.description"/>
                    </FormItem>
                  </Form>
                  <Button type="primary" @click="handleChange()">Submit</Button>
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
      modelValue: "",
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
        curriculumObj: '',
        description: ''
      }],
      curriculumObj: {
        id : '',
        obj : '',
        description: ''
      },
      curriculumObjDetail: {}
    }
  },
  created() {
    this.originCurriculum = this.$route.query.curriculum
    if (this.originCurriculum.id === undefined) {
      this.originCurriculum = JSON.parse(localStorage.getItem("editingCur"))
    }

    if (this.originCurriculum === null)
      this.$router.push("CurriculumList")

    this.$axios.get("/obe/cur_obj/list").then(res => {
      if (res.data.status === "success") {
        this.curriculumData = res.data.data
        console.log(res)
      } else {
        this.$Message["error"]({
          background: true,
          content: '课程目标获取失败'
        });
      }
    })
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
    },
    Editable2 () {
      this.editable2 = !this.editable2
    },
    EditDone2 () {
      this.editable2 = !this.editable2
      this.$axios({
        method : "post",
        url : "/obe/cur/edit",
        data : this.originCurriculum,
      })
    },
    handleSubmit() {
      this.$axios({
        method : "post",
        url : "/obe/cur_obj/edit",
        data : this.curriculumObj,
      }).then(res =>{
        if (res.data.data === 1) {
          this.$refs["curriculumObj"].resetFields()
          this.$Message['success']({
            background: true,
            content: '添加成功'
          });
        }
      })
    },
    handleChange() {
      this.$axios({
        method : "post",
        url : "/obe/cur_obj/edit",
        data : this.curriculumObjDetail,
      }).then(res =>{
        if (res.data.data === 1) {
          this.$Message['success']({
            background: true,
            content: '修改成功'
          });
        }
      })
    },
    details(curriculumObjDetail) {
      this.curriculumObjDetail = curriculumObjDetail
      this.modelValue = 'detail';
    }
  },

}
</script>
