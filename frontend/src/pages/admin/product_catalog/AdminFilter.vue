<script setup>
defineProps({
    placeholder: { type: String, default: 'Search...' },
    btnText: { type: String, default: 'Add Item' },
    showBtn: { type: Boolean, default: true }
})
defineEmits(['search', 'add-click'])
</script>

<template>
    <div class="filter-table">
        <div class="search-container">
            <fa class="filter-search-icon" icon="search" />
            <input 
                class="filter-input" 
                type="text" 
                :placeholder="placeholder"
                @input="$emit('search', $event.target.value)"
            >
        </div>
        <div class="filter-table-right">
            <slot name="filters"></slot>
            
            <button v-if="showBtn" class="filter-table--add" @click="$emit('add-click')">
                {{ btnText }}
            </button>
        </div>
    </div>
</template>