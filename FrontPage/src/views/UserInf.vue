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
          <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']">
            <Submenu name="1">
              <template #title>
                <Icon type="ios-navigate"></Icon>
                Item 1
              </template>
              <MenuItem name="1-1">Option 1</MenuItem>
              <MenuItem name="1-2">Option 2</MenuItem>
              <MenuItem name="1-3">Option 3</MenuItem>
            </Submenu>
            <Submenu name="2">
              <template #title>
                <Icon type="ios-keypad"></Icon>
                Item 2
              </template>
              <MenuItem name="2-1">Option 1</MenuItem>
              <MenuItem name="2-2">Option 2</MenuItem>
            </Submenu>
            <Submenu name="3">
              <template #title>
                <Icon type="ios-analytics"></Icon>
                Item 3
              </template>
              <MenuItem name="3-1">Option 1</MenuItem>
              <MenuItem name="3-2">Option 2</MenuItem>
            </Submenu>
          </Menu>
        </Sider>
          <Layout :style="{padding: '14px 24px 24px'}">
            <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
              个人信息管理


            </Content>
          </Layout>
      </Layout>
    </Layout>
  </div>
</template>


<script>
import MyHeader from '@/components/MyHeader'
export default {
  components: {MyHeader}
}
</script>
