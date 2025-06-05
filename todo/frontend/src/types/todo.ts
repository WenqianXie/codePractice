// 对应后端
export interface TodoBase{
    title: string;
    details?: string;
    completed: boolean;
}

export interface TodoCreate extends TodoBase{}

export interface TodoInfo extends TodoBase {
    id: string; // UUID 以字符串形式表示
    created_at: string;  // 日期时间以 ISO 字符串形式表示
}