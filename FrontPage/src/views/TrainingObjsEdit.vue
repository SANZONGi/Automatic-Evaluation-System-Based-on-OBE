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
            <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
              <el-form-item label="ID" label-width="100px">
                <el-input v-model="formLabelAlign.id" style="width: 100px" disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="培养目标" label-width="100px">
                <el-input v-model="formLabelAlign.objective" style="width: 150px"></el-input>
              </el-form-item>
              <el-form-item label="描述" label-width="100px">
                <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 20, rows: 8}"
                    placeholder="请输入内容"
                    v-model="formLabelAlign.content">
                </el-input>
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
      formLabelAlign: {
        id: '',
        objective: '',
        content: '',
        fid: ''
      }
    };
  },
  methods : {
    OnSubmit() {
      this.formLabelAlign.fid = JSON.parse(localStorage.getItem("program")).id
      this.$axios({
        method : "post",
        url : "/obe/objective/edit",
        data : this.formLabelAlign
      }).then((res) => {
        if (res.data.data === 1) {
          this.$router.push("TrainingObjs");
        } else {
          alert("error");
        }
      })
    }
  },
  created() {
    this.formLabelAlign.id = this.$route.query.id
  }
}
</script>
