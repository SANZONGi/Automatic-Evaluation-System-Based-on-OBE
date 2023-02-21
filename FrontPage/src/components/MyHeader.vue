<style>
.layout-logo {
  width: 100px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
}

.layout-nav {
  width: 420px;
  margin: 0 20px 0 auto;
}
</style>


<template>
  <Header>
    <Menu mode="horizontal" theme="dark" active-name="1">
      <div class="layout-logo"></div>
      <div class="layout-nav">
        <MenuItem name="3">
          <el-select v-model="value" placeholder="请选择培养方案版本" @change="Change" value-key="id">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item">
            </el-option>
          </el-select>
        </MenuItem>
        <Dropdown trigger="click" style="margin-left: 20px">
          <Avatar icon="ios-person" size="large"/>
          <template #list>
            <DropdownMenu>
              <router-link to="UserInf">
                <DropdownItem>
                  信息管理
                </DropdownItem>
              </router-link>
                <DropdownItem>
                  <span @click="logout">退出登录</span>
                </DropdownItem>
            </DropdownMenu>
          </template>
        </Dropdown>
      </div>
    </Menu>
  </Header>
</template>


<script>
export default {
  inject: ['reload'],
  name: "MyHeader",
  data() {
    return {
      options: [{
        id: '',
        name: '',
        grade: '',
        description: ''
      }],
      value: ''
    }
  },
  created() {
    if (localStorage.getItem("program") !== null) {
      this.value = JSON.parse(localStorage.getItem("program"))
    }
    this.$axios.get("/obe/program/list").then(res => {
      if (res.data.status === "success") {
        this.options = res.data.data;
      } else {
        alert("error");
      }
    })
  }, methods: {
    Change() {
      localStorage.setItem("program", JSON.stringify(this.value))
      this.reload()
    },
    logout () {
      this.$store.commit("REMOVE_INFO")
      this.$router.push("Login")
      this.$Message.info("退出成功")
    }
  }
}
</script>
