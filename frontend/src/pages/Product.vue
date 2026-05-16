<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import api from '../services/api';
import { useToast } from 'vue-toastification';
import '../style/main_content/product.css'

const route = useRoute()
const product = ref(null)
const loading = ref(true)
const toast = useToast()

const formatPrice = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}

const currentImage = ref('')

onMounted(async () => {
    const slug = route.params.slug
    try {
        const response = await api.get(`/api/products/public/slug/${slug}`)
        product.value = response.data
        if (product.value.images && product.value.images.length > 0) {
            currentImage.value = product.value.images[0].url
        } else {
            currentImage.value = '/img/default-product-image.png'
        }
    } catch (error) {
        console.error("Error finding product:", error)
    } finally {
        loading.value = false
    }
})

const setImage = (imgUrl) => {
    currentImage.value = imgUrl;
}

const addCart = async (productId)=> {
    try {
        console.log(productId)
        const body = {productId:productId, quantity:1}
        await api.post("/api/cart", body)
        toast.success("Item adicionado com sucesso ao carrinho")
    } catch(e){
        console.error("Error: " + e.message || e)
        toast.error("Erro ao adicionar esse produto ao carrinho, tente novamente")
    }
}

</script>

<template>
    <div v-if="loading" style="text-align: center; padding: 50px;">
        <h2>Loading Product...</h2>
    </div>

    <main v-else-if="product" class="product-container">

        <div class="product-gallery">
            <div class="thumbnail-list">
                <img v-for="(img, index) in product.images" :key="index" :src="img.url" class="thumb"
                    :class="{ 'active': currentImage === img.url }" @click="setImage(img.url)" alt="Thumbnail">
            </div>
            <div class="main-image-wrapper">
                <img class="main-img" :src="currentImage" :alt="product.title">
            </div>
        </div>

        <div class="product-info">
            <h1 class="title">{{ product.title }}</h1>

            <p class="brand">Brand: {{ product.brand }}</p>
            <a href="#" class="seller-link">Visit shop: {{ product.seller || 'Nexo Oficial' }}</a>

            <div class="rating-box">
                <span class="score">4.7</span>
                <div class="stars">
                    <fa icon="star" />
                    <fa icon="star" />
                    <fa icon="star" />
                    <fa icon="star" />
                    <fa icon="star" />
                </div>
                <span class="reviews">(2947 reviews)</span>
            </div>

            <div class="price-section">
                <h1 class="price">{{ formatPrice(product.finalPrice) }}</h1>

                <p v-if="product.discountPercent > 0"
                    style="color: #999; text-decoration: line-through; font-size: 0.9rem;">
                    {{ formatPrice(product.price) }}
                </p>

                <p class="installments">
                    em até 10x de {{ formatPrice(product.finalPrice / 10) }} sem juros
                </p>
            </div>

            <div class="description" style="margin-top: 1rem; color: #555;">
                <h3>About this item</h3>
                <p>{{ product.description }}</p>
            </div>
        </div>

        <div class="buy-box">
            <div class="shipping">
                <h2>Calcular envio</h2>
                <div class="input-shipping">
                    <input type="text" placeholder="CEP">
                    <button>OK</button>
                </div>
            </div>

            <div class="stock-info">
                <p v-if="product.stockQuantity > 0" class="stock">Estoque disponível ({{ product.stockQuantity }})</p>
                <p v-else style="color: red; font-weight: bold;">Esgotado</p>

                <p class="seller-price">Vendido por <strong>{{ product.seller || 'Nexo Oficial' }}</strong></p>
            </div>

            <button :disabled="product.stockQuantity <= 0" class="btn-buy">Comprar Agora</button>
            <button :disabled="product.stockQuantity <= 0" class="btn-cart" @click="addCart(product.id)">Adicionar ao Carrinho</button>
        </div>
    </main>

    <div v-else style="text-align: center; padding: 50px;">
        <h2>Product not found</h2>
        <router-link to="/">Go back Home</router-link>
    </div>
</template>

<style scoped>

</style>