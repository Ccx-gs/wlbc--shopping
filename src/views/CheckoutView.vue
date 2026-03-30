<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCart } from '../composables/useCart.js'
import { useUser } from '../composables/useUser.js'

const router = useRouter()
const { cartItems, cartTotal, cartCount, clearCart } = useCart()
const { user } = useUser()

const orderSubmitted = ref(false)
const submitting = ref(false)

function formatPriceParts(price) {
  const [integer, decimals] = Number(price || 0).toFixed(2).split('.')
  return { integer, decimals }
}

function submitOrder() {
  if (!cartCount.value) return
  submitting.value = true
  setTimeout(() => {
    clearCart()
    submitting.value = false
    orderSubmitted.value = true
    window.dispatchEvent(new CustomEvent('toast:show', { detail: { message: '订单提交成功' } }))
  }, 800)
}
</script>

<template>
  <div class="checkout-view">
    <div v-if="orderSubmitted" class="success-state fade-up">
      <div class="icon">🎉</div>
      <h1>下单成功</h1>
      <p>订单已提交，预计 24 小时内完成发货。</p>
      <button @click="router.push('/')">返回首页</button>
    </div>

    <div v-else-if="cartCount > 0" class="checkout-layout">
      <section class="main fade-up">
        <h1>确认订单</h1>

        <div class="card">
          <h2>收货信息</h2>
          <p>{{ user.name }}</p>
          <p class="muted">{{ user.address }}</p>
        </div>

        <div class="card">
          <h2>商品明细</h2>
          <div class="items">
            <article v-for="item in cartItems" :key="item.cartKey || item.id" class="item-row">
              <img :src="item.image" :alt="item.name" loading="lazy" />
              <div>
                <p class="name">{{ item.name }}</p>
                <p class="muted">{{ item.skuName || '默认规格' }} × {{ item.quantity }}</p>
              </div>
              <p class="price item-price">
                <span class="currency">¥</span>
                <span class="amount">{{ formatPriceParts(item.price * item.quantity).integer }}</span>
                <span class="decimals">.{{ formatPriceParts(item.price * item.quantity).decimals }}</span>
              </p>
            </article>
          </div>
        </div>
      </section>

      <aside class="summary fade-up">
        <h2>订单汇总</h2>
        <div class="row"><span>商品件数</span><span>{{ cartCount }}</span></div>
        <div class="row"><span>商品金额</span>
          <p class="price"><span class="currency">¥</span><span class="amount">{{ formatPriceParts(cartTotal).integer }}</span><span class="decimals">.{{ formatPriceParts(cartTotal).decimals }}</span></p>
        </div>
        <div class="row"><span>运费</span><span class="free">免运费</span></div>
        <div class="line"></div>
        <div class="row total"><span>应付总额</span>
          <p class="price"><span class="currency">¥</span><span class="amount">{{ formatPriceParts(cartTotal).integer }}</span><span class="decimals">.{{ formatPriceParts(cartTotal).decimals }}</span></p>
        </div>
        <button :disabled="submitting" @click="submitOrder">{{ submitting ? '处理中...' : '提交订单' }}</button>
      </aside>
    </div>

    <div v-else class="empty fade-up">
      <div class="icon">📦</div>
      <h2>暂无待结算商品</h2>
      <p>先去购物车添加商品，再来下单吧。</p>
      <button @click="router.push('/list')">去逛商城</button>
    </div>
  </div>
</template>

<style scoped>
.checkout-view {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.checkout-layout {
  display: grid;
  grid-template-columns: 1fr 320px;
  gap: 16px;
  align-items: start;
}

.main {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.main h1 {
  color: #111827;
}

.card,
.summary,
.success-state,
.empty {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: var(--shadow-soft);
}

.card {
  padding: 14px;
}

.card h2 {
  margin-bottom: 10px;
  font-size: 1rem;
}

.items {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.item-row {
  display: grid;
  grid-template-columns: 64px 1fr auto;
  gap: 10px;
  align-items: center;
}

.item-row img {
  width: 64px;
  height: 64px;
  border-radius: 8px;
  object-fit: cover;
}

.name {
  color: #111827;
  margin-bottom: 4px;
}

.item-price {
  color: #ef4444;
}

.summary {
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
  align-items: center;
}

.total {
  color: #111827;
  font-weight: 700;
}

.line {
  height: 1px;
  background: #e5e7eb;
  margin: 10px 0;
}

.free {
  color: #16a34a;
}

.summary button,
.success-state button,
.empty button {
  width: 100%;
  min-height: 44px;
  border-radius: 8px;
  background: linear-gradient(135deg, #4f46e5, #4338ca);
  color: #fff;
  font-weight: 700;
}

.summary button:disabled {
  opacity: 0.6;
}

.muted {
  color: #6b7280;
  font-size: 0.9rem;
}

.success-state,
.empty {
  text-align: center;
  padding: 56px 20px;
  max-width: 520px;
  margin: 0 auto;
}

.icon {
  font-size: 3rem;
  margin-bottom: 12px;
}

.success-state h1,
.empty h2 {
  color: #111827;
  margin-bottom: 6px;
}

.success-state p,
.empty p {
  color: #6b7280;
  margin-bottom: 14px;
}

@media (max-width: 1024px) {
  .checkout-layout {
    grid-template-columns: 1fr;
  }

  .summary {
    position: static;
  }
}
</style>
