<template>
    <div class="perfil-images">
        <div class="user-banner">
            <div class="perfil-image-container">
                <img class="perfil-image" src="../../../public//img/default-user-pic.png" alt="Profile Picture Default Png@nicepng.com">
                <fa class="upload-icon" icon="upload"/>
            </div>
            <p class="username">Nome</p>
            <button class="edit-banner">Edit Banner</button>
        </div>
    </div>
    <div>
        
    </div>

</template>
<style scoped>
    .perfil-images {
        width: 100%;
    }

    .user-banner {
        width: 100%;
        max-height: 30vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        background-image: url("../../../public/img/default-user-banner.png");
        padding: 5rem;
        gap: 1rem;
        position: relative;
    }

    .perfil-image {
        width: 10rem;
        border-radius: 50%;
    }


    .perfil-image-container {
        position: relative;
    }

    .upload-icon {
        position: absolute;
        font-size: 1.5rem;
        color: #fff;
        background-color: #367cc1;
        border: 1px solid transparent;
        padding: 0.5rem;
        border-radius: 50%;
        bottom: 0;
        cursor: pointer;
        right: 0;
        transition: 0.25s;
    }

    .upload-icon:hover {
        color: #367cc1;
        background-color: #fff;
        border: 1px solid #fff;
    }

    .username {
        color: #fff;
        font-size: 1.5rem;
    }

    .edit-banner {
        position: absolute;
        right: 0;
        top: 0;
        padding: 0.33rem 0.5rem;
        font-weight: bold;
        background-color: transparent;
        font-size: 0.8rem;
        border: 1px solid transparent;
        color: #fff;
        transition: 0.25s;
        border-radius: 8px;
        margin: 1rem;
    }

    .edit-banner:hover {
        cursor: pointer;
        color: #1e4770;
        background-color: #fff;
    }

</style>