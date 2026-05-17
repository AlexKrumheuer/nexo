<script setup>
import SliderItemCard from './SliderItemCard.vue'
import { onMounted, ref } from 'vue'
import api from '../../services/api'

const props = defineProps({
    slider: {
        type: String
    }
})

let scrollContainer = ref(null)
let listProducts = ref([])
let listRandom = ref([])
let listLatest = ref([])
const getRandomProducts = async () => {
    try {
        const response = await api.get("/api/products/public/random")
        listRandom.value = response.data
    } catch (e) {
        console.error("Error getting recommended products: ", e)
    }
}

const getLatestProducts = async () => {
    try {
        const response = await api.get("/api/products/public/last")
        listLatest.value = response.data
    } catch (e) {
        console.error("Error getting latest products: ", e)
    }
}

const getListProducts = async () => {
    try {
        const response = await api.get("/api/products/public?page=0&size=10")
        listProducts.value = response.data.content

    } catch (e) {
        console.error("Error getting products: ", e)
    }
}

onMounted(() => {
    getListProducts()
    getLatestProducts()
    getRandomProducts()
})


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
            <h2 v-if="slider == 'recommended'">{{ "Recommended for you" }}</h2>
            <h2 v-else>{{ "Last Products Added" }}</h2>
            <p>View All</p>
        </div>
        <div class="container-slider">
            <button @click="scrollLeft" class="nav-btn prev">
                <fa icon="chevron-left" />
            </button>
            <div class="carousel-track" ref="scrollContainer">
                <router-link :to="{ name: 'Product', params: { slug: item.slug } }" v-for="item in listRandom"
                    v-if="slider == 'recommended'">
                    <SliderItemCard :key="item.id" :product="item" />
                </router-link>
                <router-link :to="{ name: 'Product', params: { slug: itemRandom.slug } }"
                    v-for="itemRandom in listLatest" :key="itemRandom.id" v-else-if="slider == 'random'">
                    <SliderItemCard :key="itemRandom.id" :product="itemRandom" />
                </router-link>
                <router-link :to="{ name: 'Product', params: { slug: product.slug } }" v-for="product in listProducts"
                    :key="product.id" v-else="">
                    <SliderItemCard :key=product.id :product="product" />
                </router-link>

            </div>
            <button @click="scrollRight" class="nav-btn next">
                <fa icon="chevron-right" />
            </button>
        </div>
    </div>
</template>
<style scoped>

.slider {
    width: 80%;
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
}
.slider-title {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.container-slider {
    position: relative;
    display: flex;
    align-items: center;
    width: 100%;
}

.carousel-track {
    display: flex;
    gap: 20px;
    overflow-x: hidden;
    scroll-behavior: smooth;
    scroll-snap-type: x mandatory;
    padding: 10px 5px;
}

.carousel-track > * {
    flex-shrink: 0;
    flex-basis: calc(25% - 20px); 
    max-width: calc(25% - 20px);
    scroll-snap-align: start;
}

.nav-btn {
    position: absolute;
    z-index: 10;
    background: white;
    border: 1px solid #ddd;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    cursor: pointer;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #333;
    transition: transform 0.2s;
}

.nav-btn:hover {
    background-color: #f0f0f0;
    transform: scale(1.1);
}

.prev {
    left: -20px;
}

.next {
    right: -20px;
}

@media (max-width: 768px) {
    .nav-btn {
        display: none;
    }
}

@media (max-width: 1024px) {
    .carousel-track > * {
        flex-basis: calc(50% - 20px);
        max-width: calc(50% - 20px);
    }
}

@media (max-width: 600px) {
    .carousel-track > * {
        flex-basis: calc(100% - 20px);
        max-width: calc(100% - 20px);
    }
}

.slideritem-card {
  background-color: #fff;
  padding: 1rem;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  color: #000;
  min-width: 150px;
  height: 100%; 
  justify-content: space-between;
  cursor: pointer;
  transition: 0.25s;
}

.slideritem-card:hover {
    transform: scale(1.05);
}

.image-container {
  width: 100%;
  height: 160px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f9f9f9;
  border-radius: 12px;
  overflow: hidden; 
  flex-shrink: 0; 
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: contain;
}
</style>