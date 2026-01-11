<script setup>
// Seus imports e lógica ficam aqui
</script>

<template>
    <main class="product-container">
        <div class="product-gallery">
            <div class="thumbnail-list">
                <img class="thumb" src="../../img/default-product-image.png" alt="">
                <img class="thumb" src="../../img/default-product-image.png" alt="">
                <img class="thumb" src="../../img/default-product-image.png" alt="">
                <img class="thumb" src="../../img/default-product-image.png" alt="">
            </div>
            <div class="main-image-wrapper">
                <img class="main-img" src="../../img/default-product-image.png" alt="Imagem Principal">
            </div>
        </div>

        <div class="product-info">
            <h1 class="title">Fone de Ouvido Bluetooth Noise Cancelling WH-1000XM5</h1>
            
            <a href="#" class="seller-link">Visit Seller's shop</a>
            
            <div class="rating-box">
                <span class="score">4.7</span>
                <div class="stars">
                    <fa icon="star"/>
                    <fa icon="star"/>
                    <fa icon="star"/>
                    <fa icon="star"/>
                    <fa icon="star"/>
                </div>
                <span class="reviews">(2947 reviews)</span>
            </div>
            
            <div class="price-section">
                <h1 class="price">R$ 1.999,00</h1>
                <p class="installments">em até 10x de R$ 199,90 sem juros</p>
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
                <p class="stock">Estoque disponível</p>
                <p class="seller-price">Vendido por <strong>Nexo Oficial</strong></p>
            </div>

            <button class="btn-buy">Comprar Agora</button>
            <button class="btn-cart">Adicionar ao Carrinho</button>
        </div>
    </main>
</template>

<style scoped>
.product-container {
    max-width: 1280px;
    margin: 0 auto;
    padding: 2rem;
    display: grid;
    grid-template-columns: 45% 30% 20%; 
    gap: 2.5%;
    align-items: start;
    font-family: 'Inter', sans-serif;
}

.product-gallery {
    display: flex;
    gap: 15px;
}

.thumbnail-list {
    display: flex;
    flex-direction: column; 
    gap: 10px;
}

.thumb {
    width: 60px;
    height: 60px;
    object-fit: contain;
    border: 1px solid #ddd;
    border-radius: 4px;
    cursor: pointer;
    transition: border 0.2s;
}

.thumb:hover {
    border-color: #367cc1;
}

.main-image-wrapper {
    flex-grow: 1;
    display: flex;
    justify-content: center;
    align-items: center;
}

.main-img {
    width: 100%;
    max-width: 500px; 
    object-fit: contain;
}

.product-info {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.title {
    font-size: 1.5rem;
    font-weight: 600;
    line-height: 1.3;
    color: #333;
}

.seller-link {
    font-size: 0.9rem;
    color: #367cc1;
    text-decoration: none;
}

.rating-box {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 0.9rem;
    color: #f68c36; 
}

.reviews {
    color: #666;
}

.price-section {
    margin-top: 15px;
    border-top: 1px solid #eee;
    padding-top: 15px;
}

.price {
    font-size: 2rem;
    font-weight: 300;
    color: #333;
}

.installments {
    font-size: 1rem;
    color: #367cc1;
}

.buy-box {
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 4px 12px rgba(0,0,0,0.05);
    position: sticky;
    top: 20px; 
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.buy-box h2 {
    font-size: 1rem;
    margin-bottom: 10px;
    color: #444;
}

.input-shipping {
    display: flex;
    gap: 5px;
}

.input-shipping input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.input-shipping button {
    padding: 0 10px;
    background: #eee;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.stock-info {
    font-size: 0.9rem;
    color: #16a34a; 
    font-weight: 600;
}

.seller-price {
    font-size: 0.85rem;
    color: #666;
    font-weight: normal;
    margin-top: 5px;
}

.btn-buy, .btn-cart {
    width: 100%;
    padding: 12px;
    border: none;
    border-radius: 6px;
    font-weight: bold;
    cursor: pointer;
    font-size: 1rem;
    transition: 0.2s;
}

.btn-buy {
    background-color: #367cc1;
    color: white;
}

.btn-buy:hover {
    background-color: #2a629a;
}

.btn-cart {
    background-color: #eef6fc; 
    color: #367cc1;
}

.btn-cart:hover {
    background-color: #dbeafe;
}

@media (max-width: 900px) {
    .product-container {
        grid-template-columns: 1fr; 
        gap: 30px;
    }
    
    .product-gallery {
        flex-direction: column-reverse; 
    }
    
    .thumbnail-list {
        flex-direction: row;
        justify-content: center;
    }
    
    .buy-box {
        position: static;
    }
}
</style>