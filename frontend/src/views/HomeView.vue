<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import http from '../api/http.js'

const router = useRouter()
const products = ref([])
const currentSlide = ref(0)
const searchQuery = ref('')
const now = ref(Date.now())
let timer = null
let countdownTimer = null

const banners = [
  {
    title: '校园焕新季 · 最高立减 ¥1200',
    desc: '轻奢科技好物，开学装备一步到位',
    button: '立即选购',
    image: 'https://images.unsplash.com/photo-1512054502232-10a0a035d672?auto=format&fit=crop&w=1800&q=80',
  },
  {
    title: '数码秒杀专场',
    desc: '限时抢购，热门单品低至 7 折',
    button: '进入秒杀',
    image: 'https://images.unsplash.com/photo-1498049794561-7780e7231661?auto=format&fit=crop&w=1800&q=80',
  },
  {
    title: '影音装备节',
    desc: '沉浸音效 + 高清屏幕，学习娱乐都在线',
    button: '查看专区',
    image: 'https://images.unsplash.com/photo-1484704849700-f032a568e944?auto=format&fit=crop&w=1800&q=80',
  },
]

const categoryItems = [
  { key: 'smartphone', label: '手机', icon: '📱' },
  { key: 'laptop', label: '电脑', icon: '💻' },
  { key: 'tablet', label: '平板', icon: '📘' },
  { key: 'audio', label: '耳机音频', icon: '🎧' },
  { key: 'wearable', label: '智能穿戴', icon: '⌚' },
  { key: 'gaming', label: '游戏设备', icon: '🎮' },
]

const flashSaleEnd = new Date('2026-12-31T23:59:59').getTime()

const countdown = computed(() => {
  const diff = Math.max(0, flashSaleEnd - now.value)
  const hours = String(Math.floor(diff / 1000 / 60 / 60)).padStart(2, '0')
  const mins = String(Math.floor((diff / 1000 / 60) % 60)).padStart(2, '0')
  const secs = String(Math.floor((diff / 1000) % 60)).padStart(2, '0')
  return { hours, mins, secs }
})

const recommendationProducts = computed(() => {
  const q = searchQuery.value.trim().toLowerCase()
  const filtered = products.value.filter(item => !q || item.name.toLowerCase().includes(q) || item.brand.toLowerCase().includes(q))
  return filtered.slice().sort((a, b) => b.sales - a.sales).slice(0, 12)
})

function nextSlide() {
  currentSlide.value = (currentSlide.value + 1) % banners.length
}

function prevSlide() {
  currentSlide.value = (currentSlide.value - 1 + banners.length) % banners.length
}

function goList(category = '') {
  router.push(category ? `/list?category=${category}` : '/list')
}

function revealOnScroll() {
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) entry.target.classList.add('in-view')
      })
    },
    { threshold: 0.12 }
  )

  document.querySelectorAll('.fade-up').forEach((el) => observer.observe(el))
}

onMounted(() => {
  timer = setInterval(nextSlide, 3600)
  countdownTimer = setInterval(() => { now.value = Date.now() }, 1000)
  revealOnScroll()
  http.get('/products').then(({ data }) => {
    products.value = Array.isArray(data) ? data : []
  })
})

onBeforeUnmount(() => {
  clearInterval(timer)
  clearInterval(countdownTimer)
})
</script>

<template>
  <div class="home-view">
    <section class="banner fade-up">
      <img :src="banners[currentSlide].image" :alt="banners[currentSlide].title" class="banner-bg" />
      <div class="banner-overlay"></div>
      <div class="banner-content">
        <p class="kicker">Campus Tech Mall</p>
        <h1>{{ banners[currentSlide].title }}</h1>
        <p>{{ banners[currentSlide].desc }}</p>
        <button class="primary-btn" @click="goList()">{{ banners[currentSlide].button }}</button>
      </div>
      <div class="banner-controls">
        <button class="ctrl" @click="prevSlide">←</button>
        <button class="ctrl" @click="nextSlide">→</button>
      </div>
    </section>

    <section class="category-grid fade-up">
      <button
        v-for="item in categoryItems"
        :key="item.key"
        class="category-item"
        @click="goList(item.key)"
      >
        <span class="icon">{{ item.icon }}</span>
        <span>{{ item.label }}</span>
      </button>
    </section>

    <section class="flash-sale fade-up">
      <div>
        <h2>限时秒杀</h2>
        <p>精选单品爆款直降，每天 10:00 / 20:00 更新</p>
      </div>
      <div class="countdown">
        <span>{{ countdown.hours }}</span>
        <i>:</i>
        <span>{{ countdown.mins }}</span>
        <i>:</i>
        <span>{{ countdown.secs }}</span>
      </div>
      <button class="danger-btn" @click="goList()">抢购会场</button>
    </section>

    <section class="recommend fade-up">
      <div class="recommend-head">
        <h2>猜你喜欢</h2>
        <input v-model="searchQuery" type="search" placeholder="搜索品牌或商品" />
      </div>
      <div class="product-grid">
        <ProductCard v-for="product in recommendationProducts" :key="product.id" :product="product" />
      </div>
    </section>
  </div>
</template>

<style scoped>
.home-view {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.banner {
  position: relative;
  min-height: 340px;
  width: 100vw;
  margin-left: calc(50% - 50vw);
  border-radius: 0;
  overflow: hidden;
  box-shadow: none;
}

.banner-bg {
  width: 100%;
  height: 100%;
  position: absolute;
  inset: 0;
  object-fit: cover;
}

.banner-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(120deg, rgba(49, 46, 129, 0.85), rgba(79, 70, 229, 0.45));
}

.banner-content {
  position: relative;
  z-index: 1;
  color: #fff;
  max-width: 520px;
  padding: 44px 32px;
}

.kicker {
  font-size: 0.78rem;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  opacity: 0.9;
}

.banner-content h1 {
  font-size: clamp(1.8rem, 4vw, 2.8rem);
  line-height: 1.15;
  margin: 10px 0;
}

.banner-content p {
  opacity: 0.95;
  margin-bottom: 16px;
}

.primary-btn,
.danger-btn {
  min-height: 44px;
  border-radius: 8px;
  padding: 10px 16px;
  font-weight: 700;
  color: #fff;
}

.primary-btn {
  background: #4f46e5;
}

.danger-btn {
  background: #ef4444;
}

.banner-controls {
  position: absolute;
  z-index: 1;
  right: 20px;
  bottom: 20px;
  display: flex;
  gap: 8px;
}

.ctrl {
  width: 44px;
  height: 44px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.25);
  color: #fff;
  backdrop-filter: blur(6px);
  border: 1px solid rgba(255, 255, 255, 0.4);
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 12px;
}

.category-item {
  min-height: 88px;
  border-radius: 12px;
  background: #fff;
  border: 1px solid #e5e7eb;
  box-shadow: var(--shadow-soft);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 8px;
  font-weight: 600;
  color: #374151;
}

.icon {
  width: 38px;
  height: 38px;
  border-radius: 999px;
  background: #eef2ff;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.flash-sale {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  box-shadow: var(--shadow-soft);
  padding: 18px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 14px;
  flex-wrap: wrap;
}

.flash-sale h2 {
  color: #111827;
  margin-bottom: 4px;
}

.flash-sale p {
  color: #6b7280;
  font-size: 0.92rem;
}

.countdown {
  display: flex;
  align-items: center;
  gap: 6px;
}

.countdown span {
  width: 42px;
  height: 42px;
  border-radius: 8px;
  background: #111827;
  color: #fff;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
}

.recommend {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.recommend-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.recommend-head h2 {
  font-size: 1.3rem;
  color: #111827;
}

.recommend-head input {
  min-height: 44px;
  padding: 10px 14px;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  width: min(320px, 100%);
  outline: none;
}

.recommend-head input:focus {
  border-color: #4f46e5;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.12);
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

@media (max-width: 1024px) {
  .category-grid {
    grid-template-columns: repeat(3, 1fr);
  }

  .product-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 680px) {
  .banner {
    min-height: 300px;
  }

  .banner-content {
    padding: 30px 18px;
  }

  .category-grid {
    grid-template-columns: repeat(3, 1fr);
  }

  .product-grid {
    grid-template-columns: 1fr;
  }

  .recommend-head {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>
