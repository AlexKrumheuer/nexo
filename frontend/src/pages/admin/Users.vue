<script setup>
import '../../style/admin/users.css'
</script>

<template>
    <section class="admin-dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Users Management</h1>
            <p class="dashboard-subtitle">Manage system users, access levels and restrictions.</p>
        </div>

        <div class="admin-dashboard--content">
            <div class="main-info">
                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Total users</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">500.000</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> +12% from last month
                        </p>
                    </div>
                </div>

                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Active Today</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">3.200</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> +10% from yesterday
                        </p>
                    </div>
                </div>

                <div class="grid-item card-stats">
                    <div class="card-header">
                        <h2>Banned Users</h2>
                    </div>
                    <div class="card-content">
                        <h2 class="stat-value">45</h2>
                        <p class="stat-trend positive">
                            <fa icon="arrow-up" /> -2% from last month
                        </p>
                    </div>
                </div>
            </div>

            <div class="grid-item card-table-container">
                <div class="card-header">
                    <h2>User List</h2>
                    <a href="#" class="view-all">View All</a>
                </div>
                <div class="filter-table">
                    <div class="search-container">
                        <div class="container-filter-search-icon">
                            <fa class="filter-search-icon" icon="search"/>
                        </div>
                        <input class="filter-input" type="text" placeholder="Search for name...">
                    </div>
                    <div class="filter-table-right">
                        <select class="filter-select">
                            <option value="">Filter by Role</option>
                            <option value="">Filter by Name</option>
                            <option value="">Filter by Status</option>
                        </select>
                        <button class="filter-table--add">Add User</button>
                    </div>
                </div>
                
                <div class="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="table-item">
                                <td class="id-cell">
                                    <div class="id-cell__img">
                                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZsbDUaOj73epjVaDWhej5fPrNuFtfZSz0rw&s" alt="User Avatar">
                                    </div>
                                    <p>Lewandowski</p>
                                </td>
                                <td>
                                    <div class="user-cell">
                                        <span>lewa@email.com</span>
                                    </div>
                                </td>
                                <td><span class="status-badge seller">Seller</span></td>
                                <td><span class="status-badge success">Active</span></td>
                                <td class="actions"> 
                                    <div class="action-btn edit" title="Edit User">
                                        <fa icon="edit"/>
                                        <span>edit</span>
                                    </div>
                                    <div class="action-btn ban" title="Ban User">
                                        <fa icon="trash"/>
                                        <span>ban</span>
                                    </div>
                                    <p class="view-details">view details</p>
                                </td>
                            </tr>
                            <tr class="table-item">
                                <td class="id-cell">
                                    <div class="id-cell__img">
                                        <div class="avatar-placeholder">JD</div>
                                    </div>
                                    <p>John Doe</p>
                                </td>
                                <td>
                                    <div class="user-cell">
                                        <span>john.doe@email.com</span>
                                    </div>
                                </td>
                                <td><span class="status-badge user">User</span></td>
                                <td><span class="status-badge pending">Pending</span></td>
                                <td class="actions"> 
                                    <div class="action-btn edit">
                                        <fa icon="edit"/>
                                        <span>edit</span>
                                    </div>
                                    <div class="action-btn ban">
                                        <fa icon="trash"/>
                                        <span>ban</span>
                                    </div>
                                    <p class="view-details">view details</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="pagination-area">
            <p class="pagination-info">
                Showing <span class="highlight">1</span> to <span class="highlight">10</span> of <span class="highlight">500</span> results
            </p>
            
            <div class="pagination-controls">
                <button class="pagination-btn disabled" disabled>
                    <fa icon="angle-left"></fa>
                </button>

                <button class="pagination-btn active">1</button>
                <button class="pagination-btn">2</button>
                <button class="pagination-btn">3</button>
                <button class="pagination-btn">4</button>
                <span class="pagination-dots">...</span>
                <button class="pagination-btn">50</button>

                <button class="pagination-btn">
                    <fa icon="angle-right"></fa>
                </button>
            </div>
        </div>
    </section>
</template>

<style scoped>


.search-container {
    display: grid;
    grid-template-columns: 15% 85%;
    align-items: center;
    justify-content: space-between;
    font-size: 0.8rem;
    width: 15rem;
    border: 1px solid #000;
    border-radius: 8px;
}

.filter-table {
    display: flex;
    justify-content: space-between;
    padding: 1rem;
}

.container-filter-search-icon {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

}

.search-icon {
    width: 100%;
    text-align: center;
    padding-left: 0.5rem;
}

.filter-input {
    padding: 0.5rem;
    border: none;
    border-radius: 8px;
}

.filter-input:focus {
    outline: none;
}

.filter-table-right {
    display: flex;
    gap: 2rem;
}

.filter-select {
    padding: 0.5rem;
    background-color: #fff;
    border-radius: 8px;
}

.filter-table--add {
    padding: 0.5rem 1.5rem;
    background-color: #0369a1;
    border: 1px solid transparent;
    font-weight: bold;
    color: #fff;
    border-radius: 8px;
}

.filter-table--add:hover {
    cursor: pointer;
    background-color: #fff;
    color: #0369a1;
    border: 1px solid #0369a1;
}
</style>