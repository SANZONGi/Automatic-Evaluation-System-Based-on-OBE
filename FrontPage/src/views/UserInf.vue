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
  components: {MyHeader, MySider}
}
</script>
