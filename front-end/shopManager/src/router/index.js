import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  },
  {
	  path: '/main',
	  name: 'name',
	  component: () => import('@/views/main/index.vue'),
	  children:[
		  {
		  	path: '/new',
		  	name: 'new',
		  	component: () => import('@/views/new/index.vue')
		  },{
			path: '/manager',
			name: 'manager',
			component: () => import('@/views/manager/index.vue')
		  },{
			path: '/modify_:id',
			name: 'modify',
			component: () => import('@/views/modify/index.vue'),
			props: true
		  },{
			path: '/summary',
			name: 'summary',
			component: () => import('@/views/summary/index.vue')
		  },
	  ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
