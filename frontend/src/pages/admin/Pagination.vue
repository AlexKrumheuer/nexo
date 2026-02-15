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