import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index.js'
import axiosPlugin from './plugins/axios.js'
import elementPlugin from './plugins/element.js'

import './assets/main.css'

const app = createApp(App)

app.use(store)
app.use(router)
app.use(axiosPlugin)
app.use(elementPlugin)

app.mount('#app')
