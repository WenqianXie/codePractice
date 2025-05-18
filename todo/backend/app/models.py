from pydantic import BaseModel #所有数据模型的基类
from pydantic import Field #用于给模型的字段添加额外信息，比如默认值，min/max length，example
from typing import Optional #表示这个字段是可有可无的
from datetime import datetime
from uuid import UUID # 一个通用唯一标识符类型(Universally Unique Identifier),用来做数据库主键或者ID避免冲突
from uuid import uuid4 # 生成一个随机的 UUID（v4）

class TodoBase(BaseModel): # 公共字段定义，所有模型都继承它
    title : str = Field(..., min_length = 1, max_length = 100, description = "Todo Title", example = "Buy Milk")
    # Field(...) 没有默认值 → 必填字段
    details : Optional[str] = Field(None, max_length = 200, description = "Todo Details", example = "Buy 2% Milk")
    completed : bool = Field(False, description = "Completed?")

class TodoCreate(TodoBase): # 请求体，只在输入时需要
    pass
    # pass代表什么也不做，只是继承，这里留着给一些只在create的时候做的事情，不影响base，留着为了未来的开发

class TodoInfo(TodoBase): # 响应体，只在输出时需要
    # 用来响应
    id: UUID = Field(default_factory = uuid4) 
    created_at : datetime = Field(default_factory = datetime.now)
    # 后端自动生成，出现在返回数据中

    class Config:
        from_attributes = True

