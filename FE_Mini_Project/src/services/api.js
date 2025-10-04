import axios from 'axios'
import { te } from 'date-fns/locale';
const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080'
const client = axios.create({ baseURL: API_BASE, headers: { 'Content-Type': 'application/json' } })
client.interceptors.request.use(cfg => { const token = localStorage.getItem('token'); if (token) cfg.headers.Authorization = `Bearer ${token}`; return cfg })
export default {
  login: (data) => client.post('/auth/token', data),
  introspect: (data) => client.post('/auth/introspect', data),

  getBooks: () => client.get('/book'),
  getBookById: (id) => client.get(`/book/id/${id}`),
  getBookByTitle: (title) => client.get(`/book/title/${title}`),
  createBook: (data) => client.post('/book', data),
  updateBook: (id, data) => client.put(`/book/${id}`, data),
  deleteBook: (id) => client.delete(`/book/${id}`),

  getRecords: () => client.get('/record'),
  getRecord: (id) => client.get(`/record/${id}`),
  createRecord: (data) => client.post('/record', data),
  updateRecord: (id, data) => client.put(`/record/${id}`, data),
  deleteRecord: (id) => client.delete(`/record/${id}`),
  getRecordsByUser: (userId) => client.get(`/record/user/${userId}`),
  getRecordsByBook: (bookId) => client.get(`/record/book/${bookId}`),

  getNotifications: () => client.get('/notification'),
  getNotification: (id) => client.get(`/notification/${id}`),
  getNotificationsByUser: (userId) => client.get(`/notification/user/${userId}`),
  createNotification: (data) => client.post('/notification', data),
  updateNotification: (id, data) => client.put(`/notification/${id}`, data),
  deleteNotification: (id) => client.delete(`/notification/${id}`),
 
  
  getUsers: () => client.get('/user'),
  getInfo : () => client.get('/user/myInfo'),
  getUserById: (id) => client.get(`/user/id/${id}`),
  getUserByUsername: (username) => client.get(`/user/username/${username}`),
  createUser: (data) => client.post('/user', data),
  updateUser: (id, data) => client.put(`/user/${id}`, data),
  deleteUser: (id) => client.delete(`/user/${id}`),
}