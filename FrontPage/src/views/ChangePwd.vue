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
            个人信息管理
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" style="margin-top: 50px">
              <FormItem label="原密码" prop="pwd">
                <Input v-model="formValidate.pwd" placeholder="Enter your password"/>
              </FormItem>
              <FormItem label="新密码" prop="newPwd">
                <Input v-model="formValidate.newPwd" placeholder="Enter your new password"/>
              </FormItem>
              <FormItem label="再次输入" prop="newPwd2">
                <Input v-model="formValidate.newPwd2" placeholder="Enter your new password again"/>
              </FormItem>
              <FormItem>
                <Button type="primary" @click="handleSubmit('formValidate')">Submit</Button>
<!--                <Button @click="handleReset('formValidate')" style="margin-left: 8px">Reset</Button>-->
              </FormItem>
            </Form>

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
  data () {
    const equalToPassword = (rule, value, callback) => {
      if (this.formValidate.newPwd !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      formValidate: {
        account: '',
        pwd: '',
        newPwd: '',
        newPwd2: '',
      },

      ruleValidate: {
        pwd: [
          { required: true, message: 'The password cannot be empty', trigger: 'blur' }
        ],
        newPwd: [
          { required: true, message: 'The new password cannot be empty', trigger: 'blur' },
        ],
        newPwd2: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.formValidate = JSON.parse(sessionStorage.getItem("userInfo"))
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.$axios.post("/obe/user/changePwd", this.formValidate).then(res=>{
            if (res.data.status === "success") {
              this.$Message.success('Success!');
            } else {
              this.$Message.error("Error")
            }
          })
        } else {
          this.$Message.error('Fail!');
        }
      })
    },
    handleReset (name) {
      this.$refs[name].resetFields();
    }
  }
}
</script>
