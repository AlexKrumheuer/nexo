<script setup>
    const currentYear = new Date().getFullYear()

    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        })
    }
</script>
<template>
    <footer>
        <div class="back-to-top" @click="scrollToTop">
            Back to Top
        </div>

        <div class="footer-container">
            <div class="footer-columns">
                <ul class="ul-content">
                    <li class="list-title">Help & Support</li>
                    <li><router-link to="/help">Help Center</router-link></li>
                    <li><router-link to="/track">Track Your Order</router-link></li>
                    <li><router-link to="/returns">Returns & Refunds</router-link></li>
                    <li><router-link to="/shipping">Shipping & Delivery</router-link></li>
                    <li><router-link to="/contact">Contact Us</router-link></li>
                </ul>
                <ul class="ul-content">
                    <li class="list-title">About Nexo</li>
                    <li><router-link to="/about">About Us</router-link></li>
                    <li><router-link to="/careers">Careers</router-link></li>
                    <li><router-link to="/press">Press</router-link></li>
                    <li><router-link to="/terms">Terms & Conditions</router-link></li>
                    <li><router-link to="/privacy">Privacy Policy</router-link></li>
                </ul>
                <ul class="ul-content">
                    <li class="list-title">Business Services</li>
                    <li><router-link to="/sell">Sell on Nexo</router-link></li>
                    <li><router-link to="/portal">Seller Portal</router-link></li>
                    <li><router-link to="/affiliates">Affiliate Program</router-link></li>
                    <li><router-link to="/advertise">Advertise with Us</router-link></li>
                </ul>
            </div>
        </div>

        <div class="footer-credit">
            <div class="credit-content">
                <img class="footer-logo" src="../../img/logo.png" alt="Nexo Logo">
                <p>&copy; {{ currentYear }} Nexo Marketplace Inc. All rights reserved.</p>
            </div>
        </div>
    </footer>
</template>
<style>
    footer {
        width: 100%;
        background-color: #132e48; 
        color: #e2e8f0;
        display: flex;
        flex-direction: column;
    }

    .back-to-top {
        width: 100%;
        text-align: center;
        background-color: #1e4770; 
        padding: 1rem;
        font-size: 0.9rem;
        font-weight: 600;
        text-transform: uppercase;
        color: #fff;
        cursor: pointer;
        transition: 0.3s;
    }

    .back-to-top:hover {
        background-color: #2a629a;
    }


    .footer-container {
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
        padding: 4rem 2rem;
    }

    .footer-columns {
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
        gap: 2rem;
    }

    .ul-content {
        list-style: none;
        display: flex;
        flex-direction: column;
        gap: 0.8rem;
        padding: 0;
        min-width: 200px; 
    }

    .list-title {
        color: #fff;
        font-weight: bold;
        font-size: 1.1rem;
        margin-bottom: 0.5rem;
    }

    .ul-content a {
        color: #cbd5e1;
        text-decoration: none;
        font-size: 0.9rem;
        transition: color 0.2s;
    }

    .ul-content a:hover {
        color: #f68c36; 
        padding-left: 5px; 
    }

   
    .footer-credit {
        background-color: #0f2438; 
        padding: 1.5rem 0;
        border-top: 1px solid rgba(255,255,255,0.1);
    }

    .credit-content {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 2rem;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 1.5rem;
    }

    .footer-logo {
        width: 6rem;
        opacity: 0.8; 
        filter: brightness(0) invert(1); 
    }
    .footer-credit p {
        font-size: 0.8rem;
        color: #94a3b8;
    }
    
    @media (max-width: 768px) {
        .footer-columns {
            flex-direction: column;
            text-align: center;
        }
        
        .credit-content {
            flex-direction: column;
            gap: 1rem;
        }
    }

</style>