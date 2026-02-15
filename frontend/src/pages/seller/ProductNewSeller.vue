<script setup>
import {computed, onMounted, ref} from 'vue'
import api from '../../services/api'
import { useToast } from 'vue-toastification'
import Validator from '../../util/Validator'

const toast = useToast()
const categoryList = ref([])
const error = ref('')


const form = ref({
    title: '',
    description: '',
    categoryId: '',
    brand: '',
    price: 0,
    discountPercent: 0,
    stockQuantity: 0,
    sku: '',
    active: true
})

const finalPrice = computed(() => {
    const price = parseFloat(form.value.price) || 0
    const discount = parseFloat(form.value.discountPercent) || 0

    if(price <= 0) return 'R$ 0,00'
    const calc = price - (price * (discount/100))
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(calc)
})

onMounted(() => {
    fetchCategories()
    console.log(categoryList.value)
})

const postProduct = async () => {
    if(!productValidation()) return

    try {
        const formData = new FormData();
        formData.append('data', JSON.stringify(form.value));
        selectedFiles.value.forEach(f => formData.append('images', f));
        
        await api.post('/products', formData)
        
        toast.success("Produto salvo com sucesso!");
    } catch(e) {
        toast.error("Error saving product, try again later")
        console.error("Error saving produc: ", e.message || e)
    }
}

const fetchCategories = async () => {
    try {
        const response = await api.get("/api/categories")
        categoryList.value = response.data.content
    } catch(e) {
        toast.error("Internal error, try again later")
        console.error("Error: internal error" + error.message || error)
    }
}

const fileInput = ref(null)
const isDragging = ref(false)

const triggerUpload = () => {
    fileInput.value.click()
}

const handleFileUpload = (event) => {
    processFiles(event.target.files)
    event.target.value = '' 
}

const onDrop = (event) => {
    isDragging.value = false 
    const files = event.dataTransfer.files
    processFiles(files)
}

const selectedFiles = ref([])
const previewImages = ref([]);

const  processFiles = (files) => {
    if (!files) return

    for (let i = 0; i < files.length; i++) {
        const file = files[i]
        
        selectedFiles.value.push(file)

        previewImages.value.push({
            url: URL.createObjectURL(file),
            name: file.name
        })
    }
}

const removeImage = (index) => {
    URL.revokeObjectURL(previewImages.value[index].url);
    selectedFiles.value.splice(index, 1)
    previewImages.value.splice(index, 1)
};

const productValidation = () => {
    if(!Validator.textValidator(form.value.title)) {
        toast.error("Product name must not be blank")
        return false
    }
    if(!Validator.description(form.value.description)) {
        toast.error("Description must be at least 30 chars")
        return false
    }
    if(!form.value.categoryId) {
        toast.error("Select a category")
        return false
    }
    if(!Validator.textValidator(form.value.brand)) { 
        toast.error("Brand is required")
        return false
    }
    if(form.value.price <= 0) {
        toast.error("Price must be greater than 0")
        return false
    }
    if(form.value.stockQuantity === "" || form.value.stockQuantity < 0) {
        toast.error("Invalid stock quantity")
        return false
    }
    if(!Validator.textValidator(form.value.sku)) { 
        toast.error("SKU is required")
        return false
    }
    if(selectedFiles.value.length === 0) {
        toast.error("Upload at least one image")
        return false
    }
    return true

}
</script>
<template>
    <div class="product-form-page">
        <div class="page-header">
            <div class="titles">
                <router-link to="/seller/product" class="back-link">
                    <fa icon="arrow-left" /> Go Back
                </router-link>
                <h1>New Product</h1>
            </div>
            <div class="header-actions">
                <button class="btn-secondary">Cancel</button>
                <button class="btn-primary"  @click="postProduct()">
                    <fa icon="save" /> Save Product
                </button>
            </div>
        </div>

        <form class="form-layout" @submit.prevent="postProduct()">
            <div class="main-column">
                <div class="card">
                    <h3>General Info</h3>
                    
                    <div class="form-group">
                        <label>Product's Title</label>
                        <input type="text" placeholder="Ex: Smartphone Galaxy S23 Ultra" v-model="form.title"/>
                    </div>

                    <div class="form-group">
                        <label>Complete Description</label>
                        <textarea rows="6" placeholder="Describe all tecnic details, guarantee, etc..." v-model="form.description"></textarea>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Category</label>
                            <select v-model="form.categoryId">
                                <option value="" disabled selected>Select a category</option>
                                <option 
                                v-for="category in categoryList" 
                                :key="category.id" 
                                :value="category.id"
                                >
                                    {{category.name}}
                                </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Brand</label>
                            <input type="text" placeholder="Ex: Samsung" v-model="form.brand"/>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <h3>Price & Stock</h3>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Original Price (R$)</label>
                            <input type="number" placeholder="0,00" v-model="form.price"/>
                        </div>
                        <div class="form-group">
                            <label>% Offer</label>
                            <input type="number" placeholder="0" v-model="form.discountPercent"/>
                        </div>
                        <div class="form-group">
                            <label>Final Price</label>
                            <input type="text" value="R$ 0,00" disabled class="disabled-input" v-model="finalPrice"/>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Stock Quantity</label>
                            <input type="number" placeholder="0" v-model="form.stockQuantity"/>
                        </div>
                        <div class="form-group">
                            <label>SKU (Code)</label>
                            <input type="text" placeholder="Ex: ELE-001" v-model="form.sku"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="side-column">
                <div class="card">
                    <h3>Status</h3>
                    <div class="toggle-group">
                        <label class="switch">
                            <input type="checkbox" checked v-model="form.active">
                            <span class="slider round"></span>
                        </label>
                        <span>Product Active</span>
                    </div>
                    <p class="help-text">Inactive Products doesn't appear in the Shop.</p>
                </div>

                <div class="card">
                    <h3>Product's Images</h3>
                    <div 
                        class="upload-area" 
                        :class="{ 'dragging': isDragging }"
                        @click="triggerUpload"
                        @dragover.prevent='isDragging = true'
                        @dragleave.prevent='isDragging = false'
                        @drop.prevent='onDrop'
                    >
                        <fa :icon="isDragging ? 'arrow-down' : 'cloud-upload-alt'" class="upload-icon" />                        
                        <p v-if="!isDragging">Drag your images here or click to select</p>
                        <p v-else>Drop to upload!</p>
                        <input 
                            type="file" 
                            multiple 
                            hidden 
                            accept="image/*"
                            @change="handleFileUpload"
                            ref="fileInput"
                        />
                    </div>
                    
                    <div class="image-preview-list">
                        <div class="preview-item" v-for="(image, index) in previewImages" :key="index">
                            <img :src="image.url" alt="Preview's product" />
                            <button type="button" class="remove-btn" @click="removeImage(index)">
                                <fa icon="times" />
                            </button>
                        </div>

                    </div>
                </div>
            </div>
        </form>
    </div>
</template>

<style scoped>
.product-form-page {
    display: flex;
    flex-direction: column;
    gap: 20px;
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
}

.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.titles {
    display: flex;
    flex-direction: column;
    gap: 5px;
}

.back-link {
    text-decoration: none;
    color: #64748b;
    font-size: 0.9rem;
    display: flex;
    align-items: center;
    gap: 5px;
}

.back-link:hover {
    color: #3b82f6;
}

.page-header h1 {
    font-size: 1.5rem;
    color: #1e293b;
    margin: 0;
}

.header-actions {
    display: flex;
    gap: 10px;
}

.btn-primary {
    background-color: #3b82f6;
    color: white;
    padding: 10px 20px;
    border-radius: 8px;
    border: none;
    font-weight: 500;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 8px;
}

.btn-primary:hover {
    background-color: #2563eb;
}

.btn-secondary {
    background-color: white;
    color: #64748b;
    padding: 10px 20px;
    border-radius: 8px;
    border: 1px solid #cbd5e1;
    font-weight: 500;
    cursor: pointer;
}

.btn-secondary:hover {
    background-color: #f1f5f9;
}

.form-layout {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 20px;
}

.card {
    background: white;
    padding: 25px;
    border-radius: 12px;
    box-shadow: 0 1px 3px rgba(0,0,0,0.05);
    border: 1px solid #e2e8f0;
    margin-bottom: 20px;
}

.card h3 {
    margin: 0 0 20px 0;
    font-size: 1.1rem;
    color: #0f172a;
    border-bottom: 1px solid #f1f5f9;
    padding-bottom: 10px;
}

.main-column, .side-column {
    display: flex;
    flex-direction: column;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 20px;
    flex: 1;
}

.form-row {
    display: flex;
    gap: 20px;
}

label {
    font-size: 0.9rem;
    font-weight: 500;
    color: #334155;
}

input, select, textarea {
    padding: 10px 12px;
    border: 1px solid #cbd5e1;
    border-radius: 6px;
    font-size: 0.95rem;
    outline: none;
    color: #334155;
    background-color: white;
    transition: border 0.2s;
}

input:focus, select:focus, textarea:focus {
    border-color: #3b82f6;
    box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
}

textarea {
    resize: vertical;
}

.disabled-input {
    background-color: #f1f5f9;
    color: #94a3b8;
    cursor: not-allowed;
}

.toggle-group {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 10px;
}

.help-text {
    font-size: 0.8rem;
    color: #94a3b8;
    margin: 0;
}

/* Toggle Switch CSS */
.switch {
    position: relative;
    display: inline-block;
    width: 50px;
    height: 26px;
}

.switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #cbd5e1;
    transition: .4s;
}

.slider:before {
    position: absolute;
    content: "";
    height: 20px;
    width: 20px;
    left: 3px;
    bottom: 3px;
    background-color: white;
    transition: .4s;
}

input:checked + .slider {
    background-color: #3b82f6;
}

input:checked + .slider:before {
    transform: translateX(24px);
}

.slider.round {
    border-radius: 34px;
}

.slider.round:before {
    border-radius: 50%;
}

.upload-area {
    border: 2px dashed #cbd5e1;
    border-radius: 8px;
    padding: 30px;
    text-align: center;
    cursor: pointer;
    background-color: #f8fafc;
    transition: all 0.2s ease-in-out;
}

.upload-area:hover {
    border-color: #3b82f6;
    background-color: #eff6ff;
}

.upload-area.dragging {
    border-color: #2563eb;      
    background-color: #dbeafe;   
    transform: scale(1.02);    
    border-style: solid;        
}
.upload-icon {
    font-size: 2rem;
    color: #94a3b8;
    margin-bottom: 10px;
}

.upload-area p {
    color: #64748b;
    font-size: 0.9rem;
    margin: 0;
}

.image-preview-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 15px;
}

.preview-item {
    position: relative;
    width: 80px;
    height: 80px;
    border-radius: 6px;
    overflow: hidden;
    border: 1px solid #e2e8f0;
}

.preview-item img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.remove-btn {
    position: absolute;
    top: 2px;
    right: 2px;
    background: rgba(0,0,0,0.6);
    color: white;
    border: none;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    font-size: 0.7rem;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
}

.remove-btn:hover {
    background: #ef4444;
}

@media (max-width: 900px) {
    .form-layout {
        grid-template-columns: 1fr;
    }
    
    .form-row {
        flex-direction: column;
        gap: 0;
    }
}
</style>