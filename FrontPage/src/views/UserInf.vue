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
                <FormItem label="Name" prop="name">
                  <Input v-model="formValidate.name" placeholder="Enter your name"/>
                </FormItem>
                <FormItem label="E-mail" prop="email">
                  <Input v-model="formValidate.email" placeholder="Enter your e-mail"/>
                </FormItem>
                <FormItem label="Phone" prop="phone">
                  <Input v-model="formValidate.phone" placeholder="Enter your phone"/>
                </FormItem>
                <FormItem>
                  <Button type="primary" @click="handleSubmit('formValidate')">Submit</Button>
<!--                  <Button @click="handleReset('formValidate')" style="margin-left: 8px">Reset</Button>-->
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
    return {
      formValidate: {
        id: '',
        name: '',
        email: '',
        phone: '',
      },
      ruleValidate: {
        name: [
          { required: true, message: 'The name cannot be empty', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Mailbox cannot be empty', trigger: 'blur' },
          { type: 'email', message: 'Incorrect email format', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: 'Please enter phone', trigger: 'blur'},
          { message: "format error", trigger: "blur", pattern: '^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$'}
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
          this.$axios.post("/obe/user/editInf", this.formValidate)
          this.$Message.success('Success!');
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
