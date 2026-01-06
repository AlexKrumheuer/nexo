import axios from 'axios'

// creates basic axios object
const api = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})


// create axios interceptions to look for a token into the localStorage
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')

    if(token) {
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
})

export default api