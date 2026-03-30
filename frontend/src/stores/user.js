import { defineStore } from 'pinia'

const defaultUser = {
  id: 'user_001',
  name: 'John Doe',
  address: '123 Main Street, Tech City',
}

export const useUserStore = defineStore('user', {
  state: () => ({
    profile: JSON.parse(localStorage.getItem('user')) || defaultUser,
  }),
  actions: {
    updateProfile(nextProfile) {
      this.profile = { ...this.profile, ...nextProfile }
      localStorage.setItem('user', JSON.stringify(this.profile))
    },
  },
})
