import { ref } from 'vue'

const defaultUser = { id: 'user_001', name: 'John Doe', address: '123 Main Street, Tech City' }

const user = ref(JSON.parse(localStorage.getItem('user')) || defaultUser)

export function useUser() {
  return { user }
}
