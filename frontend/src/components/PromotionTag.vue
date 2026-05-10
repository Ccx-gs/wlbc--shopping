<script setup>
import { computed } from 'vue'

const props = defineProps({
  promotion: {
    type: Object,
    default: null,
  },
})

const hasPromotion = computed(() => {
  if (!props.promotion) return false
  return props.promotion.discountAmount > 0
})
</script>

<template>
  <div v-if="promotion" class="promotion-tag">
    <span class="tag primary">{{ promotion.title }}</span>
    <span v-if="hasPromotion && promotion.subtitle" class="tag secondary">{{ promotion.subtitle }}</span>
  </div>
</template>

<style scoped>
.promotion-tag {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag {
  display: inline-flex;
  align-items: center;
  min-height: 28px;
  border-radius: 999px;
  padding: 0 10px;
  font-size: 0.78rem;
  font-weight: 700;
}

.primary {
  color: #fff;
  background: linear-gradient(135deg, #ef4444, #dc2626);
}

.secondary {
  color: #b45309;
  background: #fffbeb;
  border: 1px solid #fde68a;
}
</style>
