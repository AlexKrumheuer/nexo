import { defineStore } from "pinia";
import { ref } from "vue";
import api from "./api";

export const userUserStore = defineStore("user", () => {
  const userData = ref(null);
  const loading = ref(false);

  const fetchUser = async () => {
    if(userData.value !== null) return
    loading.value = true;
    try {
      loading.value = true;
      const response = await api.get("/users/me");
      userData.value = response.data;
    } catch (e) {
      console.error("Error getting user info");
    } finally {
      loading.value = false;
    }
  };

  const clearUser = () => {
      userData.value = null;
    };

  return { userData, loading, fetchUser, clearUser };
});
