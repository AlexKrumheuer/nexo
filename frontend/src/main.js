import { createApp } from 'vue'

import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {library} from '@fortawesome/fontawesome-svg-core'
import { fas} from '@fortawesome/free-solid-svg-icons'

import App from './App.vue'
import router from './router'
import Toast from 'vue-toastification'
import "vue-toastification/dist/index.css";

library.add(fas)

const options = {

}

createApp(App)
.component('fa', FontAwesomeIcon)
.use(router)
.use(Toast, options)
.mount('#app')
