import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import { createPinia } from 'pinia'
import 'element-plus/dist/index.css'
import './style.css'

createApp(App).use(createPinia()).use(router).use(ElementPlus).mount('#app')
