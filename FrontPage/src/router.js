import Vue from 'vue'
import VueRouter from 'vue-router'
import Meta from 'vue-meta'

Vue.use(VueRouter)
Vue.use(Meta)
const Login = () => import('./views/Login')
export default new VueRouter({
    routes: [
        {
            path: '/',
            redirect: {name: "Login"},
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                requireAuth: false
            }
        }
    ]
})
