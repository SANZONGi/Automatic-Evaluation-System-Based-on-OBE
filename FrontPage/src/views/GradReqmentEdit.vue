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
            <el-form :label-position="labelPosition" label-width="80px" :model="form">
              <el-form-item label="ID" label-width="100px">
                <el-input v-model="form.id" style="width: 100px" disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="毕业要求" label-width="100px">
                <el-input v-model="form.name" style="width: 150px"></el-input>
              </el-form-item>
              <el-form-item label="描述" label-width="100px">
                <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 20, rows: 8}"
                    placeholder="请输入内容"
                    v-model="form.content">
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
      form: {
        id: '',
        name: '',
        content: ''
      }
    };
  },
  methods : {
    OnSubmit() {
      console.log(this.form)
      this.$axios({
        method : "post",
        url : "/obe/gradreqs/edit",
        data : this.form
      }).then((res) => {
        if (res.data.data === 1) {
          this.$router.push("GradReqment");
        } else {
          alert("error");
        }
      })
    }
  },
  created() {
    this.form.id = this.$route.query.id
  }
}
</script>
