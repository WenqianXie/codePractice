from fastapi import APIRouter # 创建可复用的路由模块
# 一个项目中可能有非常多的路由，可以将每一类路由拆成独立模块
# 比如user_routes.py：负责用户注册、登录等，todo_routes.py：负责待办事项的增删改查，admin_routes.py：管理员相关接口
from fastapi import HTTPException # 用于返回错误响应（如404）
from fastapi import status # 提供HTTP状态码常量
from typing import List, Dict
from uuid import UUID
from app.models import TodoCreate, TodoInfo

router = APIRouter(
    prefix = "/todos", # 所有路径都以todos开头
    tags = ["todos"],  # 用于 FastAPI 自动生成文档时的分组标签（Swagger UI 里展示用）
    responses = {404: {"description":"Not Found"}} # 为这个路由模块中所有路径设置统一的响应信息（比如默认的 404 响应）
) 

todos_db : Dict[UUID, TodoInfo] = {} # 临时保存todo数据

'''
这里 response_model=List[TodoInfo] 的意思是：这个 GET 请求返回的是一个由 TodoInfo 类型组成的 列表。
response_model的作用：
1. Validation: 确保你返回的数据符合指定的 Pydantic 模型类型。比如如果返回的 title 是 None，但模型要求是 str，就会报错。
2. Conversion: 会把你返回的原始数据自动转换为 Pydantic 模型格式。比如你返回了一个 UUID 对象，它会被自动转换成 JSON 字符串。
3. Filtering: 如果你返回的字典中有模型中没定义的字段，这些字段会被自动剔除。
response_model 决定的是「接口最终返回给前端的结构」，而不是前端传什么
'''
@router.get("/", response_model = List[TodoInfo])
async def get_todos():
    return list(todos_db.values())

'''
接收一个 TodoCreate 实例（由请求体 JSON 自动解析），
用它构造一个 TodoInfo 实例（自动生成了 id 和 created_at），
把它存入内存数据库 todos_db，然后作为响应返回给前端。
'''
@router.post("/", response_model = TodoInfo, status_code = status.HTTP_201_CREATED)
async def create_todo(todo: TodoCreate):
    todo_info = TodoInfo(
        title = todo.title,
        details = todo.details,
        completed = todo.completed
    )
    todos_db[todo_info.id] = todo_info
    return todo_info

'''
第一行 {todo_id} 是路径参数名，
第二行 todo_id: UUID 是函数的参数声明，FastAPI 会自动从路径中提取字符串并转换成 UUID 类型传入函数。
'''
@router.get("/{todo_id}", response_model = TodoInfo)
async def get_todo(todo_id: UUID):
    if todo_id not in todos_db:
        raise HTTPException(status_code=404, detail = "Todo not found")
    return todos_db[todo_id]

'''
用 todo_id 找到已有的 TodoInfo
用 TodoCreate 里的新数据更新它
返回更新后的 TodoInfo
'''
@router.put("/{todo_id}", response_model = TodoInfo)
async def update_todo(todo_id: UUID, todo: TodoCreate):
    if todo_id not in todos_db:
        raise HTTPException(status_code = 404, detail = "Todo not found")
    
    todo_info = todos_db[todo_id]
    todo_info.title = todo.title
    todo_info.details = todo.details
    todo_info.completed = todo.completed

    return todo_info

'''
DELETE /todos/{todo_id}：删除一个 todo。
返回状态码 204（成功但无内容）。
实际删除对应字典项
'''
@router.delete("/{todo_id}", status_code = status.HTTP_204_NO_CONTENT)
async def delete_todo(todo_id: UUID):
    if todo_id not in todos_db:
        raise HTTPException(status_code = 404, detail = "Todo not found")
    del todos_db[todo_id]
    return None
