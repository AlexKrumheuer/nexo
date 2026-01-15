<script setup>
import {useRoute} from  'vue-router'
import './style/style.css'
import Footer from './components/Footer.vue';
import Header from './components/Header.vue';
const route = useRoute()
</script>

<template>
  <Header v-if="!route.meta.hideNavigation"/>
  <router-view>

  </router-view>
  <Footer v-if="!route.meta.hideNavigation"/>
</template>

<style>
  
</style>
