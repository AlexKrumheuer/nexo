<script setup>
import SliderItemCard from './SliderItemCard.vue'
import { onMounted, ref } from 'vue'
import api from '../../services/api'
import '../../style/main_content/slider.css'
import '../../style/main_content/sliderItemCard.css'

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
        const response = await api.get("/api/products/random")
        listRandom.value  = response.data
    } catch(e) {
        console.error("Error getting recommended products: ", e)
    }
}

const getLatestProducts = async () => {
    try {  
        const response = await api.get("/api/products/last")
        listLatest.value = response.data
    } catch(e) {
        console.error("Error getting latest products: ", e)
    }
}

const getListProducts = async () => {
    try {
        const response = await api.get("/api/products")
        listProducts.value = response.data
        for(product in listProducts.value) {
            console.log(product)
        }
    } catch(e) {
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
            <h2 v-if="slider == 'recommended'">{{"Recommended for you"}}</h2>
            <h2 v-else>{{"Last Products Added"}}</h2>
            <p>View All</p>
        </div>
        <div class="container-slider">
            <button @click="scrollLeft" class="nav-btn prev">
                <fa icon="chevron-left" />
            </button>
            <div class="carousel-track" ref="scrollContainer">
                <router-link 
                    :to="{ name: 'Product', params: { slug: item.slug } }" 
                    v-for="item in listRandom" 
                    v-if="slider == 'recommended'">
                    <SliderItemCard  
                    :key="item.id" 
                    :product="item" 
                />
                </router-link>
                <router-link 
                    :to="{ name: 'Product', params: { slug: itemRandom.slug } }" 
                    v-for="itemRandom in listLatest" 
                    :key="itemRandom.id" 
                    v-else="">
                    <SliderItemCard 
                        
                        :product="itemRandom" 
                    />
                </router-link>
                

            </div>
            <button @click="scrollRight" class="nav-btn next">
                <fa icon="chevron-right" />
            </button>
        </div>
    </div>
</template>
