<script setup>
import { onMounted, ref } from 'vue';
import api from '../../services/api';

const selectedAddress = ref(0);

const pageState = ref('list')

const addresses = ref([])

const addressObject = ref({
    street: '',
    number: '',
    complement: '',
    city: '',
    state: '',
    zipCode: '',
    addressType: 'Residencial'
})

const editAddressObject = ref({
    id: null,
    street: '',
    number: '',
    complement: '',
    city: '',
    state: '',
    zipCode: '',
    addressType: ''
})

const addressesAddAddress = async () => {
    try {
        const response = await api.post('/address', addressObject.value)
        console.log('Address added successfully:', response.data)
        await fetchAddress()
        addressObject.value = {
            street: '',
            number: '',
            complement: '',
            city: '',
            state: '',
            zipCode: '',
            addressType: 'Residencial'
        }
        changePageState('cancel')
    } catch (error) {
        console.error('Error adding address:', error)
    }
}

const editAddress = (address) => {
    editAddressObject.value = { ...address }
    pageState.value = 'edit'
}

const editAddressSubmit = async () => {
    try {
        const response = await api.put(`/address/${editAddressObject.value.id}`, editAddressObject.value)
        console.log('Address edited successfully:', response.data)
        await fetchAddress()
        editAddressObject.value = {
            id: null,
            street: '',
            number: '',
            complement: '',
            city: '',
            state: '',
            zipCode: '',
            addressType: ''
        }
        changePageState('cancel')
    } catch (error) {
        console.error('Error editing address:', error)
    }
}

onMounted(() => {
    fetchAddress()
})

const fetchAddress = async () => {
    const loading = ref(true)
    try {
        loading.value = true
        const addressesResponse = await api.get('/address')
        addresses.value = addressesResponse.data.address || []
    } catch (error) {
        console.error('Error fetching addresses:', error)
    } finally {
        loading.value = false
    }
}

const removeAddress = async (id) => {
    try {
        await api.delete(`/address/${id}`)
        console.log('Address removed successfully')
        await fetchAddress()
    } catch (error) {
        console.error('Error removing address:', error)
    }
}

const changePageState = (state) => {
    if(state === 'cancel') {
        addressObject.value = {
            street: '',
            number: '',
            complement: '',
            city: '',
            state: '',
            zipCode: '',
            addressType: 'Residencial'
        }
        pageState.value = 'list'
    } else {
        pageState.value = 'add'
    }
}

</script>

<template>
    <div class="container-address">
        <h1 class="title">That's all your address</h1>

        <div v-if="pageState === 'list'" class="checkout-layout">
            <p v-if="addresses.length === 0">You don't have any addresses yet.</p>
            <form v-else class="address-list">
                <label v-for="(addr, index) in addresses" :key="addr.id" class="card-address"
                    :class="{ 'is-selected': selectedAddress === index }">

                    <div class="radio-wrapper">
                        <input type="radio" name="address" :value="index" v-model="selectedAddress">
                    </div>

                    <div class="address-details">
                        <div class="address-header">
                            <h2 class="address-type">{{ addr.addressType }}</h2>

                            <div class="container-edit-address">
                                <fa class="edit" icon="pencil-alt" @click.stop="editAddress(addr)"></fa>
                                <fa class="remove" icon="trash" @click.stop="removeAddress(addr.id)"></fa>
                            </div>
                        </div>

                        <p class="address-text">
                            <strong>{{ addr.street }}</strong>, {{ addr.number }}
                        </p>

                        <p v-if="addr.complement" class="address-subtext">{{ addr.complement }}</p>

                        <p class="address-location">
                            {{ addr.city }} - {{ addr.state }} | {{ addr.zipCode }}
                        </p>

                    </div>
                </label>
                <button class="btn-add-address" @click="changePageState('add')">Add New Address</button>
            </form>
        </div>
        <div v-else-if="pageState === 'add'" class="container-new-address">
            <h2 class="title-form">Add New Address</h2>
            <form class="form-address" @submit.prevent="addressesAddAddress">

                <div class="form-row">
                    <div class="form-group flex-2">
                        <label for="zipcode">ZIPCODE</label>
                        <input type="text" id="zipcode" placeholder="00000-000" v-model="addressObject.zipCode">
                    </div>
                    <div class="form-group flex-1">
                        <label for="addressType">Address Type</label>
                        <select id="addressType" v-model="addressObject.addressType">
                            <option value="Residencial">Residencial</option>
                            <option value="Comercial">Comercial</option>
                        </select>
                    </div>
                </div>

                <div class="form-group form-street">
                    <div class="form-street-container">
                        <label for="full-address">Street</label>
                        <input type="text" id="full-address" placeholder="Street, Neighborhood"
                            v-model="addressObject.street">
                    </div>
                    <div class="form-street-container">
                        <label for="number">Street Number</label>
                        <input type="text" id="number" placeholder="Number" v-model="addressObject.number">
                    </div>
                </div>



                <div class="form-group">
                    <label for="complement">Complement (Optional)</label>
                    <input type="text" id="complement" placeholder="Apt, Block, Suite"
                        v-model="addressObject.complement">
                </div>

                <div class="form-row">
                    <div class="form-group flex-3">
                        <label for="city">City</label>
                        <input type="text" id="city" placeholder="" v-model="addressObject.city">
                    </div>
                    <div class="form-group flex-1">
                        <label for="state">State</label>
                        <input type="text" id="state" placeholder="PR" v-model="addressObject.state">
                    </div>
                </div>

                <div class="container-actions">
                    <button type="button" class="btn-cancel" @click="changePageState('cancel')">Cancel</button>
                    <button class="btn-save" type="submit">Save Address</button>
                </div>
            </form>
        </div>
        <div v-else-if="pageState === 'edit'" class="container-new-address">
            <h2 class="title-form">Edit Address</h2>
            <form class="form-address" @submit.prevent="editAddressSubmit">

                <div class="form-row">
                    <div class="form-group flex-2">
                        <label for="zipcode">ZIPCODE</label>
                        <input type="text" id="zipcode" placeholder="00000-000" v-model="editAddressObject.zipCode">
                    </div>
                    <div class="form-group flex-1">
                        <label for="addressType">Address Type</label>
                        <select id="addressType" v-model="editAddressObject.addressType">
                            <option value="Residencial">Residencial</option>
                            <option value="Comercial">Comercial</option>
                        </select>
                    </div>
                </div>

                <div class="form-group form-street">
                    <div class="form-street-container">
                        <label for="full-address">Street</label>
                        <input type="text" id="full-address" placeholder="Street, Neighborhood"
                            v-model="editAddressObject.street">
                    </div>
                    <div class="form-street-container">
                        <label for="number">Street Number</label>
                        <input type="text" id="number" placeholder="Number" v-model="editAddressObject.number">
                    </div>
                </div>



                <div class="form-group">
                    <label for="complement">Complement (Optional)</label>
                    <input type="text" id="complement" placeholder="Apt, Block, Suite"
                        v-model="editAddressObject.complement">
                </div>

                <div class="form-row">
                    <div class="form-group flex-3">
                        <label for="city">City</label>
                        <input type="text" id="city" placeholder="" v-model="editAddressObject.city">
                    </div>
                    <div class="form-group flex-1">
                        <label for="state">State</label>
                        <input type="text" id="state" placeholder="PR" v-model="editAddressObject.state">
                    </div>
                </div>

                <div class="container-actions">
                    <button type="button" class="btn-cancel" @click="changePageState('cancel')">Cancel</button>
                    <button class="btn-save" type="submit">Edit Address</button>
                </div>
            </form>
        </div>
    </div>
</template>

<style scoped>
.container-address {
    max-width: 1100px;
    margin: 0 auto;
    padding: 40px 20px;
    font-family: sans-serif;
    color: #333;
}

.title {
    font-size: 1.8rem;
    margin-bottom: 30px;
    font-weight: 700;
}

.checkout-layout {
    display: flex;
    flex-direction: column;
    gap: 40px;
}

.card-address {
    display: flex;
    gap: 15px;
    border: 2px solid #e0e0e0;
    padding: 20px;
    border-radius: 12px;
    margin-bottom: 16px;
    cursor: pointer;
    transition: all 0.2s ease;
}

.card-address:hover {
    border-color: #bdbdbd;
    background-color: #f9f9f9;
}

.card-address.is-selected {
    border-color: #0369a1;
    background-color: #f0fff4;
}

.address-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
}

.address-details {
    width: 100%;
}

.address-text small {
    display: block;
    color: #888;
    font-weight: normal;
    margin-top: 2px;
}

.radio-wrapper {
    display: flex;
    align-items: center;
    padding-top: 4px;
}

.container-edit-address fa {
    cursor: pointer;
    transition: transform 0.1s ease;
}

.container-edit-address fa:active {
    transform: scale(0.9);
}

.address-tag {
    display: inline-block;
    margin-top: 8px;
}

.address-type {
    font-size: 1.1rem;
    margin: 0;
}

.container-edit-address {
    display: flex;
    gap: 10px;
    color: #888;
    font-size: 1rem;
}

.edit {
    color: #0369a1
}

.remove {
    color: #e53935;
}

.edit:hover {
    color: #024ea6;
}

.remove:hover {
    color: #b71c1c;
}

.shipping-price {
    font-size: 0.9rem;
    color: #666;
    font-weight: 600;
}

.arrive {
    font-size: 1rem;
    color: #888;
    margin-bottom: 10px;
}

.address-text {
    font-size: 0.95rem;
    line-height: 1.4;
    color: #555;
    margin-bottom: 10px;
}

.address-tag {
    font-size: 0.75rem;
    text-transform: uppercase;
    background: #eee;
    padding: 4px 8px;
    border-radius: 4px;
    color: #777;
}

.btn-add-address {
    background-color: transparent;
    width: 100%;
    color: #0369a1;
    border: 2px dashed #0369a1;
    padding: 12px;
    border-radius: 8px;
    font-weight: bold;
    font-size: 0.95rem;
    cursor: pointer;
    transition: all 0.2s ease;
}

.btn-add-address:hover {
    background-color: #0369a1;
    color: white;
    border-color: #0369a1;
}

.summary-card {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 24px;
    height: fit-content;
    position: sticky;
    top: 20px;
}

.summary-title {
    font-size: 1.3rem;
    margin-bottom: 20px;
}

.summary-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 12px;
    color: #666;
}

.total {
    font-size: 1.2rem;
    color: #000;
    margin-top: 15px;
}

.divider {
    border: 0;
    border-top: 1px solid #eee;
    margin: 15px 0;
}

.btn-finish {
    width: 100%;
    background-color: #0369a1;
    color: white;
    border: none;
    padding: 14px;
    border-radius: 8px;
    font-weight: bold;
    font-size: 1rem;
    cursor: pointer;
    margin-top: 20px;
    transition: background 0.2s;
}

.btn-finish:hover {
    background-color: #024ea6;
}

.container-new-address {
    margin-top: 50px;
    padding: 30px;
    border: 1px solid #e0e0e0;
    border-radius: 12px;
    background-color: #fff;
}

.title-form {
    font-size: 1.4rem;
    margin-bottom: 25px;
    color: #333;
}

.form-address {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.form-street {
    display: grid;
    grid-template-columns: 80% 20%;
    align-items: center;
    width: 100%;
}

.form-street-container {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.form-group label {
    font-size: 0.9rem;
    font-weight: 600;
    color: #666;
}

.form-group input,
.form-group select {
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 1rem;
    outline-color: #0369a1;
    transition: border-color 0.2s;
}

.form-group input:focus {
    border-color: #0369a1;
}

.form-row {
    display: flex;
    gap: 20px;
}

/* Auxiliares de largura para o grid */
.flex-1 {
    flex: 1;
}

.flex-2 {
    flex: 2;
}

.flex-3 {
    flex: 3;
}

.container-actions {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 10px;
}

.btn-cancel {
    background: transparent;
    border: 1px solid #ccc;
    padding: 12px 24px;
    border-radius: 8px;
    font-weight: bold;
    cursor: pointer;
    color: #666;
}

.btn-save {
    background-color: #0369a1;
    color: white;
    border: none;
    padding: 12px 24px;
    border-radius: 8px;
    font-weight: bold;
    cursor: pointer;
    transition: background 0.2s;
}

.btn-save:hover {
    background-color: #024ea6;
}

@media (max-width: 600px) {
    .form-row {
        flex-direction: column;
    }
}

@media (max-width: 850px) {
    .checkout-layout {
        grid-template-columns: 1fr;
    }
}
</style>