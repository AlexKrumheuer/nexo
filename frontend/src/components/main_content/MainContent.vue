<script setup>
import Activity from './Activity.vue';
import Sidebar from './Sidebar.vue';
import Slider from './Slider.vue';
</script>
<template>
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
                <Slider :slider="'recommended'"/>
                <Slider :slider="'random'"/>
                <Slider :slider="'product'"/>
            </div>
        </div>
</template>
<style scoped>
main {
    height: auto;
    width: 100%;
    background-color: #e9f3fc;
}

.main-content {
    display: flex;
    flex-direction: column;
    gap: 3rem;
    padding: 3rem;
}

.main-content h2 {
    font-size: 1.5rem;
}

.wrapper-activity {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
}

.container-activity {
    display: grid;
    grid-template-columns: 25% 25% 25%;
    width: 100%;
    gap: 1rem;
}

.container-slider {
    background-color: #f68c36;
}

.container-slider-wrapper {
    display: flex;
    flex-direction: column;
    gap: 3rem;
}
</style>