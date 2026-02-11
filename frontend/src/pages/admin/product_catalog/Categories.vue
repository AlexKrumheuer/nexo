<script setup>
import '../../../style/admin/admin-global.css'
import AdminFilter from './AdminFilter.vue'
</script>

<template>
    <AdminFilter placeholder="Search categories..." btnText="Add Category">
        <template #filters>
            <select class="filter-select">
                <option value="">Status: All</option>
                <option value="">Active</option>
            </select>
        </template>
    </AdminFilter>

    <div class="table-wrapper">
        <table class="admin-table">
            <thead>
                <tr>
                    <th>Category Name</th>
                    <th>Description</th>
                    <th>Products</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr class="table-item">
                    <td>
                        <div class="flex-center-cell">
                            <strong>Electronics</strong>
                        </div>
                    </td>
                    <td>Gadgets and devices</td>
                    <td>1,240</td>
                    <td><span class="status-badge success">Active</span></td>
                    <td>
                        <div class="flex-center-cell">
                            <button class="action-btn edit">
                                <fa icon="edit" /> <span>Edit</span>
                            </button>
                            <button class="action-btn ban">
                                <fa icon="trash" /> <span>Delete</span>
                            </button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>