<script setup>
import Login from './components/auth/Login.vue';
</script>

<template>
  <router-view>

  </router-view>
</template>

<style>
  * {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }

  body {
    width: 100vw;
    height: 100vh;
    font-family: "Inter", "Arial";
    font-size: 62.5%;
  }

  #app {
    width: 100%;
    height: 100%;

  }
</style>
