<script setup>
import '../../style/seller/seller_dashboard.css'


</script>

<template>
    <div class="dashboard-container">
        <h1>Overview</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <div class="icon-bg blue">
                    <fa icon="dollar-sign" />
                </div>
                <div class="stat-info">
                    <span class="label">Today's SAles</span>
                    <span class="value">R$ 1.250,00</span>
                </div>
            </div>

            <div class="stat-card">
                <div class="icon-bg orange">
                    <fa icon="box-open" />
                </div>
                <div class="stat-info">
                    <span class="label">Pending Orders</span>
                    <span class="value">5</span>
                </div>
            </div>

            <div class="stat-card">
                <div class="icon-bg green">
                    <fa icon="shopping-bag" />
                </div>
                <div class="stat-info">
                    <span class="label">Total Products</span>
                    <span class="value">42</span>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="icon-bg red">
                    <fa icon="exclamation-triangle" />
                </div>
                <div class="stat-info">
                    <span class="label">Low Stock</span>
                    <span class="value">3</span>
                </div>
            </div>
        </div>

        <div class="recent-orders-section">
            <div class="section-header">
                <h3>Latest Orders</h3>
                <router-link to="/seller/orders" class="view-all">View All</router-link>
            </div>
            
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Client</th>
                            <th>Date</th>
                            <th>Value</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>#1024</td>
                            <td>Maria Silva</td>
                            <td>14/02/2026</td>
                            <td>R$ 150,00</td>
                            <td><span class="status-badge warning">Pendente</span></td>
                            <td><button class="btn-action">Ver</button></td>
                        </tr>
                        <tr>
                            <td>#1023</td>
                            <td>João Souza</td>
                            <td>13/02/2026</td>
                            <td>R$ 89,90</td>
                            <td><span class="status-badge success">Enviado</span></td>
                            <td><button class="btn-action">Ver</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>