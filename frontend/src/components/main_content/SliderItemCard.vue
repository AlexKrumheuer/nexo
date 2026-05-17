<script setup>
const props = defineProps({
    product: {
        type: Object,
        required: true
    }
})

const formatPrice = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
}

</script>
<template>
    <div class="slideritem-card">
        <div class="image-container">
            <img class=product-image :src="product.images[0].url" alt="">
        </div>
        <div class="card-info">
            <p class="product-title">{{ product.title }}</p>
            <h3 class="product-price">{{ formatPrice(product.price) }}</h3>
        </div>
    </div>
</template>
<style scoped>
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
