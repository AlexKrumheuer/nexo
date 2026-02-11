<script setup>
import '../../../style/admin/admin-global.css'
import AdminFilter from './AdminFilter.vue'
</script>

<template>
    <AdminFilter placeholder="Search product or seller..." :showBtn="false">
        <template #filters>
            <select class="filter-select">
                <option value="">All Categories</option>
                <option value="">Oldest First</option>
            </select>
        </template>
    </AdminFilter>

    <div class="table-wrapper">
        <table class="admin-table">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Seller</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Date</th>
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
                            <p>Relógio do CR7</p>
                        </div>
                    </td>
                    <td>CR7 Shop</td>
                    <td>Sports</td>
                    <td>R$ 450,00</td>
                    <td>24/07/2025</td>
                    <td>
                        <div class="flex-center-cell">
                            <button class="action-btn approve" title="Approve">
                                <fa icon="check" /> <span>Approve</span>
                            </button>
                            <button class="action-btn ban" title="Reject">
                                <fa icon="times" /> <span>Reject</span>
                            </button>
                            <p class="view-details">
                                <fa icon="eye" /> View
                            </p>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>