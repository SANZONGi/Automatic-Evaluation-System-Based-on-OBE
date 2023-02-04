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
              <span>课程管理 / {{this.originCurriculum.name}}</span>
            </el-row>

            <template>
              <Tabs type="card" style="margin-top: 20px">

                <TabPane label="课程大纲">
<!--                  v-model禁止使用this-->
                  <Input v-model="originCurriculum.outline" type="textarea" :autosize="{minRows: 2}"
                         placeholder="Enter something..." :disabled="!editable"/>
                  <Icon type="md-create" @click="Editable" v-if="!editable"/>
                  <Icon type="md-done-all" @click="EditDone" v-if="editable"/>
                </TabPane>

                <TabPane label="评分办法">标签二的内容</TabPane>
                <TabPane label="标签三">标签三的内容</TabPane>
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
      console.log(this.originCurriculum.outline)
      this.$axios({
        method : "post",
        url : "/obe/cur/edit",
        data : this.originCurriculum,
      }).then((res) => {
        if (res.data.data === 1) {
          this.$router.push("CurriculumList");
        } else {
          alert("error");
        }
      })
    }
  },

}
</script>
