<script>
</script>
<template>
  <div>
    <div class="loading-overlay">
      <div class="spinner"></div> 
      <p>Processing, please wait...</p>
    </div>
  </div>
</template>
<style scoped>
.loading-overlay {
  position: fixed; 
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(255, 255, 255, 0.7); 
  z-index: 999999; 
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  pointer-events: all; 
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #1e4770; 
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>