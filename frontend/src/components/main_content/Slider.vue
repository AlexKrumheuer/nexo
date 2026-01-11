<script setup>
import SliderItemCard from './SliderItemCard.vue';
import { ref } from 'vue'
import '../../style/main_content/slider.css'

const scrollContainer = ref(null)

const scrollRight = () => {
    if (scrollContainer.value) {
        scrollContainer.value.scrollBy({
            left: 300,
            behavior: 'smooth'
        })
    }
}

const scrollLeft = () => {
    if (scrollContainer.value) {
        scrollContainer.value.scrollBy({
            left: -300,
            behavior: 'smooth'
        })
    }
}
</script>
<template>
    <div class="slider">
        <div class="slider-title">
            <h2>Recommended for you</h2>
            <p>View All</p>
        </div>
        <div class="container-slider">
            <button @click="scrollLeft" class="nav-btn prev">
                <fa icon="chevron-left" />
            </button>
            <div class="carousel-track" ref="scrollContainer">
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
                <SliderItemCard />
            </div>
            <button @click="scrollRight" class="nav-btn next">
                <fa icon="chevron-right" />
            </button>
        </div>
    </div>
</template>
