<script setup>
import { computed, onBeforeUnmount, ref, watch } from 'vue'

const props = defineProps({
  promotion: {
    type: Object,
    default: null,
  },
})

const remainingSeconds = ref(0)
const timer = ref(null)

function calcRemainingSeconds(endAt) {
  if (!endAt) return 0
  const end = new Date(endAt).getTime()
  if (Number.isNaN(end)) return 0
  return Math.max(0, Math.floor((end - Date.now()) / 1000))
}

function stopTimer() {
  if (timer.value) {
    clearInterval(timer.value)
    timer.value = null
  }
}

function startTimer() {
  stopTimer()
  remainingSeconds.value = calcRemainingSeconds(props.promotion?.endAt)
  if (!remainingSeconds.value) return
  timer.value = setInterval(() => {
    remainingSeconds.value = calcRemainingSeconds(props.promotion?.endAt)
    if (!remainingSeconds.value) {
      stopTimer()
    }
  }, 1000)
}

const countdownText = computed(() => {
  const hours = Math.floor(remainingSeconds.value / 3600)
  const minutes = Math.floor((remainingSeconds.value % 3600) / 60)
  const seconds = remainingSeconds.value % 60
  return [hours, minutes, seconds]
    .map((item) => String(item).padStart(2, '0'))
    .join(':')
})

watch(() => props.promotion?.endAt, startTimer, { immediate: true })
onBeforeUnmount(stopTimer)
</script>

<template>
  <div v-if="promotion" class="countdown-wrap">
    <span class="title">距活动结束</span>
    <strong class="time">{{ remainingSeconds > 0 ? countdownText : '活动已结束' }}</strong>
  </div>
</template>

<style scoped>
.countdown-wrap {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  margin-top: 10px;
  background: #fff7ed;
  border: 1px solid #fed7aa;
  border-radius: 8px;
  padding: 6px 10px;
  width: fit-content;
}

.title {
  color: #9a3412;
  font-size: 0.84rem;
}

.time {
  color: #c2410c;
  letter-spacing: 0.06em;
}
</style>
