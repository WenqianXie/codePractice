import axios from 'axios'; // 一个用于发送 HTTP 请求的库，常用于与 REST API 通信。
import type { TodoInfo, TodoCreate } from '../types/todo';

const API_URL = 'http://localhost:8001/api'; // fastapi 后端地址

export const todoService = {
    // async 说明是一个异步函数，会返回Promise，Promise<TodoInfo[]> 表示将来会返回一个包含TodoInfo Objects的list
    async getAll(): Promise<TodoInfo[]> {
        const response = await axios.get<TodoInfo[]>(`${API_URL}/todos`);
        return response.data
    },
    async getById(id: string): Promise<TodoInfo> {
        const response = await axios.get<TodoInfo>(`${API_URL}/todos/${id}`);
        return response.data
    },
    async create(todo: TodoCreate): Promise<TodoInfo> {
        // axios.post<ResponseType>(url, data, config?)
        // axios.post<你想要返回的类型>(url, 要发送的数据, 可选配置)
        const response = await axios.post<TodoInfo>(`${API_URL}/todos`, todo);
        return response.data
    },
    async update(id: string, todo: TodoCreate): Promise<TodoInfo> {
        const response = await axios.put<TodoInfo>(`${API_URL}/todos/${id}`, todo);
        return response.data
    },
    async delete(id: string): Promise<void> {
        await axios.delete(`${API_URL}/todos/${id}`); // 不需要 <TodoInfo> 泛型，因为不关心返回值
    }

}



