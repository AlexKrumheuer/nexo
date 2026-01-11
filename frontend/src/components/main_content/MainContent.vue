<script setup>
import Activity from './Activity.vue';
import Sidebar from './Sidebar.vue';
import Slider from './Slider.vue';
import '../../style/main_content/mainContent.css'
</script>
<template>
    <main>
        <Sidebar />
        <div class="main-content">
            <div class="wrapper-activity">
                <h2>Recent Activity</h2>
                <div class="container-activity">
                    <Activity />
                    <Activity />
                    <Activity />
                </div>
            </div>
            <div class="container-slider-wrapper">
                <Slider />
                <Slider />
                <Slider />
                <Slider />
            </div>
        </div>
    </main>
</template>
