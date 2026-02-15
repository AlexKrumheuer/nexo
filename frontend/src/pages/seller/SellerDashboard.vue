<script setup>
// Aqui você chamaria sua API para pegar os dados reais
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
.dashboard-container {
    padding: 2rem;
}

.dashboard-container h1 {
    font-size: 1.5rem;
    color: #1e293b;
    margin-bottom: 20px;
}

.stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    background: white;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.04);
    display: flex;
    align-items: center;
    gap: 15px;
}

.icon-bg {
    width: 50px;
    height: 50px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.2rem;
}
.icon-bg.blue { background: #dbeafe; color: #2563eb; }
.icon-bg.orange { background: #ffedd5; color: #ea580c; }
.icon-bg.green { background: #dcfce7; color: #16a34a; }
.icon-bg.red { background: #fee2e2; color: #dc2626; }

.stat-info {
    display: flex;
    flex-direction: column;
}

.stat-info .label { font-size: 0.85rem; color: #64748b; }
.stat-info .value { font-size: 1.25rem; font-weight: bold; color: #0f172a; }

/* Tabela */
.recent-orders-section {
    background: white;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.04);
}

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.view-all { color: #3b82f6; text-decoration: none; font-size: 0.9rem; }

.table-container { overflow-x: auto; }

table { width: 100%; border-collapse: collapse; }

th { text-align: left; color: #64748b; font-size: 0.85rem; padding: 12px; border-bottom: 1px solid #e2e8f0; }
td { padding: 12px; font-size: 0.9rem; color: #334155; border-bottom: 1px solid #f1f5f9; }

.status-badge {
    padding: 4px 8px;
    border-radius: 20px;
    font-size: 0.75rem;
    font-weight: 500;
}
.status-badge.warning { background: #ffedd5; color: #9a3412; }
.status-badge.success { background: #dcfce7; color: #166534; }

.btn-action {
    background: none;
    border: 1px solid #cbd5e1;
    border-radius: 4px;
    padding: 4px 10px;
    cursor: pointer;
    font-size: 0.8rem;
}
.btn-action:hover { background: #f8fafc; }
</style>