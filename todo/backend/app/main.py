from fastapi import FastAPI # 创建 Web 应用的主类
from fastapi.middleware.cors import CORSMiddleware # 用于处理跨源请求
from app.routers import todos # 将todos路由包含到主应用中

app = FastAPI (
    title = "Todo API",
    description = "A mini to-do app to practice fastapi",
    version = "0.1.0"
)
# 创建了一个 FastAPI 实例 app，所有接口都注册在它上面

origins = [ 
    "http://localhost:5173"
]
# 定义允许的“前端来源”

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,          # 只允许上述 origins 访问
    allow_methods=["*"],            # 允许所有 HTTP 方法（GET、POST、PUT 等）
    allow_headers=["*"],            # 允许所有自定义请求头
)

app.include_router(todos.router)

@app.get("/")
def root():
    return {"Welcome to Todo App!"}