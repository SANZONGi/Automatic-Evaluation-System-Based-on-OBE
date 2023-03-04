<template>
  <Menu theme="light" width="auto" :open-names="active_names" :active-name="active" @on-select="Select"
        @on-open-change="openChange">
    <Submenu name="1">
      <template #title>
        <Icon type="ios-navigate"></Icon>
        培养方案
      </template>
      <MenuItem name="1-1" to="Program">培养方案管理</MenuItem>
      <MenuItem name="1-2" to="TrainingObjs">培养目标管理</MenuItem>
      <MenuItem name="1-3">Option 3</MenuItem>
    </Submenu>
    <Submenu name="2">
      <template #title>
        <Icon type="ios-keypad"></Icon>
        毕业要求
      </template>
      <MenuItem name="2-1" to="GradReqment">毕业要求管理</MenuItem>
      <MenuItem name="2-2" to="Subdivision">毕业要求细分点管理</MenuItem>
      <MenuItem name="2-3" to="SubdivisionCurObjMatrix">毕业要求细分点-培养目标矩阵</MenuItem>
    </Submenu>
    <Submenu name="3">
      <template #title>
        <Icon type="ios-analytics"></Icon>
        课程体系
      </template>
      <MenuItem name="3-1" to="CurriculumList">课程体系管理</MenuItem>
    </Submenu>
    <Submenu name="4">
      <template #title>
        <Icon type="ios-analytics"></Icon>
        课程管理
      </template>
      <MenuItem name="4-1">课程大纲管理</MenuItem>
      <MenuItem name="4-2">课程目标管理</MenuItem>
      <MenuItem name="4-3">学业活动考核标准管理</MenuItem>
      <MenuItem name="4-4">学生成绩-课程目标分数支撑矩阵</MenuItem>
      <MenuItem name="4-5">学生成绩管理</MenuItem>
    </Submenu>
    <Submenu name="5">
      <template #title>
        <Icon type="md-person" />
        信息管理
      </template>
      <MenuItem name="5-1" to="UserInf">个人信息</MenuItem>
      <MenuItem name="5-1" to="ChangePwd">修改密码</MenuItem>
    </Submenu>
  </Menu>
</template>

<script>
export default {
  data() {
    return {
      active: '',
      active_names: []
    }
  }, methods: {
    Select(name) {
      this.active = name
      localStorage.setItem("active-item-name", name)
    },
    openChange(names) {
      this.active_names = names
      localStorage.setItem("active-menu-name", JSON.stringify(names))
    }
  }, created() {
    this.active = localStorage.getItem("active-item-name")
    this.active_names = JSON.parse(localStorage.getItem("active-menu-name"))
  }
}
</script>