<script setup>
import { computed, ref, onMounted, onBeforeUnmount } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { Search, ShoppingCart, Bell } from '@element-plus/icons-vue'
import { useCart } from '../composables/useCart.js'

const { cartCount, fetchCart } = useCart()
const route = useRoute()
const router = useRouter()
const mobileOpen = ref(false)
const shake = ref(false)
const searchKeyword = ref(String(route.query.keyword || '').trim())
const notificationCount = ref(0)

const hotKeywords = ['iPhone', 'MacBook', '降噪耳机', '游戏本']
const categories = [
  { key: 'all', label: '全部分类' },
  { key: 'smartphone', label: '手机' },
  { key: 'laptop', label: '电脑' },
  { key: 'tablet', label: '平板' },
  { key: 'audio', label: '耳机音频' },
  { key: 'wearable', label: '智能穿戴' },
  { key: 'gaming', label: '游戏设备' },
]

const activeCategory = computed(() => route.query.category || 'all')

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

function performSearch(keyword = searchKeyword.value) {
  const query = String(keyword || '').trim()
  router.push(query ? `/list?keyword=${encodeURIComponent(query)}` : '/list')
  closeMobile()
}

function goKeyword(keyword) {
  searchKeyword.value = keyword
  performSearch(keyword)
}

function goCategory(category) {
  router.push(category === 'all' ? '/list' : `/list?category=${category}`)
  closeMobile()
}

onMounted(() => {
  window.addEventListener('cart:updated', onCartUpdated)
  fetchCart()
})

onBeforeUnmount(() => {
  window.removeEventListener('cart:updated', onCartUpdated)
})
</script>

<template>
  <header class="mall-header">
    <div class="top-bar">
      <div class="container top-inner">
        <p>你好，请登录</p>
        <div class="top-links">
          <RouterLink to="/checkout">我的订单</RouterLink>
          <a href="#">帮助中心</a>
        </div>
      </div>
    </div>

    <div class="main-head">
      <div class="container main-inner">
        <RouterLink to="/" class="brand" @click="closeMobile">
          <span class="brand-mark">WL</span>
          <div>
            <p class="brand-name">WLBC Mall</p>
            <p class="brand-sub">旗舰级数码购物平台</p>
          </div>
        </RouterLink>

        <div class="search-panel">
          <el-input
            v-model="searchKeyword"
            class="global-search"
            size="large"
            placeholder="搜索商品、品牌或型号"
            @keyup.enter="performSearch()"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
            <template #append>
              <el-button type="primary" @click="performSearch()">搜索</el-button>
            </template>
          </el-input>
          <div class="hot-keywords">
            <span>热门搜索：</span>
            <button
              v-for="word in hotKeywords"
              :key="word"
              class="hot-word"
              @click="goKeyword(word)"
            >
              {{ word }}
            </button>
          </div>
        </div>

        <div class="head-actions">
          <RouterLink
            to="/cart"
            :class="['action-btn', 'cart-link', { active: route.path === '/cart', shake }]"
            @click="closeMobile"
          >
            <el-badge :value="cartCount" :hidden="cartCount === 0" :offset="[4, 4]">
              <el-icon><ShoppingCart /></el-icon>
            </el-badge>
            <span>购物车</span>
          </RouterLink>
          <button class="action-btn">
            <el-badge :value="notificationCount" :hidden="notificationCount === 0" :offset="[4, 4]">
              <el-icon><Bell /></el-icon>
            </el-badge>
            <span>消息</span>
          </button>
        </div>

        <button class="hamburger" aria-label="菜单" @click="toggleMobile">
          <span></span><span></span><span></span>
        </button>
      </div>
    </div>

    <nav class="category-nav" :class="{ open: mobileOpen }">
      <div class="container category-inner">
        <button
          v-for="item in categories"
          :key="item.key"
          :class="['category-link', { active: activeCategory === item.key }]"
          @click="goCategory(item.key)"
        >
          {{ item.label }}
        </button>
      </div>
    </nav>
  </header>
</template>

<style scoped>
.mall-header {
  border-bottom: 1px solid #dcdfe6;
  background: #fff;
}

.top-bar {
  background: #303133;
  color: #dcdfe6;
  font-size: 0.82rem;
}

.top-inner {
  min-height: 34px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.top-links {
  display: inline-flex;
  align-items: center;
  gap: 14px;
}

.top-links a:hover {
  color: #fff;
}

.main-head {
  border-bottom: 1px solid #ebeef5;
}

.main-inner {
  min-height: 96px;
  display: grid;
  grid-template-columns: auto minmax(460px, 1fr) auto;
  gap: 20px;
  align-items: center;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-mark {
  width: 48px;
  height: 48px;
  border-radius: 10px;
  background: linear-gradient(135deg, #4f46e5, #0066cc);
  color: #fff;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
  font-size: 1.1rem;
}

.brand-name {
  font-size: 1rem;
  font-weight: 700;
  color: #111827;
  line-height: 1.1;
}

.brand-sub {
  font-size: 0.74rem;
  color: #6b7280;
}

.search-panel {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

:deep(.global-search .el-input__wrapper) {
  border-radius: 10px 0 0 10px;
}

:deep(.global-search .el-input-group__append .el-button) {
  border-radius: 0 10px 10px 0;
  background: var(--primary);
  border-color: var(--primary);
}

.hot-keywords {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;
  font-size: 0.82rem;
  color: #909399;
}

.hot-word {
  background: transparent;
  color: #606266;
  font-size: 0.82rem;
}

.hot-word:hover {
  color: var(--primary);
}

.head-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.action-btn {
  min-width: 82px;
  min-height: 44px;
  padding: 0 12px;
  border-radius: 10px;
  background: #f5f7fa;
  color: #303133;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-weight: 600;
}

.action-btn.active,
.action-btn:hover {
  background: #ecf5ff;
  color: var(--primary);
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

.category-nav {
  position: sticky;
  top: 0;
  z-index: 50;
  background: rgba(255, 255, 255, 0.72);
  border-bottom: 1px solid rgba(229, 231, 235, 0.9);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
}

.category-inner {
  min-height: 50px;
  display: flex;
  align-items: center;
  gap: 6px;
  overflow-x: auto;
  scrollbar-width: none;
}

.category-inner::-webkit-scrollbar {
  display: none;
}

.category-link {
  border-radius: 8px;
  min-height: 36px;
  padding: 8px 12px;
  color: #606266;
  background: transparent;
  white-space: nowrap;
}

.category-link:hover,
.category-link.active {
  color: var(--primary);
  background: #ecf5ff;
}

.hamburger {
  display: none;
  min-width: 44px;
  min-height: 44px;
  border-radius: 8px;
  background: #ecf5ff;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 4px;
}

.hamburger span {
  width: 18px;
  height: 2px;
  background: var(--primary);
}

@media (max-width: 1024px) {
  .main-inner {
    grid-template-columns: auto 1fr auto;
  }

  .head-actions {
    display: none;
  }
}

@media (max-width: 760px) {
  .main-inner {
    grid-template-columns: 1fr auto;
    min-height: auto;
    padding-top: 12px;
    padding-bottom: 12px;
  }

  .search-panel {
    grid-column: 1 / -1;
    order: 3;
  }

  .hamburger {
    display: inline-flex;
  }

  .category-nav {
    display: none;
  }

  .category-nav.open {
    display: block;
  }

  .category-inner {
    padding-bottom: 10px;
  }
}
</style>
