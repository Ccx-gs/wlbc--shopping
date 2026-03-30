import { computed } from 'vue'
import { useUserStore } from '../stores/user.js'

export function useUser() {
  const store = useUserStore()
  return {
    user: computed(() => store.profile),
  }
}
