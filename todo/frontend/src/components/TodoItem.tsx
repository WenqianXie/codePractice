import React, { useState } from 'react';
import type { TodoInfo } from '../types/todo';
import styles from './TodoItem.module.css';
import { FaTrash, FaEdit } from 'react-icons/fa';

// 为了明确组件(TodoItem) 需要接收哪些props，每个props的类型是什么
interface TodoItemProps {
    todo: TodoInfo; 
    onToggleComplete: (id: string) => void;
    onDelete: (id: string) => void;
    onUpdate: (id: string, updatedTodo: {title: string; details?: string}) => void;
}

// export const TodoItem: 把组件导出，方便在其他文件中使用。
// React.FC 是 React Function Component 的缩写，说明这是一个函数组件；
// TodoItemProps 是这个组件接收的 props 类型
// ({...})从传进来的 props 中直接提取出这四个属性
export const TodoItem: React.FC<TodoItemProps> = ({
    todo,
    onToggleComplete,
    onDelete,
    onUpdate
}) => {

    const [isEditing, setIsEditing] = useState(false);
    const [editTitle, setEditTitle] = useState(todo.title);
    const [editDetails, setEditDetails] = useState(todo.details || "");

    const saveHandler = () =>{
        if (editTitle.trim()){
            onUpdate(todo.id, {title: editTitle, details: editDetails || ""});
            setIsEditing(false);
        }
    }

    if (isEditing) {
        return (
            <div className={styles.todoItem}>
                <div className={styles.editForm}>
                    <input
                        type="text"
                        value={editTitle}
                        onChange={(e) => setEditTitle(e.target.value)}
                        className={styles.editInput}
                    />
                    <input
                        type="text"
                        value={editDetails}
                        onChange={(e) => setEditDetails(e.target.value)}
                        className={styles.editInput}
                        placeholder='Optional'
                    />
                    <div>
                        <button onClick={saveHandler}>Save</button>
                        <button onClick={()=> setIsEditing(false)}>Cancel</button>
                    </div>
                </div>

            </div>
        )
    }

    return (
        <div className={styles.todoItem}>
            <input // <input> 是一个非常通用的标签，它可以变成很多种形式，取决于给它的 type 值
                type = "checkbox" // HTML 原生的写法,用来创建一个checkbox
                checked = {todo.completed} // 是否被勾选，取决于 todo 的 completed 状态
                onChange={() => onToggleComplete(todo.id)}
                className={styles.checkbox}
            />
            <div className={styles.content}>
                <h4 className={`${styles.title} ${todo.completed ? styles.completed : ""}`}> 
                    {/*如果任务已完成，额外加上 styles.completed*/}
                    {todo.title}
                </h4>
                {todo.details && (
                    <p className={`${styles.details} ${todo.completed ? styles.completed : ""}`}>
                        {todo.details}
                    </p>
                )
                }
                {/* <small> 是一个 HTML 标签，用来标记不太重要的辅助信息，比如时间、作者、提示说明等，让这部分文字变小、显得更轻。*/}
                <small className={styles.date}>
                    {new Date(todo.created_at).toLocaleString()} {/* 把这条待办事项的创建时间格式化为本地可读的日期字符串，并显示在页面上。*/}
                </small>
            </div>
            <div>
                <button 
                onClick={() => setIsEditing(true)}>
                    <FaEdit />
                </button>
                <button 
                    onClick={() => onDelete(todo.id)}
                    className={styles.deleteButton}
                    aria-label='delete todo item'
                >
                    <FaTrash />   
                </button>
            </div>

        </div>
    );

}

export default TodoItem