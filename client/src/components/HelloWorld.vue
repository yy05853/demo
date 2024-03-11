<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

defineProps({
  msg: {
    type: String,
    required: true
  }
})

const data = ref(null);

onMounted(() => {
  axios.get('http://localhost:8080/')
    .then(response => {
      data.value = response.data;
    })
    .catch(error => {
      console.error("エラー発生:", error);
    });
});
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      You’ve successfully created a project with
      <a href="https://vitejs.dev/" target="_blank" rel="noopener">Vite</a> +
      <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue 3</a>.
    </h3>
    <div>
      <p>データ: {{ data }}</p>
    </div>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
