<template>
  <el-container class="back">
    <el-header>
      <h1 style="height: 50px">基于OBE的课程目标和毕业要求达成度自动化评价系统</h1>
    </el-header>
    <el-main>
      <div class="form">
        <div style="background-color: #17233d; height: 15%;top:-5%; position: relative">
          <h2 style="position:relative;align-content: center;top: 10px">登录</h2>
        </div>
        <el-form :model="form" status-icon ref="form" label-width="20px">
          <el-form-item class="item" prop="username">
            <el-input prefix-icon="el-icon-user-solid" v-model="form.account" style="width: 250px"
                      placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item class="item" prop="pwd">
            <el-input prefix-icon="el-icon-edit" type="password" v-model="form.pwd" style="width: 250px"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item class="item" style="margin-left: 0">
            <el-button type="primary" @click="onSubmit" class="el-button">登录</el-button>
          </el-form-item>
          <router-link :to="{name: 'Register'}" style="color:black;float: right;margin-right: 20px;margin-top: 10px">
            注册新账号
          </router-link>
        </el-form>
      </div>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      BASE64: '',
      activeName: '',
      input4: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      form: {
        account: '',
        pwd: ''
      },
    };
  },
  methods: {
    onSubmit() {
      this.$axios({
        method: 'post',
        url: '/obe/user/verify',
        data: this.form
      }).then((res) => {
        // console.log(this.form)
        //登录失败
        if (res.data.status !== 'success') {
          alert(res.data.msg)
        } else {
          //登录成功
          this.$store.commit("SET_TOKEN", res.data.msg)//改变SET_TOKEN中的值，用res.data.msg传回store.js中代替原本store中的token值
          this.$store.commit("SET_USERINF", res.data.data)
          this.$router.push("Home")
        }
      })
    },
    created() {
      this.reload()
    }
  }
};
</script>


<style scoped>

.item {
  margin: 40px auto;
}

.el-button {
  margin: 0 auto;
}

.back {
  background: url("../assets/v.jpg");
  background-size: 100% 100%;
  height: 100%;
  position: fixed;
  width: 100%;
  top: 0px;
  left: 0px;
  text-align: center;
}

.form {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12);
  width: 450px;
  height: 55%;
  background-color: whitesmoke;
  position: absolute;
  top: 20%;
  left: 35%;
  border-radius: 0;
}
</style>
