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
              <Tabs type="card" style="margin-top: 20px" v-model="modelValue" :value="modelValue" @on-tab-remove="handleTabRemove">
                <TabPane label="课程大纲">
<!--                  v-model禁止使用this-->
                  <Input v-model="originCurriculum.outline" type="textarea" :autosize="{minRows: 4}"
                         placeholder="Enter something..." :disabled="!editable"/>
                  <Icon type="md-create" @click="Editable" v-if="!editable"/>
                  <Icon type="md-done-all" @click="EditDone" v-if="editable"/>
                </TabPane>
                <TabPane label="课程任务" name="tAssignment">
                  <el-row>
                    <el-button style="float: right" @click="()=>{this.assignmentAdd=true;modelValue='assignmentAdd'}">新增任务</el-button>
                  </el-row>
                  <el-table
                      :data="assignmentData"
                      max-height="550px"
                      style="width: 100%;">
                    <el-table-column
                        label="课程任务 ID"
                        prop="id">
                    </el-table-column>
                    <el-table-column
                        label="课程任务"
                        prop="name">
                    </el-table-column>
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="50px"
                        :show-overflow-tooltip="true">
                      <template slot-scope="scope">
                        <el-button @click="Adetails(scope.row)" type="text" size="small">详情</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </TabPane>
                <TabPane label="评分办法">
                  <Input v-model="originCurriculum.criterion" type="textarea" :autosize="{minRows: 4}"
                         placeholder="Enter something..." :disabled="!editable2"/>
                  <Icon type="md-create" @click="Editable2" v-if="!editable2"/>
                  <Icon type="md-done-all" @click="EditDone2" v-if="editable2"/>
                </TabPane>
                <TabPane label="课程目标" name="tObj">
                  <el-row>
                      <el-button style="float: right" @click="()=>{this.objAdd=true;modelValue='objAdd'}">新增目标</el-button>
                  </el-row>
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
                <TabPane v-if="objAdd" label="课程目标新增" name="objAdd" closable="true">
                  <Form ref="curriculumObj" :model="curriculumObj" label-position="top">
                    <FormItem label="课程目标">
                      <Input v-model="curriculumObj.obj"/>
                    </FormItem>
                    <FormItem label="课程目标描述">
                      <Input v-model="curriculumObj.description"/>
                    </FormItem>
                  </Form>
                  <Button type="primary" @click="handleSubmit()">Submit</Button>
                </TabPane >
                <TabPane v-if="assignmentAdd" label="课程任务新增" name="assignmentAdd" closable="true">
                  <Form ref="curriculumObj" :model="assignmentDetail" label-position="top">
                    <FormItem label="课程任务">
                      <Input v-model="assignment.name"/>
                    </FormItem>
                  </Form>
                  <Button type="primary" @click="handleSubmitAssignment()">Submit</Button>
                </TabPane >
                <TabPane v-if="objDetails" label="课程目标详情" name="objDetails" closable="true">
                  <Form :model="curriculumObj" label-position="top">
                    <FormItem label="课程目标 ID" >
                      <Input v-model="curriculumObjDetail.id" disabled="disabled"/>
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
                <TabPane v-if="assignmentDetails" label="课程任务详情" name="assignmentDetails" closable="true">
                  <Form :model="curriculumObj" label-position="top">
                    <FormItem label="课程任务 ID" >
                      <Input v-model="assignmentDetail.id" disabled="true"/>
                    </FormItem>
                    <FormItem label="课程任务">
                      <Input v-model="assignmentDetail.name"/>
                    </FormItem>
                  </Form>
                  <Button type="primary" @click="handleChangeAssignment()">Submit</Button>
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
      objDetails: false,
      objAdd: false,
      assignmentDetails: false,
      assignmentAdd: false,
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
      curriculumObjDetail: {},
      assignment:{
        id: '',
        name: '',
        curriculumId: ''
      },
      assignmentDetail:{
        id: '',
        name: '',
        curriculumId: ''
      },
      assignmentData: [{
        id: '',
        name: '',
        curriculumId: ''
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
    this.$axios.get("/obe/cur/assignment/list").then(res=>{
      this.assignmentData = res.data.data
    })
  },
  methods: {
    handleTabRemove(name){
      this[name] = false;
      console.log(name)
    },
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
      this.modelValue="tObj"
    },
    handleSubmitAssignment() {
      this.assignment.curriculumId = this.originCurriculum.id
      this.$axios({
        method : "post",
        url : "/obe/cur/assignment/edit",
        data : this.assignment,
      }).then(res =>{
        if (res.data.data === 1) {
          this.$Message['success']({
            background: true,
            content: '添加成功'
          });
        }
      })
      this.modelValue="tAssignment"
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
      this.objDetails = false
    },
    handleChangeAssignment() {
      this.$axios({
        method : "post",
        url : "/obe/cur/assignment/edit",
        data : this.assignmentDetail,
      }).then(res =>{
        if (res.data.data === 1) {
          this.$Message['success']({
            background: true,
            content: '修改成功'
          });
        }
      })
      this.assignmentDetails = false
    },
    details(curriculumObjDetail) {
      this.objDetails = true
      this.curriculumObjDetail = curriculumObjDetail
      this.modelValue = 'objDetails';
    } ,
    Adetails(assignmentDetail) {
      this.assignmentDetails = true
      this.assignmentDetail = assignmentDetail
      this.modelValue = 'assignmentDetails';
    }
  },

}
</script>
