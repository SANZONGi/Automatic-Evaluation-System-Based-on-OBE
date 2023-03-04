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

</style>


<template>
  <div class="layout">
    <Layout>
      <MyHeader/>
      <Layout>
        <Sider hide-trigger :style="{background: '#fff'}">
          <MySider/>
        </Sider>
        <Layout :style="{padding: '14px 24px 24px'}">
          <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
            <el-form :label-position="labelPosition" label-width="100px" :model="assignmentDetail">
              <el-form-item label="课程任务 ID" label-width="140px">
                <el-input v-model="assignmentDetail.id" style="width: 100px" disabled></el-input>
              </el-form-item>
              <el-form-item label="课程任务" label-width="140px">
                <el-input v-model="assignmentDetail.name" style="width: 150px"></el-input>
              </el-form-item>
              <el-form-item label="所属课程" label-width="140px">
                <el-input v-model="assignmentDetail.curriculumId" style="width: 150px"></el-input>
              </el-form-item>
            </el-form>
            <el-row type="flex" justify="center" align="middle">
              <el-button type="primary" @click="OnSubmit">确定</el-button>
            </el-row>
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
  components: {MyHeader, MySider},
  data() {
    return {
      labelPosition: 'right',
      assignmentDetail: {
        id: '',
        name: '',
        curriculumId: ''
      },
    };
  },
  methods: {
    OnSubmit() {
      this.$axios({
        method: "post",
        url: "/obe/cur/assignment/edit",
        data: this.assignmentDetail,
      }).then(res => {
        if (res.data.data === 1) {
          this.$Message['success']({
            background: true,
            content: '修改成功'
          });
        }
      })
    }
  },
  created() {
    this.$axios.get("/obe/cur/assignment/" + this.$route.query.id).then((res) => {
      this.assignmentDetail = res.data.data
      console.log(this.assignmentDetail)
    })
  }
}
</script>
