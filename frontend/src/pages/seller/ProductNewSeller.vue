<script setup>
import { computed, onMounted, ref } from 'vue'
import api from '../../services/api'
import { useToast } from 'vue-toastification'
import Validator from '../../util/Validator'
import '../../style/seller/product_new_seller.css'

const toast = useToast()
const categoryList = ref([])
const error = ref('')
const loading = ref(false)

// THIS IS THE OBJECT WITH ALL INPUTS FIELD
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

// FUNCTION TO CALC FINAL PRICE
// WITH COMPUTED IF PRICE OR DISCOUNT IS ALTERED
// THE FUNCTION IS RECALLED AND RECALC FINALPRICE
const finalPrice = computed(() => {
    const price = parseFloat(form.value.price) || 0
    const discount = parseFloat(form.value.discountPercent) || 0

    if (price <= 0) return 'R$ 0,00'
    const calc = price - (price * (discount / 100))
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(calc)
})

onMounted(() => {
    fetchCategories()
    console.log(categoryList.value)
})

// FUNCTION THAT IS CALLED WHEN USER SUBMIT THE PRODUCT
const postProduct = async () => {
    if (!productValidation()) return
    loading.value = true
    try {
        const formData = new FormData();
        formData.append('data', JSON.stringify(form.value));
        selectedFiles.value.forEach(f => formData.append('images', f));

        await api.post('/api/products', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })

        toast.success("Product successfully saved!")
        cleanFields()
    } catch (e) {
        toast.error("Error saving product, try again later")
        console.error("Error saving produc: ", e.message || e)
    } finally {
        loading.value = false
    }
}

const cleanFields = () => {
    form.value.active = true
    form.value.brand = ''
    form.value.categoryId = null
    form.value.description = ''
    form.value.price = 0
    form.value.sku = ''
    form.value.stockQuantity = 0
    form.value.title = ''
    form.value.discountPercent = 0
    selectedFiles.value = []
    previewImages.value = []
}

// FUNCTION TO GET ALL CATEGORIES FROM DB
const fetchCategories = async () => {
    try {
        const response = await api.get("/api/categories")
        categoryList.value = response.data.content
    } catch (e) {
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

// THIS FUNCTION IS USED WHEN THE IMAGES ARE SENT USING DRAG
// IT SAVES THE IMAGES IN A FILE AND THEN CALL PROCESSFILES
const onDrop = (event) => {
    isDragging.value = false
    const files = event.dataTransfer.files
    processFiles(files)
}

const selectedFiles = ref([])
const previewImages = ref([]);

// THIS FUNCTION IS USED TO DEAL WITH IMAGES BEING SELECTED
// IT ITERATES OVER EVERY IMAGE SENT AND THEN SAVE IN 
// SELECTEDFILES AND PREVIEWIMAGES
const processFiles = (files) => {
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

// THIS FUNCTION IS USED TO REMOVE SELECTED IMAGES
const removeImage = (index) => {
    URL.revokeObjectURL(previewImages.value[index].url);
    selectedFiles.value.splice(index, 1)
    previewImages.value.splice(index, 1)
};


// THIS FUNCTION IS USED TO VALIDATE ALL PRODUCT INFO
const productValidation = () => {
    if (!Validator.textValidator(form.value.title)) {
        toast.error("Product name must not be blank")
        return false
    }
    if (!Validator.description(form.value.description)) {
        toast.error("Description must be at least 30 chars")
        return false
    }
    if (!form.value.categoryId) {
        toast.error("Select a category")
        return false
    }
    if (!Validator.textValidator(form.value.brand)) {
        toast.error("Brand is required")
        return false
    }
    if (form.value.price <= 0) {
        toast.error("Price must be greater than 0")
        return false
    }
    if (form.value.stockQuantity === "" || form.value.stockQuantity < 0) {
        toast.error("Invalid stock quantity")
        return false
    }
    if (!Validator.textValidator(form.value.sku)) {
        toast.error("SKU is required")
        return false
    }
    if (selectedFiles.value.length === 0) {
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
                <button class="btn-secondary" type="button" :disabled="loading">Cancel</button>
                <button class="btn-primary" @click="postProduct()" :disabled="loading">
                    <fa icon="spinner" spin v-if="Loading" />
                    <fa icon="save" v-else/> 
                    {{ loading ? 'Saving...' : 'Save Product'}}
                </button>
            </div>
        </div>

        <form @submit.prevent="postProduct()">
            <fieldset :disabled="loading" class="contents-wrapper form-layout">
                <div class="main-column">
                    <div class="card">
                        <h3>General Info</h3>

                        <div class="form-group">
                            <label>Product's Title</label>
                            <input type="text" placeholder="Ex: Smartphone Galaxy S23 Ultra" v-model="form.title" />
                        </div>

                        <div class="form-group">
                            <label>Complete Description</label>
                            <textarea rows="6" placeholder="Describe all tecnic details, guarantee, etc..."
                                v-model="form.description"></textarea>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Category</label>
                                <select v-model="form.categoryId">
                                    <option :value="null" disabled selected>Select a category</option>
                                    <option v-for="category in categoryList" :key="category.id" :value="category.id">
                                        {{ category.name }}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Brand</label>
                                <input type="text" placeholder="Ex: Samsung" v-model="form.brand" />
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <h3>Price & Stock</h3>
                        <div class="form-row">
                            <div class="form-group">
                                <label>Original Price (R$)</label>
                                <input type="number" placeholder="0,00" v-model="form.price" />
                            </div>
                            <div class="form-group">
                                <label>% Offer</label>
                                <input type="number" placeholder="0" v-model="form.discountPercent" />
                            </div>
                            <div class="form-group">
                                <label>Final Price</label>
                                <input type="text" value="R$ 0,00" disabled class="disabled-input"
                                    v-model="finalPrice" />
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Stock Quantity</label>
                                <input type="number" placeholder="0" v-model="form.stockQuantity" />
                            </div>
                            <div class="form-group">
                                <label>SKU (Code)</label>
                                <input type="text" placeholder="Ex: ELE-001" v-model="form.sku" />
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
                        <div class="upload-area" :class="{ 'dragging': isDragging }" @click="triggerUpload"
                            @dragover.prevent='isDragging = true' @dragleave.prevent='isDragging = false'
                            @drop.prevent='onDrop'>
                            <fa :icon="isDragging ? 'arrow-down' : 'cloud-upload-alt'" class="upload-icon" />
                            <p v-if="!isDragging">Drag your images here or click to select</p>
                            <p v-else>Drop to upload!</p>
                            <input type="file" multiple hidden accept="image/*" @change="handleFileUpload"
                                ref="fileInput" />
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
            </fieldset>
        </form>
    </div>
</template>

<style scoped></style>