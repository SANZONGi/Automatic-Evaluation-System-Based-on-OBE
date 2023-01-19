import Vue from 'vue'
import VueRouter from 'vue-router'
import Meta from 'vue-meta'

Vue.use(VueRouter)
Vue.use(Meta)

const Login = () => import('./views/Login')
const Home = () => import('./views/Home')
const UserInf = () => import('./views/UserInf')
const Program = () => import('./views/Program')
const ProgramEdit = () => import('./views/ProgramEdit')
const TrainingObjs = () => import('./views/TrainingObjs')
const TrainingObjsEdit = () => import('./views/TrainingObjsEdit')
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
        },
        {
            path: '/UserInf',
            name: 'UserInf',
            component: UserInf
        },
        {
            path: '/Program',
            name: 'Program',
            component: Program
        },
        {
            path: '/ProgramEdit',
            name: 'ProgramEdit',
            component: ProgramEdit
        },
        {
            path: '/TrainingObjs',
            name: 'TrainingObjs',
            component: TrainingObjs
        },
        {
            path: '/TrainingObjsEdit',
            name: 'TrainingObjsEdit',
            component: TrainingObjsEdit
        }
    ]
})
