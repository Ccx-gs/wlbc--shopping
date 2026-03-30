<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { useCart } from '../composables/useCart.js'

const { cartCount } = useCart()
const route = useRoute()
const mobileOpen = ref(false)
const shake = ref(false)

function toggleMobile() {
  mobileOpen.value = !mobileOpen.value
}

function closeMobile() {
  mobileOpen.value = false
}

function onCartUpdated() {
  shake.value = true
  setTimeout(() => {
    shake.value = false
  }, 520)
}

onMounted(() => {
  window.addEventListener('cart:updated', onCartUpdated)
})

onBeforeUnmount(() => {
  window.removeEventListener('cart:updated', onCartUpdated)
})
</script>

<template>
  <header class="navbar">
    <div class="container navbar-inner">
      <RouterLink to="/" class="brand" @click="closeMobile">
        <span class="brand-mark">WL</span>
        <div>
          <p class="brand-name">Campus Digital Mall</p>
          <p class="brand-sub">现代轻奢 · 青春活力</p>
        </div>
      </RouterLink>

      <button class="hamburger" aria-label="菜单" @click="toggleMobile">
        <span></span><span></span><span></span>
      </button>

      <nav class="nav-links" :class="{ open: mobileOpen }">
        <RouterLink to="/" :class="['nav-link', { active: route.path === '/' }]" @click="closeMobile">首页</RouterLink>
        <RouterLink to="/list" :class="['nav-link', { active: route.path === '/list' }]" @click="closeMobile">分类</RouterLink>
        <RouterLink to="/checkout" :class="['nav-link', { active: route.path === '/checkout' }]" @click="closeMobile">订单</RouterLink>
        <RouterLink to="/cart" :class="['nav-link', 'cart-link', { active: route.path === '/cart', shake }]" @click="closeMobile">
          购物车
          <span v-if="cartCount > 0" class="cart-badge">{{ cartCount }}</span>
        </RouterLink>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  background: rgba(255, 255, 255, 0.72);
  border-bottom: 1px solid rgba(224, 231, 255, 0.9);
  backdrop-filter: blur(14px);
  position: sticky;
  top: 0;
  z-index: 100;
}

.navbar-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 72px;
  gap: 16px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-mark {
  width: 42px;
  height: 42px;
  border-radius: 12px;
  background: linear-gradient(135deg, #4f46e5, #7c3aed);
  color: #fff;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
}

.brand-name {
  font-size: 0.95rem;
  font-weight: 700;
  color: #111827;
  line-height: 1.1;
}

.brand-sub {
  font-size: 0.72rem;
  color: #6b7280;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-link {
  min-height: 44px;
  padding: 10px 14px;
  border-radius: 8px;
  font-weight: 600;
  color: #4b5563;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.nav-link:hover,
.nav-link.active {
  color: #312e81;
  background: #eef2ff;
}

.cart-link {
  position: relative;
  gap: 6px;
}

.cart-badge {
  min-width: 20px;
  height: 20px;
  border-radius: 999px;
  background: #ef4444;
  color: #fff;
  font-size: 0.72rem;
  font-weight: 700;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0 6px;
}

.shake {
  animation: cart-shake 0.5s ease;
}

@keyframes cart-shake {
  0%, 100% { transform: translateX(0); }
  20% { transform: translateX(-4px); }
  40% { transform: translateX(4px); }
  60% { transform: translateX(-2px); }
  80% { transform: translateX(2px); }
}

.hamburger {
  display: none;
  min-width: 44px;
  min-height: 44px;
  border-radius: 8px;
  background: #eef2ff;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 4px;
}

.hamburger span {
  width: 18px;
  height: 2px;
  background: #312e81;
}

@media (max-width: 760px) {
  .hamburger {
    display: inline-flex;
  }

  .nav-links {
    position: absolute;
    top: 72px;
    right: 14px;
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(14px);
    border: 1px solid #e5e7eb;
    border-radius: 12px;
    box-shadow: var(--shadow-soft);
    flex-direction: column;
    align-items: stretch;
    padding: 8px;
    min-width: 170px;
    display: none;
  }

  .nav-links.open {
    display: flex;
  }
}
</style>
