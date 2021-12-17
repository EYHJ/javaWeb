import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
	  path:'/',
	  name:'main',
	  component: () => import('@/views/main/index.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path:'/register',
    name:'register',
    component: () => import('@/views/register/index.vue')
  },
  {
    path:'/commodity_:id',
    name:'commodity',
    component: () => import('@/views/commodity/index.vue'),
    props: true,
  },
  {
    path: '/order',
    name: 'order',
    component: () => import('@/views/order/index.vue'),
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
