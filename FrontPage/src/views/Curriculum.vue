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
              <span>课程管理 / {{ this.originCurriculum.name }}</span>
            </el-row>

            <template>
              <Tabs type="card" style="margin-top: 20px;" v-model="modelValue" :value="modelValue"
                    @on-tab-remove="handleTabRemove" >
                <TabPane label="课程大纲">
                  <!--                  v-model禁止使用this-->
                  <Input v-model="originCurriculum.outline" type="textarea" :autosize="{minRows: 4}"
                         placeholder="Enter something..." :disabled="!editable"/>
                  <Icon type="md-create" @click="editable=!editable" v-if="!editable"/>
                  <Icon type="md-done-all" @click="EditDone" v-if="editable"/>
                </TabPane>
                <TabPane label="课程任务" name="tAssignment">
                  <el-row>
                    <el-button style="float: right" @click="$router.push('AssignmentEdit')">
                      新增任务
                    </el-button>
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
                        <el-button @click="Adetails(scope.row.id)" type="text" size="small">详情</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </TabPane>
                <TabPane label="评分办法">
                  <Input v-model="originCurriculum.criterion" type="textarea" :autosize="{minRows: 4}"
                         placeholder="Enter something..." :disabled="!editable2"/>
                  <Icon type="md-create" @click="editable2 = !editable2" v-if="!editable2"/>
                  <Icon type="md-done-all" @click="EditDone2" v-if="editable2"/>
                </TabPane>
                <TabPane label="课程目标" name="tObj">
                  <el-row>
                    <el-button style="float: right" @click="$router.push('curriculumObjEdit')">新增目标
                    </el-button>
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
                        <el-button @click="details(scope.row.id)" type="text" size="small">详情</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </TabPane>
                <TabPane label="课程目标支撑矩阵" name="martix">
                  <el-table :data="matrix" style="width: 100%" border :summary-method="getSummaries" show-summary
                            @cell-click="selectCell">
                    <el-table-column fixed prop="assignment.name" label="课程任务\课程目标"
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
                </TabPane>
                <TabPane label="成绩管理" name="score">
                  <el-row>
                    <el-upload
                        class="upload-demo"
                        action
                        :on-change="handleFileChange"
                        :on-exceed="handleExceed"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        :limit="limitUpload"
                        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                        :auto-upload="false">
                      <el-button size="small" type="primary">选择文件</el-button>
                      <div slot="tip" class="el-upload__tip">只 能 上 传 xlsx / xls 文 件</div>
                    </el-upload>
                  </el-row>
                  <el-table :data="studentModel" style="width: 100%" border>
                    <el-table-column fixed prop="name" label="课程任务\课程目标"
                                     width="180"></el-table-column>
                    <el-table-column v-for="(i, index) in studentModel"
                                     :label="i.detail[index].assignment" align="center"
                                     header-align="center" :key="index">
                      <template v-slot="scope">{{ scope.row.detail[index].score }}</template>
                    </el-table-column>
                  </el-table>

                </TabPane>
                <TabPane label="课程目标达成度" name="achievement">
                  <el-table :data="achievementMatrix" style="width: 100%" border>
                    <el-table-column fixed prop="name" label="课程任务\课程目标"
                                     width="180"></el-table-column>
                    <el-table-column v-for="(i, index) in achievementMatrix"
                                     :label="i.curObjAchievement[index].curObj" align="center"
                                     header-align="center" :key="index">
                      <template v-slot="scope">{{ scope.row.curObjAchievement[index].achievement }}</template>
                    </el-table-column>
                  </el-table>
                </TabPane>
                <TabPane label="课程达成度" name="curAchievement">
                  <el-table
                      :data="curriculumAchievementData"
                      max-height="550px"
                      style="width: 100%;">
                    <el-table-column
                        label="学号"
                        prop="id">
                    </el-table-column>
                    <el-table-column
                        label="姓名"
                        prop="name">
                    </el-table-column>
                    <el-table-column
                        label="达成度"
                        prop="achievement"
                    >
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
import router from "@/router";

export default {
  inject: ['reload'],
  components: {MyHeader, MySider},
  data() {
    return {
      curriculumAchievementData: [],
      calculate: '',
      achievementMatrix: [{
        name: '',
        curObjAchievement: [{
          achievement: '',
          curObj: ''
        }]
      }],
      studentModel: [
        {
          curId: '',
          detail:
              {
                name: '',
                detail: [{assignment: '', score: ''}]
              }
        }
      ],
      limitUpload: 1,
      fileTemp: "",
      file: "",
      fileListUpload: [],
      dialogFormVisible: false,
      matrix: [{
        assignment: {id: '', name: ''},
        objArray: [
          {
            curriculumObj: {
              curriculumObj: '',
              id: ''
            },
            weight: ''
          }
        ]
      }],
      weightCount: [{
        objId: '',
        obj: '',
        total: ''
      }],
      modelValue: "",
      originCurriculum: {},
      editable: false,
      editable2: false,
      tableData: [
        {
          id: '',
          teacher: '',
          name: '',
          open: '',
          outline: '',
          criterion: ''
        }
      ],
      curriculumData: [{
        id: '',
        curriculumObj: '',
        description: ''
      }],
      assignment: {
        id: '',
        name: '',
        curriculumId: ''
      },
      assignmentData: [{
        id: '',
        name: '',
        curriculumId: ''
      }],
      cellEdit: {
        curriculumObjId: '',
        assignmentId: '',
        weight: ''
      },
    }
  },
  created() {
    this.originCurriculum = this.$route.query.curriculum
    if (this.originCurriculum.id === undefined) {
      this.originCurriculum = JSON.parse(localStorage.getItem("editingCur"))
    }
    if (this.originCurriculum === null) this.$router.push("CurriculumList")
    // 课程目标
    this.$axios.get("/obe/cur_obj/list/" + this.originCurriculum.id).then(res => {
      if (res.data.status === "success") {
        this.curriculumData = res.data.data
      } else {
        this.$Message["error"]({
          background: true,
          content: '课程目标获取失败'
        });
      }
    })
    // 课程任务
    this.$axios.get("/obe/cur/assignment/list/" + this.originCurriculum.id).then(res => {
      this.assignmentData = res.data.data
    })
    // 支撑矩阵
    this.$axios.get("/obe/cur_obj/matrix/" + this.originCurriculum.id).then(res => {
      this.matrix = res.data.data
    })
    // 学生分数
    this.$axios.get("/obe/cur/score/" + this.originCurriculum.id).then(res => {
      this.studentModel = res.data.data
    })
    // 课程目标达成度
    this.$axios.get("/obe/cur_obj/matrix/achievement/" + this.originCurriculum.id).then(res=>{
      this.achievementMatrix = res.data.data
    })

    this.modelValue = localStorage.getItem("actived_cur_tabpane")
  },
  methods: {
    router() {
      return router
    },
    handleFileChange(file) {
      this.fileTemp = file.raw;
      let form = new FormData
      form.append("file", file.raw)
      if (this.fileTemp) {
        if (this.fileTemp.type === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ||
            this.fileTemp.type === "application/vnd.ms-excel") {
          let instance = this.$axios.create({
            baseURL: 'http://localhost:8081',
            data: form,
            headers: {'Content-Type': 'multipart/form-data'}
          })
          instance.post("/obe/cur/excel", form).then(res => {
            console.log(res)
          })
          this.$message({
            type: "info",
            message: "选择成功"
          })
        } else {
          this.$message({
            type: "warning",
            message: "附件格式错误，请删除后重新上传!"
          });
        }
      }
      localStorage.setItem("actived_cur_tabpane", this.modelValue)
      this.reload()
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    handleRemove() {
      this.fileTemp = null;
    },
    handleExceed(files, fileList) {
      this.$message.warning(
          `当前限制选择1个文件，本次选择了 ${
              files.length
          } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },


    handleTabRemove(name) {
      this[name] = false;
    }
    ,
    EditDone() {
      this.editable = !this.editable
      this.$axios({
        method: "post",
        url: "/obe/cur/edit",
        data: this.originCurriculum,
      })
    }
    ,
    EditDone2() {
      this.editable2 = !this.editable2
      this.$axios({
        method: "post",
        url: "/obe/cur/edit",
        data: this.originCurriculum,
      })
    }
    ,
    handleSubmit() {
      this.curriculumObj.curriculumId = this.originCurriculum.id
      this.$axios({
        method: "post",
        url: "/obe/cur_obj/edit",
        data: this.curriculumObj,
      }).then(res => {
        if (res.data.data === 1) {
          this.$refs["curriculumObj"].resetFields()
          this.$Message['success']({
            background: true,
            content: '添加成功'
          });
        }
      })
      this.modelValue = "tObj"
    }
    ,
    details(id) {
      this.$router.push({path: '/CurriculumObjEdit', query: {id : id }})
    }
    ,
    Adetails(id) {
      this.$router.push({path: '/AssignmentEdit', query: {id : id }})
    }
    ,
    getSummaries() {
      const sums = []
      sums[0] = '总计';
      for (let i = 0; i < this.matrix.length; i++) {
        let objA = this.matrix[i].objArray;
        for (let j = 0; j < objA.length; j++) {
          if (sums[j + 1] === undefined) sums[j + 1] = Number(objA[j].weight);
          else sums[j + 1] += Number(objA[j].weight)
        }
      }
      return sums;
    }
    ,
    selectCell(row, column) {
      this.dialogFormVisible = true
      this.cellEdit.assignmentId = row.assignment.id
      for (let i = 0; i < row.objArray.length; i++) {
        let tmp = row.objArray[i];
        if (tmp.curriculumObj.curriculumObj === column.label) {
          this.cellEdit.curriculumObjId = tmp.curriculumObj.id
          this.cellEdit.weight = tmp.weight
        }
      }
    }
    ,
    doneDialog() {
      this.dialogFormVisible = false
      for (let i = 0; i < this.matrix.length; i++) {
        if (this.matrix[i].assignment.id === this.cellEdit.assignmentId) {
          let objArr = this.matrix[i].objArray
          for (let j = 0; j < objArr.length; j++) {
            if (objArr[j].curriculumObj.id === this.cellEdit.curriculumObjId) {
              this.matrix[i].objArray[j].weight = this.cellEdit.weight
            }
          }
        }
      }
      this.getSummaries()
      this.$axios.post("/obe/cur_obj/matrix/updateWeight", this.cellEdit);

    }
    ,

  }
  ,

}
</script>
