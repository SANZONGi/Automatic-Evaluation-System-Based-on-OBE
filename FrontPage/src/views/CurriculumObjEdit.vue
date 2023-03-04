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
            <el-form :label-position="labelPosition" label-width="100px" :model="curriculumObj">
              <el-form-item label="课程目标 ID" label-width="140px">
                <el-input v-model="curriculumObj.id" style="width: 100px" disabled></el-input>
              </el-form-item>
              <el-form-item label="课程目标" label-width="140px">
                <el-input v-model="curriculumObj.curriculumObj" style="width: 150px"></el-input>
              </el-form-item>
              <el-form-item label="课程目标描述" label-width="140px">
                <el-input v-model="curriculumObj.description" style="width: 150px"></el-input>
              </el-form-item>
              <el-form-item label="课程目标描述权重" label-width="140px">
                <el-input v-model="curriculumObj.weight" style="width: 150px"></el-input> %
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
      curriculumObj: {
        id: '',
        curriculumObj: '',
        weight: '',
        description: '',
        curriculumId: ''
      },
    };
  },
  methods: {
    OnSubmit() {
      this.$axios({
        method: "post",
        url: "/obe/cur_obj/edit",
        data: this.curriculumObj,
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
    this.$axios.get("/obe/cur_obj/" + this.$route.query.id).then((res) => {
      this.curriculumObj = res.data.data
    })
  }
}
</script>
