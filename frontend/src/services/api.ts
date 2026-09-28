import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:9090/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message = error?.response?.data?.message || 'Request failed. Please try again.';
    return Promise.reject(new Error(message));
  }
);
