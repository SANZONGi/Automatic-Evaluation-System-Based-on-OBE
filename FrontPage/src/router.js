import Vue from 'vue'
import VueRouter from 'vue-router'
import Meta from 'vue-meta'

Vue.use(VueRouter)
Vue.use(Meta)

const Login = () => import('./views/Login')
const Home = () => import('./views/Home')
const UserInf = () => import('./views/UserInf')
const ChangePwd = () => import('./views/ChangePwd')
const Program = () => import('./views/Program')
const ProgramEdit = () => import('./views/ProgramEdit')
const TrainingObjs = () => import('./views/TrainingObjs')
const TrainingObjsEdit = () => import('./views/TrainingObjsEdit')
const GradReqmentEdit = () => import('./views/GradReqmentEdit')
const GradReqment = () => import('./views/GradReqment')
const Subdivision = () => import('./views/Subdivision')
const SubdivisionEdit = () => import('./views/SubdivisionEdit')
const CurriculumList = () => import('./views/CurriculumList')
const CurriculumListEdit = () => import('./views/CurriculumListEdit')
const Curriculum = () => import('./views/Curriculum')
const SubdivisionCurObjMatrix = () => import('./views/SubdivisionCurriculumMatrix.vue')
const CurriculumObjEdit = () => import('./views/CurriculumObjEdit.vue')
const AssignmentEdit = () => import('./views/AssignmentEdit.vue')



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
            component: Home,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/UserInf',
            name: 'UserInf',
            component: UserInf,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/Program',
            name: 'Program',
            component: Program,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/ProgramEdit',
            name: 'ProgramEdit',
            component: ProgramEdit,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/TrainingObjs',
            name: 'TrainingObjs',
            component: TrainingObjs,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/TrainingObjsEdit',
            name: 'TrainingObjsEdit',
            component: TrainingObjsEdit,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/GradReqment',
            name: 'GradReqment',
            component: GradReqment,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/GradReqmentEdit',
            name: 'GradReqmentEdit',
            component: GradReqmentEdit,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/Subdivision',
            name: 'Subdivision',
            component: Subdivision,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/SubdivisionEdit',
            name: 'SubdivisionEdit',
            component: SubdivisionEdit,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/CurriculumList',
            name: 'CurriculumList',
            component: CurriculumList,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/CurriculumListEdit',
            name: 'CurriculumListEdit',
            component: CurriculumListEdit,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/Curriculum',
            name: 'Curriculum',
            component: Curriculum,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/ChangePwd',
            name: 'ChangePwd',
            component: ChangePwd,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/SubdivisionCurObjMatrix',
            name: 'SubdivisionCurObjMatrix',
            component: SubdivisionCurObjMatrix,
            mate: {
                requireAuth: true
            }
        },
        {
            path: '/CurriculumObjEdit',
            name: 'CurriculumObjEdit',
            component: CurriculumObjEdit,
            mate: {
                requireAuth: true
            }
        },
        {
            path: '/AssignmentEdit',
            name: 'AssignmentEdit',
            component: AssignmentEdit,
            mate: {
                requireAuth: true
            }
        }
    ]
})
