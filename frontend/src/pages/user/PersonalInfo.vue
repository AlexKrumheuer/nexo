<script setup>
import { onMounted, ref } from 'vue'
import { userUserStore } from '../../services/userStore'
import { storeToRefs } from 'pinia'
import { useToast } from 'vue-toastification'
import api from '../../services/api'
import Validator from '../../util/Validator'

const toast = useToast()
const userStore = userUserStore()

const { userData, loading } = storeToRefs(userStore)

const { fetchUser } = userStore


// BECOMING A SELLER
const fileSeller = ref(null)
const sellerLogoFile = ref(null) // Criada a variável separada para o arquivo
const fileSellerPreview = ref('')
const seller = ref({
    storeName: '',
    type: 'PF',
    cnpj: '',
    cpf: '',
    support_phone: ''
})


const fileBanner = ref(null)
const filePerfil = ref(null)

const triggerSellerLogo = () => {
    fileSeller.value.click()
}

const triggerBanner = () => {
    fileBanner.value.click()
}

const triggerPerfil = () => {
    filePerfil.value.click()
}

const isUploadingFile = ref(false)

const handleFileUpload = async (event, type) => {
    const file = event.target.files[0]
    if (!file) return

    if (file.size > 5 * 1024 * 1024) {
        if (type === 'banner') toast.error("Your banner image exceedes the 5MB limit")
        else toast.error("Your perfil image exceedes the 5MB limit")
        return
    }
    isUploadingFile.value = true
    const formData = new FormData()
    formData.append('file', file)

    try {
        if (type === 'banner') {
            const response = await api.post("/users/me/banner-image", formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            })
            userData.value.bannerUrl = response.data.bannerUrl
            toast.success("Banner Image Updated Successfully")
        } else if (type === 'perfil') {
            const response = await api.post("/users/me/perfil-image", formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            })
            userData.value.perfilUrl = response.data.perfilUrl
            toast.success("Perfil Image Updated Successfully")
        } else if (type === 'seller') {
            sellerLogoFile.value = file // Agora salva na variável correta
            fileSellerPreview.value = URL.createObjectURL(file)
            return
        }
    } catch (e) {
        toast.error("Something went wrong with your image, try again...")
        console.error("Error uploading image")
    } finally {
        isUploadingFile.value = false
    }
}

const editView = ref('')

const changeView = (view) => {
    newUsername.value = ''
    newEmail.value = ''
    password.value = ''
    newPassword.value = ''
    confirmNewPassword.value = ''

    if (view === editView.value) {
        editView.value = ''
    } else {
        editView.value = view
    }
}


const newUsername = ref('')
const newEmail = ref('')
const password = ref('')
const newPassword = ref('')
const confirmNewPassword = ref('')

const loadingUpdateUser = ref(false)
const loadingCreatingSeller = ref(false)

const submitChange = async () => {
    if (newUsername.value != '') {
        loadingUpdateUser.value = true
        try {
            const response = await api.put("/users/edit-username", {
                username: newUsername.value
            })
            userData.username = response.data.username
            toast.success("Username updated successfully")
        } catch (e) {
            console.error("Error changing name")
            toast.error("Error when trying to change your username, try again...")
        } finally {
            loadingUpdateUser.value = false
            editView.value = ''
            userStore.clearUser()
        }
    }
    if (editView.value === 'seller') {
        if (validatingSellerInfo()) {
            loadingCreatingSeller.value = true
            try {
                const formData = new FormData()
                formData.append('file', sellerLogoFile.value) // Passa o arquivo diretamente
                formData.append('storeName', seller.value.storeName)
                formData.append('type', seller.value.type)
                
                if (seller.value.cpf) {
                    formData.append('cpf', seller.value.cpf.replace(/\D/g, ''))
                }
                if (seller.value.cnpj) {
                    formData.append('cnpj', seller.value.cnpj.replace(/\D/g, ''))
                }
                if (seller.value.support_phone) {
                    formData.append('supportPhone', seller.value.support_phone.replace(/\D/g, '')) 
                }

                const response = await api.post("/seller", formData, {
                    headers: { 'Content-Type': 'multipart/form-data' }
                })
                
                toast.success("Seller account created successfully!")
                editView.value = ''
            } catch (e) {
                console.error("Error creating a seller: " + e.message || e)
                toast.error("Something went wrong, try again later...")
            } finally {
                loadingCreatingSeller.value = false
                editView.value = ''
                userStore.clearUser()
            }
        }
    }
}

const validatingSellerInfo = () => {
    if (!Validator.textValidator(seller.value.storeName)) {
        toast.error("Store name must not be null")
        return
    }
    if (seller.value.type != 'PF' && seller.value.type != 'PJ') {
        toast.error("You must choose how you are gonna work")
        return
    }
    if (seller.value.type === 'PF' && seller.value.cpf.length !== 14) {
        toast.error("Your CPF must have 11 char")
        return
    }
    if (seller.value.type === 'PJ' && seller.value.cnpj.length != 18) {
        toast.error("Your CNPJ must have 14 char")
        return
    }
    if (seller.value.type === 'PJ' && seller.value.support_phone.length != 15) {
        toast.error("Your Phone must have 15 char")
        return
    }
    
    // Verificação simplificada e funcional
    if (!sellerLogoFile.value) {
        toast.error("You must select your company's logo")
        return false
    }

    return true
}

const formatCPF = (value) => {
    return value
        .replace(/\D/g, '')
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d{1,2})/, '$1-$2')
        .replace(/(-\d{2})\d+?$/, '$1')
}

const formatCNPJ = (value) => {
    return value
        .replace(/\D/g, '')
        .replace(/^(\d{2})(\d)/, '$1.$2')
        .replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3')
        .replace(/\.(\d{3})(\d)/, '.$1/$2')
        .replace(/(\d{4})(\d)/, '$1-$2')
        .replace(/(-\d{2})\d+?$/, '$1')
}

const formatPhone = (value) => {
    if (!value) return ""
    let r = value.replace(/\D/g, "")
    r = r.substring(0, 11)

    if (r.length > 10) {
        return r.replace(/^(\d{2})(\d{5})(\d{4}).*/, "($1) $2-$3")
    } else if (r.length > 5) {
        return r.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, "($1) $2-$3")
    } else if (r.length > 2) {
        return r.replace(/^(\d{2})(\d{0,5})/, "($1) $2")
    } else if (r.length > 0) {
        return r.replace(/^(\d*)/, "($1")
    }
    return r
}

onMounted(() => {
    fetchUser()
})

</script>

<template>
    <div class="profile-wrapper" v-if="!loading">
        <div class="perfil-images">
            <div class="user-banner"
                :style="{ backgroundImage: userData?.bannerUrl ? `url(${userData.bannerUrl})` : `url('/img/default-user-banner.png')` }">
                <div class="perfil-image-container">
                    <img class="perfil-image"
                        :src="userData.perfilUrl ? userData.perfilUrl : '../../../public/img/default-user-pic.png'"
                        alt="Profile">
                    <div class="upload-icon-wrapper" @click="triggerPerfil">
                        <fa class="upload-icon" icon="upload" />
                    </div>
                    <input type="file" accept="image/png, image/jpeg, image/webp" hidden ref="filePerfil"
                        @change="(e) => handleFileUpload(e, 'perfil')">
                </div>
                <p class="username">{{ userData.username }}</p>
                <button class="edit-banner" @click="triggerBanner">
                    <fa icon="camera" /> Edit Banner
                </button>
                <input type="file" accept="image/png, image/jpeg, image/webp" hidden ref="fileBanner"
                    @change="(e) => handleFileUpload(e, 'banner')">
            </div>
        </div>

        <div class="edit-personalinfo-container">
            <div class="container-personalinfo-button">
                <button :class="{ active: editView === 'username' }" @click="changeView('username')">
                    Edit Username
                </button>
                <button :class="{ active: editView === 'email' }" @click="changeView('email')">
                    Edit E-mail
                </button>
                <button :class="{ active: editView === 'password' }" @click="changeView('password')">
                    Edit Password
                </button>
                <button v-if="userData.userRole === 'USER'" :class="{ active: editView === 'seller' }" @click="changeView('seller')">
                    Become a Seller
                </button>
            </div>

            <div class="container-editing" v-if="editView != ''">
                <div class="editing-card">
                    <h3 class="editing-title">
                        {{ editView === 'username' ? 'Update Username' :
                            editView === 'email' ? 'Update E-mail' :
                                editView === 'password' ? 'Update Password' : 'Become a Seller' }}
                    </h3>

                    <div class="input-group" v-if="editView == 'username'">
                        <label>New Username</label>
                        <input type="text" placeholder="Type your new username here" v-model="newUsername">
                    </div>

                    <div class="input-group" v-else-if="editView == 'email'">
                        <label>New E-mail Address</label>
                        <input type="email" placeholder="example@email.com" v-model="newEmail">
                    </div>

                    <div class="input-group" v-else-if="editView == 'password'">
                        <label>Current Password</label>
                        <input type="password" placeholder="••••••••" v-model="password">
                        <label class="mt-1">New Password</label>
                        <input type="password" placeholder="••••••••" v-model="newPassword">
                        <label class="mt-1">Confirm New Password</label>
                        <input type="password" placeholder="••••••••" v-model="confirmNewPassword">
                    </div>

                    <div class="input-group" v-else-if="editView == 'seller'">
                        <label>Store Name</label>
                        <input type="text" placeholder="Your Store Name..." v-model="seller.storeName">
                        <div>
                            <label class="mt-1">How are you going to Sell?</label>
                            <div class="seller-type" style="display: flex; gap: 1rem;">
                                <input type="radio" name="mt-1" value="PF" v-model="seller.type" />
                                <span>PF</span>
                            </div>
                            <div class="seller-type" style="display: flex; gap: 1rem;">
                                <input type="radio" name="mt-1" value="PJ" v-model="seller.type" />
                                <span>PJ</span>
                            </div>
                        </div>
                        <div class="input-group">
                            <div v-if="seller.type === 'PF'" class="input-seller">
                                <label>Type your CPF</label>
                                <input type="text" placeholder="000.000.000-00"
                                    @input="seller.cpf = formatCPF($event.target.value)" v-model="seller.cpf">
                            </div>
                            <div v-else-if="seller.type === 'PJ'" class="input-seller">
                                <label>Type your CNPJ</label>
                                <input type="text" placeholder="00.000.000/0000-00"
                                    @input="seller.cnpj = formatCNPJ($event.target.value)" maxlength="18"
                                    v-model="seller.cnpj">
                            </div>

                            <label class="mt-1">Support Phone (Optional)</label>
                            <input type="tel" placeholder="(99) 9-9999-9999"
                                @input="seller.support_phone = formatPhone($event.target.value)"
                                v-model="seller.support_phone">

                            <label class="mt-1">Company Logo</label>
                            <div class="seller-logo-upload-wrapper" @click="triggerSellerLogo">
                                <div class="seller-logo-preview" :class="{ 'has-image': fileSellerPreview }">
                                    <img v-if="fileSellerPreview" :src="fileSellerPreview" alt="Store Logo Preview">
                                    <div v-else class="upload-placeholder">
                                        <fa icon="upload" class="upload-icon-large" />
                                        <span>Click to upload logo</span>
                                        <small>JPG, PNG or WEBP (Max 5MB)</small>
                                    </div>

                                    <div class="upload-overlay" v-if="fileSellerPreview">
                                        <fa icon="camera" />
                                        <span>Change Logo</span>
                                    </div>
                                </div>
                            </div>
                            <input type="file" accept="image/png, image/jpeg, image/webp" hidden ref="fileSeller"
                                @change="(e) => handleFileUpload(e, 'seller')">
                        </div>

                    </div>

                    <div class="action-buttons">
                        <button class="btn-cancel" @click="editView = ''">Cancel</button>
                        <button class="btn-save" @click="submitChange">Save Changes</button>
                    </div>
                </div>
            </div>
            <div v-else class="editing-card">
                <div class="input-group">
                    <label>Your Current username:</label>
                    <input type="email" disabled :value="userData.username">
                </div>
                <div class="input-group">
                    <label>Your Current email:</label>
                    <input type="email" disabled :value="userData.email">
                </div>
            </div>
        </div>
    </div>
    <p v-else>loading...</p>
</template>

<style scoped>
.profile-wrapper {
    width: 100%;
    display: flex;
    flex-direction: column;
    background-color: #f8f9fa;
    min-height: 100vh;
}

.perfil-images {
    width: 100%;
}

.user-banner {
    width: 100%;
    height: 280px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-color: #1e4770;
    background-size: cover;
    background-position: center;
    position: relative;
    gap: 0.5rem;
}

.perfil-image-container {
    position: relative;
    margin-top: 2rem;
}

.perfil-image {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    object-fit: cover;
    border: 4px solid #fff;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

.upload-icon-wrapper {
    position: absolute;
    bottom: 5px;
    right: 5px;
}

.upload-icon {
    font-size: 1.2rem;
    color: #fff;
    background-color: #367cc1;
    padding: 0.6rem;
    border-radius: 50%;
    cursor: pointer;
    border: 2px solid #fff;
    transition: all 0.3s ease;
}

.upload-icon:hover {
    background-color: #1e4770;
    transform: scale(1.05);
}

.username {
    color: #fff;
    font-size: 1.5rem;
    font-weight: bold;
    text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.5);
}

.edit-banner {
    position: absolute;
    right: 1.5rem;
    top: 1.5rem;
    padding: 0.5rem 1rem;
    font-weight: 600;
    background-color: rgba(0, 0, 0, 0.4);
    font-size: 0.85rem;
    border: 1px solid rgba(255, 255, 255, 0.5);
    color: #fff;
    transition: 0.3s;
    border-radius: 6px;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.edit-banner:hover {
    cursor: pointer;
    background-color: #fff;
    color: #1e4770;
}

.edit-personalinfo-container {
    display: grid;
    grid-template-columns: 250px 1fr;
    gap: 3rem;
    padding: 3rem;
    max-width: 1200px;
    margin: 0 auto;
    width: 100%;
}

.container-personalinfo-button {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.container-personalinfo-button button {
    width: 100%;
    padding: 1rem;
    cursor: pointer;
    background-color: transparent;
    color: #3b7bb9;
    font-weight: 600;
    border: 2px solid #3b7bb9;
    border-radius: 8px;
    transition: all 0.3s ease;
    text-align: left;
    font-size: 1rem;
}

.container-personalinfo-button button:hover {
    background-color: #f0f6fc;
}

.container-personalinfo-button button.active {
    background-color: #3b7bb9;
    color: #fff;
    box-shadow: 0 4px 12px rgba(59, 123, 185, 0.3);
}

.container-editing {
    display: flex;
    flex-direction: column;
    animation: fadeIn 0.4s ease-in-out;
}

.editing-card {
    background-color: #fff;
    padding: 2.5rem;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
    border: 1px solid #eaeaea;
}

.editing-title {
    margin-bottom: 1.5rem;
    color: #1e4770;
    font-size: 1.4rem;
    border-bottom: 2px solid #f0f0f0;
    padding-bottom: 0.5rem;
}

.input-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    margin-bottom: 1.5rem;
}

.input-group label {
    font-weight: 600;
    color: #4a5568;
    font-size: 0.9rem;
}

.mt-1 {
    margin-top: 1rem;
}

.input-group input {
    padding: 0.8rem 1rem;
    width: 100%;
    max-width: 400px;
    border-radius: 8px;
    border: 1px solid #cbd5e0;
    font-size: 1rem;
    transition: border 0.3s;
}

.input-group input:focus {
    outline: none;
    border-color: #3b7bb9;
    box-shadow: 0 0 0 3px rgba(59, 123, 185, 0.2);
}

.action-buttons {
    display: flex;
    gap: 1rem;
    margin-top: 2rem;
}

.btn-cancel {
    padding: 0.8rem 1.5rem;
    background-color: transparent;
    border: none;
    color: #718096;
    font-weight: 600;
    cursor: pointer;
    transition: 0.3s;
}

.btn-cancel:hover {
    color: #e53e3e;
}

.btn-save {
    padding: 0.8rem 1.5rem;
    background-color: #1e4770;
    border: none;
    color: #fff;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
}

.btn-save:hover {
    background-color: #153250;
    transform: translateY(-2px);
}

input[type="file"]::file-selector-button {}

input[type="file"]::file-selector-button:hover {
    background-color: #81ecec;
}

.seller-type {
    padding: 1rem;
    display: flex;
    width: 5rem;
    gap: 0.5rem;
    font-size: 0.8rem;
}


.input-seller {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.seller-logo-upload-wrapper {
    margin-top: 0.5rem;
    width: 100%;
    max-width: 400px;
    display: flex;
    justify-content: flex-start;
}

.seller-logo-preview {
    position: relative;
    width: 150px;
    height: 150px;
    border: 2px dashed #cbd5e0;
    border-radius: 12px;
    background-color: #f8fafc;
    cursor: pointer;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
}

.seller-logo-preview:hover {
    border-color: #3b7bb9;
    background-color: #f0f6fc;
}

.seller-logo-preview.has-image {
    border: 2px solid #e2e8f0;
}

.seller-logo-preview img {
    width: 100%;
    height: 100%;
    object-fit: cover; 
}

.upload-placeholder {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
    color: #718096;
    text-align: center;
    padding: 1rem;
}

.upload-icon-large {
    font-size: 2rem;
    color: #a0aec0;
    margin-bottom: 0.2rem;
}

.upload-placeholder span {
    font-size: 0.9rem;
    font-weight: 600;
}

.upload-placeholder small {
    font-size: 0.75rem;
    color: #a0aec0;
}

.upload-overlay {
    position: absolute;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: white;
    opacity: 0;
    transition: opacity 0.3s ease;
    gap: 0.5rem;
}

.seller-logo-preview:hover .upload-overlay {
    opacity: 1;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@media (max-width: 768px) {
    .edit-personalinfo-container {
        grid-template-columns: 1fr;
        padding: 1.5rem;
        gap: 2rem;
    }

    .input-group input {
        max-width: 100%;
    }

    .container-personalinfo-button {
        flex-direction: row;
        overflow-x: auto;
        padding-bottom: 0.5rem;
    }

    .container-personalinfo-button button {
        text-align: center;
        white-space: nowrap;
    }
}
</style>