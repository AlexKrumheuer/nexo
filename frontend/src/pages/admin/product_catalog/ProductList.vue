<script setup>
import '../../../style/admin/admin-global.css'
import AdminFilter from './AdminFilter.vue'
</script>

<template>
    <AdminFilter placeholder="Search for product..." btnText="Add Product">
        <template #filters>
            <select class="filter-select">
                <option value="">Category</option>
                <option value="">Price (Desc)</option>
                <option value="">Price (Asc)</option>
                <option value="">Status</option>
                <option value="">Stock (Desc)</option>
                <option value="">Stock (Asc)</option>
            </select>
        </template>
    </AdminFilter>

    <div class="table-wrapper">
        <table class="admin-table">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr class="table-item">
                    <td>
                        <div class="flex-center-cell">
                            <div class="id-cell__img">
                                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZsbDUaOj73epjVaDWhej5fPrNuFtfZSz0rw&s" alt="Product">
                            </div>
                            <p>Smart Watch</p>
                        </div>
                    </td>
                    <td>Electronics</td>
                    <td>R$ 450,00</td>
                    <td>120</td>
                    <td><span class="status-badge success">Active</span></td>
                    <td>
                        <div class="flex-center-cell">
                            <button class="action-btn edit" title="Edit">
                                <fa icon="edit" /> <span>Edit</span>
                            </button>
                            <button class="action-btn ban" title="Delete">
                                <fa icon="trash" /> <span>Delete</span>
                            </button>
                            <p class="view-details">Details</p>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>