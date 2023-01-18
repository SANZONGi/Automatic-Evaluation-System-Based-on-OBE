<style>
.layout-logo{
width: 100px;
height: 30px;
background: #5b6270;
border-radius: 3px;
float: left;
position: relative;
top: 15px;
left: 20px;
}
.layout-nav{
width: 420px;
margin: 0 20px 0 auto;
}
</style>


<template>
  <Header>
    <Menu mode="horizontal" theme="dark" active-name="1">
      <div class="layout-logo"></div>
      <div class="layout-nav">
        <MenuItem name="1">
          <Icon type="ios-navigate"></Icon>
          Item 1
        </MenuItem>
        <MenuItem name="2">
          <Icon type="ios-keypad"></Icon>
          Item 2
        </MenuItem>
        <MenuItem name="3">
          <Icon type="ios-analytics"></Icon>
          Item 3
        </MenuItem>
        <Dropdown trigger="click" style="margin-left: 20px">
          <Avatar icon="ios-person" size="large" />
          <template #list>
            <DropdownMenu>
              <DropdownItem>信息管理</DropdownItem>
              <DropdownItem>推出登录</DropdownItem>
            </DropdownMenu>
          </template>
        </Dropdown>
      </div>
    </Menu>
  </Header>
</template>


<script>
export default {
  name: "MyHeader",
}
</script>
