<script setup>
    import '../../style/main_content/sliderItemCard.css' 
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
            <img class=product-image :src="product.images[0]" alt="">
        </div>
        <div class="card-info">
            <p class="product-title">{{ product.title }}</p>
            <h3 class="product-price">{{ formatPrice(product.price) }}</h3>
        </div>
    </div>
</template>
