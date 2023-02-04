<template>
  <Menu theme="light" width="auto" :open-names="[1]">
    <Submenu name="1">
      <template #title>
        <Icon type="ios-navigate"></Icon>
        培养方案
      </template>
      <router-link to="Program">
        <MenuItem name="1-1" >培养方案管理</MenuItem>
      </router-link>
      <router-link to="TrainingObjs">
        <MenuItem name="1-2" >培养目标管理</MenuItem>
      </router-link>
      <MenuItem name="1-3">Option 3</MenuItem>
    </Submenu>
    <Submenu name="2">
      <template #title>
        <Icon type="ios-keypad"></Icon>
        毕业要求
      </template>
      <router-link to="GradReqment">
        <MenuItem name="2-1">毕业要求管理</MenuItem>
      </router-link>
      <router-link to="Subdivision">
        <MenuItem name="2-2">毕业要求细分点管理</MenuItem>
      </router-link>
      <MenuItem name="2-3">毕业要求-培养目标矩阵</MenuItem>
    </Submenu>
    <Submenu name="3">
      <template #title>
        <Icon type="ios-analytics"></Icon>
        课程体系
      </template>
      <router-link to="CurriculumList">
        <MenuItem name="3-1">课程体系管理</MenuItem>
      </router-link>
    </Submenu>
    <Submenu name="4">
      <template #title>
        <Icon type="ios-analytics"></Icon>
        课程管理
      </template>
      <MenuItem name="4-1">课程大纲管理</MenuItem>
      <MenuItem name="4-2">课程目标管理</MenuItem>
      <MenuItem name="4-3">学业活动考核标准管理
      </MenuItem>
      <MenuItem name="4-4">学生成绩-课程目标分数支撑矩阵
      </MenuItem>
      <MenuItem name="4-5">学生成绩管理
      </MenuItem>
    </Submenu>
  </Menu>
</template>

<script>
export default  {

}
</script>