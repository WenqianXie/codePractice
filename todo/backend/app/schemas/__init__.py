from pydantic import BaseModel, Field
from typing import Optional
from datetime import datetime
from uuid import UUID

# Base schema with common fields
class TodoBase(BaseModel):
    title: str = Field(..., min_length=1, max_length=100)
    details: Optional[str] = Field(None, max_length=200)
    completed: bool = Field(False)

# Schema for creating a new todo
class TodoCreate(TodoBase):
    pass

# Schema for updating a todo
class TodoUpdate(BaseModel):
    title: Optional[str] = Field(None, min_length=1, max_length=100)
    details: Optional[str] = Field(None, max_length=200)
    completed: Optional[bool] = None

# Schema for API responses
class TodoResponse(TodoBase):
    id: UUID
    created_at: datetime
    
    class Config:
        from_attributes = True