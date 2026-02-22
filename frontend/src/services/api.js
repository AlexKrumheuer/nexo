import axios from 'axios'

// creates basic axios object
const api = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 60000,
    headers: {
        'Content-Type': 'application/json'
    }
})

const publicRoutes = [
    '/api/products'
]


// create axios interceptions to look for a token into the localStorage
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')

    const isPublicRoute = publicRoutes.some(route => config.url.includes(route))
    if(token && !isPublicRoute) {
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
})

export default api