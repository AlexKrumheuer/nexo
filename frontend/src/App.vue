<script setup>
import {useRoute} from  'vue-router'
import './style/style.css'
import Footer from './components/Footer.vue'
import Header from './components/Header.vue'
import Sidebar from './components/main_content/Sidebar.vue'
const route = useRoute()
</script>

<template>
  <Header v-if="!route.meta.hideNavigation"/>

  <div class="app-layout">
    
    <aside v-if="!route.meta.hideSidebar" class="app-sidebar">
      <Sidebar />
    </aside>

    <main class="app-page-content" :class="{ 'full-width': route.meta.hideNavigation }">
      <router-view></router-view>
    </main>

  </div>

  <Footer v-if="!route.meta.hideNavigation"/>
</template>

<style scoped>
.app-layout {
  display: flex;
  min-height: 80vh; 
  max-width: 100vw; 
  margin: 0 auto;
}

.app-sidebar {
  width: 250px; 
  flex-shrink: 0; 
  border-right: 1px solid #eee; 
}

.app-page-content {
  flex-grow: 1; 
  width: 100%; 
}

.app-page-content.full-width {
  padding: 0; 
}

@media (max-width: 768px) {
  .app-layout {
    flex-direction: column;
  }
  .app-sidebar {
    width: 100%;
    display: none;
  }
}
</style>