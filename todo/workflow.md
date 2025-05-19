一、后端开发流程 (FastAPI)

1. 项目设置
   1.1 结构：
   backend/
   ├── app/
   │ ├── **init**.py
   │ ├── main.py
   │ ├── models.py
   │ └── routers/
   │ ├── **init**.py
   │ └── todos.py
   ├── requirements.txt
   └── venv/

   1.2 配置虚拟环境
   python -m venv venv
   source venv/bin/activate # Linux/Mac

   1.3 安装依赖
   pip install fastapi[standard] uvicorn
   pip freeze > requirements.txt

2. 数据模型设计
   2.1 设计三层模型结构 (在 models.py 中)

   - 基础模型 (TodoBase): 包含共享字段，遵循单一责任原则
   - 创建模型 (TodoCreate): 用于创建新资源，继承基础模型
   - 响应模型 (TodoInfo): 用于 API 响应，包含系统生成的字段，继承基础模型

3. API 路由设计
   3.1 创建路由模块 (在 routers/todos.py 中)
   3.2 实现 CRUD 操作

   - 创建 (POST /todos/): 接收 TodoCreate，返回 TodoInfo
   - 读取 (GET /todos/ 和 GET /todos/{todo_id}): 返回 TodoInfo 或列表
   - 更新 (PUT /todos/{todo_id}): 接收 TodoCreate，返回更新后的 TodoInfo
   - 删除 (DELETE /todos/{todo_id}): 删除资源，无返回内容

4. 应用主入口配置
   创建 FastAPI 应用 (在 main.py 中)

5. 启动后端服务器
   uvicorn app.main:app --reload
   访问 http://localhost:8000/docs 可以测试 API

二、前端开发流程 (React + TypeScript)

1. 项目设置
   1.1 创建前端项目
   npm create vite@latest frontend -- --template react-ts
   cd frontend
   npm install
   npm install axios react-icons

   1.2 项目结构
   frontend/
   ├── src/
   │ ├── components/
   │ │ ├── TodoItem.tsx
   │ │ ├── TodoList.tsx
   │ │ ├── TodoList.module.css
   │ │ └── TodoItem.module.css
   │ ├── types/
   │ │ └── todo.ts
   │ ├── services/
   │ │ └── todoService.ts
   │ ├── App.tsx
   │ └── main.tsx
   └── package.json

2. 前端类型定义
   创建与后端匹配的类型 (在 types/todo.ts 中)

3. API 服务设计
   创建 API 服务模块 (在 services/todoService.ts 中)

4. 组件设计与实现
   4.1 设计单项组件 (TodoItem.tsx)

   - 负责展示单个待办事项
   - 管理自己的编辑状态
   - 通过回调函数通知父组件进行 API 操作
     4.2 设计列表组件 (TodoList.tsx)

   - 管理整个待办事项列表的状态
   - 负责与 API 交互
   - 包含添加新待办事项的表单
   - 渲染多个 TodoItem 组件

5. App 组件集成 (App.tsx)

6. 启动前端应用
   npm run dev
   访问 http://localhost:5173

三、全栈应用开发核心原则

    1. 分层设计

    后端三层模型:
    - 基础模型 (TodoBase): 共享字段的基类
    - 创建模型 (TodoCreate): 用于创建请求的数据结构
    - 响应模型 (TodoInfo): 包含系统生成字段的完整模型

    前端组件分层:
    - 单项组件 (TodoItem): 负责单个待办事项的显示和用户交互
    - 列表组件 (TodoList): 负责数据管理和整体逻辑
    - 服务层 (todoService): 封装 API 调用

    2. 类型驱动开发

    前后端类型一致:
    - 后端使用 Pydantic 模型
    - 前端使用 TypeScript 接口
    - 保持命名和结构一致性

    类型安全的 API 交互:
    - 明确定义请求和响应类型
    - 使用泛型增强类型安全性 (axios.get<TodoInfo>)

    3. 响应式状态管理

    React 状态设计:
    - 使用 useState 管理组件状态
    - 使用 useEffect 处理副作用（API 调用）
    - 父组件管理全局状态，通过 props 传递给子组件

    状态拆分原则:
    - 全局状态放在容器组件 (TodoList)
    - 局部状态放在展示组件 (TodoItem 的编辑状态)

    4. 组件通信模式

    向下传递数据:
    - 父组件通过 props 向子组件传递数据
    - 子组件通过类型化的 props 接收数据

    向上传递事件:
    - 子组件通过回调函数通知父组件
    - 父组件实现回调函数，处理状态更新和 API 调用

    父组件是数据的源头，通过 props 把数据给子组件；
    子组件是行为的触发者，通过调用父组件提供的回调函数，来“请求”状态更新。
    这就是所谓的单向数据流：数据向下走，事件向上传。
