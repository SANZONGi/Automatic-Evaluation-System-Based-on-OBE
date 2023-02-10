import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios";
import Element from 'element-ui'
import iView from 'view-design'
import "./axios"
import "./guard"
import 'view-design/dist/styles/iview.css'
import "element-ui/lib/theme-chalk/index.css"

Vue.use(Element)
Vue.use(iView);

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,//注册store
  render: h => h(App)
}).$mount('#app')
