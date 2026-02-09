<template>
  <h1 class="cart-title">My Cart</h1>
  <div class="container-cart">
    <div>
      <div>
        <img src="" alt="">
      </div>
      <div>
        <h3>Product Title</h3>
        <p>Product Brand</p>
        <div>
          <p>Product Company</p>
          <p>R$ 594,91</p>
        </div>
        <div>
          <p>1</p>
          <div>
            <p>Remover</p>
            <p>1</p>
            <p>+</p>
          </div>
        </div>
      </div>
    </div>

    <div class="container-finish-cart">
      <h3>Total do pedido</h3>
      <div class="container-finish-cart__row">
        <p>Subtotal (2 Items)</p>
        <p>R$ 594,91</p>
      </div>
      <div class="container-finish-cart__row">
        <p>Frete</p>
        <p>Grátis</p>
      </div>
      <div class="container-finish-cart__row">
        <p class="total"><b>Total</b></p>
        <p>R$ 594,91</p>
      </div>
      <button class="container-finish-button button-finish">Finalizar Compra</button>
      <button class="container-finish-button button-continue">Continuar Comprando</button>
    </div>
  </div>

</template>
<style>
  .cart-title {
    font-size: 2rem;
    margin-left: 5rem;
  }

  .container-cart {
    display: grid;
    grid-template-columns: 45% 25%;
    gap: 1rem;
    justify-content: center;
  }

  .container-finish-cart {
    padding: 1rem;
    border-radius: 8px;
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
    font-size: 1rem;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    max-width: 20rem;
  }

  .container-finish-cart__row {
    display: flex;
    justify-content: space-between;
    color: rgb(75, 85, 99);
  }

  .total {
    color: #000;
  }

  .container-finish-button {
    background-color: rgb(54, 124, 193);
    border: 1px solid transparent;
    padding: 0.8rem;
    font-size: 0.9rem;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.25s;
  }

  .button-finish {
    color: #fff;
  }

  .button-finish:hover {
    background-color: rgb(15, 36, 56);
  }

  .button-continue {
    background-color: #fff;
    color: rgb(55, 65, 81);
    border: 1px solid #c0c0c0;
  }

  .button-continue:hover {
    background-color: #f0f0f0;
  }
</style>