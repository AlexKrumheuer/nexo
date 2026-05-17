<script setup>
import { computed } from 'vue';
import '../../style/pagination.css'

const emit = defineEmits(['change-page', 'change-size'])

const props = defineProps({
    page: { type: Number, required: true }, 
    totalPages: { type: Number, required: true },
    size: { type: Number, required: true },
    totalItems: { type: Number, required: true },
})

const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < props.totalPages && newPage !== props.page) {
        emit('change-page', newPage)
    }
}

const handleSizeChange = (event) => {
    const newSize = Number(event.target.value)
    emit('change-size', newSize)
}

const visiblePages = computed(() => {
    const pages = []
    const total = props.totalPages
    const current = props.page + 1 
    const delta = 2 

    for (let i = 1; i <= total; i++) {
        if (i === 1 || i === total || (i >= current - delta && i <= current + delta)) {
            pages.push(i)
        } else if (pages[pages.length - 1] !== '...') {
            pages.push('...')
        }
    }
    return pages
})

const showingStart = computed(() => props.totalItems === 0 ? 0 : (props.page * props.size) + 1)
const showingEnd = computed(() => Math.min((props.page + 1) * props.size, props.totalItems))

</script>

<template>
    <div class="pagination-area" v-if="totalPages > 0">
        <div class="pagination-size">
            <p class="pagination-info">
                Showing <span class="highlight">{{ showingStart }}</span> to 
                <span class="highlight">{{ showingEnd }}</span> of 
                <span class="highlight">{{ totalItems }}</span> results
            </p>
            
            <select class="pagination-select" :value="size" @change="handleSizeChange">
                <option :value="10">10</option>
                <option :value="20">20</option>
                <option :value="50">50</option>
            </select>
        </div>

        <div class="pagination-controls">
            <button 
                class="pagination-btn" 
                :disabled="page === 0" 
                @click="handlePageChange(page - 1)"
            >
                <fa icon="angle-left"></fa>
            </button>

            <button 
                v-for="(p, index) in visiblePages" 
                :key="index"
                class="pagination-btn"
                :class="{ 'active': (p - 1) === page, 'disabled': p === '...' }"
                @click="p !== '...' ? handlePageChange(p - 1) : null"
            >
                {{ p }}
            </button>

            <button 
                class="pagination-btn" 
                :disabled="page >= totalPages - 1"
                @click="handlePageChange(page + 1)"
            >
                <fa icon="angle-right"></fa>
            </button>
        </div>
    </div>
</template>
<style scoped>
.pagination-size {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.pagination-select {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    padding: 0.4rem 2rem 0.4rem 0.8rem;
    font-size: 0.9rem;
    font-family: 'Inter', sans-serif;
    color: #4a5568; 
    background-color: #fff;
    border: 1px solid #e2e8f0;
    border-radius: 6px;
    cursor: pointer;
    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e");
    background-position: right 0.5rem center;
    background-repeat: no-repeat;
    background-size: 1.5em 1.5em;
    transition: all 0.2s ease-in-out;
}

.pagination-select:hover {
    border-color: #cbd5e0;
    background-color: #f8fafc;
}

.pagination-select:focus {
    outline: none;
    border-color: #367cc1; 
    box-shadow: 0 0 0 3px rgba(54, 124, 193, 0.15); 
}
</style>