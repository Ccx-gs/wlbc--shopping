<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'

const toast = ref({ show: false, text: '' })
const showBackTop = ref(false)
let toastTimer = null
let toastHandler = null

function showToast(message) {
  toast.value = { show: true, text: message }
  clearTimeout(toastTimer)
  toastTimer = setTimeout(() => {
    toast.value.show = false
  }, 2200)
}

function handleScroll() {
  showBackTop.value = window.scrollY > 380
}

function scrollTop() {
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

onMounted(() => {
  toastHandler = (event) => showToast(event.detail?.message || '操作成功')
  window.addEventListener('toast:show', toastHandler)
  window.addEventListener('scroll', handleScroll)
})

onBeforeUnmount(() => {
  if (toastHandler) window.removeEventListener('toast:show', toastHandler)
  window.removeEventListener('scroll', handleScroll)
  clearTimeout(toastTimer)
})
</script>

<template>
  <div id="app-wrapper">
    <NavBar />
    <main class="page-content">
      <div class="container">
        <RouterView />
      </div>
    </main>

    <Transition name="toast">
      <div v-if="toast.show" class="global-toast">{{ toast.text }}</div>
    </Transition>

    <button
      v-if="showBackTop"
      class="back-top"
      aria-label="返回顶部"
      @click="scrollTop"
    >
      ↑
    </button>
  </div>
</template>

<style scoped>
#app-wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.global-toast {
  position: fixed;
  right: 20px;
  top: 90px;
  background: linear-gradient(135deg, #4f46e5, #6366f1);
  color: #fff;
  padding: 12px 16px;
  border-radius: 10px;
  font-size: 0.9rem;
  box-shadow: var(--shadow-elevated);
  z-index: 999;
}

.back-top {
  position: fixed;
  right: 20px;
  bottom: 28px;
  width: 48px;
  height: 48px;
  border-radius: 999px;
  background: linear-gradient(180deg, #4f46e5, #4338ca);
  color: #fff;
  font-size: 1.2rem;
  box-shadow: var(--shadow-elevated);
  z-index: 200;
}

.toast-enter-active,
.toast-leave-active {
  transition: all 0.24s ease;
}

.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateY(-12px);
}
</style>
