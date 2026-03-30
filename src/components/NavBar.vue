<script setup>
import { RouterLink, useRoute } from 'vue-router'
import { useCart } from '../composables/useCart.js'

const { cartCount } = useCart()
const route = useRoute()
</script>

<template>
  <header class="navbar">
    <div class="container navbar-inner">
      <RouterLink to="/" class="brand">
        <span class="brand-icon">🛍</span>
        <span class="brand-name">TechStore</span>
      </RouterLink>

      <nav class="nav-links">
        <RouterLink to="/" :class="['nav-link', { active: route.path === '/' }]">Home</RouterLink>
        <RouterLink to="/cart" :class="['nav-link', 'cart-link', { active: route.path === '/cart' }]">
          Cart
          <span v-if="cartCount > 0" class="cart-badge">{{ cartCount }}</span>
        </RouterLink>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  background: rgba(255, 255, 255, 0.88);
  border-bottom: 1px solid rgba(214, 224, 238, 0.8);
  backdrop-filter: blur(10px);
  position: sticky;
  top: 0;
  z-index: 100;
}

.navbar-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: 700;
  font-size: 1.2rem;
  color: #13263f;
  text-decoration: none;
}

.brand-icon {
  font-size: 1.4rem;
}

.brand-name {
  color: #1f4f86;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 8px;
}

.nav-link {
  padding: 8px 16px;
  border-radius: 999px;
  font-size: 0.95rem;
  font-weight: 600;
  color: #4d6078;
  text-decoration: none;
  transition: background 0.2s, color 0.2s;
}

.nav-link:hover {
  background: #edf4ff;
  color: #1f4f86;
}

.nav-link.active {
  color: #1f4f86;
  background: #e6effc;
}

.cart-link {
  position: relative;
  display: flex;
  align-items: center;
  gap: 6px;
}

.cart-badge {
  background: #1f4f86;
  color: #fff;
  font-size: 0.72rem;
  font-weight: 700;
  min-width: 18px;
  height: 18px;
  border-radius: 9px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
  line-height: 1;
}
</style>
