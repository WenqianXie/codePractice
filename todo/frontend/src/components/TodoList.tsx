import React, { useState, useEffect } from 'react';
import { TodoItem } from './TodoItem'; // 引入写好的单个待办事项组件（用来展示每个 todo）
import type { TodoInfo, TodoCreate } from '../types/todo';
import { todoService } from '../services/todoService';
import styles from './TodoList.module.css';


// 定义了一个名叫 TodoList 的 函数组件，用 React.FC（FunctionComponent）标注其类型
// 不接受任何 props
export const TodoList: React.FC = () => {
    const [todos, setTodos] = useState<TodoInfo[]>([]);
    // 收集用户输入：用于创建全新的待办事项, TodoItem中的edit是编辑现有的待办事项
    const [newTitle, setNewTitle] = useState('');
    const [newDetails, setNewDetails] = useState('');
    const [error, setError] = useState<string | null>(null);

    useEffect(()=>{
        const fetchTodos = async() =>{
            try {
                const data = await todoService.getAll();
                setTodos(data);
                setError(null);
            } catch (error) {
                console.log("Failed to fetch todos: ", error);
                setError("Failed to load data. Please try again later.");
            }
        }
        fetchTodos();
    }, [])

    const addTodoHandler = async (e: React.FormEvent) =>{
        e.preventDefault(); 
        // 必须调用 preventDefault() 来阻止浏览器刷新，由 React 接管这个行为, 不然页面会整个刷新
        if (!newTitle.trim()) return;
        
        // 创建一个符合 TodoCreate 类型的对象
        const newTodoData: TodoCreate = {
            title: newTitle.trim(),
            details: newDetails.trim() || undefined,
            completed: false
        }
        try{
            const createdTodo = await todoService.create(newTodoData);
            setTodos([...todos, createdTodo]);
            setNewTitle('');
            setNewDetails('');
            console.log("Added new todo:", createdTodo);
        } catch (error){
            console.error("Failed to create todo:", error);
        }
    }

    const toggleCompleteHandler = async (id: string) => {
        const todoUpdate = todos.find(todo => todo.id === id);
        if (!todoUpdate) return;

        const updatedData : TodoCreate = {
            title: todoUpdate.title,
            details: todoUpdate.details,
            completed: !todoUpdate.completed
        }
        try{
            const updatedTodo = await todoService.update(id, updatedData);
            setTodos(todos.map(todo => 
                todo.id === id ? updatedTodo : todo
            ))
        } catch (error) {
            console.error("Failed to toggle todo completion:", error);
            setError("Failed to update todo status. Please try again.");
        }
    }

    const deleteHandler = async (id: string) => {
        try{
            await todoService.delete(id);
            setTodos(todos.filter(todo => todo.id !== id));
        } catch (error) {
            console.error("Failed to delete todo:", error);
            setError("Failed to delete todo. Please try again.");
        }
    }

    const updateHandler = async (id: string, updatedField: {title: string; details?: string}) => {
        try{
            const todoToUpdate = todos.find(todo => todo.id === id);
            if (!todoToUpdate) return;

            const updatedData: TodoCreate = {
                ...updatedField,
                completed: todoToUpdate.completed
            }

            const updatedTodo = await todoService.update(id, updatedData)
            setTodos(todos.map(todo => 
                todo.id === id ? updatedTodo : todo
            ))
        } catch (error) {
            console.error("Failed to update todo:", error);
            setError("Failed to update todo. Please try again.");
        }
    }
    
 
    return (
        <div className={styles.todoList}>
            <h1>Your Todo List</h1>
            {/*
            HTML 中 <form> 是一个表单容器，用来提交用户输入的数据
            htmlFor="title" 是为了让这个 label 控制 id="title" 的输入框 
            这样用户点击文字也能聚焦输入框，既方便又提升可访问性，是 HTML 表单的最佳实践之一 
            */}
            <form
                onSubmit={addTodoHandler}
                className={styles.createForm}
            >
                <div className={styles.formGroup}>
                    <label htmlFor='title'>Title:</label>
                    <input
                        id="title"
                        type="text"
                        value={newTitle}
                        onChange={(e) => setNewTitle(e.target.value)}
                        placeholder='What needs to be done?'
                        className={styles.input}
                        required
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="details">Details (optional):</label>
                        <input
                            id="details"
                            type="text"
                            value={newDetails}
                            onChange={(e) => setNewDetails(e.target.value)}
                            placeholder="Add some details"
                            className={styles.input}
                        />
                </div>
                <button type="submit" className={styles.button}> 
                    {/* type="submit" 表示这个按钮是“提交表单”的按钮。点击它会触发 <form onSubmit={...}> 绑定的函数，这是表单交互的标准行为 */}
                    Add
                </button>
            </form>

            <div className={styles.todoItems}>
                {todos.length == 0 ? (
                    <p>You currently have nothing to do.</p>
                ): (
                    todos.map(todo => (
                        <TodoItem
                            key={todo.id} // key 就是一个 在“同一个列表里”必须唯一的标识符, 帮助 React 识别哪些元素被添加、修改或删除
                            todo={todo}
                            onToggleComplete={toggleCompleteHandler}
                            onDelete={deleteHandler}
                            onUpdate={updateHandler}
                        />
                    ))
                )}
            </div>
        </div>
    )
}

export default TodoList