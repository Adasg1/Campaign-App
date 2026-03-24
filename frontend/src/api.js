import axios from 'axios';

export const api = axios.create({
    baseURL: 'https://campaign-app-xx6w.onrender.com/api'
});