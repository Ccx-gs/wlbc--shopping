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
  background: #ffffff;
  border-bottom: 1px solid #e0e0e0;
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
  color: #1a1a1a;
  text-decoration: none;
}

.brand-icon {
  font-size: 1.4rem;
}

.brand-name {
  color: #0066cc;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 8px;
}

.nav-link {
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 0.95rem;
  font-weight: 500;
  color: #444;
  text-decoration: none;
  transition: background 0.15s, color 0.15s;
}

.nav-link:hover {
  background: #f0f4ff;
  color: #0066cc;
}

.nav-link.active {
  color: #0066cc;
  background: #f0f4ff;
}

.cart-link {
  position: relative;
  display: flex;
  align-items: center;
  gap: 6px;
}

.cart-badge {
  background: #0066cc;
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
