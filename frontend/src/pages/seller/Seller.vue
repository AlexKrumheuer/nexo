<script setup>
import SidebarSeller from './SidebarSeller.vue';

</script>

<template>
  <div class="app-layout">
    
    <aside class="app-sidebar">
        <SidebarSeller></SidebarSeller>
    </aside>

    <main class="app-page-content">
      <router-view></router-view>
    </main>
  </div>

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