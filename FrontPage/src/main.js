import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import "./axios"
import axios from "axios";
import "./guard"
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
Vue.use(VueQuillEditor)
Vue.use(iView);

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(Element)
new Vue({
  router,
  store,//注册store
  render: h => h(App)
}).$mount('#app')
