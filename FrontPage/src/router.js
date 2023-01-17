import Vue from 'vue'
import VueRouter from 'vue-router'
import Meta from 'vue-meta'

Vue.use(VueRouter)
Vue.use(Meta)

const Login = () => import('./views/Login')
const Home = () => import('./views/Home')
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
        },
        {
            path: '/Home',
            name: 'Home',
            component: Home
        }
    ]
})
