from typing import List, Optional
from uuid import UUID
from fastapi import HTTPException
from sqlalchemy.orm import Session
from app.repositories.todo_repository import TodoRepository
from app.schemas.todo import TodoCreate, TodoUpdate, TodoResponse

class TodoService:
    def __init__(self, db: Session):
        self.repository = TodoRepository(db)
    
    def get_all_todos(self) -> List[TodoResponse]:
        todos = self.repository.get_all()
        return [TodoResponse.from_orm(todo) for todo in todos]
    
    def get_todo_by_id(self, todo_id: UUID) -> TodoResponse:
        todo = self.repository.get_by_id(todo_id)
        if not todo:
            raise HTTPException(status_code=404, detail="Todo not found")
        return TodoResponse.from_orm(todo)
    
    def create_todo(self, todo_data: TodoCreate) -> TodoResponse:
        todo = self.repository.create(todo_data)
        return TodoResponse.from_orm(todo)
    
    def update_todo(self, todo_id: UUID, todo_data: TodoUpdate) -> TodoResponse:
        todo = self.repository.update(todo_id, todo_data)
        if not todo:
            raise HTTPException(status_code=404, detail="Todo not found")
        return TodoResponse.from_orm(todo)
    
    def delete_todo(self, todo_id: UUID) -> None:
        if not self.repository.delete(todo_id):
            raise HTTPException(status_code=404, detail="Todo not found")