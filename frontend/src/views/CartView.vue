<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import CartItem from '../components/CartItem.vue'
import { useCart } from '../composables/useCart.js'

const router = useRouter()
const { cartItems, cartTotal, cartCount, fetchCart } = useCart()

function formatPriceParts(price) {
  const [integer, decimals] = Number(price || 0).toFixed(2).split('.')
  return { integer, decimals }
}

onMounted(() => {
  fetchCart()
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) entry.target.classList.add('in-view')
    })
  }, { threshold: 0.1 })
  document.querySelectorAll('.fade-up').forEach((el) => observer.observe(el))
})
</script>

<template>
  <div class="cart-view">
    <h1 class="title fade-up">购物车</h1>

    <div v-if="cartCount > 0" class="cart-layout">
      <div class="cart-items">
        <CartItem v-for="item in cartItems" :key="item.cartKey || item.id" :item="item" />
      </div>

      <aside class="summary fade-up">
        <h2>结算信息</h2>
        <div class="row">
          <span>商品数量</span>
          <span>{{ cartCount }}</span>
        </div>
        <div class="row">
          <span>运费</span>
          <span class="free">免运费</span>
        </div>
        <div class="line"></div>
        <div class="row total-row">
          <span>合计</span>
          <p class="price total">
            <span class="currency">¥</span>
            <span class="amount">{{ formatPriceParts(cartTotal).integer }}</span>
            <span class="decimals">.{{ formatPriceParts(cartTotal).decimals }}</span>
          </p>
        </div>
        <button class="checkout-btn" @click="router.push('/checkout')">去结算</button>
      </aside>
    </div>

    <div v-else class="empty fade-up">
      <div class="illustration">🛍️</div>
      <h2>购物车还是空的</h2>
      <p>去逛逛校园热卖数码，挑一件心仪好物吧。</p>
      <button @click="router.push('/list')">立即选购</button>
    </div>
  </div>
</template>

<style scoped>
.cart-view {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.title {
  font-size: 1.7rem;
  color: #111827;
}

.cart-layout {
  display: grid;
  grid-template-columns: 1fr 320px;
  gap: 16px;
  align-items: start;
}

.cart-items {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.summary {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: var(--shadow-soft);
  padding: 14px;
  position: sticky;
  top: 88px;
}

.summary h2 {
  margin-bottom: 12px;
}

.row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  color: #4b5563;
}

.free {
  color: #16a34a;
}

.line {
  height: 1px;
  background: #e5e7eb;
  margin: 10px 0;
}

.total-row {
  color: #111827;
  font-weight: 700;
  align-items: center;
}

.total {
  color: #ef4444;
}

.checkout-btn,
.empty button {
  width: 100%;
  min-height: 44px;
  border-radius: 8px;
  background: linear-gradient(135deg, #4f46e5, #4338ca);
  color: #fff;
  font-weight: 700;
}

.empty {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  box-shadow: var(--shadow-soft);
  text-align: center;
  padding: 54px 20px;
  max-width: 520px;
  margin: 20px auto;
}

.illustration {
  font-size: 3rem;
  margin-bottom: 12px;
}

.empty h2 {
  color: #111827;
  margin-bottom: 4px;
}

.empty p {
  color: #6b7280;
  margin-bottom: 16px;
}

@media (max-width: 1024px) {
  .cart-layout {
    grid-template-columns: 1fr;
  }

  .summary {
    position: static;
  }
}
</style>
